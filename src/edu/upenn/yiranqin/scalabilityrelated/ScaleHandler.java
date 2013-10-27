package edu.upenn.yiranqin.scalabilityrelated;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;


public class ScaleHandler {
	public static void main(String[] argv){
		new ScaleHandler().start();
	}
     
	public void start(){
		fileBasedUtilTest();
//		NIOPerformanceTest();
	}
	
	public void fileBasedUtilTest(){
		String filePath = "/home/cis455/workspace/" + "start: 1, num: 10000000, isInOrder:false, hasDup:false, mask:[89332]";
//		String filePath = "WordList";
//		String filePath = "start^% 1, num^% 1000, isInOrder^%false, hasDup^%false, mask^%[332]";
//		String filePath = "start^% 1, num^% 2500000, isInOrder^%false, hasDup^%false, mask^%[332]";
		File file = new File(filePath);
		FileBasedUtil.externalSort(file, 10, false, false);
	}
	
	/**
	 * For ~100MB files, no significant performance different between blocking or nonblocking IO
	 * But a lot better for smaller size(10M)
	 */
	public void NIOPerformanceTest(){
//		String filePath = FileGenerator.generate("/home/cis455/workspace/", 1, 10000000, false, false, new long[]{89332});
//		String filePath = "start^% 1, num^% 10000000, isInOrder^%false, hasDup^%false, mask^%[89332]";
//		String filePath = "WordList";
//		String filePath = "start^% 1, num^% 1000, isInOrder^%false, hasDup^%false, mask^%[332]";
//		String filePath = "start^% 1, num^% 2500000, isInOrder^%false, hasDup^%false, mask^%[332]";
//		String filePath = "/home/cis455/workspace/" + "start: 1, num: 10000000, isInOrder:false, hasDup:false, mask:[89332]";
		String filePath = "/home/cis455/workspace/" +"start: 1, num: 2500000, isInOrder:false, hasDup:false, mask:[332]";
		File file = new File(filePath);
		long start = System.currentTimeMillis();
		FileBasedUtil.sortSmallFile(file, true, true);
		long end1 = System.currentTimeMillis();
		System.out.println("sort small file using reguler buffered reader and scanner: " + (end1 - start));
		
		FileBasedUtil.sortSmallFile(file, true, false);
		long end2 = System.currentTimeMillis();
		System.out.println("sort small file using reguler buffered reader but no scanner: " + (end2 - end1));
		
		FileBasedUtil.sortSmallFileNIO(file, true, true);
		long end3 = System.currentTimeMillis();
		System.out.println("sort small file using NIO and scanner: " + (end3 - end2));
		
		FileBasedUtil.sortSmallFileNIO(file, true, false);
		long end4 = System.currentTimeMillis();
		System.out.println("sort small file using NIO and split: " + (end4 - end3));
	}
	
	public void findMissingNumberTest(){
//		String filePath = FileGenerator.generate(1, 1000, false, false, new long[]{332});
//		String filePath = "start^% 1, num^% 10000000, isInOrder^%false, hasDup^%false, mask^%[89332]";
		String filePath = "start^% 1, num^% 1000, isInOrder^%false, hasDup^%false, mask^%[332]";
		File file = new File(filePath);
		System.out.println(LargeNumberSetUtil.findMissingNumber10K(file));
		System.out.println(LargeNumberSetUtil.findMissingNumberMap(file, 10000000));
		System.out.println(LargeNumberSetUtil.findMissingNumber10M(file));
	}
	
	public void superLargeNumberByteTest(){
		
		SuperLargeNumberByte number = new SuperLargeNumberByte(3, false);
		System.out.println(number.toString());
		System.out.println(number.getNumDigit());
		
		SuperLargeNumberByte threshold = new SuperLargeNumberByte(3, true);
		System.out.println(threshold.toString());
		System.out.println(threshold.getNumDigit());
		
		SuperLargeNumberByte sum = number.add(threshold);
		System.out.println(sum.toString());
		System.out.println(sum.getNumDigit());
		
		System.out.println((long)(9999 + 999999));
		
		SuperLargeNumberByte num1 = new SuperLargeNumberByte(Long.MAX_VALUE);
		SuperLargeNumberByte num2 = new SuperLargeNumberByte(Long.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		System.out.println(num1.add(num2).toString());
		System.out.println(123456 + 44335356);
		
		
		while(number.compareTo(threshold) <= 0){ 
//			System.out.println(number.toString() + "  "+ number.getNumDigit());
			number.increment();
		}
		System.out.println(number.toString() + "  "+ number.getNumDigit());
		for(int i = 0; i< 100; i++){			
			number.increment();
		}
		System.out.println(number.toString() + "  "+ number.getNumDigit());
		
//		SuperLargeNumberByte start = new SuperLargeNumberByte(10000);
//		SuperLargeNumberByte end = new SuperLargeNumberByte(1000000);
//		System.out.println(end.toString() + "  "+ end.getNumDigit());
		SuperLargeNumberByte start = new SuperLargeNumberByte(Long.MAX_VALUE);
		SuperLargeNumberByte end = new SuperLargeNumberByte(new byte[]{9,22,33,72,03,68,54,77,59,00});
		SuperLargeNumberByte sum1 = new SuperLargeNumberByte(1,false);  
		while(start.compareTo(end) <= 0){
			sum1 = sum1.add(start);			
			start.increment();
//			System.out.println(start.toString());
		}
		
		System.out.println(sum1.toString() + "  "+ sum1.getNumDigit());
		
		ArrayList<SuperLargeNumberByte> list = new ArrayList<SuperLargeNumberByte>(10);
		for(int i = 0; i < 10; i++){
			SuperLargeNumberByte cur = start.clone();
			list.add(cur);			
			start.increment();
		}
		ArrayUtil.printArray(list);		
		ArrayUtil.shuffleArray(list);
		ArrayUtil.printArray(list);
		
		Collections.sort(list);
		ArrayUtil.printArray(list);
		
		SuperLargeNumberByte numOne = new SuperLargeNumberByte(213220);
		ArrayUtil.printArray(numOne.getValue());
		SuperLargeNumberByte numTwo = new SuperLargeNumberByte(300000);
		ArrayUtil.printArray(numTwo.getValue());
		System.out.println(num1.multiply(numTwo).toString());
	}
	
	public void arrayUtilTest(){
		int limit = 64;
		int[] array = ArrayUtil.generateRandomArray(limit);
		
		ArrayUtil.printArray(array);		
		LargeNumberSetUtil.printAllDuplicates(array, limit);
		
		int[] distinctArray = ArrayUtil.generateDistinctIntArray(limit);
		
		ArrayUtil.printArray(distinctArray);
		LargeNumberSetUtil.printAllDuplicates(distinctArray, limit);
		
		int[] sub = ArrayUtil.randomSubarray(distinctArray, 10);
		ArrayUtil.printArray(sub);
	}
	
	public void bitmapTest(){
		//this is the way array get initialized, nothing changed without new.
		int[] bitmapValue = new int[]{256, 800, -1, -434343};
		System.out.println(bitmapValue[0] + " " + bitmapValue[1]);
		MyBitmap largebitmap = new MyBitmap(bitmapValue);
			
		System.out.println(largebitmap.toBinaryString());
		largebitmap.setBit(64);
		System.out.println(largebitmap.toBinaryString());
		
		largebitmap = new MyBitmap(256);
		largebitmap.setBit(72);
		largebitmap.setBit(35);
		System.out.println(largebitmap.toBinaryString());
		System.out.println(largebitmap.getFirstSetBit());
	}
	
	public void integerBitmapTest(){
		MyIntegerBitmap bitmap = new MyIntegerBitmap(-434343);
		System.out.println(bitmap.getValue());
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		System.out.println(bitmap.countSetBits());
		
		bitmap.clearBitsMSthroughI(31);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		bitmap.clearBitsIthrough0(31);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		bitmap.clearNBitsFromRight(22);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		bitmap.setBit(0);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		System.out.println(bitmap.getBit(3));
		
		bitmap.toggleBit(23);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		bitmap.updateBit(10, 1);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		bitmap.clearBit(31);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
		
		System.out.println(Integer.toBinaryString(bitmap.getBit(31)));
		
		bitmap.updateBit(31, 1);
		System.out.println(Integer.toBinaryString(bitmap.getValue()));
	}
	
	
	
}
