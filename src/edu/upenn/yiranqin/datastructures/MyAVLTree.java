package edu.upenn.yiranqin.datastructures;

import java.util.ArrayDeque;
import java.util.Stack;

public class MyAVLTree<T extends Comparable<? super T>> {
	private MyAVLTreeNode<T> root = null;
	
	public MyAVLTree(T[] values){
		root = buildAVLTree(values);
	}
	
	public MyAVLTreeNode<T> getRoot(){
		return root;
	}
	
	public MyAVLTreeNode<T> buildAVLTree(T[] values){
		if(values != null){
			for(T data : values){
				root = insertNode(data, root);
			}
			return root;
		}else
			return null;
	}
	
	public MyAVLTreeNode<T> insertNode(T x, MyAVLTreeNode<T> root){
		if(root == null){
			root= new MyAVLTreeNode<T>(x);
		}
		else if(x.compareTo(root.getData()) < 0){
			root.setLeftChild( insertNode(x, root.getLeftChild()));
			/* Since we already computed out the height for each node and each node preserve this data
			 * we can just reference to it
			 * 
			 * Alternative is to compute every time
			 **/
//			if((TreeUtil.Height(root.getLeftChild()) - TreeUtil.Height(root.getRightChild())) == 2){
			int leftHeight = (root.getLeftChild() == null) ? -1 : root.getLeftChild().getHeight();
			int rightHeight = (root.getRightChild() == null) ? -1 : root.getRightChild().getHeight();
			if((leftHeight - rightHeight) == 2){
				if(x.compareTo(root.getLeftChild().getData()) < 0){
					root = singleRotateWithLeft(root);
				}
				else{
					root = doubleRotateWithLeft(root);
				}
			}
		}
		else if(x.compareTo(root.getData()) > 0){
			root.setRightChild( insertNode(x, root.getRightChild()));
			/* Since we already computed out the height for each node and each node preserve this data
			 * we can just reference to it
			 **/
//			if((TreeUtil.Height(root.getRightChild()) - TreeUtil.Height(root.getLeftChild())) == 2){
			int leftHeight = (root.getLeftChild() == null) ? -1 : root.getLeftChild().getHeight();
			int rightHeight = (root.getRightChild() == null) ? -1 : root.getRightChild().getHeight();
			if((rightHeight - leftHeight) == 2){
				if(x.compareTo(root.getRightChild().getData()) > 0){
					root = singleRotateWithRight(root);
				}
				else{
					root = doubleRotateWithRight(root);
				}
			}
		}
		root.setHeight( Math.max(TreeUtil.Height(root.getLeftChild()), TreeUtil.Height(root.getRightChild())) + 1 );
		return root;
	}
	
	/**
	 * Single and Double Rotation using the algorithm and notation from Data Structure and Algorithm Analysis in C 
	 * @param K2
	 * @return
	 */
	private MyAVLTreeNode<T> singleRotateWithLeft(MyAVLTreeNode<T> K2){
		MyAVLTreeNode<T> K1 = K2.getLeftChild();
		K2.setLeftChild( K1.getRightChild());
		K1.setRightChild( K2);
		K2.setHeight( Math.max(TreeUtil.Height(K2.getLeftChild()), TreeUtil.Height(K2.getRightChild())) + 1);
		K1.setHeight( Math.max(TreeUtil.Height(K1.getLeftChild()), K2.getHeight()) + 1);
		return K1;
	}
	
	private MyAVLTreeNode<T> singleRotateWithRight(MyAVLTreeNode<T> K1){
		MyAVLTreeNode<T> K2 = K1.getRightChild();
		K1.setRightChild( K2.getLeftChild());
		K2.setLeftChild( K1);
		K1.setHeight( Math.max(TreeUtil.Height(K1.getLeftChild()), TreeUtil.Height(K1.getRightChild())) + 1);
		K2.setHeight( Math.max(K1.getHeight(), TreeUtil.Height(K2.getRightChild())));
		return K2;
	}
	
	private MyAVLTreeNode<T> doubleRotateWithLeft(MyAVLTreeNode<T> K3){
		K3.setLeftChild( singleRotateWithRight(K3.getLeftChild()));
		return K3 = singleRotateWithLeft(K3);
	}
	
	private MyAVLTreeNode<T> doubleRotateWithRight(MyAVLTreeNode<T> K1){
		K1.setRightChild( singleRotateWithLeft(K1.getRightChild()));
		return K1 = singleRotateWithRight(K1);
	}
	
	public void labelAllDepth(MyAVLTreeNode<T> root){
		if(root != null){
			if(root.getLeftChild() != null && root.getRightChild() != null){
				root.getLeftChild().setDepth( root.getDepth() + 1);
				root.getRightChild().setDepth( root.getDepth() + 1);
				labelAllDepth(root.getLeftChild());
				labelAllDepth(root.getRightChild());
			}
			else if (root.getLeftChild() != null){
				root.getLeftChild().setDepth( root.getDepth() + 1);
				labelAllDepth(root.getLeftChild());
			}
			else if(root.getRightChild() != null){
				root.getRightChild().setDepth( root.getDepth() + 1);
				labelAllDepth(root.getRightChild());
			}	
		}
	}
	
	public void printAllDepth(MyAVLTreeNode<T> root){
		if(root != null){
			printAllDepth(root.getLeftChild());
			System.out.println("Data: "+root.getData()+" Depth: "+root.getDepth());
			printAllDepth(root.getRightChild());
		}
	}
	
	public void printAllDepthPreorder(MyAVLTreeNode<T> root){
		if(root == null)
			return;
		Stack<MyAVLTreeNode<T>> s = new Stack<MyAVLTreeNode<T>>();
		s.push(root);
		while( !s.empty()){
			MyAVLTreeNode<T> current = s.pop();
			System.out.println("Data: "+ current +" Depth: "+ current.getDepth());
			if(current.getRightChild() != null){ s.push(current.getRightChild());}
			if(current.getLeftChild() != null){ s.push(current.getLeftChild());}
		}
	}
	
	public void printAllDepthInOrder(MyAVLTreeNode<T> root){
		Stack<MyAVLTreeNode<T>> s = new Stack<MyAVLTreeNode<T>>();
		while(root != null || !s.empty()){
			if(root != null){
				s.push(root);
				root = root.getLeftChild();
			}else{
				root = s.pop();
				System.out.println("Data: " + root + " Depth: " + root.getDepth());
				root = root.getRightChild();
			}
		}
	}
	
	public void printAllDepthPostorder(MyAVLTreeNode<T> root){
		Stack<MyAVLTreeNode<T>> s = new Stack<MyAVLTreeNode<T>>();
		if(root != null){
			s.push(root);
		}
		while (! s.empty()){
			root = s.peek();
			if (root.getLeftChild() != null && !root.getLeftChild().isVisited()){
				s.push(root.getLeftChild());
			}
			else if ( root.getRightChild() != null && !root.getRightChild().isVisited()){
				s.push(root.getRightChild());
			}
	        else{
	        	System.out.println("Data: "+  root.getData() + " Depth: "+ root.getDepth());
	        	root.setVisited(true);
	        	s.pop();
	        }
		}
	}
	
	public void printAllDepthLevelOrder(MyAVLTreeNode<T> root){
		ArrayDeque<MyAVLTreeNode<T>> queue = new ArrayDeque<MyAVLTreeNode<T>>();
		queue.addLast(root);
		while (!queue.isEmpty())  
		{  
		    MyAVLTreeNode<T> node = queue.remove();

		    System.out.println("Data: "+ node.getData() + " Depth: "+ node.getDepth());

		    if (node.getLeftChild()  != null) { queue.addLast(node.getLeftChild());  }
		    if (node.getRightChild() != null) { queue.addLast(node.getRightChild()); }
		}
	}
	
	/**
	 * Summing up all subtree, could be used to calculate all sumUp for each node
	 * @param n
	 * @return
	 */
	public int sumUp(MyAVLTreeNode<Integer> n){
		if (n.getLeftChild() == null && n.getRightChild() == null)
			n.setSumUp( n.getData() );
		else if (n.getLeftChild() == null)
			n.setSumUp ( n.getData() + sumUp(n.getRightChild()));
		else if (n.getRightChild() == null)
			n.setSumUp( n.getData() + sumUp(n.getLeftChild()));
		else n.setSumUp( n.getData() + + sumUp(n.getLeftChild()) + + sumUp(n.getRightChild()));
		
		return n.getSumUp();
	}
		
	public int sumUp1(MyAVLTreeNode<Integer> n){
		if( n == null){
			return 0;
		}
		else{
			n.setSumUp( n.getData() + ((sumUp1 (n.getLeftChild()) == 0) ? 0 : n.getLeftChild().getSumUp())
					+ ((sumUp1 (n.getRightChild()) == 0) ? 0 : n.getRightChild().getSumUp()));
		}
		return n.getSumUp();
	}
}
