package edu.upenn.yiranqin.arrayrelated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

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
		
		selectionKTest();
//		profitTest();
//		arrayUtilTest();
	}
	
	public void arrayUtilTest(){
		int[] test1 = {-1, 2,-5, 2, -1, 15, -8, -6, 5, 2, 3};
		int[] test2 = {-1, -2, -4, 0, -5};
		
		ArrayUtil.printArray(ArrayUtil.maxContinuousSubArray(test1));
		ArrayUtil.printArray(ArrayUtil.maxContinuousSubArray(test2));
		
		
		int[] array = ArrayUtil.generateRandomArray(15);
		ArrayUtil.shuffleArray(array);
		ArrayUtil.printArray(array);
		ArrayUtil.findPairsToSum(array, 10);

    	ArrayList<int[]> list = ArrayUtil.findContinuousSubArrayToSumWithSize(array, 10, 2);
    	for(int[] subarray : list){
    		ArrayUtil.printArray(subarray);
    	}
    	
    	Integer[] testArray1 = ArrayUtil.generateIntegerArray(5, 10);
    	ArrayUtil.shuffleArray(testArray1);
    	ArrayUtil.printArray(testArray1);
    	Integer[] testArray2 = ArrayUtil.generateIntegerArray(8, 10);
    	ArrayUtil.shuffleArray(testArray2);
    	ArrayUtil.printArray(testArray2);
    	ArrayUtil.printArray(ArrayUtil.longestCommonContinuousSubset(testArray1, testArray2));
    	ArrayUtil.printArray(ArrayUtil.longestCommonSequence(testArray1, testArray2));
    	ArrayUtil.printArray(ArrayUtil.longestIncreasingSequence(testArray1));
    	ArrayUtil.printArray(ArrayUtil.powerSet(new Integer[]{1,2,4, 3,1,1}));
    	
    	Integer[] array1 = {1};
    	for(int i = 2; i <= 12; i++){
    		int start = array1.length + 1;
    		Integer[] array2 = ArrayUtil.generateIntegerArray(start, i);
    		ArrayUtil.shuffleArray(array2);
    		array1 = ArrayUtil.concatenateArrays(array1, array2);
    	}
    	array1 = ArrayUtil.concatenateArrays(array1, ArrayUtil.generateIntegerArray(array1.length + 1, 7));
    	ArrayUtil.printArray(array1);
    	
    	System.out.println(ArrayUtil.searchBucketOrderedArray(array1, 36));
    	System.out.println();
    	
    	int[] testArray3 = {-1, -3, 4, 5, -2, -3, -1};
    	ArrayUtil.printArray(testArray3);
    	LinkedList<int[]> result = ArrayUtil.findAllContinuousSubArrayToSum(testArray3, 9);
    	for(int[] subarray : result){
    		ArrayUtil.printArray(subarray);
    	}
	}
	
	public void profitTest(){
		int[] B = {-2,4,30,-50,90,-60,100,120};
    	int[] C = {90,190,10,90};
    	int[] D = {190,90,10,90};
    	int[] E = {10,90,90,190};
    	int[] F = {80,70};
    	System.out.println("The maximum of profit is: " + ArrayUtil.findMaxProfitSingleTransaction(B));
    	System.out.println(ArrayUtil.maxDiffRightToLeft(B));	
    	System.out.println("The maximum of profit is: " + ArrayUtil.findMaxProfitSingleTransaction(C));
    	System.out.println(ArrayUtil.maxDiffRightToLeft(C));
    	System.out.println("The maximum of profit is: " + ArrayUtil.findMaxProfitSingleTransaction(D));
    	System.out.println(ArrayUtil.maxDiffRightToLeft(D));
    	System.out.println("The maximum of profit is: " + ArrayUtil.findMaxProfitSingleTransaction(E));
    	System.out.println(ArrayUtil.maxDiffRightToLeft(E));
    	System.out.println("The maximum of profit is: " + ArrayUtil.findMaxProfitSingleTransaction(F));
    	System.out.println(ArrayUtil.maxDiffRightToLeft(F));
    	
    	int[] test = {2, 4, 1, 16, 7, 5, 11, 9};
    	System.out.println(ArrayUtil.maxDiffLeftToRight(test));	
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
		int size = 300;
		int repetition = 10000;
		int[] distinctArray = ArrayUtil.generateDistinctIntArray(size);//ArrayUtil.generateRandomArray(size);//
		Integer[] disIntegerArray = ArrayUtil.generateIntegerArray(1, size);
		
		long end11 = System.currentTimeMillis();
		ArrayUtil.shuffleArray(distinctArray);
		int k = 10;
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
		
		ArrayUtil.shuffleArray(disIntegerArray);
		Integer[] result = ArrayUtil.priorityQueueSelection(disIntegerArray, k);
//		ArrayUtil.printArray(result);
		for(int i = 0; i < repetition; i++){
			ArrayUtil.shuffleArray(disIntegerArray);
			ArrayUtil.priorityQueueSelection(disIntegerArray, k);
		}
		long end15 = System.currentTimeMillis();
		System.out.println(repetition +" PriorityQueue Selection:" + (end15 - end14));
		
		
//		ArrayUtil.shuffleArray(distinctArray);
//		ArrayUtil.printArray(distinctArray);
//		ArrayUtil.buildHeap(distinctArray, distinctArray.length, false);
//		ArrayUtil.printArray(distinctArray);
//		
//		ArrayUtil.printHeap(distinctArray);
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
	 * If do this in C/C++, there is a possible memory leak since what reference originally point to is not freed
	 * but in Java this won't work, nothing happens after the scope of this program 
	 * @param reference
	 */
	public void assignNew(byte[] reference){
		byte[] newOne = new byte[reference.length];
		reference = newOne;
	}
}
