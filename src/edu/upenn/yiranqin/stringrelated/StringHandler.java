package edu.upenn.yiranqin.stringrelated;

import java.io.File;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.datastructures.MyTrie;

public class StringHandler {
	public String exampleAllWords = "verygoodboy";
	public String exampleAllWordsWithDiffParse = "Thisisawesome";
	public String exampleNotAllWords = "thit";
	public String exampleNoWords = "thumgry";	
	
	public static void main(String[] argv){
		new StringHandler().start();
	}
	
	public void start(){
//		parseSimple(0, 0, exampleNotAllWords);
//		palindromeTest();
//		LCSTest();
//		stringUtilTest();
		dictionaryUtilTest();
	}
	
	public void dictionaryUtilTest(){
		String filePath = "words";
		File file = new File(filePath);
		MyTrie dicTrie = DictionaryUtil.buildUpDictionaryTrie(file);
		System.out.println(dicTrie.containsWord("Good"));
	}
	
	public void palindromeTest(){
		String str = "123xyx321";
		System.out.println(StringUtil.reverseString(str));
		System.out.println(str);
		char[] array = str.toCharArray();
		StringUtil.reverseCharArray(array, 0, str.length() - 1);
		System.out.println(String.valueOf(array));
		System.out.println(str);
		System.out.println(StringUtil.leftRotateString(str, 1));
		
		System.out.println("number of characters needed to build a palindrome is "+ StringUtil.findMissingCharactersToPalindrome(str));
		System.out.println(StringUtil.findLongestPalindrome(str));
		System.out.println("Geeks for Geeks".length());
	}
	
	public void LCSTest(){
    	String str1 = "abcrfghwetf";
    	String str2 = "abrfghwwetxyab";
    	
    	System.out.println(StringUtil.longestCommonSubstring(str1, str2));
    	ArrayUtil.printArray(StringUtil.getAllCommonSubstrings(str1, str2));
    	
    	
    	System.out.println(StringUtil.reverseString(str1));
    	String palindrom = "123xyz321";
    	String origin = new String(palindrom);
    	String re = StringUtil.reverseString(palindrom);
    	System.out.println(StringUtil.longestCommonSubstring(re, origin));
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i = 0; i < ((str1.length() < str2.length())? str1.length(): str2.length()); i++){
    		sb.append( (char) str1.charAt(i) ^ str2.charAt(i));
    	}
    	System.out.println(sb);
    	
    	System.out.println(StringUtil.KMP(str1,str2));
    	int[] T = new int[6]; 
    	ArrayUtil.printArray(StringUtil.KMPtable(T, "ababab"));
	}
	
	public void stringUtilTest(){
		ArrayUtil.printArray(StringUtil.allSubstring("aaaa"));
		ArrayUtil.printArray(StringUtil.fullPermutation("123"));
    	try{
    		String str = "2334300";
    		long num = StringUtil.stringToInt(str);
    		System.out.println("the input number is "+num);
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}
	
	public int parseSimple(int wordStart, int wordEnd, String sentence){
		if(wordEnd >= sentence.length())
			return wordEnd - wordStart;
		
		String word = sentence.substring(wordStart, wordEnd + 1);
		
		int bestExact = parseSimple(wordEnd + 1, wordEnd + 1, sentence);
		int bestExtend = parseSimple(wordStart, wordEnd + 1, sentence);
		
		System.out.println(word + " " + bestExact + " " + bestExtend);
		return Math.min(bestExact, bestExtend);
	
	}
	
	public void URLParserTest(){
		String requestURL = "/web/src/yes.html";
    	String requestURI = null; 
    	URLParser parser = new URLParser(requestURL);
    	System.out.println(parser.getURI());
    	System.out.println(parser.getQueryString());
    	
    	System.out.println("/".substring(1,"/".length()));
	}    	
}
