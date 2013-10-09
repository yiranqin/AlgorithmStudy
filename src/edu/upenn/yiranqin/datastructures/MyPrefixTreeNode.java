package edu.upenn.yiranqin.datastructures;

import java.util.HashMap;

class MyPrefixTreeNode {	 
    private final Character ch;
    private final String value;
    private HashMap<Character, MyPrefixTreeNode> children = new HashMap<Character, MyPrefixTreeNode>();
    private boolean isValidWord;

    public MyPrefixTreeNode(char argChar, String argValue) {
            ch = argChar;
            value = argValue;
    }

    public boolean addChild(MyPrefixTreeNode argChild) {
        if (children.containsKey(argChild.getChar())) {
        	return false;
        }

        children.put(argChild.getChar(), argChild);
        return true;
    }

    public boolean containsChildValue(char c) {
        return children.containsKey(c);
    }

    public String getValue() {
        return value.toString();
    }

    public char getChar() {
        return ch;
    }

    public MyPrefixTreeNode getChild(char c) {
        return children.get(c);
    }

    public boolean isWord() {
        return isValidWord;
    }

    public void setIsWord(boolean argIsWord) {
        isValidWord = argIsWord;
    }

    public String toString() {
        return value;
    }
}
