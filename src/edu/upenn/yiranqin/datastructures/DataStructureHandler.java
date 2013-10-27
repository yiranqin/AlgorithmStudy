package edu.upenn.yiranqin.datastructures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class DataStructureHandler {
	public static void main(String[] argv){
		new DataStructureHandler().start();
	}
	
	public void start(){
		AVLTreeTest();
//		BinaryTreeTest();
//		twoTreesTest();
//		buildTreeTest();
	}
	
	public void AVLTreeTest(){
//		Integer[] values = new Integer[]{1,2,3,4,5,6,7,8,-9,19,18,-15,10,21,-12,19,-17,59};
		Integer[] values = ArrayUtil.generateIntegerArray(10, 20);
		ArrayUtil.shuffleArray(values);
		MyAVLTree<Integer> avlTree = new MyAVLTree<Integer>(values);
		MyAVLTreeNode<Integer> root = avlTree.getRoot();
		System.out.println("Print out pre order");
		avlTree.printAllDepthPreorder(root);
		System.out.println("Print out in order");
		avlTree.printAllDepthInOrder(root);
		System.out.println("Print out post order");
		avlTree.printAllDepthPostorder(root);
		System.out.println("Print out level order");
		avlTree.printAllDepthLevelOrder(avlTree.getRoot());
		avlTree.labelAllDepth(root);
		System.out.println("Print out level order");
		avlTree.printAllDepthLevelOrder(avlTree.getRoot());
		
		ArrayList<LinkedList<MyBinaryTreeNode<Integer>>> nodesInAllLevel = TreeUtil.retriveNodesForAllLevels(avlTree.getRoot(), true);
		ArrayUtil.printArray(nodesInAllLevel);
//		for(LinkedList<MyBinaryTreeNode<Integer>> curLevel : nodesInAllLevel){
//			for(MyBinaryTreeNode<Integer> curNode : curLevel){
//				System.out.print(TreeUtil.depth(root, curNode));
//			}
////			ArrayUtil.printArray(curLevel);
//			System.out.println();
//		}
		TreeUtil.printTree(root);
		System.out.println(TreeUtil.diameterOfTree(root));
		System.out.println("\nPrint out in order");
		TreeUtil.MorrisTraversalInOrder(root);
		System.out.println("\nPrint out pre order");
		TreeUtil.MorrisTraversalPreOrder(root);
		System.out.println("\nPrint out post order");
		TreeUtil.MorrisTraversalPostOrder(root);
		
		System.out.println(TreeUtil.nthNodeInOrder(root, 19));
		System.out.println(TreeUtil.nthNodeInReverseOrder(root, 21));
		
		System.out.println(TreeUtil.minHeight(root));		
		System.out.println(TreeUtil.isBST(root));
		System.out.println(TreeUtil.depth(root, new MyBinaryTreeNode<Integer>(29)));
		
		MyBinaryTreeNode<Integer> head = TreeUtil.convertTreeToDoublyLinkedList(root);
		while(head != null){
			System.out.print(head + ", ");
			head = head.getRightChild();
		}
		System.out.println();
		
		
	}
	
	public void buildTreeTest(){
		Integer[] values2 = ArrayUtil.generateIntegerArray(12, 20);
    	ArrayUtil.shuffleArray(values2);
    	MyBinaryTreeNode<Integer> root = TreeUtil.buildTreeLevelWise(values2);
    	TreeUtil.printTree(root);
    	System.out.println(TreeUtil.isBalanced(root));
    	
    	root = TreeUtil.buildTreeDepthWise(values2, 5);
    	TreeUtil.printTree(root);
    	System.out.println(TreeUtil.isBalanced(root));
    	
    	TreeUtil.mirror(root);
    	TreeUtil.printTree(root);
    	TreeUtil.MorrisTraversalInOrder(root);
    	TreeUtil.traversePointer(root);
    	System.out.println(TreeUtil.diameterOfTree(root)); 	
	}
	
	public void twoTreesTest(){
		Integer[] values1 = ArrayUtil.generateIntegerArray(12, 20);
    	ArrayUtil.shuffleArray(values1);
    	MyAVLTree<Integer> avlTree1 = new MyAVLTree<Integer>(values1);
    	TreeUtil.printTree(avlTree1.getRoot());
    	
    	Integer[] values2 = ArrayUtil.generateIntegerArray(1, 20);
    	ArrayUtil.shuffleArray(values1);
    	MyAVLTree<Integer> avlTree2 = new MyAVLTree<Integer>(values2);
    	TreeUtil.printTree(avlTree2.getRoot());
    	
    	TreeUtil.printTwoBSTInOrder(avlTree1.getRoot(), avlTree2.getRoot());
    	System.out.println(TreeUtil.isBalanced(avlTree1.getRoot()));
    	System.out.println(TreeUtil.isBalanced(avlTree2.getRoot()));
    	
    	TreeUtil.MorrisTraversalInOrder(avlTree2.getRoot());
    	
    	MyAVLTreeNode<Integer> avlOneRoot = avlTree1.getRoot();
    	TreeUtil.mirror(avlOneRoot);
    	TreeUtil.printTree(avlOneRoot);
   
    	TreeUtil.swapChildren(avlOneRoot);
    	TreeUtil.printTree(avlOneRoot);
	}
	
	public void BinaryTreeTest(){
		Integer[] values = new Integer[]{1,2,3,4,5,6,7,8,-9,19,18,-15,10,21,-12,19,-17,59};
		MyAVLTree<Integer> avlTree = new MyAVLTree<Integer>(values);
		MyAVLTreeNode<Integer> avlRoot = avlTree.getRoot();
		
		MyBinaryTreeNode<Integer> a = new MyBinaryTreeNode<Integer>(null, null, 4);
    	MyBinaryTreeNode<Integer> b = new MyBinaryTreeNode<Integer>(null, null, 5);
    	MyBinaryTreeNode<Integer> c = new MyBinaryTreeNode<Integer>(null, null, -1);
    	MyBinaryTreeNode<Integer> d = new MyBinaryTreeNode<Integer>(null, null, 7);
    	MyBinaryTreeNode<Integer> e = new MyBinaryTreeNode<Integer>(c, d, 3);
    	MyBinaryTreeNode<Integer> f = new MyBinaryTreeNode<Integer>(a, b, 2);
    	MyBinaryTreeNode<Integer> g = new MyBinaryTreeNode<Integer>(e, f, 1);
    	TreeUtil.printTree(g);
    	MyBinaryTreeNode<Integer> max = TreeUtil.FindMaxSubtree(g);
    	TreeUtil.printTree(max);
    	
    	MyBinaryTreeNode<Integer> a1 = new MyBinaryTreeNode<Integer>(null, null, 4);
    	MyBinaryTreeNode<Integer> b1 = new MyBinaryTreeNode<Integer>(null, a1, 5);
    	MyBinaryTreeNode<Integer> c1 = new MyBinaryTreeNode<Integer>(null, b1, -1);
    	TreeUtil.printTree(c1);
    	System.out.println(TreeUtil.lowestCommonAncestor(c1, a1, b1));
    	
    	
    	MyBinaryTreeNode<Integer> maxNode = TreeUtil.FindMaxSubtree(avlRoot);
    	System.out.println(maxNode.getData());
    	
    	TreeUtil.printTree(avlRoot);
    	System.out.println(TreeUtil.lowestCommonAncestor(avlRoot, -9, 1));
//    	System.out.println(TreeUtil.Height(avlRoot));
//    	System.out.println(TreeUtil.successor(5, avlRoot));

    	System.out.println(TreeUtil.isBST(avlRoot));
    	TreeUtil.printLevel(avlRoot, 4);
	}
	
	public void heapTest(){
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
