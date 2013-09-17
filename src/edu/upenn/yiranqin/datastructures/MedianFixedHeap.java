package edu.upenn.yiranqin.datastructures;

/**
 *  a data structure that always maintain the median of all elements
 * median will be fixed at lowerMax
 */ 
public class MedianFixedHeap <T extends Comparable<? super T>>{
	private MyHeap<T> minHeap;
	private MyHeap<T> maxHeap;
	
	public MedianFixedHeap(){
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
		
		T lowerMax = maxHeap.peek();
		T upperMin = minHeap.peek();
		if(lowerMax == null && upperMin == null){
			maxHeap.insert(element);
			return;
		}else if(element.compareTo(lowerMax) > 0){
			if(maxHeap.size() > minHeap.size()){
				minHeap.insert(element);
				return;
			}else if(element.compareTo(upperMin) > 0 ){
				minHeap.delete();
				maxHeap.insert(upperMin);
				minHeap.insert(element);
				return;
			}
		}
		//lower than lowerMax or lower than upperMin while higher than lowerMax
		maxHeap.delete();
		maxHeap.insert(element);
		minHeap.insert(lowerMax);
	}
	
	public T getMedian(){
		return maxHeap.peek();
	}
	
	public void deletMedian(){
		if(maxHeap.size() > minHeap.size())
			maxHeap.delete();
		else{
			T lowerMax = maxHeap.peek();
			T upperMin = minHeap.peek();
			if(upperMin == null && lowerMax == null)
				return; 
			
			maxHeap.delete();
			if(upperMin != null){
				minHeap.delete();
				maxHeap.insert(upperMin);
			}
		}
	}
}

