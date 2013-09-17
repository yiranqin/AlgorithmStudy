package edu.upenn.yiranqin.scalabilityrelated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.datastructures.MyArrayHeap;

public class FileBasedUtil{
	public static final String DELIMITER = "\\r|\\n";
	
	/**
	 * 
	 * @param file
	 * @param memoryLimit in MB
	 */
	
	public static void externalSort(File file, int memoryLimit){
		if(!file.exists() || file.isDirectory())
			return;
		int fileSizeInMB = (int)file.length()/(1024 * 1024);
		boolean secondPass = false;
		int groups = fileSizeInMB/memoryLimit + 1 ;
		
		if(groups >= 100)
			secondPass = true;
		
		/* preprocessing */
		boolean inputNum = determineDataType(file); // true if the input is number, otherwise string
		
		File[] tmpFiles = preProcess(file, groups, memoryLimit * 1024 * 1024, inputNum, false);
		String outputPath = file.getParentFile() + file.getName() + "-sorted";
		
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
	 * @param file
	 * @param groups how many tmp files will be created 
	 * @param memoryLimit in Bytes
	 */
	private static File[] preProcess(File file, int groups, int memoryLimit, boolean isNum, boolean useNIO){
		FileInputStream input = null;
		PrintWriter[] writers = new PrintWriter[groups];
		File[] tmpFiles = new File[groups];
		for(int i = 0; i < groups; i++){
			writers[i] = null;
			tmpFiles[i] = null;
		}
//		writer = new PrintWriter(new FileWriter(file));
		try{
			input = new FileInputStream( file.getName() );
			FileChannel channel = input.getChannel( );
			ByteBuffer buffer = ByteBuffer.allocateDirect( 8 * 1024);
			byte[] barray = new byte[memoryLimit];
			
			File tmpDir = new File(file.getParentFile() + "/tmp-" + file.getName());
			if(!tmpDir.exists())
				tmpDir.mkdir();
			
			boolean leftOver = false;
			int curSize = 0;
			int nInBuf = 0;
			for(int i = 0; i < groups; i++){
				tmpFiles[i] = new File(tmpDir.getAbsolutePath() + "/pre" +i);
				if(!tmpFiles[i].exists())
					tmpFiles[i].createNewFile();
				writers[i] = new PrintWriter(new FileWriter(tmpFiles[i]));
				
				int nRead = 0;
				nInBuf = 0; 
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
				    	if(curSize + nInBuf > memoryLimit){
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
				
				if(curSize == memoryLimit)
					leftOver = false;
				/**
				 * Find the last delimiter in the buffer and calculate the offset
				 * Always make the string end with delimiter and thus guarantee the data integrity
				 */
				int offset = findLastDelimiter(barray, curSize - 1);
				curSize -= offset;
				String value = new String(barray, 0, curSize, "US-ASCII");
				String[] values = value.split(DELIMITER);
				
				if(isNum){
					Long[] list = new Long[values.length]; 
					for(int index = 0; index < values.length; index++){
						list[index] = Long.parseLong(values[index]);
					}
					ArrayUtil.quickSortRandom(list, 0, list.length - 1);
					for(int index = 0; index < values.length; index++){
						writers[i].write(Long.toString(list[index]) + "\n");
					}
				}else{
					ArrayUtil.quickSortRandom(values, 0, values.length - 1);
					for(int index = 0; index < values.length; index++){
						writers[i].write(values[index] + "\n");
					}
				}
				
				moveBytes(barray, curSize, 0, offset);
				curSize = offset;
				if(leftOver){
					while( buffer.hasRemaining())
				    {	
				    	nInBuf = buffer.remaining();
				        buffer.get( barray, curSize, nInBuf);
				    	curSize += nInBuf;
				    }
			    	buffer.clear();
				}
							
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
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return tmpFiles;
	}
	
	private static int findLastDelimiter(byte[] array, int start){
		int offset = 0;
		for(offset = 0; offset <= start; offset++){
			if((char)array[start - offset] == '\n' || (char)array[start - offset] == '\r')
				break;
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
		if(len == 0 || srcStart + len > array.length || dstStart + len > array.length 
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
			
			Long[] buffer = new Long[groups]; 
//			MyArrayHeap<Long> heap = new MyArrayHeap<Long>(buffer, true, false);
			PriorityQueue<Long> heap = new PriorityQueue<Long>();
			HashMap<Long, Integer> refMap = new HashMap<Long, Integer>();
			
			String curStr = null;
			Long curValue;
			int listIndex = 0;
			for(int i = 0; i < groups; i++){
				curValue = Long.parseLong(readers[i].readLine());
//				heap.insert(curValue);
				heap.add(curValue);
				refMap.put(curValue, i);
			}
			
			while(!dataFlag.isAllClear()){
				curValue = heap.peek();
				listIndex = refMap.get(curValue);
//				heap.delete();
				heap.remove();
				refMap.remove(curValue);
				writer.write(curValue + "\n");
				
				curStr = readers[listIndex].readLine();
				System.out.println(curStr + " " + listIndex);
				if(curStr == null){
					dataFlag.clearBit(listIndex);
					if(heap.size() > 0)
						continue;					
					else{
						listIndex = dataFlag.getFirstSetBit();
						curStr = readers[listIndex].readLine();
					}
				}				
				curValue = Long.parseLong(curStr);
//				heap.insert(curValue);
				heap.add(curValue);
				refMap.put(curValue, listIndex);
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
	
	public static class longStrComparator implements Comparator<String>{
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
		
		/**
		 * Directly retrieve data from file to application buffer
		 */
		Scanner in = null;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
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
				int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
				ArrayUtil.printSubArray(list, 0, printOutSize);
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
				int printOutSize = list.size()/100 > 1000 ? 1000 : list.size()/100;
				ArrayUtil.printSubArray(list, 0, printOutSize);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(in != null)
				in.close();
			try{
				if(reader != null)
					reader.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
}
