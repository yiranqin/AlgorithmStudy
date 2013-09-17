package edu.upenn.yiranqin.mathrelated;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NumToStringUtil {
	/**
	 * 17.7
	 * Convert an integer to its English representation in String
	 * 
	 * 1,041,000,123 to One Billion, Forty One Million, One Hundred Twenty Three
	 * 
	 * @param number
	 * @return
	 */
	public static String convertIntegerToEnglish(int number){
		if(number == 0)
			return "Zero";
		if(number < 0)
			return "Negative " + convertIntegerToEnglish( -1 * number); // be careful about the negative number
		
		String[] bigs = {"Billion", "Million", "Thousand", ""}; //2^31 > 10^9, match these two with same elements
		int[] nums = new int[4];
		
		StringBuffer representation = new StringBuffer();
		int index = 3;
		
		while(number > 0){
			nums[index] = number % 1000;
			number /= 1000;
			index--;
		}
		
		//the most significant part that is larger than 0, add it in
		representation.append(convertIntegerBelow1000(nums[index + 1]));
		representation.append(" " + bigs[index + 1]);
		for(int i = index + 2; i < 4; i++){
			if(nums[i] > 0){
				representation.append(",");
				representation.append(" " + convertIntegerBelow1000(nums[i]));
				representation.append(" " + bigs[i]);
			}
		}
		
		return representation.toString();
 	}
		
	private static String convertIntegerBelow1000(int number){
		if(number >= 1000 || number < 0)
			return null;
		
		String[] digits = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
		String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nighteen"};
		String[] tens = {"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Nighty"};
		
		int original = number;
		StringBuffer buffer = new StringBuffer();
		if(number > 100){
			buffer.append(digits[(number/100) - 1]);
			buffer.append(" Hundred");
			number = number % 100;
		}
		
		if(number >= 10 && number < 20){
			if(number != original)
				buffer.append(" ");
			buffer.append(teens[number - 10]);
		}
		else{
			if(number >= 20){
				if(number != original)
					buffer.append(" ");
				buffer.append(tens[(number / 10) - 2]);
				number %= 10;
			}
			
			if(number > 0){
				if(number != original)
					buffer.append(" ");
				buffer.append(digits[number - 1]);
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Convert an number within range into alphabetically ordered string
	 * E.g 0    a
	 *     26   aa
	 *     27   ab
	 *     376  zz
	 */
	
	private static final int NUMCHARS = 26;
	private static final long range = (long)Math.pow(2, 50) - 1;
	
    public static void numberToAlphabeticalString(){
    	ArrayList<Long[]> count = new ArrayList<Long[]>();
    	ArrayList<Long[]> lenRange = new ArrayList<Long[]>();
    	long totalCount = 0;
    	Long[] wordLenOneCount = new Long[NUMCHARS];
    	for(int i = 0; i < NUMCHARS; i++)
    		wordLenOneCount[i] = 1L;
    	
    	count.add(wordLenOneCount);
    	lenRange.add(new Long[]{0L, (long)(NUMCHARS - 1)});
    	totalCount += NUMCHARS;
    	
    	
    	/**
    	 * Precompute the count matrix Count[i][j], 
    	 * each element represents the number of strings start with jth char with length i 
    	 * where i is the index of the length to the words, 0 means word length 1, and so on
    	 * and j is the index of the chars, 0 means 'a', ...
    	 * 
    	 * Instead to compute range of each total strings
    	 *  for each length every time by summing up all columns of certain row
    	 *  we also precompute it and store it in a new matrix each row [start, end]  
    	 */
    	int curLenIndex = 1;
    	while(totalCount < range){
    		Long[] curLenCount = new Long[NUMCHARS];
    		for(int i = 0; i < NUMCHARS; i++)
    			curLenCount[i] = 0L;
    		Long[] curRange = new Long[]{0L, 0L};
    		
    		Long[] prevLenCount = count.get(curLenIndex - 1);
    		Long[] prevRange = lenRange.get(curLenIndex - 1);
    		long curLenTotal = 0;
    		for(int i = 0; i < NUMCHARS; i++){
    			for(int j = i; j < NUMCHARS; j++){
    				curLenCount[i] += prevLenCount[j]; 
    			}
    			curLenTotal += curLenCount[i];
    		}
    		totalCount += curLenTotal;
    		curRange[0] = prevRange[1] + 1;
    		curRange[1] = prevRange[1] + curLenTotal;
    		lenRange.add(curRange);
    		count.add(curLenCount);
    		curLenIndex++;
    	}
    	
    	BufferedReader in = null;
    	BufferedReader correct = null;
    	BufferedWriter out = null;
    	try{
    		in= new BufferedReader(new FileReader(new File("input000.txt")));//new InputStreamReader(System.in));
    		correct = new BufferedReader(new FileReader(new File("output000.txt")));
    		out = new BufferedWriter(new OutputStreamWriter(System.out));
    		
    		String line = null;
    		String correctLine = null;
    		while((line = in.readLine())!= null && (correctLine = correct.readLine()) != null){
    			long curValue = Long.parseLong(line);//377 + 351 + 325 + 30;//54552;//bcdmn 
    			int wordLen = 0;
    			/**
    			 * compute the length of the word given number
    			 */
    			for(wordLen = 0; wordLen < lenRange.size(); wordLen++){
    				Long[] curLenRange = lenRange.get(wordLen);
    				if( curValue >=  curLenRange[0] && curValue <=  curLenRange[1])
    					break;
    			}
    			wordLen++;
    			
    			long originalValue = curValue;
    			int originalLen = wordLen;
    			
    			/**
    			 * Iteratively compute first character of the word given (remaining)number
    			 */
    			StringBuilder buffer = new StringBuilder();
    			while(wordLen > 1){
	    			long offset = curValue - lenRange.get(wordLen - 1)[0];
	    			int charIndex = 0;
	    		
		    		while(offset > 0 && charIndex < NUMCHARS){
	    				offset -= count.get(wordLen - 1)[charIndex];
	    				charIndex ++;
	    			}
	    			charIndex = (offset == 0) ? charIndex : charIndex - 1; 
	 
	    			char curChar = (char)('a' + charIndex);
	    			
	    			/**
	    			 * bcdmn - baaaa = cdmn - aaaa - count(4, 0)
	    			 * cdmn = bcdmn - baaaa + aaaa + count(4, 0)
	    			 * 
	    			 * cdmn - caaa = dmn - aaa - count(3, 0) - count(3, 1)
	    			 * dmn = cdmn - caaa + aaa + count(3, 0) + count(3, 1)
	    			 * 
	    			 * dmn - daa = mn - aa - count(2, 0) - count(2, 1) - count(2, 2)
	    			 * mn = dmn - daa + aa + count(2, 0) + count(2, 1) + count(2, 2)
	    			 * 
	    			 * mn - aa = n - a - count(1, 0) - count(1, 1) - count(1, 2) - ... - count(1, 'm' - 'a')
	    			 * n = mn - aa + a + count(1, 0) + count(1, 1) + count(1, 2) + ... + count(1, 'm' - 'a')
	    			 * 
	    			 * n = n - a
	    			 */
	    			curValue -= lenRange.get(wordLen -1)[0];
//	    			System.out.println(curValue + " " + charIndex +" " + wordLen + " " + offset);
	    			for(int i = 0; i < charIndex; i++){
	    				curValue -= count.get(wordLen - 1)[i];
	    			}
    				curValue += lenRange.get(wordLen - 2)[0];
    				for(int i = 0; i < charIndex; i++){
	    				curValue += count.get(wordLen - 2)[i];
	    			}
	    			
	    			buffer.append(curChar);
	    			wordLen--;
    			}
    			if(wordLen == 1 && curValue >= 0)
    				buffer.append((char)('a' + curValue));
    			 			
    			String output = buffer.toString();
    			out.write(output + " " + correctLine + " " + output.equals(correctLine) + " " + originalValue + " " + curValue + " " + originalLen + "\n");
    		}
    	}catch(IOException ioex){
    		System.err.println("IO error");
    		ioex.printStackTrace();
    	}catch(NumberFormatException  ex){
    		System.err.println("Parsing error");
    		ex.printStackTrace();
    	}finally{
    		try{
	    		if(in != null)
	    			in.close();
	    		if(out != null)
	    			out.close();
	    		if(correct != null)
	    			correct.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }

}
