package edu.upenn.yiranqin.datastructures;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class DataStructureHandler {
	public static void main(String[] argv){
		new DataStructureHandler().start();
	}
	
	public void start(){
		int size = 5;
		boolean minHeap = false;
		Integer[] distinctArray = ArrayUtil.generateDistinctIntegerArray(size);//ArrayUtil.generateRandomArray(size);//
	
		MyHeap<Integer> heap = new MyHeap<Integer>(distinctArray, minHeap);
		heap.printHeap();
		heap.insert(55);
		heap.insert(66);
		heap.insert(6);
		heap.insert(5);
		heap.printHeap();
		
		heap.delete();
		heap.printHeap();
		heap.delete();
		heap.printHeap();
		heap.delete();
		heap.printHeap();
		
		heap = new MyHeap<Integer>(true);
		for(int i = 1; i <= size; i++){
			heap.insert(new Integer((int)(Math.random() * size + 1)));
		}
		heap.printHeap();
		for(int i = 0; i < size/2; i++){
			heap.delete();
		}
		heap.printHeap();
		
		MedianFixedHeap<Integer> medianHeap = new MedianFixedHeap<Integer>();
		for(Integer i = 1; i <= size; i++){
			medianHeap.insert(i);
			System.out.println(medianHeap.getMedian());
		}
		
		heap = medianHeap.getLower();
		heap.printHeap();
		
		heap = medianHeap.getUpper();
		heap.printHeap();
		
		for(int i = 0; i < size; i++){
			System.out.println(medianHeap.getMedian());
			medianHeap.deletMedian();
		}
		
//		Integer[] array = new Integer[size];
//		MyArrayHeap<Integer> heap1 = new MyArrayHeap<Integer>(array, true, false);
//		for(int i = 0; i <= size ; i ++){
//			heap1.insert(size - i);
//			ArrayUtil.printArray(heap1.getValue());
//		}
//		
//		for(int i = 0; i < size ; i ++){
//			heap1.delete();
//			ArrayUtil.printArray(heap1.getValue());
//		}
	}
	
	

}
