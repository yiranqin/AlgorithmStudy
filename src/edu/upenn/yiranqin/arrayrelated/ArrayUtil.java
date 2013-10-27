package edu.upenn.yiranqin.arrayrelated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class ArrayUtil{
	public static void printArray(int[] array){
		System.out.println(toArrayString(array));
	}
	
	public static void printArray(byte[] array){
		if(array == null)
			return;
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array[i] + ",");
		}
		if(len > 0)
			buffer.append(array[len - 1]);
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static void printArray(char[] array){
		if(array == null)
			return;
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array[i] + ",");
		}
		if(len > 0)
			buffer.append(array[len - 1]);
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static <T extends Object>
	void printArray(T[] array){
		if(array == null)
			return;
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array[i] + ",");
		}
		if(len > 0)
			buffer.append(array[len - 1]);
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object>
	void printArray(List<T> array){
		if(array == null)
			return;
		
		int len = array.size();
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			if(array.get(i) instanceof List<?>)
				buffer.append(toListString((List<T>) array.get(i)));
			else
				buffer.append(array.get(i) + ",");
		}
		if(len > 0)
			buffer.append(array.get(len - 1));
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static <T extends Object>
	String toListString(List<T> array){
		if(array == null)
			return null;
		
		int len = array.size();
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array.get(i) + ",");
		}
		if(len > 0)
			buffer.append(array.get(len - 1));
		buffer.append(']');
		return buffer.toString();
	}
	
	public static String toArrayString(int[] array){
		if(array == null)
			return "";
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array[i] + ",");
		}
		if(len > 0)
			buffer.append(array[len - 1]);
		buffer.append(']');
		return buffer.toString();
	}
	
	public static String toArrayString(long[] array){
		if(array == null)
			return "";
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = 0; i < len - 1; i++){
			buffer.append(array[i] + ",");
		}
		if(len > 0)
			buffer.append(array[len - 1]);
		buffer.append(']');
		return buffer.toString();
	}
	
	public static void printSubArray(int[] array, int start, int end){
		if(array == null || start > end)
			return;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = start; i < end; i++){
			buffer.append(array[i] + ",");
		}
		buffer.append(array[end]);
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static <T extends Object>
	void printSubArray(T[] array, int start, int end){
		if(array == null || start > end)
			return;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = start; i < end; i++){
			buffer.append(array[i] + ",");
		}
		buffer.append(array[end]);
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static <T extends Object>
	void printSubArray(List<T> array, int start, int end){
		if(array == null || start > end)
			return;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append('[');
		for(int i = start; i < end; i++){
			buffer.append(array.get(i) + ",");
		}
		buffer.append(array.get(end));
		buffer.append(']');
		System.out.println(buffer.toString());
	}
	
	public static String toBinaryString(byte[] array){
		if(array == null)
			return "";
		
		int len = array.length;
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < len; i++){
			for(int j = 7; j >= 0; j--){
				if((array[i] & (1 <<j)) == 0)
					buffer.append("0");
				else
					buffer.append("1");
			}
		}
		return buffer.toString();
	}
	
	public static <T extends Object>
	T[] concatenateArrays(T[] array1, T[] array2){
		if(array1 == null && array2 == null)
			return null;
		else if(array1 == null)
			return array2;
		else if(array2 == null)
			return array1;
		
		T[] newArray = Arrays.copyOf(array1, array1.length + array2.length);
		for(int i = 0; i < array2.length; i++){
			newArray[array1.length + i] = array2[i];
		}
		return newArray;
	}
	
	public static void swap(int[] array, int i, int j){
		if(i != j){
			array[i] = array[i] ^ array[j];
			array[j] = array[i] ^ array[j];
			array[i] = array[i] ^ array[j];
		}
	}
	
	public static void swap(long[] array, int i, int j){
		if(i != j){
			array[i] = array[i] ^ array[j];
			array[j] = array[i] ^ array[j];
			array[i] = array[i] ^ array[j];
		}
	}
	
	public static void swap(char[] array, int i, int j){
		char temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static <T extends Object>
	void swap(T[] array, int i, int j){
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static <T extends Object>
	void swap(List<T> array, int i, int j){
		T temp = array.get(i);
		array.set(i, array.get(j));
		array.set(j, temp);
	}
	
	/**
	 * Generate increasing order array from start with up size elements
	 * @param size
	 * @return
	 */
	public static int[] generateArray(int start, int size){
		if(start + size > Integer.MAX_VALUE || size <= 0 || start < 0)
			return null;
		int[] array = new int[size];
		for(int i = 0; i < size; i ++){
			array[i] = i + start;
		}
		return array;
	}
	
	public static Integer[] generateIntegerArray(int start, int size){
		if(start + size > Integer.MAX_VALUE || size <= 0 || start < 0)
			return null;
		Integer[] array = new Integer[size];
		for(int i = 0; i < size; i ++){
			array[i] = i + start;
		}
		return array;
	}
	
	/**
	 * Generate from start with up to size elements
	 * Since there is no guarantee that how many elements within mask
	 * So we just use a tmp array and copy it back
	 * 
	 * @param start
	 * @param size
	 * @param mask
	 * @return
	 */
	public static int[] generateArrayWithMask(int start, int size, int[] mask){
		if(mask == null || mask.length < 1)
			return generateArray(start, size);
		if(start + size > Integer.MAX_VALUE || size < 0 || mask.length > size)
			return null;
		
		int[] tmp = new int[size];
		int index = 0;
		for(int i = 0; i < size; i++){
			if(!belongArray(i + start, mask)){
				tmp[index] = i + start;
				index++;
			}
		}
		
		int[] array = new int[index];
		for(int i = 0; i < index; i++){
			array[i] = tmp[i];
		}
		return array;
	}
	
	public static boolean belongArray(int num, int[] array){
		if(array == null)
			return false;
		
		for(int i : array){
			if(num == i){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Generate array with provided array to reuse the memory and without mask guarantee
	 * using hashset to test whether an element is in mask array
	 *  with the assumption that the mask array is quite long and the mask will be used repetitively
	 * 
	 * @param array
	 * @param start
	 * @param size
	 * @param mask
	 * @return the size of the array with actual value
	 */
	public static int generateArrayWithMask(long[] array, long start, int size, HashSet<Long> mask){
		if(start + size > Long.MAX_VALUE || size < 0)
			return -1;
		
		int actualLen = 0;
		boolean noMask = false;
		if(mask == null || mask.size() < 1){
			noMask = true;
		}
		
		for(int i = 0; i < size; i++){
			long curValue = i + start;
			if(noMask || !mask.contains(curValue)){
				array[actualLen] = curValue;
				actualLen++;
			}
		}
		
		return actualLen;
	}
			
	public static boolean belongArray(long num, long[] array){
		if(array == null)
			return false;
		
		for(long i : array){
			if(num == i){
				return true;
			}
		}
		return false;
	}
	
	public static int[] generateRandomArray(int size){
		int[] array = new int[size];
		for(int i = 0; i < size; i ++){
			array[i] = (int)(Math.random() * size);
		}
		return array;
	}
	
	/**
	 * Generate an array with random order from 1 to size
	 * @param size
	 * @return
	 */
	public static int[] generateDistinctIntArray(int size){
		int[] array = generateArray(1 ,size);
		shuffleArray(array);
		return array;
	}
	
	public static Integer[] generateDistinctIntegerArray(int size){
		Integer[] array = generateIntegerArray(1 ,size);
		shuffleArray(array);
		return array;
	}
	/**
	 * 18.2
	 * shuffle the array with each equal possibility for each permutation
	 * @param array
	 */
	public static void shuffleArray(int[] array){
		int size = array.length;
		for(int i = 0; i < array.length; i ++, size--){
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
		}
	}
	
	public static void shuffleArray(long[] array){
		int size = array.length;
		for(int i = 0; i < array.length; i ++, size--){
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
		}
	}
	
	public static void shuffleSubArray(long[] array, int start, int end){
		int size = end - start + 1;
		for(int i = start; i <= end; i ++, size--){
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
		}
	}
	
	public static <T extends Object>
	void shuffleArray(T[] array){
		int size = array.length;
		for(int i = 0; i < array.length; i ++, size--){
			// Math.random * size is vital for this implementation
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
		}
	}
	
	public static <T extends Object>
	void shuffleArray(List<T> array){
		int size = array.size();
		for(int i = 0; i < array.size(); i ++, size--){
			// Math.random * size is vital for this implementation
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
		}
	}
	
	/**
	 * extract a new sub-array with n randomly chosen elements
	 * @param original array
	 * @param n size of sub array
	 * @return
	 */
	public static int[] randomSubarray(int[] array, int n){
		if(array == null)
			return null;
		int[] sub = new int[n];
		int size = array.length;
		
		for(int i = 0; i < n; i++, size--){
			// Math.random * size is vital for this implementation
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
			sub[i] = array[i];
		}
		return sub;
	}
	
	
	public static <T extends Object>
	List<T> randomSubarray(List<T> array, int n){
		List<T> sub = new ArrayList<T>(n);
		int size = array.size();
		
		for(int i = 0; i < n; i++, size--){
			int index = (int)(Math.random() * size) + i;
			swap(array, i, index);
			sub.set(i, array.get(i));
		}
		return sub;
	}
	
	public static int[] subArray(int[] array, int start, int end){
		if(array == null || start > end || (end - start + 1) > array.length)
			return null;
		
		int[] subArray = new int[end - start + 1];
		for(int i = 0; i < subArray.length; i++){
			subArray[i] = array[i + start];
		}
		
		return subArray;
	}
	
	public static void bubbleSort(int[] array){
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array.length - 1 - i; j++){
				if(array[j] > array[j + 1])
					swap(array, j, j +1 );
			}
		}
	}
	
	public static void selectionSort(int[] array){
		for(int i = 0; i < array.length; i++){
			for(int j = i + 1; j < array.length; j++){
				if(array[j] < array[i])
					swap(array, i, j);
			}
		}
	}
	
	public static void insertionSort(int[] array){
		insertionSort(array, 0, array.length - 1);
	}
	
	public static void insertionSort(int[] array, int start, int end){
		int j = 0;
		for(int i = start + 1; i <= end; i++){
			int tmp = array[i];
			for(j = i; j - 1 >= start && array[j - 1] > tmp ;  j--){
				array[j] = array[j - 1];
			}
			array[j] = tmp;
		}
	}
	
	public static void insertionSortReverse(int[] array){
		int j = 0;
		for(int i = array.length - 2; i >= 0; i--){
			int tmp = array[i];
			for(j = i; j + 1 < array.length && array[j + 1] < tmp ;  j++){
				array[j] = array[j + 1];
			}
			array[j] = tmp;
		}
	}
	
	public static <T extends Comparable<? super T>>
	void insertionSort(T[] array){
		int j = 0;
		for(int i = 1; i < array.length; i++){
			T tmp = array[i];
			for(j = i; j > 0 && array[j - 1].compareTo(tmp) > 0 ;  j--){
				array[j] = array[j - 1];
			}
			array[j] = tmp;
		}
	}
	
	/**
	 * What's good about merge sort is that it use the least number of comparison while mostly depends on moving element
	 * This is a key advantage when implementing in Java
	 *  
	 * @param array
	 * @param start
	 * @param end
	 */
	public static void mergeSortInPlace(int[] array, int start, int end){
		if(start < end && array != null){
			int middle = (start + end)/2;
			mergeSortInPlace(array, start, middle);
			mergeSortInPlace(array, middle + 1, end);
			mergeInPlace(array, start, middle, middle + 1, end);
		}
	}
	
	/**
	 * Merge in place basically means to insert one into another
	 * @param array
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 */
	
	public static void mergeInPlace(int[] array, int start1, int end1, int start2, int end2){
		if(array == null || start1 > end1 || start2 > end2 || end1 > start2)
			return;
		/**
		 * Insert the second part into first part one by one
		 */
		while(start2 <= end2 && end1 <= end2){
			int tmp = array[start2];
			int index = end1;
			while(index >= start1 && array[index] > tmp){
				array[index + 1] = array[index];
				index--;
			}
			array[index + 1] = tmp;
			start2++;
			end1++;
		}
	}
	
	/**
	 * Most intuitive and inefficient way of mergeSort
	 * @param array
	 * @return
	 */
	public static int[] mergeSort(int[] array){
		if(array != null && array.length > 0){
			int middle = (array.length - 1)/2;
			int[] leftSub = subArray(array, 0, middle);
			int[] rightSub = subArray(array, middle + 1, array.length -1);
			int[] sortedLeft = (leftSub != null && leftSub.length > 1) ? mergeSort(leftSub) : leftSub;
			int[] sortedRight = (rightSub != null && rightSub.length > 1) ? mergeSort(rightSub) : rightSub;
			
			return	mergeTwoSortedArray(sortedLeft, sortedRight);
		}else{
			return null;
		}
	}
	
	public static int[] mergeTwoSortedArray(int[] arrayA, int[] arrayB){
		if(arrayA == null && arrayB == null)
			return null;
		else if(arrayA == null)
			return arrayB;
		else if(arrayB == null)
			return arrayA;
		
		int[] result = new int[arrayA.length + arrayB.length];
		int indexA = 0;
		int indexB = 0;
		int index = 0;
		
		while(indexA < arrayA.length && indexB < arrayB.length){
			if(arrayA[indexA] < arrayB[indexB]){
				result[index] = arrayA[indexA++];
			}else{
				result[index] = arrayB[indexB++];
			}
			index++;
		}
		while(indexA < arrayA.length){
			result[index++] = arrayA[indexA++];
		}
		
		while(indexB < arrayB.length){
			result[index++] = arrayB[indexB++];
		}
		
		return result;
	}
	
	public static void mergeSort(int[] array, int[] helper, int start, int end){
		if(start < end && array != null && helper != null && array.length > 0
				&& array.length == helper.length){
			int middle = (start + end)/2;
			mergeSort(array, helper, start, middle);
			mergeSort(array, helper, middle + 1, end);
			merge(array, helper, start, middle, end);
		}
	}
	
	public static void mergeSortCopyOnce(int[] array, int[] helper, int start, int end){
		if(start < end && array != null){
			/**
			 * Only copy once when helper is empty
			 */
			if(helper == null){
				helper = new int[array.length];
				for(int i = 0; i < array.length; i++){
					helper[i] = array[i];
				}
			}
			int middle = (start + end)/2;
			/**
			 * swap the target array at next level
			 */
			mergeSort(helper, array, start, middle);
			mergeSort(helper, array, middle + 1, end);
			/**
			 * always put the most up to date data back into array
			 */
			mergeIntoHelper(helper, array, start, middle, end);
		}
	}
	/**
	 * First copy everything into helper array and merge it back to array
	 * 
	 * @param array
	 * @param helper
	 * @param start
	 * @param middle
	 * @param end
	 */
	public static void merge(int[] array, int[] helper, int start, int middle, int end){
		if(array == null || helper == null || start > middle || start > end || middle > end)
			return; 
		
		int indexA = start;
		int indexB = middle + 1;
		int index = start;
		
		for(int i = start; i <= end; i++){
			helper[i] = array[i];
		}
					
		while(indexA <= middle && indexB <= end){
			if(helper[indexA] < helper[indexB]){
				array[index] = helper[indexA++];
			}else{
				array[index] = helper[indexB++];
			}
			index++;
		}
		/**
		 * In this condition, there is no need to consider the right half of the original array
		 * Since they are already in place
		 */
		while(indexA <= middle){
			array[index++] = helper[indexA++];
		}
	}
	
	/**
	 * merge the array into helper without need to copy it into helper at first place
	 * @param array
	 * @param helper
	 * @param start
	 * @param middle
	 * @param end
	 */
	private static void mergeIntoHelper(int[] array, int[] helper, int start, int middle, int end){
		if(array == null || helper == null || start > middle || start > end || middle > end)
			return; 
		
		int indexA = start;
		int indexB = middle + 1;
		int index = start;
				
		while(indexA <= middle && indexB <= end){
			if(array[indexA] < array[indexB]){
				helper[index] = array[indexA++];
			}else{
				helper[index] = array[indexB++];
			}
			index++;
		}
		while(indexA <= middle){
			helper[index++] = array[indexA++];
		}
		
		while(indexB <= end){
			helper[index++] = array[indexB++];
		}
	}
	
	/**
	 * Iterative(Bottom-Up approach to sort the array)
	 * 
	 * @param array
	 */
	public static void mergeSortIterative(int[] array){
		if(array == null || array.length <= 1)
			return;
		
		int[] helper = new int[array.length];
		int step = 2;
		int[] tmp = null;
		
		while(step < array.length){
			for(int start = 0; start < array.length; start += step){
				if(start == array.length - 1){
					helper[start] = array[start];
					break;
				}
				
				int end = (start + step >= array.length) ? array.length - 1 : start + step - 1;
				int middle = (start + step/2 >= array.length) ? array.length - 1 : start + step/2 - 1;
				
				mergeIntoHelper(array, helper, start, middle, end);
			}
			/**
			 * swap the reference of two array so that we don't consistently copy content of array into helper each iteration(as in merge)
			 * just alternate between two array and copy back to array once  
			 */
			tmp = array;
			array = helper;
			helper = tmp;
			
			step *= 2;
		}
		
		/**
		 * when step is larger than or equal to size of array, how to calculate middle is a bit different
		 */
		mergeIntoHelper(array, helper, 0, step/2 - 1, array.length -1);
		/**
		 * Since the most up to date data is in helper, we need to copy it back
		 * only swap reference won't work, but will work in C++
		 */		
		for(int i = 0; i < array.length; i++){
			array[i] = helper[i];
		}
	}	
	
	/**
	 * 
	 * @param array
	 * @param start
	 * @param end
	 */
	public static final int CUTOFF = 6; // Since we are using median3, the cutOff should at least be 3 and quick sort is slow for small size
	
	public static void quickSort(int array[], int start, int end){
		if(array != null){
			if(end - start  >= CUTOFF){
				int pivotIndex = partition(array, start, end);
				quickSort(array, start, pivotIndex - 1);
				quickSort(array, pivotIndex + 1, end);
			}else{
				insertionSort(array, start, end);
			}
		}
	}
	
	public static int medianOfThree(int[] array, int start, int middle, int end){
		if(array[start] > array[middle])
			swap(array, start, middle);
		
		if(array[start] > array[end])
			swap(array, start, end);
		
		if(array[middle] > array[end])
			swap(array, middle, end);
		
		return array[middle];		
	}
		
	private static int partition(int[] array, int start, int end){
		int middle = (start + end)/2;
		int pivot = medianOfThree(array, start, middle, end);
		//hiding the pivot
		swap(array, middle, end - 1);
		int indexA = start;
		int indexB = end - 1;
		
		while(true){
			/**
			 * Since we already know where the pivot is,
			 * we can do a much more aggressive way of swapping, always move forward
			 */
			while(array[++indexA] < pivot);
			while(array[--indexB] > pivot); 
			if(indexA < indexB)
				swap(array, indexA, indexB);
			else
				break;			
		}
		swap(array, indexA, end -1);
		return indexA;
	}
	
	/**
	 * Find the median of a sorted array
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	public static int median(int[] array, int start, int end){
		return array[(start + end)/2];
	}
	
	/**
	 * Divide the array into groups with length of 5 and calculate the median of the medians of each group
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	public static int medianOfMedianOfFive(int[] array, int start, int end){
		int size = end - start + 1;
		int groups = (size % 5 == 0) ? size/5: size/5 + 1;
		int[] medians = new int[groups];
		for(int i = 0; i < groups; i++){
			int startInner = i * 5 + start;
			int endInner = (startInner + 4 >= end) ? end : startInner + 4;
			insertionSort(array, startInner, endInner);
			medians[i] = median(array, startInner, endInner);
		}
		if(groups > 1)
			return median(medians, 0, groups -1);
		else
			return medians[0];
	}
	
	
	public static int partitionMedianOfMedianOfFive(int[] array, int start, int end, boolean increasing){
		int pivot = medianOfMedianOfFive(array, start, end);
		
//		System.out.println("start: " + start + " end: " + end + " pivot: " + pivot);
		return generalPartition(array, start, end, pivot, increasing);
	}
	
	public static void quickSortMedianOfMedianFive(int[] array, int start, int end){
		if(array != null){
			if(end >= start + CUTOFF){
				int pivotIndex = partitionMedianOfMedianOfFive(array, start, end, true);
				quickSortMedianOfMedianFive(array, start, pivotIndex - 1);
				quickSortMedianOfMedianFive(array, pivotIndex + 1, end);
			}else{
				insertionSort(array, start, end);
			}
		}
	}
	
	public static void quickSortRandom(int array[], int start, int end){
		if(start < end && array != null){
				int pivotIndex = randomPartition(array, start, end, true);
				quickSortRandom(array, start, pivotIndex - 1);
				quickSortRandom(array, pivotIndex + 1, end);
		}
	}
	
	public static <T extends Comparable<? super T>>
	void quickSortRandom(T array[], int start, int end){
		if(start < end && array != null){
				int pivotIndex = randomPartition(array, start, end, true);
				quickSortRandom(array, start, pivotIndex - 1);
				quickSortRandom(array, pivotIndex + 1, end);
		}
	}
	
	public static <T extends Comparable<? super T>>
	void quickSortRandom(T array[], int start, int end, Comparator<T> comp){
		if(start < end && array != null){
				int pivotIndex = randomPartition(array, start, end, true, comp);
				quickSortRandom(array, start, pivotIndex - 1, comp);
				quickSortRandom(array, pivotIndex + 1, end, comp);
		}
	}
	
	public static int randomPartition(int[] array, int start, int end, boolean increasing){
		/* take caution regarding to what do we need here */
		int pivotIndex = (int)Math.random()*(end - start + 1) + start;
//		System.out.println("start: " + start + " end: " + end + " pivot: " + array[pivotIndex]);
		//hiding the pivot
		swap(array, pivotIndex, end);
		
		return generalPartition(array, start, end, increasing);
	}
	
	public static <T extends Comparable<? super T>>
	int randomPartition(T[] array, int start, int end, boolean increasing){
		/* take caution regarding to what do we need here */
		int pivotIndex = (int)Math.random()*(end - start + 1) + start;
		//hiding the pivot
		swap(array, pivotIndex, end);
		
		return generalPartition(array, start, end, increasing);
	}
	
	public static <T extends Comparable<? super T>>
	int randomPartition(T[] array, int start, int end, boolean increasing, Comparator<T> comp){
		/* take caution regarding to what do we need here */
		int pivotIndex = (int)Math.random()*(end - start + 1) + start;
		//hiding the pivot
		swap(array, pivotIndex, end);
		
		return generalPartition(array, start, end, increasing, comp);
	}
	
	public static <T extends Comparable<? super T>>
	int randomPartition(List<T> array, int start, int end, boolean increasing){
		/* take caution regarding to what do we need here */
		int pivotIndex = (int)Math.random()*(end - start + 1) + start;
		//hiding the pivot
		swap(array, pivotIndex, end);
		
		return generalPartition(array, start, end, increasing);
	}
	
	/**
	 * Since we don't know where the pivot is, we should be extremely careful about the boundary condition
	 * @param array
	 * @param start
	 * @param end
	 * @param pivot
	 * @param increasing partition in increasing order or else
	 * @return
	 */
	public static int generalPartition(int[] array, int start, int end, int pivot, boolean increasing){
		int i = start;
		int j = end;
		while(true){
			/**
			 * Since we don't know where the pivot is, we need the pointer move slowly
			 * until we are sure in need of swap or break and position the pivot
			 */
			if(increasing){
				while(i < j && array[i] < pivot){ i++; }
				while(i < j && array[j] > pivot){ j--; }
			}else{
				while(i < j && array[i] > pivot){ i++; }
				while(i < j && array[j] < pivot){ j--; }
			}
			
			if(i < j)
				swap(array, i, j);
			else
				break;
			
			/**
			 *  For non-distinct values it is possible that the algorithm fails,
			 *  consider 4 4 4, this will render infinite loop,
			 *  we break it by move the slow pointer one step forward
			 */
			if(array[i] == array[j]){
				i++;
			}
		}
		return i;
	}
	/**
	 * General way of partition using fast and slow pointer
	 */
	public static int generalPartition(int[] array, int start, int end, boolean increasing){
		int pivot = array[end];
		int slow = start - 1;
		int fast = start;
		/**
		 * fast pointer ignores the larger ones and try to find a spot for it in the later half of the array
		 * only swap when there is room for the larger ones in the upper half
		 */
		while(fast < end){
			if((increasing && array[fast] <= pivot) || (!increasing && array[fast] >= pivot)){
				slow++;
				swap(array, fast, slow);
			}
			fast++;
		}
		/* keep the slow pointer always into play */
		slow++;
		
		/**
		 * swap the pivot to where it should be at
		 */
		swap(array, slow, end);
		return slow;
	}
	
	/**
	 * Generic type implementation of general partition for array and for list
	 */
	public static <T extends Comparable<? super T>>
	int generalPartition(T[] array, int start, int end, boolean increasing){
		T pivot = array[end];
		int slow = start - 1;
		int fast = start;

		while(fast < end){
			if((increasing && array[fast].compareTo(pivot) <= 0) || (!increasing && array[fast].compareTo(pivot) >= 0)){
				slow++;
				swap(array, fast, slow);
			}
			fast++;
		}
		slow++;
		
		swap(array, slow, end);
		return slow;
	}
	
	/**
	 * Generic type implementation of general partition for array and for list
	 */
	public static <T extends Comparable<? super T>>
	int generalPartition(T[] array, int start, int end, boolean increasing, Comparator<T> comp){
		T pivot = array[end];
		int slow = start - 1;
		int fast = start;

		while(fast < end){
			if((increasing && comp.compare(array[fast], pivot) <= 0) || (!increasing && comp.compare(array[fast], pivot) >= 0)){
				slow++;
				swap(array, fast, slow);
			}
			fast++;
		}
		slow++;
		
		swap(array, slow, end);
		return slow;
	}
	
	public static <T extends Comparable<? super T>>
	int generalPartition(List<T> array, int start, int end, boolean increasing){
		T pivot = array.get(end);
		int slow = start - 1;
		int fast = start;
		
		while(fast < end){
			if((increasing && array.get(fast).compareTo(pivot) <= 0) || (!increasing && array.get(fast).compareTo(pivot) >= 0)){
				slow++;
				swap(array, fast, slow);
			}
			fast++;
		}
		slow++;

		swap(array, slow, end);
		return slow;
	}
	
	/**
	 * k here means the kth smallest/largest number in the array, driver part of quick selection
	 * @param array
	 * @param k
	 * @param smallest - true when return the kth smallest, and false return the kth largest 
	 * @param random - true when using random partition, false when using median of median 5 
	 * @return
	 */
	public static int quickSelection(int[] array, int k, boolean smallest, boolean random){
		if(k > array.length)
			return Integer.MIN_VALUE;
		if(k > array.length / 2){
			k = array.length - k + 1;
			smallest = !smallest;
		}
		
		quickSelection(array, 0, array.length - 1, k, smallest, random);
		return k - 1; 
	}
	
	public static <T extends Comparable<? super T>>
	int quickSelection(T[] array, int k, boolean smallest){
		if(k > array.length)
			return Integer.MIN_VALUE;
		if(k > array.length / 2){
			k = array.length - k + 1;
			smallest = !smallest;
		}
		
		quickSelection(array, 0, array.length - 1, k, smallest);
		return k - 1; 
	}
	
	public static <T extends Comparable<? super T>>
	int quickSelection(List<T> array, int k, boolean smallest){
		if(k > array.size())
			return Integer.MIN_VALUE;
		if(k > array.size() / 2){
			k = array.size() - k + 1;
			smallest = !smallest;
		}
		
		quickSelection(array, 0, array.size() - 1, k, smallest);
		return k - 1; 
	}
	
	/**
	 * Recursion part of quick selection
	 */
	public static void quickSelection(int[] array, int start, int end, int k, boolean smallest, boolean random){
		/**
		 * This way to render code more reusable
		 */
		int pivotIndex = (random) ? randomPartition(array, start, end, smallest) : partitionMedianOfMedianOfFive(array, start, end, smallest);
//		System.out.println("start: " + start + " end: " + end + " pivot: " + array[pivotIndex]);
//		printArray(array);
		if(pivotIndex == k - 1)
			return;
		/* if the pivot index is larger than k, means the index of kth number is at the former half */
		else if(pivotIndex > k - 1)
			quickSelection(array, start, pivotIndex - 1, k, smallest, random);
		/* find the index of kth number on the later half */
		else
			quickSelection(array, pivotIndex + 1, end, k, smallest, random);
	}
	
	public static <T extends Comparable<? super T>>
	void quickSelection(T[] array, int start, int end, int k, boolean smallest){
		int pivotIndex = randomPartition(array, start, end, smallest);
		if(pivotIndex == k - 1)
			return;
		/* if the pivot index is larger than k, means the index of kth number is at the former half */
		else if(pivotIndex > k - 1)
			quickSelection(array, start, pivotIndex - 1, k, smallest);
		/* find the index of kth number on the later half */
		else
			quickSelection(array, pivotIndex + 1, end, k, smallest);
	}
	
	public static <T extends Comparable<? super T>>
	void quickSelection(List<T> array, int start, int end, int k, boolean smallest){
		int pivotIndex = randomPartition(array, start, end, smallest);
		if(pivotIndex == k - 1)
			return;
		/* if the pivot index is larger than k, means the index of kth number is at the former half */
		else if(pivotIndex > k - 1)
			quickSelection(array, start, pivotIndex - 1, k, smallest);
		/* find the index of kth number on the later half */
		else
			quickSelection(array, pivotIndex + 1, end, k, smallest);
	}
	
	public static <T extends Comparable<? super T>>
	boolean isIncreasing(T[] array){
		T last = array[0];
		for(T i : array){
			if(i.compareTo(last) < 0)
				return false;
			last = i;
		}
		return true;
	}
	
	/**
	 * Heap Sort:
	 * Building max heap
	 * Then swap the larger element to the rear positions
	 *  
	 * @param array
	 */
	public static void heapSort(int[] array){
		if(array == null || array.length == 0)
			return;
		
		buildHeap(array, array.length, false);
		for(int i = array.length - 1; i > 0; i--){
			swap(array, 0, i);
			percolateDown(array, 0, i - 1, false);		
		}
	}
	
	public static void buildHeap(int[] array, int len, boolean min){
		for(int i= len/2 - 1; i >= 0; i--){
			/**
			 * update all level of elements except for the lowest level
			 */
			percolateDown(array, i, len - 1, min);
		}
	}
	
	/**
	 * Push the array[index] down to where is should be located
	 *  and update the position of all the elements along the path
	 *  
	 * @param array
	 * @param index
	 * @param end
	 */
	private static void percolateDown(int[] array, int index, int end, boolean min){
		int bubble = array[index];
		int child = index * 2 + 1;
		while(child <= end){
			if(!min){
				if(child + 1 <= end && array[child + 1] > array[child])
						child++;
				
				if(bubble < array[child])
					array[index] = array[child];
				else
					break;
			}else{
				if(child + 1 <= end && array[child + 1] < array[child])
					child++;
			
				if(bubble > array[child])
					array[index] = array[child];
				else
					break;
			}
			
			index = child;
			child = child*2 + 1;
		}
		array[index] = bubble;
	}
	
	public static void heapSelection(int[] array, int k, boolean min){
		if(array == null || array.length == 0 || k > array.length)
			return;
		
		buildHeap(array, k, !min);
		for(int i = k; i < array.length; i++){
			if(min && array[i] < array[0]){
				swap(array, 0, i);
				percolateDown(array, 0, k - 1, !min);
			}else if(!min && array[i] > array[0]){
				swap(array, 0, i);
				percolateDown(array, 0, k - 1, !min);
			}
		}
	}
	
	/**
	 * Generic Type for heap manipulations
	 */
	public static <T extends Comparable<? super T>>
	void heapSort(T[] array){
		if(array == null || array.length == 0)
			return;
		
		buildHeap(array, array.length, false);
		for(int i = array.length - 1; i > 0; i--){
			swap(array, 0, i);
			percolateDown(array, 0, i - 1, false);		
		}
	}
	
	public static <T extends Comparable<? super T>>
	void buildHeap(T[] array, int len, boolean min){
		for(int i= len/2 - 1; i >= 0; i--){
			/**
			 * update all level of elements except for the lowest level
			 */
			percolateDown(array, i, len - 1, min);
		}
	}

	public static <T extends Comparable<? super T>>
	void percolateDown(T[] array, int index, int end, boolean min){
		T bubble = array[index];
		int child = index * 2 + 1;
		while(child <= end){
			if(!min){
				if(child + 1 <= end && array[child + 1].compareTo(array[child]) > 0)
						child++;
				
				if(bubble.compareTo(array[child]) < 0)
					array[index] = array[child];
				else
					break;
			}else{
				if(child + 1 <= end && array[child + 1].compareTo(array[child]) < 0)
					child++;
			
				if(bubble.compareTo(array[child]) > 0)
					array[index] = array[child];
				else
					break;
			}
			
			index = child;
			child = child*2 + 1;
		}
		array[index] = bubble;
	}
		
	public static <T extends Comparable<? super T>>
	void heapSelection(T[] array, int k, boolean min){
		if(array == null || array.length == 0 || k > array.length)
			return;
		
		buildHeap(array, k, !min);
		for(int i = k; i < array.length; i++){
			if(min && array[i].compareTo(array[0]) < 0){
				swap(array, 0, i);
				percolateDown(array, 0, k - 1, !min);
			}else if(!min && array[i].compareTo(array[0]) > 0){
				swap(array, 0, i);
				percolateDown(array, 0, k - 1, !min);
			}
		}
	}
	
	public static Integer[] priorityQueueSelection(Integer[] array, int k){
		Integer[] result = new Integer[k];
		return priorityQueueSelection(array, k, new IntegerPQSelectionComparator()).toArray(result);
	}
	
	private static class IntegerPQSelectionComparator implements Comparator<Integer>{
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2) * (-1);
		}
	}
	
	public static <T extends Comparable<? super T>>
	PriorityQueue<T> priorityQueueSelection(T[] array, int k, Comparator<T> comp){
		if(array == null || array.length == 0 || k > array.length)
			return null;
		
		PriorityQueue<T> heap = new PriorityQueue<T>(array.length, comp);
		for(int i = 0; i < k; i ++){
			heap.add(array[i]);
		}		
		
		for(int i = k; i < array.length; i++){
			/**
			 * For a max heap, the comparator will be counter intuitive as when comparing two elements,
			 * compare(a, b) > 0 when a < b, 
			 * 
			 * In current setting, only need to update heap when compare > 0, regardless of the comparator  
			 */
			if(comp.compare(array[i], heap.peek()) > 0){
				heap.poll();
				heap.add(array[i]);
			}
		}
		return heap;
	}
	
	/**
	 * Print the heap with each element exactly INLINE char long
	 * This could also be set as the value with most digits
	 * 
	 * valid index start from 0
	 * 
	 * @param heap
	 */
	public static final int INLINE = 3;
	public static void printHeap(int[] heap){
		if(heap == null || heap.length == 0)
			return;
		
		int height = heapHeight(heap);
		int level = 0;
		for(int i = 0; i < ((int)Math.pow(2, height) - 1) * INLINE; i++)
			System.out.print(" ");
		System.out.println(heap[0]);
		
		int index = 1;
		while(index < heap.length){
			level++;
			height--;
			
			int firstIndexInThisLevel = (int)Math.pow(2, level) - 1;
			int firstIndexInNextLevel = (int)Math.pow(2, level + 1) - 1;
			int firstPosition = (int)Math.pow(2, height) - 1;
			int interval = (int)Math.pow(2, height + 1) - 1;
			
			for(index = firstIndexInThisLevel; index < heap.length &&
					index < firstIndexInNextLevel; index++){
				if(index == firstIndexInThisLevel){
					for(int i = 0; i < firstPosition * INLINE; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < interval * INLINE; i++)
						System.out.print(" ");
				}
				System.out.print((index % 2 == 1)?"/" : "\\");
				for(int i = 0; i < INLINE - 1; i++)
					System.out.print(" ");
			}
			System.out.println();
			for(index = firstIndexInThisLevel; index < heap.length &&
					index < firstIndexInNextLevel; index++){
				if(index == firstIndexInThisLevel){
					for(int i = 0; i < firstPosition * INLINE; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < interval * INLINE; i++)
						System.out.print(" ");
				}
				System.out.print(heap[index]);
				for(int i = 0; i < INLINE - String.valueOf(heap[index]).length(); i++)
					System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	private static int heapHeight(int[] heap){
		int height = 0;
		while(heap.length/(1 << height) > 0)
			height++;
		
		return --height;
	}
	
	/**
	 * get the full permutation as a list given an array 
	 * @param array
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	LinkedList<T[]> fullPermutation(T[] array){
		LinkedList<T[]> result = null;
		if(array == null)
			return result;
		
		result = new LinkedList<T[]>();
		fullPermutationRecursively(array, 0, array.length - 1, result);		
		return result;
	}
	
	private static <T extends Comparable<? super T>>
	void fullPermutationRecursively(T[] array,
			int start, int end, LinkedList<T[]> result){
		if(start > end)
			return;
		else if(start == end){
			result.add(array.clone());
		}
		
		for(int i = start; i <= end; i++){
			if(array[start].equals(array[i]) && start != i)
				continue;
			ArrayUtil.swap(array, start, i);
			fullPermutationRecursively(array, start + 1, end, result);
			ArrayUtil.swap(array, start, i);
		}		
	}
	
	/**
	 * A more general Implementation of all subset of a certain array
	 * @param array
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	List<ArrayList<T>> powerSet(T[] array){
		if(array == null)
			return null;
		
		ArrayList<T> distinctEles = new ArrayList<T>();
		ArrayList<Integer> occurNum = new ArrayList<Integer>();
		
//		quickSortRandom(array, 0, array.length - 1);
//		for(int i = 0; i < array.length; i++){
//			int count = 1;
//			while(i + 1 < array.length && array[i] == array[i+1]){
//				count++;
//				i++;
//			}
//			distinctEles.add(array[i]);
//			occurNum.add(count);			
//		}		
		HashMap<T, Integer> map = new HashMap<T, Integer>();
		for(int i = 0; i < array.length; i++){
			if(!map.containsKey(array[i]))
				map.put(array[i], 1);
			else{
				int count = map.get(array[i]);
				map.remove(array[i]);
				map.put(array[i], count + 1);
			}
		}
		distinctEles.addAll(map.keySet());
		occurNum.addAll(map.values());
		
		
		LinkedList<ArrayList<T>> allNumLists = new LinkedList<ArrayList<T>>();
		allNumLists.add(null);
		
		for(int elementNum = 1; elementNum <= array.length; elementNum++){
			ArrayList<T> curResult = new ArrayList<T>(elementNum);
			for(int i = 0; i < elementNum; i++){
				curResult.add(null);
			}
			powerSetRecursively(distinctEles, occurNum, elementNum, 0,
					0, allNumLists, curResult);
		}
		
		return allNumLists;
	}
	
	private static <T extends Comparable<? super T>>
	void powerSetRecursively(ArrayList<T> distinctEles, ArrayList<Integer> occurNum,
			int elementNum, int curEleNum, int start, 
			LinkedList<ArrayList<T>> curNumList, ArrayList<T> curResult){
		/** if the current element number equals to the current level element number
		 * this will be a result
		 */
		if(curEleNum == elementNum){
			ArrayList<T> tmp = new ArrayList<T>();
			tmp.addAll(curResult);
			curNumList.add(tmp);
			return;
		}
		
		for(int i = start; i < distinctEles.size(); ++i){
			/* if the in order current element still not fully used
			 * it will be a valid candidate and thus included in the result
			 **/
			if(occurNum.get(i) > 0){
				occurNum.set(i, occurNum.get(i) - 1);
				/* update the number of the rest of this element */
				curResult.set(curEleNum, distinctEles.get(i));
				/**
				 * The key for this recursion to succeed is start
				 * Sine we want to use as more lower indexed elements as possible first
				 * And then use the same strategy recursively to higher indexed elements
				 * 
				 * Do not update start until either all lower indexed ones are used
				 *  or already reached the length limit  
				 */
				powerSetRecursively(distinctEles, occurNum, elementNum, curEleNum + 1,
						i, curNumList, curResult);
				occurNum.set(i, occurNum.get(i) + 1);
			}
		}
	}
	
	/**
	 * Find the continuous sub-array that render the sum maximum
	 * Since there is no way to always keep track of the maximum from and to 
	 * So we need a table to look up by maxSum
	 * 
	 * @param array
	 * @return
	 */
	public static int[] maxContinuousSubArray(int[] array){
		if(array == null)
			return null;
		
		int from = 0;
		int to = 0;
		long sum = 0, maxSum = 0;
		int maxValue = Integer.MIN_VALUE;
		HashMap<Long, int[]> map = new HashMap<Long, int[]>();
		
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
			to = i; // RANGE OF MAXIMUM SUB ARRAY TILL THIS POSITION
			if (sum > maxSum) {
				maxSum = sum;
				map.put(new Long(maxSum), new int[]{from, to});
			}else if (sum < 0) {
				from = i + 1; // RANGE OF MAXIMUM SUB ARRAY STARTS FROM THIS POSITION.
				sum = 0;
			}
			if(array[i] > maxValue)
				maxValue = array[i];
		}
		
		int[] result = null;
		if(maxValue > 0){
			int[] range = map.get(maxSum);
			result = Arrays.copyOfRange(array, range[0], range[1] + 1);
		}else 
			result = new int[]{maxValue}; 
 		
		return result;
	}
	
	/**
	 * find the max profit for a single transaction
	 * (a.k.a find the largest different between right(max) and left(min))
	 * 
	 * @param array
	 * @return
	 */
	public static int findMaxProfitSingleTransaction(int array[]){		
		if(array == null || array.length < 2)
			return 0;
		
		int profit = 0;
		int protentialProfit = 0;
		int smallest = array[0];
		int potentialSmallest = array[0];
		
		for(int i = 1; i < array.length; i++){
			protentialProfit = array[i] - potentialSmallest;
			if(protentialProfit > profit){
				smallest = potentialSmallest;
				profit = protentialProfit;
			}
			if(array[i] > smallest && profit < array[i] - smallest){
				profit = array[i] - smallest;
			}
			else if( array[i] < smallest ){
				potentialSmallest = array[i];
			}
		}
		return profit;
	}
	
	public static int maxDiffRightToLeft(int array[]){
		if(array == null || array.length < 2)
			return 0;
		int end = array.length - 1;
		
		int max = array[end];
		int maxDiff = array[end] - array[end - 1];
		
		for(int i = end - 1; i >= 0; i--){
			int diff = max - array[i];
			if(diff > maxDiff)
				maxDiff = diff;
			
			if(array[i] > max)
				max = array[i];
		}
		
		return (maxDiff < 0) ? 0 : maxDiff;
	}
	/**
	 * find the largest different between left(max) and right(min)
	 * @param array
	 * @return
	 */
	public static int maxDiffLeftToRight(int array[]){
		if(array == null || array.length < 2)
			return 0;
		
		int max = array[0];
		int maxDiff = array[0] - array[1];
		
		for(int i = 1; i < array.length; i++){
			int diff = max - array[i];
			if(diff > maxDiff)
				maxDiff = diff;
			
			if(array[i] > max)
				max = array[i];
		}
		
		return (maxDiff < 0) ? 0 : maxDiff;
	}
	
	/**
	 * Find pairs within an array that sum to a certain number
	 * No duplicate pairs
	 * For example sum = 10, array = [1,9,9,1] should be two pairs, but [1, 9, 1] should be only one pair
	 * Also, use index as value so that [5, 9, 1] will not emit 5,5 pair 
	 * 
	 * @param array
	 * @param sum
	 */
	public static void findPairsToSum(int[] array, int sum){
		if(array == null)
			return;
		
		Hashtable<Integer,Integer> table = new Hashtable<Integer,Integer>();
		int current = 0;
		for(int i = 0; i < array.length; i++){
			table.put((sum - array[i]), i);
			if(table.get(array[i]) != null){
				 current = table.get(array[i]);
				 table.remove(array[i]);
				 if(i != current){
					 System.out.println("Pair "+ array[current] + " " + array[i] + " satisfied the condition");
				 }
			}
		}
	}
	
	/**
	 * Find all sub-array that contain elements with sum and size k
	 * @param A
	 * @param sum
	 * @param N
	 * @param k
	 * @return
	 */
	public static ArrayList<int[]> findContinuousSubArrayToSumWithSize(int[] array, int sum, int k){
		if(k > array.length)
			return null;
			
		int subsum = 0;
		int i = 0;
		ArrayList<int[]> result = new ArrayList<int[]>();
		for(i = 0; i < k; i++){
			subsum += array[i];
		}
		for(i = k - 1; i < array.length; i++){
			subsum = (i > k-1) ? subsum + array[i] - array[i-k] : subsum;
			if(subsum == sum){
				result.add(Arrays.copyOfRange(array, i-(k-1), i + 1));
			}
		}
		return result;
	}
	
	/**
	 * find all continuous sub array to a certain sum based on increment
	 * array [-1, -3, 4, 5, -2, -3, -1]
	 * sum array [0, -1, -4, 0, 5, 3, 0, -1]
	 * 
	 * if we want to find all that sum to 0, we could just find all equal pairs
	 *  as for the segment within this range, no other increment
	 *  
	 * Similarly, if we want to find all that sum to 3, we could just find all pairs with increment of 3
	 *  as for the segment within this range, the sum is exactly 3
	 *   
	 * @param array
	 * @param sum
	 * @return
	 */
	public static LinkedList<int[]> findAllContinuousSubArrayToSum(int[] array, int sum){
		int[] sumArray = new int[array.length + 1];
		sumArray[0] = 0;
		for(int i = 0; i < array.length; i++){
			sumArray[i + 1] = sumArray[i] + array[i];
		}
		
		HashMap<Integer, ArrayList<Integer>> sumMap = new HashMap<Integer,ArrayList<Integer>>();
		HashMap<Integer, Integer> targetMap = new HashMap<Integer, Integer>();
		LinkedList<int[]> result = new LinkedList<int[]>();
		
		for(int i = 0; i < sumArray.length; i++){
			if(!sumMap.containsKey(sumArray[i])){
				ArrayList<Integer> sumList = new ArrayList<Integer>();
				sumList.add(i);
				sumMap.put(sumArray[i], sumList);
			}else{
				ArrayList<Integer> sumList = sumMap.get(sumArray[i]);
				sumList.add(i);
			}
			
			int target = sumArray[i] + sum;
			targetMap.put(target, sumArray[i]);
			if(targetMap.containsKey(sumArray[i])){
				ArrayList<Integer> sumList = sumMap.get(targetMap.get(sumArray[i]));
				for(int startIndex : sumList){
					if(startIndex != i){
						result.add(Arrays.copyOfRange(array, startIndex, i));
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * General Longest Common Continuous Subset  using DP	
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	T[] longestCommonContinuousSubset(T[] array1, T[] array2){
		if(array2 == null || array1 == null){
			return null;
		}
		
		final int M = array1.length;
		final int N = array2.length;
		int[][] L = new int[M][N];
		int max = 0;
		T[] common = null;
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				if(array1[i].equals(array2[j])){
					if(i == 0 || j == 0){
						L[i][j] = 1;
					}
					else{
						L[i][j] = L[i-1][j-1] + 1;
					}
					if(L[i][j] > max){
						max = L[i][j];
					}
					if(L[i][j] == max){
						common = Arrays.copyOfRange(array1, i - max + 1, i + 1);
					}
				}
				else L[i][j] = 0;
			}
		}
		return common;
	}
	
	/**
	 * Longest Common Sub Sequence with tracing back of what exactly the sequence is
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	List<T> longestCommonSequence(T[] array1, T[] array2){
		if(array2 == null || array1 == null){
			return null;
		}
		
		final int M = array1.length;
		final int N = array2.length;
		int[][] L = new int[M + 1][N + 1];
		List<T> common = new LinkedList<T>();
		for(int i = 0; i <= M; i++){
			for(int j = 0; j <= N; j++){
				if(i == 0 || j == 0){
					L[i][j] = 0;
				}else{
					if(array1[i - 1].equals(array2[j - 1])){
						L[i][j] = L[i -1][j -1] + 1;
					}
					else L[i][j] = Math.max(L[i -1][j], L[i][j - 1]);
				}
			}
		}
		System.out.println(L[M][N]);
//		backtrackLongestCommonSequence(L, array1, array2, M, N, common);
		trackLongestCommonSequenceIteratively(L, array1, array2, M, N, common);
		return common;
	}

	public static <T extends Comparable<? super T>>
	void backtrackLongestCommonSequence(int[][] LCStable, T[] array1, T[] array2,
			int i, int j, List<T> common){
	    if( i == 0 || j == 0)
	        return; 
	    else if( array1[i - 1].equals(array2[j - 1])){
	    	backtrackLongestCommonSequence(LCStable, array1, array2, i-1, j-1, common);
	    	common.add(array1[i - 1]);
	    }else{
	    	if( LCStable[i][j-1] > LCStable[i-1][j])
	    		backtrackLongestCommonSequence(LCStable, array1, array2, i, j-1, common);
	    	else
	    		backtrackLongestCommonSequence(LCStable, array1, array2, i-1, j, common);
	    }
	}
	
	public static <T extends Comparable<? super T>>
	void trackLongestCommonSequenceIteratively(int[][] L, T[] array1, T[] array2,
			int M, int N, List<T> common){
		int s1position = M, s2position = N;
		while (s1position != 0 && s2position != 0){
			if (array1[s1position - 1].equals(array2[s2position - 1])){
				common.add(array1[s1position - 1]);
				s1position--;
				s2position--;
			}
			else if (L[s1position][s2position - 1] >= L[s1position][s2position]){
				s2position--;
			}
			else{
				s1position--;
			}
		}
		Collections.reverse(common);
	}
	
	/**
	 * longest increasing sub sequence
	 * @param array
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	List<T> longestIncreasingSequence(T[] array){
		if(array == null){
			return null;
		}
		
		final int M = array.length;
		int[] L = new int[M];
		L[0] = 1;		
		int len = L[0];
		List<T> resultSeq = new LinkedList<T>();
		for(int i = 1; i < M; i++){
			L[i] = 1;
			for(int j = 0; j < i; j++){
				if(array[j].compareTo(array[i]) < 0){
					L[i] = Math.max(L[i], L[j] + 1);
				}
			}				
			len = Math.max(len, L[i]);
		}
		
		int tmp = len;
		for(int i = M - 1; i >= 0; i--){
			if(tmp == L[i]){
				resultSeq.add(array[i]);
				tmp--;
			}
			if(tmp == 0)
				break;
		}
		Collections.reverse(resultSeq);
		System.out.println(len);
		return resultSeq;
	}
	
	/**
	 * Search a given element within 
	 * [[1][2][3][4[5]]
	 * [1,2,3,6,5,4,9,7,8,10,11,12,15,13,14]
	 * 
	 * @param array
	 * @param key
	 * @return
	 */
	public static  <T extends Comparable<? super T>>
	int searchBucketOrderedArray(T[] array, T key){
		int totalBuckets = (int)Math.sqrt(array.length * 2);//calculate out the total buckets number
		if(array.length != ((totalBuckets * (totalBuckets + 1))/2)){
			System.out.println("The array provided is not full");
		}
		SearchResult result = searchBucketOrderedArray(array, key, 1, totalBuckets);
		if(result.getIndex() < 0)
			return -1;
		else
			return result.getIndex();
	}
	
	private static  <T extends Comparable<? super T>>
	SearchResult searchBucketOrderedArray(T[] array, T key, int startBucket, int endBucket){
		if(startBucket > endBucket)
			return new SearchResult(-1, false);
		if(startBucket == endBucket)
			return searchWithinBucket(array, key, startBucket);
				
		int elementsInRange = ((endBucket - startBucket + 1) * (startBucket + endBucket)) / 2;
		int medianElementsInRange = elementsInRange/2;
		int medianBucket = startBucket;
		while(medianElementsInRange > 0 && medianBucket < endBucket){
			medianBucket++;
			medianElementsInRange -= medianBucket; 
		}
		SearchResult tmp = searchWithinBucket(array, key, medianBucket);
		if(tmp.getIndex() > 0)
			return tmp;
		else if(tmp.searchHigher())
			return searchBucketOrderedArray(array, key, medianBucket + 1, endBucket);
		else
			return searchBucketOrderedArray(array, key, startBucket, medianBucket - 1);
	}
	
	private static  <T extends Comparable<? super T>>
	SearchResult searchWithinBucket(T[] array, T key, int bucket){
		int end = ((bucket * (bucket + 1)) / 2) - 1;
		end = (end > array.length - 1) ? array.length - 1 : end;
		
		int start = ((bucket * (bucket + 1)) / 2) - bucket;
		
		int higherCount = 0;
		for(int i = start; i <= end; i++){
			if(array[i].equals(key))
				return new SearchResult(i, false);
			else if(array[i].compareTo(key) < 0)
				higherCount++;
		}
		return (higherCount > 0) ? new SearchResult(-1, true) : new SearchResult(-1, false);
	}
	
	/**
	 * Result Wrapper such that if not in, then index will be -1, 
	 * searchHigher indicating search higher half or the lower half
	 */	
	private static class SearchResult{
		private int index;
		private boolean searchHigher;
		protected SearchResult(int index, boolean searchHigher){
			this.index = index;
			this.searchHigher = searchHigher;
		}
		
		protected int getIndex(){
			return index;
		}
		
		protected boolean searchHigher(){
			return searchHigher;
		}
	}
}
