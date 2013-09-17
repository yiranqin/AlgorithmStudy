package edu.upenn.yiranqin.datastructures;

import java.util.ArrayList;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

/**
 * 
 * @author qyr1987
 * A Heap Implementation with index 0 element a null as sentinel
 */
public class MyHeap <T extends Comparable<? super T>> {
	private ArrayList<T> values;
	private boolean isMin = true;
	private int size = 0;
	
	public MyHeap(T[] array, boolean isMin){
		values = new ArrayList<T>(array.length + 1);
		values.add(null);
		for(T element : array){
			values.add(element);
		}
		size = array.length;
		this.isMin = isMin;
		buildHeap();
	}
	
	public MyHeap(boolean isMin){
		values = new ArrayList<T>(1);
		this.isMin = isMin;
		size = 0;
		values.add(null);
	}
	
	public void insert(T element){
		ArrayList<T> tmp;
		size++;
		if(size == values.size()){
			tmp = new ArrayList<T>(size * 2 + 1);
			for(T value : values){
				tmp.add(value);
			}
			/* padding the newly allocated arraylist so just use list.set will be fine */
			for(int i = values.size() + 1; i <= size * 2; i++){
				tmp.add(null);
			}
			values = tmp;
		}
		percolateUp(size, element);
	}
	
	private void percolateUp(int index, T element){
		T bubble = element;
		for(int parent = index/2; parent > 0; ){
			if(isMin && values.get(parent).compareTo(bubble) > 0){
				values.set(index, values.get(parent));
			}else if(!isMin && values.get(parent).compareTo(bubble) < 0){
				values.set(index, values.get(parent));
			}else
				break;
			
			index = parent;
			parent = parent/2;
		}
		values.set(index, bubble);
	}
	
	public void delete(){
		if(size > 2){
			ArrayUtil.swap(values, 1, size);
			size--;
			percolateDown(1);
		}else if(size > 0){
			values.set(size - 1, values.get(size));
			size--;
		}else
			return;
	}
	
	public T peek(){
		if(size == 0)
			return null;
		else
			return values.get(1);
	}
	
	public int size(){
		return size;
	}
	
	private void buildHeap(){
		for(int i= size/2 ; i > 0; i--){
			/**
			 * update all level of elements except for the lowest level
			 */
			percolateDown(i);
		}
	}
	
	private void percolateDown(int index){
		T bubble = values.get(index);
		int child = index * 2;
		while(child <= size){
			if(!isMin){
				if(child + 1 <= size && values.get(child + 1).compareTo(values.get(child)) > 0)
						child++;
				
				if(bubble.compareTo(values.get(child)) < 0)
					values.set(index, values.get(child));
				else
					break;
			}else{
				if(child + 1 <= size && values.get(child + 1).compareTo(values.get(child))< 0)
					child++;
			
				if(bubble.compareTo(values.get(child)) > 0)
					values.set(index, values.get(child));
				else
					break;
			}
			
			index = child;
			child = child*2;
		}
		values.set(index, bubble);
	}
	
	public void printHeap(){
		if(values == null || size == 0)
			return;
		
		int height = heapHeight();
		int level = 0;
		final int INLINE = longestElementLen();;
		
		for(int i = 0; i < ((int)Math.pow(2, height) - 1) * INLINE; i++)
			System.out.print(" ");
		System.out.println(values.get(1));
		
		int index = 1;
		while(index < size){
			level++;
			height--;
			
			int firstIndexInThisLevel = (int)Math.pow(2, level) - 1;
			int firstIndexInNextLevel = (int)Math.pow(2, level + 1) - 1;
			int firstPosition = (int)Math.pow(2, height) - 1;
			int interval = (int)Math.pow(2, height + 1) - 1;
			
			for(index = firstIndexInThisLevel; index < size &&
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
			for(index = firstIndexInThisLevel; index < size &&
					index < firstIndexInNextLevel; index++){
				if(index == firstIndexInThisLevel){
					for(int i = 0; i < firstPosition * INLINE; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < interval * INLINE; i++)
						System.out.print(" ");
				}
				System.out.print(values.get(index + 1));
				for(int i = 0; i < INLINE - values.get(index + 1).toString().length(); i++)
					System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	private int longestElementLen(){
		int maxLen = 0;
		for(T element : values){
			if(element == null)
				continue;
			int curLen = element.toString().length();
			if(curLen > maxLen)
				maxLen = curLen;
		}
		return maxLen;
	}
	
	private int heapHeight(){
		int height = 0;
		while(size/(1 << height) > 0)
			height++;
		
		return --height;
	}
}
