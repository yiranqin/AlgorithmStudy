package edu.upenn.yiranqin.arrayrelated;

import java.util.HashMap;
import java.util.HashSet;

import edu.upenn.yiranqin.scalabilityrelated.SuperLargeNumberByte;

public class ArrayHandler {
	public static void main(String[] argv){
		new ArrayHandler().start();
	}
	
	public void start(){
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		map.keySet();
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(0);
		

//		selectionKTest();
				
	}
	
	public void sortingPerformanceTest(){
		int size = 31;
		int repetition = 100;
		int[] distinctArray = ArrayUtil.generateDistinctIntArray(size);//ArrayUtil.generateRandomArray(size);//
		
		long start = System.currentTimeMillis();
		ArrayUtil.bubbleSort(distinctArray);
		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.bubbleSort(distinctArray);
		}
		long end1 = System.currentTimeMillis();
		System.out.println(repetition +" Bubble Sort:" + (end1 - start));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.selectionSort(distinctArray);
		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.selectionSort(distinctArray);
		}
		long end2 = System.currentTimeMillis();
		System.out.println(repetition +" Selection Sort:" + (end2 - end1));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.insertionSort(distinctArray, 0 , distinctArray.length - 1);
		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.insertionSort(distinctArray);
		}
		long end3 = System.currentTimeMillis();
		System.out.println(repetition +" Insertion Sort:" + (end3 - end2));
		
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.mergeSortInPlace(distinctArray, 0, distinctArray.length - 1);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.mergeSortInPlace(distinctArray, 0, distinctArray.length - 1);
		}
		long end4 = System.currentTimeMillis();
		System.out.println(repetition +" Merge Sort In Place:" + (end4 - end3));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.mergeSort(distinctArray);
//		ArrayUtil.printArray(ArrayUtil.mergeSort(distinctArray));
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.mergeSort(distinctArray);
		}
		long end5 = System.currentTimeMillis();
		System.out.println(repetition +" Merge Sort New Every Time:" + (end5 - end4));
		
		ArrayUtil.shuffleArray(distinctArray);
		int[] helper = new int[distinctArray.length];
		ArrayUtil.mergeSort(distinctArray, helper, 0, distinctArray.length - 1);		
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			helper = new int[distinctArray.length];
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.mergeSort(distinctArray, helper, 0, distinctArray.length - 1);
		}
		long end6 = System.currentTimeMillis();
		System.out.println(repetition +" Merge Sort Copy Every Time:" + (end6 - end5));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.mergeSortCopyOnce(distinctArray, null, 0, distinctArray.length - 1);		
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.mergeSortCopyOnce(distinctArray, null, 0, distinctArray.length - 1);
		}
		long end7 = System.currentTimeMillis();
		System.out.println(repetition +" Merge Sort Copy Once:" + (end7 - end6));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.mergeSortIterative(distinctArray);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.mergeSortIterative(distinctArray);
		}
		long end8 = System.currentTimeMillis();
		System.out.println(repetition +" Merge Sort Iterative and Only Copy Once:" + (end8 - end7));
		
		
		ArrayUtil.shuffleArray(distinctArray);
//		ArrayUtil.printArray(distinctArray);
		ArrayUtil.quickSort(distinctArray, 0, distinctArray.length - 1);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.quickSort(distinctArray, 0, distinctArray.length - 1);
		}
		long end9 = System.currentTimeMillis();
		System.out.println(repetition +" Quick Sort Median3:" + (end9 - end8));
		
		ArrayUtil.shuffleArray(distinctArray);
//		ArrayUtil.printArray(distinctArray);
		ArrayUtil.quickSortMedianOfMedianFive(distinctArray, 0, distinctArray.length - 1);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.quickSortMedianOfMedianFive(distinctArray, 0, distinctArray.length - 1);
//			System.out.println(ArrayUtil.isIncreasing(distinctArray));
		}
		long end10 = System.currentTimeMillis();
		System.out.println(repetition +" Quick Sort Median Of Median Of Five:" + (end10 - end9));
		
		ArrayUtil.shuffleArray(distinctArray);
//		ArrayUtil.printArray(distinctArray);
		ArrayUtil.quickSortRandom(distinctArray, 0, distinctArray.length - 1);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.quickSortRandom(distinctArray, 0, distinctArray.length - 1);
		}
		long end11 = System.currentTimeMillis();
		System.out.println(repetition +" Quick Sort Random:" + (end11 - end10));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.heapSort(distinctArray);
//		ArrayUtil.printArray(distinctArray);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.heapSort(distinctArray);
		}
		long end12 = System.currentTimeMillis();
		System.out.println(repetition +" Heap Sort:" + (end12 - end11));
	}
	
	public void selectionKTest(){
		int size = 61;
		int repetition = 10000;
		int[] distinctArray = ArrayUtil.generateDistinctIntArray(size);//ArrayUtil.generateRandomArray(size);//
		
		long end11 = System.currentTimeMillis();
		ArrayUtil.shuffleArray(distinctArray);
		int k = 3;
		boolean increasing = false;
		boolean random = true;
//		ArrayUtil.printSubArray(distinctArray, 0, ArrayUtil.quickSelection(distinctArray, k, increasing, random));
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.quickSelection(distinctArray, k, increasing, random);
		}
		long end12 = System.currentTimeMillis();
		System.out.println(repetition +" Quick Selection Random:" + (end12 - end11));
		
		random = false;
		ArrayUtil.shuffleArray(distinctArray);
//		ArrayUtil.printSubArray(distinctArray, 0, ArrayUtil.quickSelection(distinctArray, k, increasing, random));
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.quickSelection(distinctArray, k, increasing, random);
		}
		long end13 = System.currentTimeMillis();
		System.out.println(repetition +" Quick Selection Median Of Median Of 5:" + (end13 - end12));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.heapSelection(distinctArray, k, increasing);
//		ArrayUtil.printSubArray(distinctArray, 0, k - 1);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(distinctArray);
			ArrayUtil.heapSelection(distinctArray, k, increasing);
		}
		long end14 = System.currentTimeMillis();
		System.out.println(repetition +" Heap Selection:" + (end14 - end13));
		
		ArrayUtil.shuffleArray(distinctArray);
		ArrayUtil.printArray(distinctArray);
		ArrayUtil.buildHeap(distinctArray, distinctArray.length, false);
		ArrayUtil.printArray(distinctArray);
		
		ArrayUtil.printHeap(distinctArray);
	}
	
	public void partitionTest(){
		int size = 310;
		int[] distinctArray = ArrayUtil.generateDistinctIntArray(size);//ArrayUtil.generateRandomArray(size);//
		
		for(int i = 0; i < distinctArray.length; i++){
			ArrayUtil.shuffleArray(distinctArray);
			int pivot = distinctArray[i];
			ArrayUtil.printArray(distinctArray);
//			ArrayUtil.swap(distinctArray, i, distinctArray.length - 1);
			ArrayUtil.generalPartition(distinctArray, 0, distinctArray.length - 1, pivot, true);
			ArrayUtil.printArray(distinctArray);
			ArrayUtil.generalPartition(distinctArray, 0, distinctArray.length - 1, true);
			ArrayUtil.printArray(distinctArray);
		}
	}
	
	public void objectTest(){
		byte[] arrayA = new byte[]{1,2,3};
		byte[] arrayB = new byte[]{2,3,4};
		SuperLargeNumberByte numA = new SuperLargeNumberByte(arrayA);
		SuperLargeNumberByte numB = new SuperLargeNumberByte(arrayB);
		swap(numA, numB);
		ArrayUtil.printArray(numA.getValue());
		ArrayUtil.printArray(numB.getValue());
		
		setToNull(arrayA);
		ArrayUtil.printArray(arrayA);
		assignNew(arrayA);
		ArrayUtil.printArray(arrayA);
		
	}
	
	/**
	 * How to do swap, swap the actual value of two object rather than their reference
	 * Since if passed in references and swap them, they are both just copy of original reference
	 * and will not exist after the method return
	 *  
	 * @param numA
	 * @param numB
	 */
	public void swap(SuperLargeNumberByte numA, SuperLargeNumberByte numB){
		byte[] value = numA.getValue();
		numA.setValue(numB.getValue());
		numB.setValue(value);
	}
	
	/**
	 * The reference passed in is just another copy of the reference to an object on the heap
	 * and it sits on the stack
	 * when the method return, it will no longer exist
	 * @param reference
	 */
	public void setToNull(byte[] reference){
		reference = null;
	}
	
	/**
	 * If do this in C/C++, there will be memory leak
	 * @param reference
	 */
	public void assignNew(byte[] reference){
		byte[] newOne = new byte[reference.length];
		reference = newOne;
	}
}
