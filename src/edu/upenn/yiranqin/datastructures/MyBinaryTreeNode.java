package edu.upenn.yiranqin.datastructures;

/**
 * Since what we actually want is allow different byte of trees
 *  to get and set their own type of children but with a unified access
 *  Reflection will need to be used here so that when figuring out exact type of the tree
 *  there is a access to the children fields
 *  
 *  Also, these fields need to be declared as public to be visible if simply using getField
 *  if still better keep fields private, getDeclaredField will not force public, 
 *  but would incur insecure access Don't use it 
 *  
 * @author qyr1987
 *
 * @param <T>
 */
public class MyBinaryTreeNode<T extends Object> {
	public MyBinaryTreeNode<T> leftChild;
	public MyBinaryTreeNode<T> rightChild;
	private T data;
	
	public MyBinaryTreeNode(T v){
		leftChild = null;
		rightChild = null;
		data = v;
	}
	
	public MyBinaryTreeNode(MyBinaryTreeNode<T> l, MyBinaryTreeNode<T> r, T v){
		leftChild = l;
		rightChild = r;
		data = v;
	}
	
	@SuppressWarnings("unchecked")
	public MyBinaryTreeNode<T> getLeftChild(){
		MyBinaryTreeNode<T> result = null;
		try{
			result = (MyBinaryTreeNode<T>) this.getClass().getField("leftChild").get(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public MyBinaryTreeNode<T> getRightChild(){
		MyBinaryTreeNode<T> result = null;
		try{
			result = (MyBinaryTreeNode<T>) this.getClass().getField("rightChild").get(this);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public void setData(T Data){
		data = Data;
	}
		
	public void setLeftChild(MyBinaryTreeNode<T> node){
		try{
			this.getClass().getField("leftChild").set(this, node);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void setRightChild(MyBinaryTreeNode<T> node){
		try{
			this.getClass().getField("rightChild").set(this, node);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public T getData(){
		return data;
	}
	
	@Override
	public String toString(){
		return data.toString();
	}
	
//	public abstract MyBinaryTreeNode<T> getLeftChild();
//	
//	public abstract MyBinaryTreeNode<T> getRightChild();
//	
//	public abstract void setData(T Data);
//	
//	public abstract T getData();
//	
//	public abstract void setLeftChild(MyBinaryTreeNode<T> node);
//	
//	public abstract void setRightChild(MyBinaryTreeNode<T> node);
//	
//	@Override
//	public abstract String toString();
}
