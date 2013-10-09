package edu.upenn.yiranqin.datastructures;

public class MyAVLTreeNode<T extends Comparable<? super T>> extends MyBSTNode<T>{
	public MyAVLTreeNode<T> leftChild;
	public MyAVLTreeNode<T> rightChild;
	private T sumUp;
	private int height = 0;
	private int depth = 0;
	private boolean isVisited = false;
	
	public MyAVLTreeNode(T v){
		super(v);
		leftChild = null;
		rightChild = null;
		height = 0;
		depth = 0;
		isVisited = false;
	}
	
	private MyAVLTreeNode(T v, MyAVLTreeNode<T> leftChild, MyAVLTreeNode<T> rightChild,
			int height,  int depth, boolean isVisited, T sumUp){
		super(v);
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.height = height;
		this.depth = depth;
		this.isVisited = isVisited;
		this.sumUp = sumUp;
	}
	
	public MyAVLTreeNode<T> getLeftChild(){
		return leftChild;
	}
	
	public MyAVLTreeNode<T> getRightChild(){
		return rightChild;
	}

	public void setLeftChild(MyAVLTreeNode<T> node){
		leftChild = node;
//		if(node == null)
//			leftChild = null;
//		else
//			leftChild = node.clone();
	}
	
	public void setRightChild(MyAVLTreeNode<T> node){
		rightChild = node;
//		if(node == null)
//			rightChild =  null;
//		else 
//			rightChild = node.clone();
	}
	
	public T getSumUp(){
		return sumUp;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public boolean isVisited(){
		return isVisited;
	}
	
	public void setSumUp(T sum){
		sumUp = sum; 
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public void setDepth(int d){
		depth = d;
	}
	
	public void setVisited(boolean v){
		isVisited = v;
	}
	
	@Override
	/* is it really needed as a deep copy ?*/
	public MyAVLTreeNode<T> clone(){
		return new MyAVLTreeNode<T>(super.getData(), 
				(this.leftChild == null) ? null : this.leftChild.clone(),
				(this.rightChild == null) ? null : this.rightChild.clone(),
				this.height,
				this.depth,
				this.isVisited,
				this.sumUp);
	}
}
