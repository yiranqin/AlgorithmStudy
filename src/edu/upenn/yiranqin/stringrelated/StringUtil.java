package edu.upenn.yiranqin.stringrelated;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.datastructures.MyTrie;

public class StringUtil{
	public static MyTrie dictionary = null;
	static{
		String filePath = "words";
		File file = new File(filePath);
		dictionary = DictionaryUtil.buildUpDictionaryTrie(file);
	}
	
	/**
	 * Full permutation of given string
	 * @param str
	 * @return
	 */
	public static LinkedList<String> fullPermutation(String str){
		LinkedList<String> result = null;
		if(str == null)
			return result;
		
		result = new LinkedList<String>();
		char[] strChars = str.toCharArray();
		
		fullPermutationRecursively(strChars, 0, strChars.length - 1, result);		
		return result;
	}
	
	private static void fullPermutationRecursively(char[] strChars,
			int start, int end, LinkedList<String> result){
		if(start > end)
			return;
		else if(start == end){
			result.add(String.valueOf(strChars));
		}
		
		for(int i = start; i <= end; i++){
			if(i == start || strChars[start] != strChars[i]){
				ArrayUtil.swap(strChars, start, i);
				fullPermutationRecursively(strChars, start + 1, end, result);
				ArrayUtil.swap(strChars, start, i);
			}
		}		
	}
	
	/**
	 * Get a list of all substring of the given string
	 * The only way I could think of to just output distinct substrings
	 * is to use a hashset 
	 *  
	 * @param str
	 * @return
	 */
	public static LinkedList<String> allSubstring(String str){
		LinkedList<String> result = new LinkedList<String>();
		if(str == null || str.length() == 0){
			result.add(null);
			return result;
		}
		
		long bitmap = 0;
		long totalNum = (long)Math.pow(2, str.length());
		while(bitmap < totalNum){
			StringBuilder tmp = new StringBuilder();
			for(int i = 0; i < str.length(); i++){
				if((bitmap & (1 << i)) > 0){
					tmp.append(str.charAt(i));
				}
			}
			result.add(tmp.toString());
			bitmap++;
		}
		
		return result;
	}
	
	/**
	 * parse a string to integer
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static long stringToInt(String s) throws Exception {
		if(s == null) 
			throw new Exception("Null pointer");
		else
		{
			int isNegative = 1;
			if(s.charAt(0) == '-')
			{
				s = s.substring(1);
				isNegative = -1;
			}
			if(s.length() == 0)
				throw new Exception("No integer inputed");
			else
			{
				long result = 0;
				for(int i = 0; i < s.length(); i++)
				{
					int value = s.charAt(i) - '0';				
					if(value < 0 || value > 9)
						throw new Exception("Not integer");

					if( (long) (result * 10 + value) *(-1) < Integer.MIN_VALUE 
							|| (long) (result * 10 + value) > Integer.MAX_VALUE) 
						throw new Exception("Out of Integer Bound");
					result = result*10 + value;
				}
				return isNegative * result;
			}
		}
	}
	
	/**
	 * Find the first duplicate char within a string
	 * @param str
	 * @return
	 */
	public static char findFirstDuplicateChar(String str){
		boolean[] map = new boolean[128];
		char[] s = str.toCharArray();
		int len = str.length();
		for(int i = 0; i < len; i++){
			char tmp = s[i];
			map[(int) tmp] = true;
			if(map[(int) tmp]){
				return tmp;
			}
		}
		return ' ';
	}
	
	public static String reverseString(String str){
		char[] charStr = str.toCharArray();
		for(int i = 0; i < str.length()/2; i++){
			ArrayUtil.swap(charStr, i, str.length() - 1 - i);
		}
		return String.valueOf(charStr);
	}
	
	public static void reverseCharArray(char[] array, int start, int end){
		if(array == null)
			return;
		while(start < end){
			ArrayUtil.swap(array, start, end);
			start++;
			end--;
		}
	}
	/**
	 * Left rotate num times
	 * @param str
	 * @param num
	 */
	public static String leftRotateString(String str, int num){
		if(str == null)
			return null;
		
		num &= str.length();
		char[] charStr = str.toCharArray();
		reverseCharArray(charStr, 0, num - 1);
		reverseCharArray(charStr, num, str.length() - 1);
		reverseCharArray(charStr, 0, str.length() - 1);
		return String.valueOf(charStr);
	}
	
	/**
	 * Find missing characters to build a palindrome
	 * This is a buggy program
	 * @param string
	 * @return
	 */
	public static int findMissingCharactersToPalindrome(String string){
		int len = string.length();
		int j=0;
		int index = 0, tempindex = 0, count = 0, tempcount = 0;
		char[] str = string.toCharArray();
		//finding longest palindrome of odd length
		for(int i = 1; i < len - 1; i++){
			j = 1;
			while(((i - j) >= 0) && ((i + j) < len) && (str[i - j] == str[i + j])){
				j++;
				if(j -1 > count){
					index = i;
					count = j - 1;
				}
			}
		}
	
		if(count >= 1){
			index = index - count;
			count = 2 * count + 1;
		}
	
		//finding longest palindrome of even  length
		tempcount=0;
		tempindex=0;
		for(int i = 0; i < len-1; i++){
			j = 0;
			while((( i - j) >= 0) && ((i + j + 1) < len) && (str[i - j]==str[i + j + 1])){
				j++;
				if(j > tempcount){
					tempindex = i;
					tempcount = j;
				}
			}
		}
		
        if(tempcount > 0) {
        	tempindex=tempindex - tempcount + 1;
        	tempcount=tempcount * 2;
        }
	
		//if even palindrome's length is greater than odd palindrome's length
		if(tempcount > count){
			index=tempindex;
			count=tempcount;
		}
		        
		return (len - count);
	}
	
	/**
	 * find the longest palindrome substring
	 * @param string
	 * @return
	 */
	public static String findLongestPalindrome(String string){ 
		char[] str = string.toCharArray();
		int index = 0, count = 0;
		int[] oddLenResult = findLongestPalindromeOddLen(str);
		int[] evenLenResult = findLongestPalindromeEvenLen(str);

		//if even palindrome's length is greater than odd palindrome's length
		if(oddLenResult[1] > evenLenResult[1]){
			index = oddLenResult[0];
			count = oddLenResult[1];
		}else{
			index = evenLenResult[0];
			count = evenLenResult[1];
		}
		
		String result = string.substring(index, index + count);
		return result;
	}
	
	public static int[] findLongestPalindromeOddLen(char[] str){
		int index = 0, count = 0;
		//finding longest palindrome of odd length
		for(int i = 1; i < str.length - 1; i++){
			int j = 1;
			while(((i - j) >= 0) && ((i + j) < str.length) && (str[i - j] == str[i + j])){
				j++;
				if(j -1 > count){
					index = i;
					count = j - 1;
				}
			}
		}
	
		if(count >= 1){
			index = index - count; //this will be the start index of the palindrome substring
			count = 2 * count + 1; //total number of chars in the palindrome
		}
		return new int[]{index, count};
	}
	
	public static int[] findLongestPalindromeEvenLen(char[] str){
		int tempindex = 0, tempcount = 0;
		//finding longest palindrome of even  length
		for(int i = 0; i < str.length - 1; i++){
			int j = 0;
			while((( i - j) >= 0) && ((i + j + 1) < str.length) && (str[i - j]==str[i + j + 1])){
				j++;
				if(j > tempcount){
					tempindex = i;
					tempcount = j;
				}
			}
		}
		
        if(tempcount > 0) {
        	tempindex=tempindex - tempcount + 1;//this will be the start index of the palindrome substring
        	tempcount=tempcount * 2;//total number of chars in the palindrome
        }
        return new int[]{tempindex, tempcount};
	}
	
	/**
	 * KMP algorithm used for determine if a String W is the substring of String S
	 * If all characters in W is distinct, there is actually no need to use KMP and could just use two pointers
	 * 
	 * Consider the case
	 * S:ABC ABCDABCDABCDABCE
	 * W:ABCDABD
	 * This will return not found if using just pointers and keep on moving when mismatch happens
	 * 
	 * @param S
	 * @param W
	 * @return
	 */
	public static int KMP(String S, String W){
		int m = 0; //the beginning of the current match in S
		int i = 0; //the position of the current character in W
		int sLen = S.length();
		final int N = W.length();
		
		int[] T = new int[N];
		/* the table used in KMP is the table used for backtracking where to start when a mismatch happens */
		T =	KMPtable(T,W);
		while( (m + i) < sLen){
			if(W.charAt(i) == S.charAt(m + i)){
				if(i == (W.length() - 1)){
					return m;
				}
				i++;
			}
			else{
				m = m + i -T[i]; //only move back the pointer T[i] step
				if(T[i] > -1){
					i = T[i];
				}else{
					i = 0;
				}
			} 
		}
		return sLen;
	}
	
	/**
	 * KMP partial matching table
	 * @param T
	 * @param W
	 * @return
	 */
	public static int[] KMPtable(int[] T, String W){
		int pos = 2; //the current position we are computing in T
		int cnd = 0; //the zero-based index in W of the next character of the current candidate substring
		T[0] = -1; //sentinel for the first element match
		T[1] = 0;
		while(pos < W.length()){
			if(W.charAt(pos - 1) == W.charAt(cnd)){
				cnd++;
				T[pos] = cnd;
				pos++;
			}
			else if(cnd > 0){
				cnd = T[cnd];
			}
			else{
				T[pos] = 0;
				pos++;
			}
		}
		return T;
	}
	/**
	 * Longest Common Substring using DP	
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String longestCommonSubstring(String str1, String str2){
		if(str2 == null || str1 == null){
			return null;
		}
		
		final int M = str1.length();
		final int N = str2.length();
		int[][] L = new int[M][N];
		int max = 0;
		String common = "";
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				if(str1.charAt(i) == str2.charAt(j)){
					if(i == 0 || j == 0){
						L[i][j] = 1;
					}
					else{
						L[i][j] = L[i-1][j-1] + 1;
					}
					if(L[i][j] > max){
						max = L[i][j];
					}
					if(L[i][j] == max){
						common = str1.substring(i - max + 1, i + 1);
					}
				}
				else L[i][j] = 0;
			}
		}
		return common;
	}
	/**
	 * get all common substrings using DP and a slightly different configuration from previous one	
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static LinkedList<String> getAllCommonSubstrings(String s1, String s2){
		if(s1 == null || s2 == null)
			return null;
		
		int len1 = s1.length(), len2 = s2.length();
		int[][] array = new int[len1 + 1][len2 + 1];
		int longest = 0;
		LinkedList<String> substrings = new LinkedList<String>();
		for(int i = 0; i < len1; i++)
		{
			for(int j = 0; j < len2; j++)
			{
				if(s1.charAt(i) == s2.charAt(j))
				{
					int v = array[i][j] + 1;
					array[i + 1][j + 1] = v;
					if(v > longest)
					{
						longest = v;
					}
					if( v == longest)
					{
						substrings.add(s1.substring(i - v + 1, i + 1));
					}
				}
			}
		}
		return substrings;
	}
	
	/**
	 * Unconcatenate a long sentence(string) into a set of words with minimum unrecognized words 
	 * @param sentence
	 * @return
	 */
	public static String unconcatenateStringIntoWords(String sentence){
		HashMap<Integer, ParseResult> cache = new HashMap<Integer, ParseResult>();
		ParseResult result = unconcatenateStringIntoWords(0, 0, sentence, cache);
		return result.parsed;
	}
	
	private static ParseResult unconcatenateStringIntoWords(int wordStart, int wordEnd,
			String sentence, HashMap<Integer, ParseResult> cache){
		if(wordEnd >= sentence.length())
			return new ParseResult(wordEnd - wordStart, sentence.substring(wordStart).toUpperCase());
		
		if(cache.containsKey(wordStart))
			return cache.get(wordStart).clone();		
		
		String word = sentence.substring(wordStart, wordEnd + 1);
		boolean validPrefix = dictionary.containsPrefix(word);
		boolean validWord = validPrefix && dictionary.containsWord(word);
		
		ParseResult bestExact = unconcatenateStringIntoWords(wordEnd + 1, wordEnd + 1, sentence, cache);
		if(validWord){
			bestExact.parsed = word + " " + bestExact.parsed; 
		}else{
			bestExact.invalidCount += word.length();
			bestExact.parsed = word.toUpperCase() + " " + bestExact.parsed;
		}
		
		ParseResult bestExtend = null;
		if(validPrefix){
			bestExtend =unconcatenateStringIntoWords(wordStart, wordEnd + 1, sentence, cache);
		}

		ParseResult best = ParseResult.min(bestExact, bestExtend);
		cache.put(wordStart, best.clone());
		return best;
	}
	
	private static class ParseResult{
		public int invalidCount = Integer.MAX_VALUE;
		public String parsed = "";
		public ParseResult(int inv, String p){
			invalidCount = inv;
			parsed = p;
		}
		
		public ParseResult clone(){
			return new ParseResult(this.invalidCount, this.parsed);
		}
		
		public static ParseResult min(ParseResult r1, ParseResult r2){
			if(r1 == null)
				return r2;
			if(r2 == null)
				return r1;
			
			return (r1.invalidCount < r2.invalidCount) ? r1 : r2; 
		}
	}
	
	/**
	 * Eliminate Comments and treat the entire input as a single string
	 * But really hard to eliminate newline this way
	 *  as there must be a roll-back mechanism to eliminate the whitespace before all line comment
	 * @param input
	 * @return
	 */
	public static String eliminateCommentsByChar(String input){
		boolean star = false;
		boolean slash = false;
		boolean deleteLine = false;
		int curIndex = 0; 
		int validIndex = 0; //keep track of the slot need to fill
		char[] buffer = input.toCharArray();
		
		while(curIndex < buffer.length){
			if(buffer[curIndex] == '/'){
				curIndex++;
				/* Only allow one of them to be set at a time */
				if(!star && buffer[curIndex] == '/'){
					slash = true;
					if(isAllWhiteSpaceLeadingLine(buffer, curIndex - 2))
						deleteLine = true;
				}else if(!slash && buffer[curIndex] == '*'){
					star = true;
				}
				/* Actually, for valid comment/code style, there must be * or / after /
				 * Just in case for the invalid comment
				 */
				if((slash || star) && curIndex < buffer.length -1)
					curIndex++;
				else
					curIndex--;
			}
			
			if(slash){
				if(buffer[curIndex] == '\n' || buffer[curIndex] == '\r'){
					slash = false;					
					if(deleteLine){
						// Roll back to last newline
						while(validIndex >= 0 && buffer[validIndex] != '\n' && buffer[validIndex] != '\r'){
							validIndex--;
						}
						validIndex++;
						if(curIndex != buffer.length -1)
							curIndex++;
						deleteLine = false;
					}
				}
			}else if(star){
				if(buffer[curIndex] == '*' && buffer[++curIndex] == '/'){
					star = false;
					curIndex++;
					// At this point, we know that if the trailing of this line is all white space or newline
					// we should roll back to last newline or last valid 
					if(isAllWhiteSpaceTrailingLine(buffer, curIndex)){
						validIndex--;
						while(validIndex >= 0 && buffer[validIndex] != '\n' && buffer[validIndex] != '\r' 
								&& (buffer[validIndex] == ' ' || buffer[validIndex] == '\t')){
							validIndex--;
						}
						/* If the roll back destination is newline then move past the next newline to delete this line */
						if(validIndex < 0 || buffer[validIndex] == '\n' || buffer[validIndex] == '\r'){
							validIndex ++;
							while(curIndex < buffer.length -1 && buffer[curIndex] != '\n' && buffer[curIndex] != '\r'){
								curIndex++;
							}
							if(curIndex < buffer.length -1){
								curIndex++;
								if(buffer[curIndex] == '/')
									continue;
							}
						}
						/* if the roll back destination is valid*/
						else{
							validIndex++;
						}
					}
				}
			}
			
			if(!star && !slash){
				buffer[validIndex] = buffer[curIndex];
				validIndex++;
			}
			curIndex++;
		}
		return String.copyValueOf(buffer, 0, validIndex);
	}
	
	public static boolean isAllWhiteSpaceLeadingLine(char[] array, int end){
		if(end < 0 || end >= array.length)
			return true;
		while(end >= 0 && array[end] != '\n'){
			if(array[end] != ' ' && array[end] != '\t')
				return false;
			end--;
		}
		return true;
	}
	
	public static boolean isAllWhiteSpaceTrailingLine(char[] array, int start){
		if(start < 0 || start >= array.length)
			return true;
		while(start < array.length && array[start] != '\n'){
			if(array[start] != ' ' && array[start] != '\t')
				return false;
			start++;
		}
		return true;
	}
	
	/**
	 * Eliminate Comments in a line by line basis
	 * But in this way, the original blank lines would be hard to keep
	 * @param input
	 * @return
	 */
	public static String eliminateCommentsByLine(String input){
		boolean star = false;
		boolean slash = false;
		int slashIndex = 0;
		int starStartIndex = 0;
		int starEndIndex = 0;
		//replace the original blank line with space so that it won't get screened out by split 
		input.replaceAll("[\\t\\x0b\\f]*[\\n\\t]", " ");
		String[] lines = input.split("[\\r\\n]");
		StringBuilder result = new StringBuilder();
		
		for(String line : lines){
			slashIndex = line.indexOf("//");
			starStartIndex = line.indexOf("/*");
			// end index is the last index for */ just in case the start is like /*//abc */
			// but this way, also remove the actually invalid comment 
			starEndIndex = line.lastIndexOf("*/");
			char[] array = line.toCharArray();
			/* Only allow the first one of them to be set at a time */
			if(!star && slashIndex > -1){
				if(starStartIndex < 0 || (starStartIndex > 0 && slashIndex < starStartIndex))
					slash = true;
			}
			if(!slash && starStartIndex > -1){
				if(slashIndex < 0 || (slashIndex > 0 && starStartIndex < slashIndex))
					star = true;
			}
			
			if(!star && !slash){
				result.append(line);
				result.append("\n");
			}
			
			if(slash){
				if(!isAllWhiteSpaceLeadingLine(array, slashIndex - 1)){
					result.append(line.subSequence(0, slashIndex));
					result.append("\n");
				}
				slash = false;
			}else if(star){
				/* If the star comment for a single line */				
				if(starStartIndex > -1 && starEndIndex > -1){
					boolean allWhiteTrailing = isAllWhiteSpaceTrailingLine(array, starEndIndex + 2);
					boolean allWhiteLeading	= isAllWhiteSpaceLeadingLine(array, starStartIndex - 1);
					/* Only add to the result if there is some valid part */
					if(!allWhiteLeading && allWhiteTrailing){
						result.append(line.substring(0, starStartIndex));
						result.append("\n");
					}
					/* Carve out the commented part */
					else if(allWhiteLeading && !allWhiteTrailing){
						result.append(line.substring(0, starStartIndex) + line.substring(starEndIndex + 2, line.length()));
						result.append("\n");
					}
					star = false;
				}
				/* If the star comment for multiple lines */
				else if(starStartIndex > -1){
					if(!isAllWhiteSpaceLeadingLine(array, starStartIndex - 1)){
						result.append(line.substring(0, starStartIndex));
						result.append("\n");
					}
				}
				else if(starEndIndex > -1){
					if(!isAllWhiteSpaceTrailingLine(array, starEndIndex + 2)){
						result.append(line.substring(starEndIndex + 2, line.length()));
						result.append("\n");
					}
					star = false;
				}
			}
		}
		return result.toString();
	}
	
}
