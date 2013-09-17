package edu.upenn.yiranqin.datastructures;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

/**
 * 
 * @author qyr1987
 * A Heap Implementation with array and fixed(maximum) underlying size
 */
public class MyArrayHeap <T extends Comparable<? super T>> {
	private T[] values;
	private boolean isMin = true;
	private int size = 0;
	
	public MyArrayHeap(T[] array, boolean isMin, boolean isFilledBuffer){
		values = array;
		this.isMin = isMin;
		if(isFilledBuffer){
			size = array.length;
			buildHeap();
		}
	}
	
	public void insert(T element){
		size++;
		if(size > values.length){
			System.err.println("Exceed the maximum capacity");
			size--;
			return;
		}
		percolateUp(size - 1, element);
	}
	
	private void percolateUp(int index, T element){
		T bubble = element;
		for(int parent = (index - 1)/2; index > 0 && parent >= 0; ){
			if(isMin && values[parent].compareTo(bubble) > 0){
				values[index] = values[parent];
			}else if(!isMin && values[parent].compareTo(bubble) < 0){
				values[index] = values[parent];
			}else
				break;
			
			index = parent;
			parent = (index - 1)/2;
		}
		values[index] = bubble;
	}
	
	public void delete(){
		if(size > 0){
			size--;
			if(size > 1){
				ArrayUtil.swap(values, 0, size);
				percolateDown(0);
			}else if(size == 1){
				ArrayUtil.swap(values, 0, 1);
			}
		}else
			return;
	}
	
	public T peek(){
		if(size == 0)
			return null;
		else
			return values[0];
	}
	
	public int size(){
		return size;
	}
	
	public T[] getValue(){
		return values;
	}
	
	private void buildHeap(){
		for(int i= size/2 - 1; i >= 0; i--){
			/**
			 * update all level of elements except for the lowest level
			 */
			percolateDown(i);
		}
	}
	
	private void percolateDown(int index){		
		T bubble = values[index];
		int child = index * 2 + 1;
		while(child <= size - 1){
			if(!isMin){
				if(child + 1 <= size - 1 && values[child + 1].compareTo(values[child]) > 0)
						child++;
				
				if(bubble.compareTo(values[child]) < 0)
					values[index] = values[child];
				else
					break;
			}else{
				if(child + 1 <= size - 1 && values[child + 1].compareTo(values[child]) < 0)
					child++;
			
				if(bubble.compareTo(values[child]) > 0)
					values[index] = values[child];
				else
					break;
			}
			
			index = child;
			child = child*2 + 1;
		}
		values[index] = bubble;
	}
}