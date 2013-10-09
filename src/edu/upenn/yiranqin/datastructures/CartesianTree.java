package edu.upenn.yiranqin.datastructures;

public class CartesianTree {
	
	public static class Node {
		public int value;
		public Node left;
		public Node right;
	}
	
	public static Node build(int[] data) {
		if (data == null || data.length == 0) return null;
		return build(data, 0, data.length - 1);
	}
	
	private static Node build(int[] data, int start, int end) {
		if (end < start) return null;
		int min = Integer.MAX_VALUE;
		int minIndex = -1;
		
		for (int i = start; i <= end; i++) {
			if (data[i] < min) {
				min = data[i];
				minIndex = i;
			}
		}
		
		Node node = new Node();
		node.value = min;
		
		node.left = build(data, start, minIndex - 1);
		node.right = build(data, minIndex + 1, end);
		
		return node;
	} 
}
