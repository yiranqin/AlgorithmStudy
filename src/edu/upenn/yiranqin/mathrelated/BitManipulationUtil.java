package edu.upenn.yiranqin.mathrelated;

import java.util.ArrayList;

public class BitManipulationUtil {
	/**
	 * 5.1
	 * Insert a 32 bits integer m into n from bit i through bit j
	 * @param n
	 * @param m
	 * @param i
	 * @param j
	 * @return if adding successfully
	 */
	public static int insertMIntoN(int n, int m, int i, int j){
		int nBits = Integer.toBinaryString(n).length();
		int mBits = Integer.toBinaryString(m).length();
		if(mBits < (j - i + 1) || j < i || nBits < j)
			return -1;
		
		int tmp = n & ((1<<i) - 1);
		
		int result = n & ~((1 << (j + 1)) - 1);
		return result | tmp | (m << i);
		
//		int mask = ((~0) << j) | tmp;
//		return n & mask | (m << i);
	}
	
	/**
	 * 5.2
	 * generate the binary representation of a double value
	 * @param value
	 * @return string 
	 */
	public static String doubleBinaryString(double value){
		if(value >= 1 || value <= 0)
			return "ERROR";
		
		int bitNum = 1;
		StringBuffer buffer = new StringBuffer();
		buffer.append("0.");
		
//		while(value != (int)value){
//			value *= 2;
//			if(value > 1)
//				buffer.append("1");
//			else
//				buffer.append("0");
		
		while(value > 0){
			value *= 2;
			if(value >= 1){
				buffer.append("1");
				value -= 1;
			}else{
				buffer.append("0");
			}
			
			if(bitNum >= 32)
				return "ERROR";
			
			bitNum++;
		}
		
		return buffer.toString();
	}
	
	/**
	 * 5.3
	 * find the next larger number with same 1s in binary presentation
	 * @param x
	 * @return
	 */
	public static int findNextLarger(int x){
		if(x < 0)
			return -1;
		
		int firstOneBit = 0; //first 1 bit
		int targetBit = 0; // first 0 bit after successive 1s
		
		while((x & (1 << firstOneBit)) == 0){
			firstOneBit++;
		}
		
		targetBit = firstOneBit + 1;
		while((x & (1 << targetBit)) != 0){
			//no 0 in the upper half, then can't be larger
			if(targetBit >= 30)
				return -1;
			targetBit++;
		}
		
//		int lowerBits = x & ((1 << targetBit) - 1);		
//		return (x & ~((1 << targetBit) - 1)) | (lowerBits >> (firstOneBit + 1)) | (1 << targetBit);
		
		//11011001111100 -> 11011010001111
		return x + (1 << firstOneBit ) - 1 + 1 + (1 << (targetBit - firstOneBit - 1)) - 1;
	}
	
	/**
	 * 5.3
	 * find the next smaller number with same 1s in binary presentation
	 * @param x
	 * @return
	 */
	public static int findNextSmaller(int x){
		if(x < 0)
			return -1;
		
		int firstZeroBit = 0; //first 0 bit 
		int targetBit = 0; // first 1 bit after successive 0s
		
		while((x & (1 << firstZeroBit)) != 0){
			firstZeroBit++;
		}
		
		targetBit = firstZeroBit + 1;		
		while((x & (1 << targetBit)) == 0){
			//no 1 in the upper half, then can't be smaller
			if(targetBit >= 30)
				return -1;
			targetBit++;
		}
		
//		int lowerBits = x & ((1 << firstZeroBit) - 1);
//		return (x & ~((1 << (targetBit + 1)) - 1)) | (lowerBits << (targetBit - firstZeroBit -1)) | (1 << (targetBit -1));
		
		//10011110000011 -> 10011101110000
		return x - ((1 << firstZeroBit) - 1) - 1 - ((1 << (targetBit - firstZeroBit - 1)) - 1);
	}
	
	
	/**
	 * 5.5 
	 * count the number of bits needed to convert from a to b
	 * @param a
	 * @param b
	 * @return
	 */
	public static int bitsNeededToConvert(int a, int b){
		int count = 0;
		for(int i = a ^ b; i > 0; i = i & (i -1)){
			count ++;
		}
		return count;
	}
	
	/**
	 * 5.6
	 * swap the odd and even bits of an integer
	 * @param value
	 * @return
	 */
	public static int swapOddAndEvenBits(int value){
		return ((value & 0xaaaaaaaa) >> 1) | ((value & 0x55555555) << 1);
	}
	
	public static int fecthJBit(int a, int j){
		if(j > 31 || j < 0)
			return -1;
		
		return (a >> j) & 1;
	}
	
	
	/**
	 * 5.7
	 * Find missing integer without access to the entire integer
	 * @param array
	 * @return
	 */
	public static int findMissingInteger(int[] array){
		int result = 0;
		
		ArrayList<Integer> list0 = new ArrayList<Integer>(array.length/2);
		ArrayList<Integer> list1 = new ArrayList<Integer>(array.length/2);
		ArrayList<Integer> tmp;
		
		for(int i : array){
			if(fecthJBit(i, 0) == 0){
				list0.add(i);
			}else{
				list1.add(i);
			}
		}
		
		int bit = 0;
		while(bit < 32 && (list0.size() > 0 || list1.size() > 0)){
			if(list0.size() > list1.size()){
				result |= 1<<bit;
				tmp = list1;								
			}else{
				result |= 0<<bit;
				tmp = list0;
			}
			bit++;
			
			list0 = new ArrayList<Integer>();
			list1 = new ArrayList<Integer>();
			for(int i : tmp){
				if(fecthJBit(i, bit) == 0){
					list0.add(i);
				}else{
					list1.add(i);
				}
			}
		}
		
		return result;
	}
	
	public static int findMissingIntegerRecursively(int[] array){
		int result = 0;
		
		ArrayList<Integer> list0 = new ArrayList<Integer>(array.length/2);
		ArrayList<Integer> list1 = new ArrayList<Integer>(array.length/2);
		ArrayList<Integer> tmp;
		
		for(int i : array){
			if(fecthJBit(i, 0) == 0){
				list0.add(i);
			}else{
				list1.add(i);
			}
		}
		
		if(list0.size() > list1.size()){
			result = 1;
			tmp = list1;								
		}else{
			result = 0;
			tmp = list0;
		}
		
		result |= find(tmp, 1);
		return result;
	}
	
	public static int find(ArrayList<Integer> list, int bit){
		if(list.size() == 0 || bit > 31)
			return 0;
		
		int result = 0; 
		ArrayList<Integer> list0 = new ArrayList<Integer>(list.size()/2);
		ArrayList<Integer> list1 = new ArrayList<Integer>(list.size()/2);
		for(int i : list){
			if(fecthJBit(i, bit) == 0){
				list0.add(i);
			}else{
				list1.add(i);
			}
		}

		if(list0.size() > list1.size()){
			// be careful when using bit++, bit+1, ++bit 
			result = find(list1, bit + 1);
			return result | (1<<bit); 								
		}else{
			result = find(list0, bit + 1);
			return result | (0<<bit); 
		}
	}
	
	/**
	 * 5.8
	 * check border conditions and offset calculation
	 * 
	 * Notice how firstFullByte and lastFullByte get computed
	 * 
	 * @param screen
	 * @param width
	 * @param x1
	 * @param x2
	 * @param y
	 */
	public static byte[] drawLine(byte[] screen, int width, int x1, int x2, int y){
		if(x1 > width || x2 > width || x2 < x1 || y >= screen.length * 8 / width)
			return null;
		
		int startOffset = x1 % 8;
		int firstFullByte = (startOffset > 0) ? x1/8 + 1 : x1/8;
		
		int endOffset = x2 % 8;
		int lastFullByte = (endOffset == 7) ? x2/8 : x2/8 -1;
		
		byte startMask = (byte)(0xFF >> startOffset); // do not mess up with real bytes
		byte endMask = (byte)~(0xFF >> (endOffset + 1));
		
		for(int i = firstFullByte; i <= lastFullByte; i++){
			screen[(width * y / 8) + i] = (byte)0xFF;
		}
		
		//in same byte
		if(firstFullByte > lastFullByte){
			screen[(width * y / 8) + x1/8] |= (startMask & endMask); 
		}
		else{
			if(startOffset != 0){
				screen[(width * y / 8) + x1/8] |= startMask; 
			}
			if(endOffset != 7){
				screen[(width * y / 8) + x2/8] |= endMask; 
			}
		}
		
		return screen;
	}	
	
	public static int findMissingIntegerInIncreasingSequence(int[] array, int start, int end){
		int result = 0;
		for(int i = start; i <= end; i++){
			result ^= i;
		}
		
		for(int i : array){
			result ^= i;
		}
		
		return result;
	}
	
	public static int findOnlyIntegerAppearOnce(int[] array){
		int result = 0;
		for(int i : array){
			result ^= i;
		}
		
		return result;
	}
	
	public static int[] findTwoIntegersAppearOnce(int[] array){
		int all = 0;
		for(int i : array){
			all ^= i;
		}
		int bit = getFirstOneBit(all);
		
		int part = 0;
		for(int i : array){
			if((i & (1 << bit))  > 0)
				part ^= i;
		}
		
		int[] result = new int[]{part, all^part};
		return result;
	}
	
	/**
	 * Find three integers that appear only once in an array
	 * the key to problem like this is to find one bit that is owned only by one of the integers need to find
	 * So that we could partition the original data set
	 * 
	 * For three integers a,b,c 
	 * It is possible that a^b^c=x= 0, but a^b= x^c> 0, b^c=x^a>0, a^c=x^b>0
	 * 
	 * f(n) - keep first 1 bit and clear all other bits
	 * 
	 * f(x^c) > 0, f(x^a) > 0, f(x^b) > 0
	 * f(f(x^c) ^ f(x^a) ^ f(x^b)) > 0, since there are odd number of sub problem 
	 * Suppose f(f(x^c) ^ f(x^a) ^ f(x^b)) = m
	 * if f(x^c) = f(x^b) = f(x^a) = m, then a&m = b&m = c&m != x&m, while x = a^b^c, so x&m = a&m = b&m = c&m, not possible
	 * So suppose f(f(x^c) ^ f(x^a) ^ f(x^b)) = f(x^c) = m, f(x^b) != m, and f(x^a) !=m, then c could only be one of those f(x^i) = m
	 * 
	 * 
	 * @param array
	 * @return
	 */
	public static int[] findThreeIntegersAppearOnce(int[] array){
		int all = 0;
		for(int i : array){
			all ^= i;
		}
		
		int flagBit = 0;
		for(int i : array){
			flagBit ^= keepFirstOne(all ^ i);
		}
		flagBit = keepFirstOne(flagBit);
		
		int[] result = new int[3];
		for(int i : array){
			if(keepFirstOne(all ^ i) == flagBit){
				result[0] ^= i;
			}
		}
		
		int[] newArray = new int[array.length + 1];
		for(int i = 0; i < array.length; i++){
			newArray[i] = array[i];
		}
		newArray[array.length] = result[0];
		
		int[] twoResult = findTwoIntegersAppearOnce(newArray);
		result[1] = twoResult[0];
		result[2] = twoResult[1];
		
		return result;
	}
	
	/**
	 * Keep the last one bit for the number
	 * @param num
	 * @return
	 */
	public static int keepFirstOne(int num){
		return num & ~(num - 1);
	}
	
	public static int getFirstOneBit(int num){
		int bit = 0;
		while(num > 0){
			if((num & 1) > 0)
				return bit;
			num >>= 1;
			bit++;
		}
		return -1;
	}
	
	public static boolean isPowerOfTwo(int num){
		return (num & (num - 1)) == 0;
	}
	
	public static int countSetBits(int num){
		num = (num & 0x55555555) + ((num >> 1) & 0x55555555);
		num = (num & 0x33333333) + ((num >> 2) & 0x33333333);
		num = (num & 0x0f0f0f0f) + ((num >> 4) & 0x0f0f0f0f);
		num = (num & 0x00ff00ff) + ((num >> 8) & 0x00ff00ff);
		num = (num & 0x0000ffff) + ((num >> 16) & 0x0000ffff);
		return num;
	}
}
