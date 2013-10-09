package edu.upenn.yiranqin.stringrelated;

import java.util.LinkedList;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class StringUtil{
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
}
