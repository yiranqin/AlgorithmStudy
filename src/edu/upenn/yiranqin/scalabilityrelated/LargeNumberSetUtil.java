package edu.upenn.yiranqin.scalabilityrelated;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class LargeNumberSetUtil {
	public static int findMissingNumberMap(File file, int limit){
		MyBitmap map = new MyBitmap(limit);
		int missingInteger = 1;
		
		Scanner input = null;
		try{
			input = new Scanner(new FileReader(file));
			int num = 1;
			while(input.hasNext()){
				 num = input.nextInt();
				 map.setBit(num - 1);
			}
			
			int index = 0;
			for(index = 0; index < limit; index++){
				if(map.getBit(index) == 0)
					break;
			}
			
			missingInteger = index + 1;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(input != null)
				input.close();
		}
		
		return missingInteger;
	}
	
	/**
	 * 10.3
	 * Generate an integer which is not contained by a file with 4 billion strictly positive integers
	 * 10MB memory
	 */
	public static int findMissingNumber10M(File file){
		int blockNum = (int)Math.pow(2, 13);
		int bitNum = (int)Math.pow(2, 19);		
		int[] blocks = new int[blockNum];
		int missingInteger = 1;
		
		Scanner input = null;
		Scanner input2 = null;
		try{
			input = new Scanner(new FileReader(file));
			while(input.hasNext()){
				int cur = input.nextInt();
				blocks[cur % bitNum == 0 ? cur/bitNum - 1: cur/bitNum]++; 
			}
			
			int i = 0;
			for(i = 0; i < blockNum; i++){
				if(blocks[i] < bitNum)
					break;
			}
			
			ArrayUtil.printArray(blocks);
			System.out.println(i);
			MyBitmap bitmap = new MyBitmap(bitNum);
			input2 = new Scanner(new FileReader(file));
			int scale = bitNum * i;
			while(input2.hasNext()){
				int cur = input2.nextInt();
				if(cur / bitNum == i){
					bitmap.setBit((cur - scale - 1) & (bitNum -1));
				}
			}
			
			int index = 0;
			for(index = 0; index < bitNum; index++){
				if(bitmap.getBit(index) == 0)
					break;
			}
			
			missingInteger = i*bitNum + index + 1;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(input != null)
				input.close();
			if(input2 != null)
				input2.close();
		}
		
		return missingInteger;		
	}
	
	/**
	 * 10K memory limit for a integer at most 2^32, 2 layer of indirection 
	 * @param file
	 * @return 
	 */
	public static int findMissingNumber10K(File file){
		int blockNum1 = (int)Math.pow(2, 9);
		int blockNum2 = (int)Math.pow(2, 9);
		int bitNum = (int)Math.pow(2, 14);		
		int[] blocks1 = new int[blockNum1];
		int[] blocks2 = new int[blockNum2];
		int missingInteger = 1;
		
		Scanner input = null;
		try{
			input = new Scanner(new FileReader(file));
			int scale = bitNum * blockNum2;
			while(input.hasNext()){
				int cur = input.nextInt();
				blocks1[cur % scale == 0 ? cur/scale - 1: cur/scale]++; 
			}
			
			int i = 0;
			for(i = 0; i < blockNum1; i++){
				if(blocks1[i] < scale)
					break;
			}
			ArrayUtil.printArray(blocks1);
			System.out.println(i);
			
			input = new Scanner(new FileReader(file));
			scale = i * bitNum * blockNum2;
			while(input.hasNext()){
				int cur = input.nextInt();
				if(cur >= i * bitNum * blockNum2 && cur < (i + 1) * bitNum * blockNum2){
//				if((cur / (bitNum * blockNum2)) == i){	
					blocks2[(cur - scale) % bitNum == 0? (cur - scale)/bitNum - 1 : (cur - scale)/bitNum]++;
				}
			}
			
			int j = 0;
			for(j = 0; i < blockNum2; j++){
				if(blocks2[j] < bitNum)
					break;
			}
			ArrayUtil.printArray(blocks2);
			System.out.println(j);
			
			MyBitmap bitmap = new MyBitmap(bitNum);
//			byte[] bitmap = new byte[bitNum >> 3]; //2^14 divisible by 8, no need to plus extra 1 
			int offset = i * blockNum2 * bitNum + j * bitNum;
			input = new Scanner(new FileReader(file));
			
			while(input.hasNext()){
				int cur = input.nextInt();
				if(cur >= offset && cur < offset + bitNum){
					bitmap.setBit((cur - offset - 1) & (bitNum -1));
//				if(cur >= offset && cur < offset + bitNum){
//					bitmap[(cur - offset) >> 3] |= 1 << ((cur - offset - 1) % 8);
				}
			}
			
			int index = 0;
			for(index = 0; index < bitNum; index++){
				if(bitmap.getBit(index) == 0)
//				if((bitmap[index >> 3] & (1 << (index % 8))) == 0)	
					break;
			}
//			System.out.println(index);
			
			missingInteger = offset + index + 1;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(input != null)
				input.close();
		}		
		
		return missingInteger;		
	}
	
	/**
	 * 10.4 
	 * Print all duplicate elements for a large array with largest number no more than n
	 * the array memory usage is not calculated in
	 * or we can read in element by element from a file 
	 * @param n
	 */
	public static void printAllDuplicates(int[] array, int n){
		MyBitmap bitmap = new MyBitmap(n);
		int count = 0;
		for(int num : array){
			int num0 = num - 1;
			if(bitmap.getBit(num0) == 1){
				System.out.println(num);
				count ++;
			}
			bitmap.setBit(num0);
		}
		if(count == 0)
			System.out.println("No Duplicates");
	}

}
