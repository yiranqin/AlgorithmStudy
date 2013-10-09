package edu.upenn.yiranqin.datastructures;

public class MyBSTNode<T extends Comparable<? super T>> extends MyBinaryTreeNode<T>{
	public MyBSTNode<T> leftChild;
	public MyBSTNode<T> rightChild;
	
	public MyBSTNode(T v){
		super(v);
		leftChild = null;
		rightChild = null;
	}
	
	@SuppressWarnings("unchecked")
	public MyBSTNode<T> getLeftChild(){
		MyBSTNode<T> result = null;
		try{
			result = (MyBSTNode<T>) this.getClass().getField("leftChild").get(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public MyBSTNode<T> getRightChild(){
		MyBSTNode<T> result = null;
		try{
			result = (MyBSTNode<T>) this.getClass().getField("rightChild").get(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	public void setLeftChild(MyBSTNode<T> node){
		try{
			this.getClass().getField("leftChild").set(this, node);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void setRightChild(MyBSTNode<T> node){
		try{
			this.getClass().getField("rightChild").set(this, node);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public int compareTo(MyBSTNode<T> other){
		return this.getData().compareTo(other.getData());
	}
}
