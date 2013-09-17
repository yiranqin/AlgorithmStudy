package edu.upenn.yiranqin.datastructures;

/**
 *  a data structure that always maintain the median of all elements
 * No guarantee where the median if could be either lowerMax or upperMin
 */ 
public class MedianHeap <T extends Comparable<? super T>>{
	private MyHeap<T> minHeap;
	private MyHeap<T> maxHeap;
	
	public MedianHeap(){
		minHeap = new MyHeap<T>(true);
		maxHeap = new MyHeap<T>(false);
	}
	
	public MyHeap<T> getLower(){
		return maxHeap;
	}
	
	public MyHeap<T> getUpper(){
		return minHeap;
	}
	
	public int size(){
		return minHeap.size() + maxHeap.size();
	}
	
	public void insert(T element){
		if(element == null)
			return;
		
		if(maxHeap.size() > minHeap.size()){
			minHeap.insert(element);
		}else{
			T lowerMax = maxHeap.peek();
			T upperMin = minHeap.peek();
			if(lowerMax == null && upperMin == null)
				maxHeap.insert(element);			
			else if(element.compareTo(upperMin) > 0){
				minHeap.delete();
				maxHeap.insert(upperMin);
				minHeap.insert(element);
			}else
				maxHeap.insert(element);
			
		}
//		maxHeap.printHeap();
//		minHeap.printHeap();
//		System.out.println(maxHeap.peek() + " " + minHeap.peek());
	}
	
	public T getMedian(){
		T lowerMax = maxHeap.peek();
		T upperMin = minHeap.peek();
		if(maxHeap.size() > minHeap.size())
			return lowerMax;
		else{
			if(upperMin == null && lowerMax == null)
				return null; 
			else if(upperMin == null)
				return maxHeap.peek();
			else
				return (lowerMax.compareTo(upperMin) < 0) ? lowerMax : upperMin;
		} 
	}
	
	public void deletMedian(){
		if(maxHeap.size() > minHeap.size())
			maxHeap.delete();
		else{
			T lowerMax = maxHeap.peek();
			T upperMin = minHeap.peek();
			if(upperMin == null && lowerMax == null)
				return; 
			else if(upperMin == null)
				maxHeap.delete();
			else if(lowerMax.compareTo(upperMin) < 0){
				maxHeap.delete();
				minHeap.delete();
				maxHeap.insert(upperMin);
			}else
				minHeap.delete();
		}
	}
}
