package edu.upenn.yiranqin.stringrelated;

import java.io.File;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.datastructures.MyTrie;

public class StringHandler {
		
	
	public static void main(String[] argv){
		new StringHandler().start();
	}
	
	public void start(){
//		parseSimple(0, 0, exampleNotAllWords);
//		palindromeTest();
//		LCSTest();
//		stringUtilTest();
//		dictionaryUtilTest();
//		parseSentenceTest();
		eliminateCommentsTest();
	}
	
	public void eliminateCommentsTest(){
		StringBuilder testString = new StringBuilder();
		testString.append("	/**\n");
		testString.append("	* KMP algorithm used for determine if a String W is the substring of String S\n");
		testString.append("	* @param S\n");
		testString.append("	* @param W\n");
		testString.append("	* @return\n");
		testString.append("	*/\n");
		testString.append("/*NO NO NO*/\n");
		testString.append("	public static int KMP(String S, String W){\n");
		testString.append("		int m = 0; //the beginning of the current match in S\n");
		testString.append("/*Extreme Condition*/\n");
		testString.append("//verbose\n");
		testString.append("		int i = 0; //the position of the current character in W\n");
		testString.append("		int sLen = S.length();\n");
		testString.append("		final int N = W.length();\n");
		testString.append("		int[] T = new int[N];\n");
		testString.append("		/* the table used in KMP is the table used for backtracking where to start when a mismatch happens */\n");
		testString.append("		T = KMPtable(T,W);\n");
		testString.append("\n");
		testString.append("		//main while loop to handle all\n");
		testString.append("		while( (m + i) < sLen){\n");
		testString.append("			/*wrong*/if(W.charAt(i) == S.charAt(m + i)){\n");
		testString.append("				if(i == (W.length() - 1)){/* this should not be kept*/  \n");
		testString.append("					return m;\n");
		testString.append("				}\n");
		testString.append("				//move forward if match\n");
		testString.append("				/*//ugly comment*/i++;\n");
		testString.append("				/* this is not\n");
		testString.append("				int a = 0;\n");
		testString.append("				used anywhere*/\n");
		testString.append("			}\n");
		testString.append("			else{\n");
		testString.append("			//this /*comment*/ is ugly\n");
		testString.append("				m = m + i -T[i]; //only move back the pointer T[i] step\n");
		testString.append("				if(T[i] > -1){/* /* //This is gonna be nasty and invalid comment */*/\n");
		testString.append("					i = T[i];\n");
		testString.append("   	//these line doest not fit inline \n");
		testString.append("  //and looks weird \n");
		testString.append("				}else{\n");
		testString.append("					i = 0;\n");
		testString.append("				}\n");
		testString.append("			}\n"); 
		testString.append("		}/*end of the while loop*/\n");
		testString.append("		/*return the length*/return sLen;\n");
		testString.append("	}\n");
		testString.append("/*what a crappy program*/     \n");
		testString.append("//don't do this again */\n");
		String testStr = testString.toString(); 
		
		int repetition = 100;
		System.out.println(testStr);
		System.out.println("\n");
		
		long start = System.currentTimeMillis(); 
		System.out.println(testStr.replaceAll("(?:[\\n\\r]/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/[\\n\\r]+)|(?:[\\t\\x0b\\f]+/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/[\\n\\r]+)|(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|([\\t\\x0b\\f]+//.*[\\n\\r])|(//.*)", ""));
		System.out.println("\n");
		for(int i = 0; i < 100; i++){
			testStr.replaceAll("(?:[\\n\\r]/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/[\\n\\r]+)|(?:[\\t\\x0b\\f]+/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/[\\n\\r]+)|(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|([\\t\\x0b\\f]+//.*[\\n\\r])|(//.*)", "");
		}
		long end1 = System.currentTimeMillis();
		System.out.println(repetition + " repetitions regular expression " + (end1 - start));
		
		System.out.println(StringUtil.eliminateCommentsByChar(testStr));
		System.out.println("\n");
		for(int i = 0; i < 100; i++){
			StringUtil.eliminateCommentsByChar(testStr);
		}
		long end2 = System.currentTimeMillis();
		System.out.println(repetition + " repetitions char by char " + (end2 - end1));
		
		System.out.println(StringUtil.eliminateCommentsByLine(testStr));
		for(int i = 0; i < 100; i++){
			StringUtil.eliminateCommentsByLine(testStr);
		}
		long end3 = System.currentTimeMillis();
		System.out.println(repetition + " repetitions line by line " + (end3 - end2));
	}
	
	public void parseSentenceTest(){
		String exampleAllWords = "verygoodboy";
		String exampleAllWordsWithDiffParse = "Thisisawesome";
		String exampleNotAllWords = "thit";
		String exampleNoWords = "thumgry";
		
		System.out.println(StringUtil.unconcatenateStringIntoWords(exampleAllWords));
		System.out.println(StringUtil.unconcatenateStringIntoWords(exampleAllWordsWithDiffParse));
		System.out.println(StringUtil.unconcatenateStringIntoWords(exampleNotAllWords));
		System.out.println(StringUtil.unconcatenateStringIntoWords(exampleNoWords));
		
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
	
	public void URLParserTest(){
		String requestURL = "/web/src/yes.html";
    	String requestURI = null; 
    	URLParser parser = new URLParser(requestURL);
    	System.out.println(parser.getURI());
    	System.out.println(parser.getQueryString());
    	
    	System.out.println("/".substring(1,"/".length()));
	}    	
}
