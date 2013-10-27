package edu.upenn.yiranqin.scalabilityrelated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
//import java.util.PriorityQueue;
import java.util.Scanner;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.datastructures.MyArrayHeap;
import edu.upenn.yiranqin.datastructures.MyHeap;

public class FileBasedUtil{
	public static final String DELIMITER = "\\r|\\n";
	
	/**
	 * 
	 * @param file
	 * @param memoryLimit in MB
	 * @param useNIO whether use NIO to do the preprocessing
	 * @param forParallelSorting true to use parallel sorting
	 * 
	 *TODO Still some bugs exist in the NIO way of handling small files, 
	 * most likely the logic around the last few elements of each one still not quite right 
	 */
	
	public static void externalSort(File file, int memoryLimit, boolean useNIO, boolean forParallelSorting){
		if(!file.exists() || file.isDirectory())
			return;
		int fileSizeInMB = (int)file.length()/(1024 * 1024);
		boolean secondPass = false;
		int groups = fileSizeInMB/memoryLimit + 1 ;
		
		if(groups >= 50)
			secondPass = true;
		
		/* preprocessing */
		boolean inputNum = determineDataType(file); // true if the input is number, otherwise string
		
		File[] tmpFiles = preProcess(file, groups, memoryLimit * 1024 * 1024, inputNum, useNIO, forParallelSorting);
		if(forParallelSorting){
			for(File tmpFile : tmpFiles){
				SortingWorker worker = new SortingWorker(tmpFile, inputNum);
				worker.run();
			}
		}
		String outputPath = file.getParentFile() +"/" + file.getName() + "-sorted";
		
		if(inputNum){
			if(secondPass){
				int tmp = (int)Math.sqrt(groups);
				int tmpGroups = (groups % tmp == 0) ? tmp : tmp + 1;
				File[] intermediateFiles = new File[tmpGroups];
				String intermediateOutputDir = file.getParentFile() + "/tmp-" + file.getName();				
				
				for(int i = 0; i < tmpGroups; i++){
					File[] tmpInputFiles;
					if(i != tmpGroups - 1)
						tmpInputFiles = new File[tmp];
					else
						tmpInputFiles = new File[tmpFiles.length - i * tmp];
						
					for(int j = 0; j < tmpInputFiles.length; j++){
						tmpInputFiles[j] = tmpFiles[i * tmp + j];
					}
					
					String intermediateOutputPath = intermediateOutputDir + "/intermediate" + i;
					intermediateFiles[i] = multiwayMergeNum(tmpInputFiles, intermediateOutputPath, memoryLimit * 1024 * 1024, 0);
				}
				multiwayMergeNum(intermediateFiles, outputPath, memoryLimit * 1024 * 1024, 0);
			}else
				multiwayMergeNum(tmpFiles, outputPath, memoryLimit * 1024 * 1024, 0);
		}else{
			
		} 
		
	}
	
	public static boolean determineDataType(File file){
		BufferedReader reader = null;
		boolean inputNum = true;
		try{
			reader = new BufferedReader(new FileReader(file));
			String cur = reader.readLine();
			
			if(cur == null){
				System.exit(-1);
			}

			try{
				long curValue = Long.parseLong(cur);
			}catch(NumberFormatException  ex){
				inputNum = false;
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return inputNum;
	}
	
	/**
	 * preProcess the file and form groups tmp files, the memoryLimit is relaxed only means the useful data in memory 
	 * 
	 *  use parallel sorting then the preProcess will utilize the java NIO transferTo technique
	 *   so that a very large file will be divided into desired smaller pieces and use multiple threads to sort each of the smaller files
	 *   since there are no race condition
	 * 
	 * @param file
	 * @param groups how many tmp files will be created 
	 * @param memoryLimit in Bytes
	 * @param useNIO whether use NIO to do the preprocessing
	 * @param forParallelSorting true to use parallel sorting
	 */
	private static File[] preProcess(File file, int groups, int memoryLimit, boolean isNum, boolean useNIO, boolean forParallelSorting){
		FileInputStream input = null;
		FileReader inputIO = null;
		PrintWriter[] writers = new PrintWriter[groups];
		FileOutputStream[] outputs = new FileOutputStream[groups];
		File[] tmpFiles = new File[groups];
		for(int i = 0; i < groups; i++){
			writers[i] = null;
			tmpFiles[i] = null;
		}
//		writer = new PrintWriter(new FileWriter(file));
		try{
			File tmpDir = new File(file.getParentFile() + "/tmp-" + file.getName());
			if(!tmpDir.exists())
				tmpDir.mkdir();
			
			/**
			 * If using NIO, then data in file will be load directly into application buffer, 
			 * we could use less memcpy and eliminate the extra memory usage for copying buffers
			 * 
			 * However in this case, we will have to sort the data, and put it into list
			 * and the decoding(naive decoding render performance gain fairly small)
			 *  required for parsing(form a new long string from binary data) 
			 * plus the data buffer, we are actually at least three times the required memory usage
			 * for the application viewpoint
			 * 
			 * Also, need to take care of the border cases since the data should be intact and not fragmented
			 */
			if(useNIO){
				//TODO figure out a way to utilize transferTo() method to separate the whole large file into smaller ones and handle each one using threads
				//TODO currently the transferTo seems do nothing
				
				input = new FileInputStream( file.getName() );
				FileChannel channel = input.getChannel( );
				FileChannel outputChannel = null;
				ByteBuffer buffer = ByteBuffer.allocateDirect( 8 * 1024);
				byte[] barray = new byte[memoryLimit];
				boolean leftOver = false;
				int curSize = 0;
				int nInBuf = 0;
				long totalOffset = 0;
				
				for(int i = 0; i < groups; i++){
					tmpFiles[i] = new File(tmpDir.getAbsolutePath() + "/pre" +i);
					if(!tmpFiles[i].exists())
						tmpFiles[i].createNewFile();
					if(!forParallelSorting)
						writers[i] = new PrintWriter(new FileWriter(tmpFiles[i]));
					else{
						outputs[i] = new FileOutputStream( tmpFiles[i].getName() );
						outputChannel = outputs[i].getChannel();
					}
					
					int nRead = 0;
					nInBuf = 0;
					leftOver = false;
					while ( (nRead = channel.read( buffer )) != -1 )
					{
					    if ( nRead == 0 )
					        continue;
					    buffer.position( 0 );
					    buffer.limit( nRead );
					    while( buffer.hasRemaining())
					    {	
					    	nInBuf = buffer.remaining();
					    	/**
					    	 * As long as there is value in the buffer not mapped to application
					    	 * While adding it into the destination buffer will exceed memory limit
					    	 * take it as leftover and make it the first bytes for the next round 
					    	 */
					    	if(curSize + nInBuf >= memoryLimit){
					    		leftOver = true;
					    		break;
					    	}
					        buffer.get( barray, curSize, nInBuf);
					    	curSize += nInBuf;
					    }
					    if(leftOver)
					    	break;
					    
					    buffer.clear();
					}
//					System.out.println("curSize before overflow " + curSize);
					/**
					 * Find the last delimiter in the buffer and calculate the offset
					 * Always make the string end with delimiter and thus guarantee the data integrity
					 */
					int offset = findLastDelimiter(barray, curSize -1);
					curSize -= offset;
					
					if(!forParallelSorting){
						String value = new String(barray, 0, curSize, "US-ASCII");
						String[] values = value.split(DELIMITER);
						
						if(isNum){
							Long[] list = new Long[values.length]; 
							for(int index = 0; index < values.length; index++){
								list[index] = Long.parseLong(values[index]);
							}
							ArrayUtil.quickSortRandom(list, 0, list.length - 1);
							for(int index = 0; index < values.length; index++){
								writers[i].write(Long.toString(list[index]) + '\n');
							}
						}else{
							ArrayUtil.quickSortRandom(values, 0, values.length - 1);
							for(int index = 0; index < values.length; index++){
								writers[i].write(values[index] + '\n');
							}
						}
					}else{
						long amountTransfered = 0;
						while(amountTransfered < curSize){
							amountTransfered += channel.transferTo(totalOffset, curSize - amountTransfered, outputChannel);
							totalOffset += amountTransfered;
						}
					}
					
					moveBytes(barray, curSize, 0, offset);
					curSize = offset;
					while( buffer.hasRemaining())
				    {	
				    	nInBuf = buffer.remaining();
				        buffer.get( barray, curSize, nInBuf);
				    	curSize += nInBuf;
				    }
			    	buffer.clear();
//			    	System.out.println("curSize after filled remaining " + curSize);
				}//end for for loop for each tmp file
			}//end for using NIO
			else{
				inputIO = new FileReader( file.getName() );
				BufferedReader reader = new BufferedReader(inputIO);
				for(int i = 0; i < groups; i++){
					tmpFiles[i] = new File(tmpDir.getAbsolutePath() + "/pre" +i);
					if(!tmpFiles[i].exists())
						tmpFiles[i].createNewFile();
					writers[i] = new PrintWriter(new FileWriter(tmpFiles[i]));
					
					if(isNum){
						ArrayList<Long> list = new ArrayList<Long>(memoryLimit/8);
						String cur = null;
						while((cur = reader.readLine()) != null && list.size() <= memoryLimit/8){
							list.add(Long.parseLong(cur));
						}
						Collections.sort(list);
						for(long num : list){
							writers[i].write(num + "\n");
						}
					}else{
						LinkedList<String> list = new LinkedList<String>();						
						String cur = null;
						int byteCount = 0;
						while((cur = reader.readLine()) != null && byteCount <= memoryLimit){
							list.add(cur);
							byteCount += cur.length();
						}
						Collections.sort(list);
						for(String str : list){
							writers[i].write(str + "\n");
						}
					}
				}
				reader.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(input != null)
					input.close();
				for(PrintWriter writer : writers){
					if(writer != null)
						writer.close();
				}
				for(FileOutputStream output : outputs){
					if(output != null)
						output.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return tmpFiles;
	}
	
	private static int findLastDelimiter(byte[] array, int start){
		int offset = 0;
		while(start >= 0 && array[start] != '\n' && array[start] != '\r') {
			start--;
			offset++;
		}
		return offset;
	}
	
	/**
	 * Move len byte elements from srcStart to dstStart by overwriting
	 * @param array
	 * @param srcStart
	 * @param dstStart
	 * @param len
	 */
	public static void moveBytes(byte[] array, int srcStart, int dstStart, int len){
		if(len <= 0 || srcStart + len > array.length || dstStart + len > array.length 
				|| srcStart == dstStart){
			return;
		}
		for(int i = 0; i < len; i++){
			array[dstStart + i] = array[srcStart + i];
		}
	} 
	
	public static File multiwayMergeNum(File[] files, String outputPath, int memoryLimit,
			int seqDegree){
		if(files == null)
			return null;
		System.out.println("merging " + files.length + " to " +outputPath);
		
		int groups = files.length;
		
		ArrayList<LinkedList<Long>> list = new ArrayList<LinkedList<Long>>(groups);
		BufferedReader[] readers = new BufferedReader[groups];
		MyBitmap dataFlag = new MyBitmap(groups); //indicate whether is data in this file
		PrintWriter writer = null;
		File outputFile = null;
		
		for(int i = 0; i < groups; i++)
			readers[i] = null;
		
		try{
			for(int i = 0; i < groups; i++){
				readers[i] = new BufferedReader(new FileReader(files[i]));
				list.add(new LinkedList<Long>());
				dataFlag.setBit(i);
			}
			outputFile = new File(outputPath);
			if(!outputFile.exists())
				outputFile.createNewFile();
			writer = new PrintWriter(new FileWriter(outputFile));
			
			
//			PriorityQueue<Long> heap = new PriorityQueue<Long>();
//			/* Using map will not allow duplicate elements, 
//			 * better wrap the key and reference to a new class and form HashSet */
//			HashMap<Long, Integer> refMap = new HashMap<Long, Integer>();
			MyHeap<ObjectReference<Long>> heap = new MyHeap<ObjectReference<Long>>(true);
			
			String curStr = null;
			ObjectReference<Long> curObj;
			Long curValue;
			int listIndex = 0;
			for(int i = 0; i < groups; i++){
				curValue = Long.parseLong(readers[i].readLine());
//				heap.add(curValue);
//				refMap.put(curValue, i);
				heap.insert(new ObjectReference<Long>(curValue, i));
			}
			
			while(true){//(!dataFlag.isAllClear())
//				curValue = heap.peek();
//				listIndex = refMap.get(curValue);
//				System.out.println(curValue + " " + heap.size());
//				heap.remove();
//				refMap.remove(curValue);
//				writer.write(curValue + "\n");
//				System.out.println(curStr + " " + listIndex);

				curObj = heap.peek();
				listIndex = curObj.listReference;
				heap.delete();
				writer.write(curObj.value + "\n");
//				System.out.println(curObj.value + " " + heap.size() + " " + listIndex);
				curStr = readers[listIndex].readLine();
				if(curStr == null){
					dataFlag.clearBit(listIndex);
					if(heap.size() > 0)
						continue;					
					else{
						listIndex = dataFlag.getFirstSetBit();
						/* if all empty */
						if(listIndex == -1)
							break;
						curStr = readers[listIndex].readLine();
					}
				}				
				curValue = Long.parseLong(curStr);
//				heap.add(curValue);
//				refMap.put(curValue, listIndex);
				heap.insert(new ObjectReference<Long>(curValue, listIndex));
			}
			//TODO
			//read in chunk memory but still merge with files.length heap
			
			
			//read in chunk memory and also build up large heap, write chunk into disk
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(writer != null)
					writer.close();
				for(BufferedReader reader : readers){
					if(reader != null)
						reader.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		return outputFile;
	}
	
	private static class ObjectReference <T extends Comparable<? super T>> implements Comparable<ObjectReference<T>>{
		public T value;
		public int listReference = 0;
		public ObjectReference(T value, int listReference){
			this.value = value;
			this.listReference = listReference;
		}
		@Override
		public int compareTo(ObjectReference<T> arg0) {
			return value.compareTo( arg0.value );
		}
	}
	
	private static class SortingWorker implements Runnable{
		public File file;
		public boolean isNum;
		
		public SortingWorker(File file, boolean isNum){
			this.file = file;
			this.isNum = isNum;
		}
		
		@Override
		public void run() {
			sortSmallFile(file, isNum, false);
		}		
	}
	
	/**
	 * Really bad idea to compare two long number as string
	 */
	private static class longStrComparator implements Comparator<String>{
		@Override
		public int compare(String s1, String s2) {
			if(s1.length() > s2.length())
				return 1;
			else if(s1.length() < s2.length())
				return -1;
			else{
				for(int i = 0; i < s1.length(); i++){
					if(s1.charAt(i) > s2.charAt(i))
						return 1;
					else if(s1.charAt(i) < s2.charAt(i))
						return -1;
				}
				return 0;
			}
		}
	}
	
	/**
	 * Sort small file(no more than 100M) using NIO approach, int could reach up to 2G, so should be enough
	 * @param file
	 * @param isNum is number or string
	 */
	public static void sortSmallFileNIO(File file, boolean isNum, boolean useScanner){
		if(!file.exists() || file.isDirectory())
			return;
		int fileSizeInByte = (int)file.length();
		
		/**
		 * Directly retrieve data from file to application buffer
		 */
		FileInputStream input = null;
		Scanner in = null;
		try{
			input = new FileInputStream( file.getName() );
			FileChannel channel = input.getChannel( );
			ByteBuffer buffer = ByteBuffer.allocateDirect( 8 * 1024);
			byte[] barray = new byte[fileSizeInByte];
			int nRead = 0;
			int curStart = 0;;
			int nInBuf = 0;
			while ( (nRead = channel.read( buffer )) != -1 )
			{
			    if ( nRead == 0 )
			        continue;
			    buffer.position( 0 );
			    buffer.limit( nRead );
			    while( buffer.hasRemaining( ) )
			    {	
			    	nInBuf = buffer.remaining(); 
			        buffer.get( barray, curStart, nInBuf);
			        curStart += nInBuf;
			    }
			    buffer.clear( );
			}
		
			String value = new String(barray, 0, fileSizeInByte, "US-ASCII");
			if(useScanner){
				in = new Scanner(value).useDelimiter("\\r|\\n");
				if(isNum){
					ArrayList<Integer> list = new ArrayList<Integer>(fileSizeInByte/8);
					while(in.hasNextInt()){
						list.add(in.nextInt());
					}
					Collections.sort(list);
					int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
					ArrayUtil.printSubArray(list, 0, printOutSize);
				}else{
					ArrayList<String> list = new ArrayList<String>(fileSizeInByte/8);
					while(in.hasNext()){
						list.add(in.next());
					}
					Collections.sort(list);
					int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
					ArrayUtil.printSubArray(list, 0, printOutSize);
				}
			}else{
				String[] values = value.split("\\r|\\n");
				if(isNum){
					/**
					 * Using comparator we will be able to save some space
					 *  but the comparison is quite costly comparing to native long 
					 */
//					ArrayUtil.quickSortRandom(values, 0, values.length - 1, new longStrComparator());
					int[] list = new int[values.length]; 
					for(int i = 0; i < values.length; i++){
						list[i] = Integer.parseInt(values[i]);
					}
					ArrayUtil.quickSortRandom(list, 0, list.length - 1);
					int printOutSize = list.length/100 > 1000 ? 1000 : list.length/100;
					ArrayUtil.printSubArray(list, 0, printOutSize);
				}else{
					ArrayUtil.quickSortRandom(values, 0, values.length - 1);
					int printOutSize = values.length/100 > 1000 ? 1000 : values.length/100;
					ArrayUtil.printSubArray(values, 0, printOutSize);
				}
//				int printOutSize = values.length/100 > 1000 ? 1000 : values.length/100;
//				ArrayUtil.printSubArray(values, 0, printOutSize);	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(input != null)
					input.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			if(in != null)
				in.close();
		}
	}
	
	/**
	 * Sort small file(no more than 100M) using regular bufferedReader approach, int could reach up to 2G, so should be enough
	 * @param file
	 * @param isNum is number or string
	 */
	public static void sortSmallFile(File file, boolean isNum, boolean useScanner){
		if(!file.exists() || file.isDirectory())
			return;
		int fileSizeInByte = (int)file.length();
		
		Scanner in = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			writer = new PrintWriter(new FileWriter(file));
			if(useScanner)
				in = new Scanner(reader).useDelimiter("\\r|\\n");
			
			if(isNum){
				ArrayList<Integer> list = new ArrayList<Integer>(fileSizeInByte/8);
				if(useScanner){
					while(in.hasNextInt()){
						list.add(in.nextInt());
					}
				}else{
					String cur = null;
					while((cur = reader.readLine()) != null){
						list.add(Integer.parseInt(cur));
					}
				}
				Collections.sort(list);
//				int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
//				ArrayUtil.printSubArray(list, 0, printOutSize);
				for(Integer i : list){
					writer.write(i + "\n");
				}
			}else{
				ArrayList<String> list = new ArrayList<String>(fileSizeInByte/8);
				if(useScanner){
					while(in.hasNext()){
						list.add(in.next());
					}
				}else{
					String cur = null;
					while((cur = reader.readLine()) != null){
						list.add(cur);
					}
				}
				Collections.sort(list);
//				int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
//				ArrayUtil.printSubArray(list, 0, printOutSize);
				for(String i : list){
					writer.write(i + "\n");
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(in != null)
				in.close();
			try{
				if(reader != null)
					reader.close();
				if(writer != null)
					writer.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
}
