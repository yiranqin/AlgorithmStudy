package edu.upenn.yiranqin.datastructures;

import java.util.List;

public class MyTrie {
    private MyPrefixTreeNode root = new MyPrefixTreeNode('\0', "");

    public MyTrie() {}

    public MyTrie(List<String> argInitialWords) {
    	for (String word:argInitialWords) {
    		addWord(word);
		}
    }

    public void addWord(String argWord) {
        addWord(argWord.toCharArray());
    }

    public void addWord(char[] argWord) {
    	MyPrefixTreeNode currentNode = root;
    	
    	for (int i = 0; i < argWord.length; i++) {
    		if (!currentNode.containsChildValue(argWord[i])) {
    			currentNode.addChild(new MyPrefixTreeNode(argWord[i], currentNode.getValue() + argWord[i]));
    		}
    		
    		currentNode = currentNode.getChild(argWord[i]);
    	}
	
    	currentNode.setIsWord(true);
    }

    public boolean containsPrefix(String argPrefix) {
    	return contains(argPrefix.toCharArray(), false);
    }

    public boolean containsWord(String argWord) {
    	return contains(argWord.toCharArray(), true);
    }

    public MyPrefixTreeNode getWord(String argString) {
    	MyPrefixTreeNode curNode = getMyPrefixTreeNode(argString.toCharArray());
    	return curNode != null && curNode.isWord() ? curNode : null;
    }

    public MyPrefixTreeNode getPrefix(String argString) {
    	return getMyPrefixTreeNode(argString.toCharArray());
    }

    @Override
    public String toString() {
    	return root.toString();
    }

    private boolean contains(char[] argString, boolean argIsWord) {
    	MyPrefixTreeNode curNode = getMyPrefixTreeNode(argString);
    	return (curNode != null && curNode.isWord() && argIsWord) || (!argIsWord && curNode != null);
    }

    private MyPrefixTreeNode getMyPrefixTreeNode(char[] argString) {
    	MyPrefixTreeNode curNode = root;

    	for (int i = 0; i < argString.length && curNode != null; i++) {
    		curNode = curNode.getChild(argString[i]);
    		
    		if (curNode == null) {
    			return null;
    		}
    	}
	
        return curNode;
    }
}
