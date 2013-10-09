package edu.upenn.yiranqin.datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

public class TreeUtil {
	/**
	 * Build a binary tree given values level by level
	 * @param values
	 * @return
	 */
	public static <T extends Object>
	MyBinaryTreeNode<T> buildTreeLevelWise(T[] values){
		if(values == null)
			return null;
		
		MyBinaryTreeNode<T> root = new MyBinaryTreeNode<T>(values[0]);
		LinkedList<MyBinaryTreeNode<T>> curLevel = new LinkedList<MyBinaryTreeNode<T>>();
		curLevel.add(root);
		int index = 1;
		while(index < values.length){
			LinkedList<MyBinaryTreeNode<T>> prevLevel = curLevel;
			curLevel = new LinkedList<MyBinaryTreeNode<T>>();
			MyBinaryTreeNode<T> curNode;
			for(MyBinaryTreeNode<T> node : prevLevel){
				if(index >= values.length)
					break;
				curNode = new MyBinaryTreeNode<T>(values[index++]);
				node.setLeftChild(curNode);
				curLevel.add(curNode);
				if(index >= values.length)
					break;
				curNode = new MyBinaryTreeNode<T>(values[index++]);
				node.setRightChild(curNode);
				curLevel.add(curNode);
			}
		}
		return root;
	}
	
	public static <T extends Object>
	MyBinaryTreeNode<T> buildTreeDepthWise(T[] values, int depthLimit){
		if(values == null)
			return null;
		
		MyBinaryTreeNode<T> root = new MyBinaryTreeNode<T>(values[0]);
		Stack<MyBinaryTreeNode<T>> nodesStack = new Stack<MyBinaryTreeNode<T>>();
		nodesStack.push(root);
		int index = 1;
		int curDepth = 0; 
		MyBinaryTreeNode<T> curNode = null;
		MyBinaryTreeNode<T> prevNode = root;
		MyBinaryTreeNode<T> tmpRoot = root;
		
		while(index < values.length){
			prevNode = nodesStack.peek();
			if(prevNode.getLeftChild() == null){
				/* Move all the way down to add left child nodes */
				while(curDepth < depthLimit && index < values.length){
					curNode = new MyBinaryTreeNode<T>(values[index++]);
					prevNode.setLeftChild(curNode);
					nodesStack.push(curNode);
					prevNode = curNode;
					curDepth++;
				}
			}
			if(index == values.length)
				break;
			/* if reach the depthLimit, then move upwards by one and add right child
			/* for previous node, either it's current node or its parent 
			 * And we only pop up one extra node when the current level is indeed the lowest
			 * 
			 * The current Depth refer to the current working node's depth 
			 */
			int prevDepth = depth(tmpRoot, prevNode);
			if(curDepth == depthLimit && prevDepth + 1 >= depthLimit
					&& nodesStack.size() > 1){
				nodesStack.pop();				
			}
			prevNode = nodesStack.pop();
			curNode = new MyBinaryTreeNode<T>(values[index++]);
			prevNode.setRightChild(curNode);
			
			curDepth = depth(tmpRoot, curNode); 
			if(curDepth < depthLimit){
				nodesStack.push(curNode);
			}
			
			/* if under the current limit the tree can not accommodate all nodes,
			 *  then use the left most left to be the new tmp root and repeat until all accommodated*/
			if(nodesStack.size() == 0 && index < values.length){
				while(tmpRoot.getLeftChild() != null){
					tmpRoot = tmpRoot.getLeftChild();
				}
				nodesStack.push(tmpRoot);
				curDepth = 0;
			}
		}
		return root;
	}
	
	public static <T extends Comparable<? super T>>
	int depth(MyBSTNode<T> root, MyBSTNode<T> x){
		if(root != null){ 
			if(x.equals(root)){
				return 0;
			}
			else if(x.compareTo(root) < 0){
				return depth(root.getLeftChild(), x) + 1;
			}
			else if(x.compareTo(root) > 0){
				return depth(root.getRightChild(), x) + 1;
			}
		}
		return -1;
	}
	/**
	 * lowest common ancestor with assumption that a and b is no the ancestor of each other
	 * @param a
	 * @param b
	 * @param root
	 * @return
	 */
	public static <T extends Comparable<? super T>> 
	MyBSTNode<T> lowestCommonAncestor(MyBSTNode<T> a, MyBSTNode<T> b, MyBSTNode<T> root){
		if( root == null){
			return null;
		}
		else if(a.compareTo(root) < 0 && b.compareTo(root) < 0){
			return lowestCommonAncestor(a, b, root.getLeftChild());
		}
		else if(a.compareTo(root) > 0 && b.compareTo(root) > 0){
			return lowestCommonAncestor(a, b, root.getRightChild());
		}
		else return root;
	}
	
	public static <T extends Object>
	int Height(MyBinaryTreeNode<T> root){
		if(root == null){
			return -1;
		}
		else return (Math.max(Height(root.getLeftChild()), Height(root.getRightChild())) + 1);
	}
	
	public static <T extends Object>
	void traverseOrderLevel(MyBinaryTreeNode<T> node) {
		if (node == null) return;
		ArrayList<MyBinaryTreeNode<T>> thisLevel = new ArrayList<MyBinaryTreeNode<T>>();
		thisLevel.add(node);
		while (!thisLevel.isEmpty()) {
			ArrayList<MyBinaryTreeNode<T>> nextLevel = new ArrayList<MyBinaryTreeNode<T>>();
			for (MyBinaryTreeNode<T> n : thisLevel) {
				System.out.print(n);
				if (n.getLeftChild() != null) nextLevel.add(n.getLeftChild());
				if (n.getRightChild() != null) nextLevel.add(n.getRightChild());
			}
			thisLevel = nextLevel;
			System.out.println();
		}
	}
	
	public static <T extends Object>
	void printLevelOrder(MyBinaryTreeNode<T> root){
		ArrayDeque<MyBinaryTreeNode<T>> queue = new ArrayDeque<MyBinaryTreeNode<T>>();
		queue.addLast(root);
		int height = Height(root);
		for (int level = 0; level <= height; level++){
			System.out.print("Level " + level + " ");
			printLevel(root, level);
			System.out.print("\n");
		}  
	}
	
	/**
	 * Retrieve all the nodes level by level and nodes for each level maintained in a linkedlist
	 * If it is for print tree, then put the nodes in list even when it is null 
	 * @param root
	 * @param isForPrintTree
	 * @return
	 */
	public static <T extends Object>
	ArrayList<LinkedList<MyBinaryTreeNode<T>>> retriveNodesForAllLevels(MyBinaryTreeNode<T> root, 
			boolean isForPrintTree){
		if(root == null)
			return null;
		
		ArrayList<LinkedList<MyBinaryTreeNode<T>>> allNodesList = 
				new ArrayList<LinkedList<MyBinaryTreeNode<T>>>();
		LinkedList<MyBinaryTreeNode<T>> current = new LinkedList<MyBinaryTreeNode<T>>();
		current.add(root);
		int height = Height(root);
		
		while(allNodesList.size() <= height){
			LinkedList<MyBinaryTreeNode<T>> lastLevel = current;
			allNodesList.add(lastLevel);
			current = new LinkedList<MyBinaryTreeNode<T>>();
			for(MyBinaryTreeNode<T> node : lastLevel){
				if(isForPrintTree && node == null){
					current.add(null);
					current.add(null);
				}else{
					if(isForPrintTree || node.getLeftChild() != null)
						current.add(node.getLeftChild());
					if(isForPrintTree || node.getRightChild() != null)
						current.add(node.getRightChild());
				}
			}
		}
		return allNodesList;
	}
	
	public static <T extends Object>
	void printLevel(MyBinaryTreeNode<T> root, int level){
		if(root == null)
			return;
		if(level == 0)
			System.out.print("Data: "+ root.getData() + "  ");
		else if (level > 0)
		{
			if(root.getLeftChild() != null) printLevel(root.getLeftChild(), level - 1);
			if(root.getRightChild() != null) printLevel(root.getRightChild(), level - 1);
		}
	}
	
	public static <T extends Object>
	void retriveAllNodesInLevel(MyBinaryTreeNode<T> root, int level,
			LinkedList<MyBinaryTreeNode<T>> list){
		if(root == null)
			return;
		if(level == 0)
			list.add(root);
		else if (level > 0)
		{
			if(root.getLeftChild() != null) retriveAllNodesInLevel(root.getLeftChild(), level - 1, list);
			if(root.getRightChild() != null) retriveAllNodesInLevel(root.getRightChild(), level - 1, list);
		}
	}
	
	/**
	 * See how this works, input is BinaryTree node with Type restriction on Comparable
	 **/
	public static <T extends Comparable<? super T>>
	boolean isBST(MyBinaryTreeNode<T> root){
		return isBSTHelper(root, null);
	}

	public static <T extends Comparable<? super T>>
	boolean isBSTHelper(MyBinaryTreeNode<T> root, MyBinaryTreeNode<T> prev){	 
		// traverse the tree in inorder fashion and keep track of prev node
		if (root != null){
			if (!isBSTHelper(root.getLeftChild(), prev)){
				return false;
			}
	        // Allows only distinct valued nodes
			if (prev != null && root.getData().compareTo( prev.getData()) <= 0)
				return false;
			
			prev = root;
			return isBSTHelper(root.getRightChild(), prev);
	    }
	 
	    return true;
	}
	
	public static boolean isBSTMinMax(MyBinaryTreeNode<Integer> root){
		return isBSTMinMax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static boolean isBSTMinMax(MyBinaryTreeNode<Integer> root, Integer min, Integer max){
		if(root == null)
			return true;
		
		if(root.getData() <= min || root.getData() > max)
			return false;
		
		if(!isBSTMinMax(root.getLeftChild(), min, root.getData()) ||
				!isBSTMinMax(root.getRightChild(), root.getData(), max))
			return false;
		return true;
	}
	
	/**
	 * Check whether the given binary tree is balanced
	 * The helper basically is build up the height for each node
	 * But also include a identifier Integer.MIN_VALUE to indicate not balanced
	 * 
	 * Actually since whenever a -1 returns, the recursion also returns directly
	 * so use -1 will do just fine, but still use a Integer.MIN_VALUE better distinguish
	 * @param root
	 * @return
	 */
	public static <T extends Object>
	boolean isBalanced(MyBinaryTreeNode<T> root){
		if(root == null)
			return true;
		int result = isBalancedHelper(root);
		return result != Integer.MIN_VALUE;
	}
	
	public static <T extends Object>
	int isBalancedHelper(MyBinaryTreeNode<T> root){
		if(root == null)
			return -1;
		int leftResult = isBalancedHelper(root.getLeftChild());
		if(leftResult == Integer.MIN_VALUE)
			return Integer.MIN_VALUE;
		
		int rightResult = isBalancedHelper(root.getRightChild());
		if(rightResult == Integer.MIN_VALUE)
			return Integer.MIN_VALUE;	
		
		if( Math.abs(leftResult - rightResult) > 1)
			return Integer.MIN_VALUE;
		else
			return Math.max(leftResult, rightResult) + 1;
	}
	
	public static <T extends Object>
	int distance(MyBinaryTreeNode<T> a, MyBinaryTreeNode<T> b, MyBinaryTreeNode<T> root){
		if(a != null && b != null && root != null){
			MyBinaryTreeNode<T> ancestor = lowestCommonAncestor(a, b, root);
			if(ancestor == null)
				return -1;
			System.out.println("ancestor: "+ ancestor);
			return depth(ancestor, a) + depth(ancestor, b);//((depth(root,a) - ancestor.Depth) + (depth(root,a) - ancestor.Depth));
		}
		else return -1; 
	}
	
	public static <T extends Object>
	int depth(MyBinaryTreeNode<T> root, MyBinaryTreeNode<T> x){
		if(root == null || x == null)
			return -1;
		
		int depth = 0;
		if(root.getData().equals(x.getData())){
			return 0;
		}else{
			depth = depth(root.getLeftChild(), x);
			depth = (depth < 0) ? -1 : depth + 1;

			if(depth < 0){ 
				depth = depth(root.getRightChild(), x);
				depth = (depth < 0) ? -1 : depth + 1;
			}
		}
		
		return depth;
	}
	
	/**
	 * Assume that there is no nodes with duplicate value
	 * @param root
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Object>
	MyBinaryTreeNode<T> lowestCommonAncestor(MyBinaryTreeNode<T> root, 
			T a, T b){
		MyBinaryTreeNode<T> aNode = new MyBinaryTreeNode<T>(a);
		MyBinaryTreeNode<T> bNode = new MyBinaryTreeNode<T>(b);
		return lowestCommonAncestor(root, aNode, bNode); 
	}
	/**
	 * Determine the lowest common ancestor for all cases 
	 * including a or b is the root of subtree including the other
	 * and/or the case that either a, b, or root is not in the tree	
	 * @param root
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T extends Object>
	MyBinaryTreeNode<T> lowestCommonAncestor(MyBinaryTreeNode<T> root, 
			MyBinaryTreeNode<T> a, MyBinaryTreeNode<T> b){
		LCAResult<T> result = lowestCommonAncestorHelper(root, a, b); 
		if(result.isAncestor)
			return result.node;
		else
			return null;
	}
	
	/**
	 * Returns a if any subtree includes a(but not b), and return b if any subtree includes b(but not a)
	 * Returns null if neither a or b is in root's subtree
	 * Else, returns the LCA
	 */
	private static <T extends Object>
	LCAResult<T> lowestCommonAncestorHelper(MyBinaryTreeNode<T> root, 
			MyBinaryTreeNode<T> a, MyBinaryTreeNode<T> b){
		if(root == null)
			return new LCAResult<T>(null, false);
		if(root.getData().equals(a.getData()) && root.getData().equals(b.getData()))
			return new LCAResult<T>(root, true);
		
		LCAResult<T> left = lowestCommonAncestorHelper(root.getLeftChild(), a,  b);
		if(left.isAncestor)
			return left;
		
		LCAResult<T> right = lowestCommonAncestorHelper(root.getRightChild(), a,  b);
		if(right.isAncestor)
			return right;
		
		/* a and b are in different subtrees */
		if(left.node != null && right.node != null)
			return new LCAResult<T>(root, true);
		else if(root.getData().equals(a.getData()) || root.getData().equals(b.getData())){
			/* this covers the case that a and b are in the same subtree and one of them is the ancestor of the other */
			/* also if both left and right of root's subtree returns null, then a and b are not in the tree 
			 * or if any of them is in, it should be the ancestor of itself*/
			boolean isAncestor = (left.node != null || right.node != null) ? true : false;
			return new LCAResult<T>(root, isAncestor);
		}else{
			return new LCAResult<T>((left.node != null) ? left.node : right.node, false);
		}
	} 
	
	protected static class LCAResult<T extends Object>{
		protected MyBinaryTreeNode<T> node;
		protected boolean isAncestor;
		
		protected LCAResult(MyBinaryTreeNode<T> candidate, boolean isAncestor){
			this.node = candidate;
			this.isAncestor = isAncestor;
		}
	}
	
	
	public static <T extends Object>
	MyBinaryTreeNode<T> mirror(MyBinaryTreeNode<T> root){
		MyBinaryTreeNode<T> tmp;
		if( root != null){			
			root.setLeftChild( (root.getLeftChild() != null) ? mirror(root.getLeftChild()): root.getLeftChild());
			root.setRightChild( (root.getRightChild() != null) ? mirror(root.getRightChild()):root.getRightChild());
			tmp = root.getLeftChild();
			root.setLeftChild( root.getRightChild());
			root.setRightChild( tmp);
		}
		return root;
	}
	
	public static <T extends Object>
	MyBinaryTreeNode<T> swapChildren(MyBinaryTreeNode<T> node){
		MyBinaryTreeNode<T> tmp;
		if( node != null){			
			tmp = node.getLeftChild();
			node.setLeftChild( node.getRightChild());
			node.setRightChild( tmp);
		}
		
		return node;
	}
	
	public static <T extends Object>
	void traversePointer (MyBinaryTreeNode<T> current) {
		MyBinaryTreeNode<T> parent = current;
		current = current.getLeftChild();
		while(current != null) {
			if (parent != null) {
				parent.setLeftChild( current.getRightChild());
				current.setRightChild( parent);
			}
		    if (current.getLeftChild() != null) {
		    	parent = current;
		    	current = current.getLeftChild();
		    } else {
		    	System.out.println("Data: "+ current);
		    	current = current.getRightChild();
		    	parent = null;
		    }
		}
	}
	 
	public static <T extends Object>
	void MorrisTraversal(MyBinaryTreeNode<T> root){
		MyBinaryTreeNode<T> current, pre;
		current = root;

		while(current != null)
		{
			if(current.getLeftChild() == null)
			{
				System.out.println("Data: "+ current);
				current = current.getRightChild();
			}
			else
			{
				/* Find the inorder predecessor of current */
				pre = current.getLeftChild();
				while(pre.getRightChild() != null && pre.getRightChild() != current)
					pre = pre.getRightChild();
	  
				/* Make current as right child of its inorder predecessor */
				if(pre.getRightChild() == null){
					pre.setRightChild( current);
					current = current.getLeftChild();
				}
	  
				/* Revert the changes made in if part to restore the original
	         	tree i.e., fix the right child of predecssor */
				else
				{
					pre.setRightChild( null);
					System.out.println("Data: "+ current);
					current = current.getRightChild();
				} /* End of if condition pre->right == NULL */
			} /* End of if condition current->left == NULL*/
		} /* End of while */
	 }
	
	/**
	 * @param Data
	 * @param root
	 * @return
	 */
	public static <T extends Comparable<? super T>>
	MyBinaryTreeNode<T> successor(T Data, MyBinaryTreeNode<T> root){   
		if(root != null){
			successor(Data, root.getRightChild());
			if(root.getRightChild() != null && root.getRightChild().getData().equals( Data)){
					return root;
			}
			else if (root.getLeftChild() != null && root.getLeftChild().getData().equals( Data)){
					return root;
			}
			successor(Data, root.getLeftChild());
		}
		return null;
	}
	
	/**
	 * Find the subtree with maximum sum
	 * @param node
	 * @param sum
	 * @return the root of the subtree with maximum sum
	 */
	public static MyBinaryTreeNode<Integer> 
	FindMaxSubtree(MyBinaryTreeNode<Integer> root){
		Hashtable<MyBinaryTreeNode<Integer>, Integer> sum = 
				new Hashtable<MyBinaryTreeNode<Integer>, Integer>();
		return FindMaxSubtreeHelper(root, sum);
	}
	
	public static MyBinaryTreeNode<Integer> 
	FindMaxSubtreeHelper(MyBinaryTreeNode<Integer> node, Hashtable<MyBinaryTreeNode<Integer>, Integer> sum){
		if(node == null)
			return null;
		
		MyBinaryTreeNode<Integer> leftMaxNode = FindMaxSubtreeHelper(node.getLeftChild(), sum);
		MyBinaryTreeNode<Integer> rightMaxNode = FindMaxSubtreeHelper(node.getRightChild(), sum);
		
		int nodeSum = node.getData() + (node.getLeftChild() == null? 0 : sum.get(node.getLeftChild()))
				+ (node.getRightChild() == null? 0 : sum.get(node.getRightChild())) ;		
		sum.put(node, nodeSum);
		
		MyBinaryTreeNode<Integer> maxNode = node;
		int max = nodeSum;
		
		if(leftMaxNode != null && sum.get(leftMaxNode) > max)
		{
			maxNode = leftMaxNode;
			max = sum.get(leftMaxNode);
		}
		
		if(rightMaxNode != null && sum.get(rightMaxNode) > max)
		{
			maxNode = rightMaxNode;
			max = sum.get(rightMaxNode);
		}		
		
		return maxNode;
	}
	
	
	public static <T extends Object>
	void printAllNodes(MyBinaryTreeNode<T> root){
		if(root != null){
			printAllNodes(root.getLeftChild());
			System.out.println("Data: "+ root);
			printAllNodes(root.getRightChild());
		}
		
	}
	
	/**
	 * Print two BST in order without merging
	 * What we need is two in order queues for all nodes in each tree
	 * We could get it via a reverse stack and it from the start
	 * 
	 * @param root1
	 * @param root2
	 */
	public static <T extends Comparable<? super T>>
	void printTwoBSTInOrder(MyBSTNode<T> root1, MyBSTNode<T> root2){
		if(root1 == null && root2 == null)
			return;
		
		Stack<MyBSTNode<T>> treeOneNodes = new Stack<MyBSTNode<T>>();
		Stack<MyBSTNode<T>> treeTwoNodes = new Stack<MyBSTNode<T>>();
		
		getAllNodesInReverseOrder(root1, treeOneNodes);
		getAllNodesInReverseOrder(root2, treeTwoNodes);
		
		MyBSTNode<T> cur1 = null;
		MyBSTNode<T> cur2 = null;
		while(treeOneNodes.size() > 0 || treeTwoNodes.size() > 0){
			/**
			 * Always do the check first then use, all stack eject operations throws Exception
			 */
			if(treeOneNodes.size() == 0){
				while(treeTwoNodes.size() > 0){
					System.out.print(" " + treeTwoNodes.pop());
				}
				break;
			}
			if(treeTwoNodes.size() == 0){
				while(treeOneNodes.size() > 0){
					System.out.print(" " + treeOneNodes.pop());
				}
				break;
			}
			
			cur1 =  treeOneNodes.peek();
			cur2 =  treeTwoNodes.peek();			
			if(cur1.compareTo(cur2) < 0){
				System.out.print(" " + cur1);
				treeOneNodes.pop();
			}else{
				System.out.print(" " + cur2);
				treeTwoNodes.pop();
			}
		}
		System.out.println();
	}
	
	public static <T extends Comparable<? super T>>
	void getAllNodesInReverseOrder(MyBSTNode<T> root, Stack<MyBSTNode<T>> stack){
		if(root != null){
			getAllNodesInReverseOrder(root.getRightChild(), stack);
			stack.push(root);
			getAllNodesInReverseOrder(root.getLeftChild(), stack);
		}
	}
	
	public static <T extends Object>
	void getAllNodesInReverseOrder(MyBinaryTreeNode<T> root, Stack<MyBinaryTreeNode<T>> stack){
		if(root != null){
			getAllNodesInReverseOrder(root.getRightChild(), stack);
			stack.push(root);
			getAllNodesInReverseOrder(root.getLeftChild(), stack);
		}
	}
	
	/**
	 * Check if tree2 is a subtree of tree1
	 * @param root1
	 * @param root2
	 * @return
	 */	
	public static <T extends Object>
	boolean containsTree(MyBinaryTreeNode<T> root1, MyBinaryTreeNode<T> root2){
		if(root2 == null)
			return true;
		
		if(root1 == null)
			return false;
		
		if(root1.getData().equals(root2.getData())){
			if(isSameTree(root1, root2))
				return true;
		}
		
		return (containsTree(root1.getLeftChild(), root2) || containsTree(root1.getRightChild(), root2));
	}
	
	/**
	 * Check if tree2 has exactly the same value and structure as tree1
	 * @param root1
	 * @param root2
	 * @return
	 */
	public static <T extends Object>
	boolean isSameTree(MyBinaryTreeNode<T> root1, MyBinaryTreeNode<T> root2){
		if(root1 == null &&  root2 == null)
			return true;
		
		if(root1 == null || root2 == null)
			return false;
				
		if(!root1.getData().equals(root2.getData())){
			return false;
		}
		return isSameTree(root1.getLeftChild(), root2.getLeftChild()) &&
				isSameTree(root1.getRightChild(), root2.getRightChild());
	}
	
	public static <T extends Object>
	int diameterOfTree(MyBinaryTreeNode<T> root){
		if(root == null)
			return 0;
		return diameterOfTreeHelper(root).getDiameter();
	}
	
	private static <T extends Object>
	DiameterResult diameterOfTreeHelper(MyBinaryTreeNode<T> root){
		if(root == null)
			return new DiameterResult(-1, 0);
		
		DiameterResult leftResult = diameterOfTreeHelper(root.getLeftChild());
		DiameterResult rightResult = diameterOfTreeHelper(root.getRightChild());
		
		int leftRightDiameter = Math.max(leftResult.getDiameter(),rightResult.getDiameter());
		leftRightDiameter = (leftRightDiameter > 0) ? leftRightDiameter : 0;
		/* diameter is the number of nodes in the left subtree with maximum height
		 *  plus the current node
		 *  plus the number of nodes in the right subtree with maximum height*/
		int diameter = Math.max(leftRightDiameter, leftResult.getHeight() + 1 + rightResult.getHeight() + 1 + 1); 
		int height = Math.max(leftResult.getHeight(), rightResult.getHeight()) + 1;
		return new DiameterResult(height, diameter);
	}
	
	/**
	 * Result Wrapper for height and current diameter  
	 */	
	private static class DiameterResult{
		private int height;
		private int diameter;
		protected DiameterResult(int height, int diameter){
			this.height = height;
			this.diameter = diameter;
		}
		
		protected int getHeight(){
			return height;
		}
		
		protected int getDiameter(){
			return diameter;
		}
	} 
	
	/**
	 * A printTree machinery mostly for debugging, take little concern regarding to performance
	 * @param root
	 */
	public static <T extends Object>
	void printTree(MyBinaryTreeNode<T> root){
		if(root == null)
			return;
		
		ArrayList<LinkedList<MyBinaryTreeNode<T>>> allNodesList = retriveNodesForAllLevels(root, true);
		int height = allNodesList.size() - 1;
		int level = 0;
		final int INLINE = longestElementLen(root);
		
		for(int i = 0; i < ((int)Math.pow(2, height) - 1) * INLINE; i++)
			System.out.print(" ");
		System.out.println(root);
		
		int index = 1;
		LinkedList<MyBinaryTreeNode<T>> nodeList;
		while(height > 0){
			level++;
			height--;
			nodeList = allNodesList.get(level);
			
			int firstPosition = (int)Math.pow(2, height) - 1;
			int interval = (int)Math.pow(2, height + 1) - 1;
			
			/* In this printing case, better use index from 1 so that odd number has / and even has \ */
			for(MyBinaryTreeNode<T> node : nodeList){
				if(index == 1){
					for(int i = 0; i < firstPosition * INLINE; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < interval * INLINE; i++)
						System.out.print(" ");
				}
				if(node != null){
					System.out.print((index % 2 == 1)?"/" : "\\");
					for(int i = 0; i < INLINE - 1; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < INLINE; i++)
						System.out.print(" ");
				}
				index++;
			}
			index = 1;
			System.out.println();
			for(MyBinaryTreeNode<T> node : nodeList){
				if(index == 1){
					for(int i = 0; i < firstPosition * INLINE; i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < interval * INLINE; i++)
						System.out.print(" ");
				}
				if(node != null){
					String output = node.toString();
					System.out.print(output);
					for(int i = 0; i < INLINE - output.length(); i++)
						System.out.print(" ");
				}else{
					for(int i = 0; i < INLINE; i++)
						System.out.print(" ");
				}
				index++;
			}
			index = 1;
			System.out.println();
		}
	}
	
	public static <T extends Object>
	int longestElementLen(MyBinaryTreeNode<T> root){
		if(root == null)
			return -1;
		
		int maxLen = 0;
		LinkedList<MyBinaryTreeNode<T>> queue = new LinkedList<MyBinaryTreeNode<T>>();
		queue.add(root);
		MyBinaryTreeNode<T> curNode = null;
		
		while(queue.size() > 0){
			curNode = queue.poll();			
			int curLen = curNode.toString().length();
			if(curLen > maxLen)
				maxLen = curLen;
			
			if(curNode.getLeftChild() != null)
				queue.add(curNode.getLeftChild());
			if(curNode.getRightChild() != null)
				queue.add(curNode.getRightChild());
		}
		return maxLen;
	}

}
