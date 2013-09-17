package edu.upenn.yiranqin.scalabilityrelated;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;


import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class FileGenerator {	
	private static final int CUTOFF = 100000; 
	/**
	 * Generate a file with number value from start to num more elements with specified condition 
	 * @param limit the number of elements
	 * @param isInOrder whether the content should be in sequential order
	 * @param hasDup will the duplication will be tolerated  
	 * @param missing the missing numbers in the content
	 * 
	 * return the full path of the file
	 */	
	public static String generate(String path, long start, long num, boolean isInOrder, boolean hasDup, long[] missing){		
//		String path = "/mnt/hgfs/HostWorkspace/AlgorithmStudy/";
//		String path = "/home/cis455/workspace/";
		String fileName = "start: " + start + ", num: " + num + 
				", isInOrder:" + isInOrder + ", hasDup:" + hasDup + ", mask:" + ArrayUtil.toArrayString(missing);
		
		PrintWriter writer = null;
		try{
			File file = new File(path + fileName);
//			File rootFile = file.getParentFile();
//			rootFile.mkdirs();
			if(!file.exists())
				file.createNewFile();
			
			writer = new PrintWriter(new FileWriter(file));
			HashSet<Long> missingSet = new HashSet<Long>();
			for(long value : missing){
				missingSet.add(value);
			}
			
			if(isInOrder){
				for(long i = start; i <= start + num; i++){
					if(!missingSet.contains(i)){					
						writer.write(Long.toString(i) + "\n");
					}
				}
			}else if(hasDup){
				for(long i = start; i <= start + num ; i++){
					long random = (long)Math.random() * num + start;
					if(!missingSet.contains(random)){					
						writer.write(Long.toString(random) + "\n");
					}
				}
			}else{
				long curStart = start;
				long[] valueBuf = new long[CUTOFF];	
				
				while(num > 0){
					int limit = ((long)CUTOFF < num) ? CUTOFF : (int)num;
					int len = ArrayUtil.generateArrayWithMask(valueBuf, curStart, limit, missingSet);
					ArrayUtil.shuffleSubArray(valueBuf, 0, len -1);
					for(int i = 0; i < len; i++){
						writer.write(Long.toString(valueBuf[i]) + "\n");
					}
					curStart += valueBuf.length;
					num -= valueBuf.length;
				}
			}
			
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			if(writer == null){
				System.exit(-1);
			}
			writer.close();
		}
		
		return path + fileName;
	}
	
	

}
