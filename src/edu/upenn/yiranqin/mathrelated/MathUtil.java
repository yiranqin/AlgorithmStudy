package edu.upenn.yiranqin.mathrelated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.scalabilityrelated.MyIntegerBitmap;

public class MathUtil {
	public static long invocation = 0;
	
	/**
	 * Joseph ring, remove the mth from the circle at every time. Find the last remaining number 
	 * This implementation used a methodology of projection
	 * To maintain the same number chosen routine, have to project the original sequence to a new one
	 * and use the projection rule to project the number back to the original one
	 * 
	 * h(n, m) = f'(h(n-1, m)) = (h(n-1, m) + m) % n
	 *   
	 * @param n integers (0, 1, .., n-1) from a circle
	 * @param m remove the mth number at every time
	 * @return the last Integer
	 */
	public static int josephRing(int n, int m){
		if(n <= 0 || m <0)
			return -1;
		
		int lastInteger = 0;
		// Notice the ending condition is n
		for(int i = 2; i <= n; i++)
			lastInteger = (lastInteger + m) % i;
		
		return lastInteger;
	}
	
	/**
	 * 18.1
	 * add two integer number without using +
	 * @param a
	 * @param b
	 * @return
	 */
	public static int add(int a, int b){
		int tmp = a ^ b;
		int carry = a & b;
		int result = tmp ^ (carry << 1);
				
		while(carry != 0){			
			result = tmp ^ (carry << 1);
			carry = tmp & (carry << 1);
			tmp = result;
		}
		
		return result;
	}
	
	public static int  addRecursively(int a, int b){
		if(b == 0) return a;
		
		int tmp = a ^ b;
		int carry = (a & b) << 1;
		
		return addRecursively(tmp, carry);
	}
	
	/**
	 * To compute the greatest common divisor of two integer
	 * use idea of recursion gcd(a, b) = gcd(b, a % b)
	 * @param a
	 * @param b
	 * @return
	 */
	
	public static int gcd(int a, int b){
		int larger = Math.max(a, b);
		int smaller = Math.min(a, b);
				
		int tmp = larger;
		while(smaller > 0){
			tmp = larger;
			larger = smaller;
			smaller = tmp % smaller;
		}
		
		return larger;
	}
	
	public static int gcdRecursively(int a, int b){
		int larger = Math.max(a, b);
		int smaller = Math.min(a, b);
		
		if(smaller == 0) return larger;
		
		return gcdRecursively(smaller, larger % smaller);
	}
	
	/**
	 * 17.3
	 * Number of trailing zeros equals to number of 2 and 5 factors pairs,
	 * which is also number of factors of 5
	 * @param n
	 * @return
	 */
	public static int computeTrailingZerosInNFactorials(int n){
		if(n < 0) return -1;
		
		int count = 0;
		for(int i = 5; i < n; i *= 5){
			count += n / i;
		}
		
		return count;
	}
	
	/**
	 * nFactorials without using for, while, and if statement
	 * 1 * 2 * ....* limit
	 * @param limit
	 * @return
	 */
	
	public static long nFactorials(int limit){
		long result = 1;
		boolean tmp = (limit > 1) && ((result = limit * nFactorials(limit - 1)) > 0);
		return result;
	}
	
	public static long combination(int n, int m){
		if(n < m)
			return -1;
		return nFactorials(n)/(nFactorials(m) * nFactorials(n-m)); 
	}
	
	public static long permutation(int n, int m){
		if(n < m)
			return -1;
		return nFactorials(n)/nFactorials(n - m);
	}
	/**
	 * nSum without using for, while, and if statement
	 * 1 + 2 + ....+ limit
	 * @param limit
	 * @return
	 */
	
	public static long nSum(int limit){
		long result = 0;
		boolean tmp = (limit > 0) && ((result = limit + nSum(limit - 1)) > 0);
		return result;
	}
	
	public static int flip(int bit){
		return bit ^ 1;
	}
	
	public static int sign(int a){
		return flip((a >> 31) & 1);
	}
		
	/**
	 * 17.4
	 * max without using any condition statement and take overflow into account
	 * @param a
	 * @param b
	 * @return
	 */
	public static int max(int a, int b){
		// when a is a very large positive integer while b is negative integer, it is possible for tmp to overflow, should return a
		// when a is a very small negative integer while b is positive integer, it is possible for tmp to overflow, should return b
		int tmp = a - b;
		int signTmp = sign(tmp);
		int signA = sign(a);
		int signB = sign(b);
		
//		int overflow = (signA ^ signB) & (signA ^ signTmp);
//		int useOfA =  overflow & signA;
//		int useOfB = overflow & signB;
// 		
//		return a * signTmp + b * flip(signTmp) + useOfA * (a - b) + useOfB * (b - a);
		
		int useSignA = signA ^ signB;
		int k = (useSignA * signA) + (flip(useSignA) *  signTmp);
		return k * a + flip(k) * b;
	}
	
	/**
	 * 7.4 
	 * Use only + to implement subtract, multiply, divide
	 * @param a
	 * @return
	 */
	
	/**
	 * Negate method here is fairly important for this problem, also a good place for code reuse 
	 */
	public static int negate(int a){
		int result = 0;
		int step = (a > 0) ? -1 : 1;
		while(a != 0){
			a += step;
			result += step;
		}
		return result;
	}
	
	public static int subtract(int a, int b){
		return a + negate(b);
	}
	
	public static int abs(int a){
		if(a > 0)
			return a;
		else
			return negate(a);
	}
	
	public static int multiply(int a, int b){
		int result = 0;
		if(b == 0 || a == 0)
			return 0;
		if(abs(b) > abs(a))
			return multiply(b, a);
		
		int absB = abs(b);
		for(int i = 0; i < absB; i++){
			result += a;
		}
		if(b > 0)
			return result;
		else
			return negate(result);
	}
	
	public static int divide(int a, int b){
		int result = 0;
		if(b == 0)
			return Integer.MIN_VALUE;
		if(a == 0)
			return 0;
		
		int absA = abs(a);
		int absB = abs(b);
		if(absA < absB)
			return 0;
		
//		while(absA > 0){
//			absA = subtract(absA, absB);
//			result++;
//		}
//		if(multiply(a, b) > 0)
//			return result;
//		else
//			return negate(result);
		int productB = 0;
		while(productB < absA){
			productB += absB;
			result++;
		}
		
		if((a > 0 && b > 0) || (a < 0 && b < 0))
			return result;
		else
			return negate(result);
	}
	
	/**
	 * 18.4
	 * Count the number of appearance of digit m from 0 ... n
	 * the count of number containing m at digit i depends on upper half of the number  
	 * @param n
	 * @param m
	 * @return the count
	 */
	public static int countDigitMInRangeN(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		
		// since 0 is not included from the range, so eliminate it
		if(m ==0 && n < 10)
			return 0;
		
		if(n == 0)
			return 0;
		else if(n < 10 && n >= m)
			return 1;
		
		int len = String.valueOf(n).length();
		int count = 0;
		
		for(int i = 0; i < len; i++){
			count += countDigitMInRangeAtDigit(n, m, i);
		}
		return count;
	}
	
	private static int countDigitMInRangeAtDigit(int n, int m, int i){
		int count = 0;
		int curWeight = (int)Math.pow(10, i);
		int curDigit = (n / curWeight) % 10;
		int nextWeight = curWeight * 10;
		
		if(m != 0){
			if(curDigit < m)
				count = (n / nextWeight) * curWeight;
			else if(curDigit == m)
				count = (n / nextWeight) * curWeight + (n % curWeight) + 1;
			else{
				count = (n / nextWeight + 1) * curWeight;	
			}
		}else{
			count = (n / nextWeight) * curWeight; 
		}
		
		return count;
	}
	
	/**
	 * brute force way of computing digit counts
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countNumOfMInRange(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		
		int count = 0;
		for(int i = 1; i <= n; i++){
			count += numOfMInValue(i, m);
		}
		return count;
	}
	
	public static int numOfMInValue(int value, int m){
		int count = 0;
		while(value > 0){
			if(value % 10 == m)
				count++;
			
			value /= 10;
		}
		return count;
	}
	
	/**
	 * brute force way of computing numbers that contain certain digit
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countNumContainsMInRange(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		
		int count = 0;
		for(int i = 1; i <= n; i++){
			if(containM(i, m))
				count++;
		}
		return count;
	}
	
	public static boolean containM(int value, int m){
		while(value > 0){
			if(value % 10 == m)
				return true;
			
			value /= 10;
		}
		return false;
	}
	
	/**
	 * Elegant way to count numbers that contain certain digit
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countNumContainsDigitMInRange(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		// since 0 is not included from the range, so eliminate it
		if(m ==0 && n < 10)
			return 0;
		
		if(n == 0)
			return 0;
		else if(n < 10 && n >= m)
			return 1;
		
		invocation++;
		int len = String.valueOf(n).length();
		int count = 0;
		
		for(int i = 0; i < len; i++){
			count += countNumContainsDigitMInRangeAtDigit(n, m, i);
		}
		return count;
	}
	
	private static int countNumContainsDigitMInRangeAtDigit(int n, int m, int i){		
		int count = 0;
		int curWeight = (int)Math.pow(10, i);
		int curDigit = (n / curWeight) % 10;
		int nextWeight = curWeight * 10;
		int upperPart = n / nextWeight;
		int higherPart = (upperPart == 0) ? 0 : (upperPart - countNumContainsDigitMInRange(upperPart, m));
		
		if(m != 0){			
			if(curDigit < m){
				//if the last number of the upper part contains the digit
				// say  n = 919, m = 9, digit = 1, there were supposedly 0 - 8 possible upper part since the lower part should be 90 - 99
				// now that 9 contains 9, eliminate the numbers in upper part that contains 9, we are actually eliminating one already not in possible set
				// so add it back
				if(containM(upperPart, m))
						higherPart ++;
				
				count = higherPart * curWeight;
			}
			else if(curDigit == m)
				count = higherPart * curWeight + (n % curWeight) + 1;
			else{
				count = (higherPart + 1) * curWeight;	
			}
		}else{
			count = higherPart * curWeight; 
		}
		
		return count;
	}
	
	
	/**
	 * DP implementation of Elegant way to count numbers that contain certain digit
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countNumContainsDigitMInRangeDP(int n, int m, HashMap<Integer, Integer> map){
		if(m > 9 || m < 0 || map == null)
			return -1;
		// since 0 is not included from the range, so eliminate it
		if(m ==0 && n < 10)
			return 0;
		
		if(n == 0)
			return 0;
		else if(n < 10 && n >= m)
			return 1;
		
		if(map.containsKey(n))
			return map.get(n);
		
		invocation++;
		int len = String.valueOf(n).length();
		int count = 0;
		
		for(int i = 0; i < len; i++){
			count += countNumContainsDigitMInRangeAtDigitDP(n, m, i, map);
		}
		
		map.put(n, count);
		return count;
	}
	
	private static int countNumContainsDigitMInRangeAtDigitDP(int n, int m, int i, HashMap<Integer, Integer> map){		
		int count = 0;
		int curWeight = (int)Math.pow(10, i);
		int curDigit = (n / curWeight) % 10;
		int nextWeight = curWeight * 10;
		int upperPart = n / nextWeight;
		int higherPart = (upperPart == 0) ? 0 : (upperPart - countNumContainsDigitMInRangeDP(upperPart, m, map));
		
		if(m != 0){			
			if(curDigit < m){
				//if the last number of the upper part contains the digit
				// say  n = 919, m = 9, digit = 1, there were supposedly 0 - 8 possible upper part since the lower part should be 90 - 99
				// now that 9 contains 9, eliminate the numbers in upper part that contains 9, we are actually eliminating one already not in possible set
				// so add it back
				if(containM(upperPart, m))
						higherPart ++;
				
				count = higherPart * curWeight;
			}
			else if(curDigit == m)
				count = higherPart * curWeight + (n % curWeight) + 1;
			else{
				count = (higherPart + 1) * curWeight;	
			}
		}else{
			count = higherPart * curWeight; 
		}
		
		return count;
	}
	
	/**
	 * Count numbers that contain certain digit using combination 
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countNumContainsDigitMInRangeCombination(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		// since 0 is not included from the range, so eliminate it
		if(m ==0 && n < 10)
			return 0;
		
		if(n == 0)
			return 0;
		if(n < 10 && n >= m)
			return 1;
		
		int len = String.valueOf(n).length();
		int MSBDigit = n / (int)Math.pow(10, len-1);
		int count = 0;
		
		if(m != 0){
			if(MSBDigit > m){
				count += (int)Math.pow(10, len-1);
				MSBDigit --; // 1 less needed to do the combination since already counted the whole 10^(len-1) numbers starting with m 
				count += countNumContainsDigitMInRangeCombination(n % (int)Math.pow(10, len-1), m);			
			}
			else if(MSBDigit == m){
				count += n % (int)Math.pow(10, len-1) + 1;
			}else{
				count += countNumContainsDigitMInRangeCombination(n % (int)Math.pow(10, len-1), m);	
			}
			
		}else{			
			count += countNumContainsDigitMInRangeCombination(n % (int)Math.pow(10, len-1), m); 
		}
		
		for(int i = 1; i < len; i++){
			count += MSBDigit *combination(len - 1, i) * Math.pow(9, len - 1 - i);
		}
		
		return count;
	}
	
	/**
	 * Count appearance of contain certain digit using combination 
	 * @param n
	 * @param m
	 * @return
	 */
	public static int countDigitMInRangeNCombination(int n, int m){
		if(m > 9 || m < 0)
			return -1;
		// since 0 is not included from the range, so eliminate it
		if(m ==0 && n < 10)
			return 0;
		
		if(n == 0)
			return 0;
		if(n < 10 && n >= m)
			return 1;
		
		int len = String.valueOf(n).length();
		int MSBDigit = n / (int)Math.pow(10, len-1);
		int count = 0;
		
		for(int i = 1; i < len; i++){
			count += combination(len - 1, i) * Math.pow(9, len - 1 - i) * i;
		}
		
		if(m != 0){
			count *=  MSBDigit;
			if(MSBDigit > m){
				count += (int)Math.pow(10, len-1);
			}
			else if(MSBDigit == m){
				count += n % (int)Math.pow(10, len-1) + 1;
			}
			count += countDigitMInRangeNCombination(n % (int)Math.pow(10, len-1), m);			
		}else{
			count *= MSBDigit;
			count += countDigitMInRangeNCombination(n % (int)Math.pow(10, len-1), m); 
		}
		
		
		
		return count;
	}	
	
	public static long fibonacci(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0,1};
		if(n < 2)
			return result[n];
		
		long FibNminus2 = 0;
		long FibNminus1 = 1;
		long FibN = 0;
		
		for(int i = 2; i <= n; i++){
			FibN = FibNminus1 + FibNminus2;
			// Caution about the order to update 
			FibNminus2 = FibNminus1;
			FibNminus1 = FibN;
		}
		
		return FibN;
	}
	
	public static long fibonacciMatrix(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0,1};
		if(n < 2)
			return result[n];
		
		Matrix2By2 base = new Matrix2By2(1, 1, 1, 0);
		Matrix2By2 PowerNMinus1 = base.power(n - 1);
		
		return PowerNMinus1.m00;
	}
	
	public static long countWaysToJumpNStairs(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0,1,2};
		if(n < 3)
			return result[n];
		
		long nMinusOneStairsWays = 2; //Be very careful about the value assigned
		long nMinusTwoStairsWays = 1;
		long nStairsWays = 0;
		
		for(int i = 3; i <= n; i++){
			nStairsWays = nMinusOneStairsWays + nMinusTwoStairsWays;
			nMinusTwoStairsWays = nMinusOneStairsWays; 
			nMinusOneStairsWays = nStairsWays;
		}
		
		return nStairsWays;
	}
	
	public static long countWaysToJumpNStairsMatrix(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0,1,2};
		if(n < 3)
			return result[n];
		
		Matrix2By2 base = new Matrix2By2(2, 1, 1, 0);
		Matrix2By2 factor = new Matrix2By2(1, 1, 1, 0);
		Matrix2By2 PowerNMinus2 = factor.power(n - 2).multiply(base);
				
		return PowerNMinus2.m00;
	}
	
	/**
	 * 9.1
	 * count the number of ways to jump up to stairs n with 1, 2 or 3 step at one time
	 * @param n
	 * @return
	 */
	public static long countJumpNStairsInThreeWays(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0, 1, 2, 4};
		if(n < 4)
			return result[n];
		
		long nMinusOneStairsWays = 4; //Be very careful about the value assigned
		long nMinusTwoStairsWays = 2;
		long nMinusThreeStairsWays = 1;
		long nStairsWays = 0;
		
		for(int i = 4; i <= n; i++){
			nStairsWays = nMinusOneStairsWays + nMinusTwoStairsWays + nMinusThreeStairsWays;
			nMinusThreeStairsWays = nMinusTwoStairsWays;
			nMinusTwoStairsWays = nMinusOneStairsWays; 
			nMinusOneStairsWays = nStairsWays;
		}
		
		return nStairsWays;
	}
	
	public static long countJumpNStairsInThreeWaysMatrix(int n){
		if(n < 0)
			return -1;
		
		int[] result = {0, 1, 2, 4};
		if(n < 4)
			return result[n];
		
		Matrix3By3 base = new Matrix3By3(7, 4, 2, 4, 2, 1, 2, 1, 0);
		Matrix3By3 factor = new Matrix3By3(1, 1, 0, 1, 0, 1, 1, 0, 0);
		Matrix3By3 PowerNMinus3 = base.multiply(factor.power(n - 4));
		
//		System.out.println(PowerNMinus3.m00 + " " + PowerNMinus3.m01 + " " + PowerNMinus3.m02
//						+ " " + PowerNMinus3.m10 + " " + PowerNMinus3.m11 + " " + PowerNMinus3.m12
//						+ " " + PowerNMinus3.m20 + " " + PowerNMinus3.m21 + " " + PowerNMinus3.m22);
		return PowerNMinus3.m00;
	}
	
	/**
	 *  Print the probability of every possible of sum for number of dices
	 */
	
	public static final int diceMax = 6;	
	public static void printSumProbabilityOfDiceRecursively(int number){
		if(number < 1)
			return;
		
		long totalSituations = (long)Math.pow(diceMax, number);
		System.out.println("Dice Sum" + "\t" + "Possibility");
		for(int i = number; i <= number * diceMax; i++){
			long curSituations = countSituationsForSumAndDiceNum(i, number);
			double curPossibility = (double)curSituations / (double)totalSituations;
			System.out.printf("%d \t %.5f\t\n", i, curPossibility); //Notice how the format is being handled
		}		
	}
	
	private static long countSituationsForSumAndDiceNum(int diceSum, int diceNum){
		if(diceNum <= 0 || diceSum < 1)
			return 0;
		
		if(diceNum == 1){
			if(diceSum >= diceNum && diceSum <= diceNum * diceMax)
				return 1;
			else 
				return 0;
		}
		
		long situations = 0;
		for(int i = 1; i <= diceMax; i++){
			situations += countSituationsForSumAndDiceNum(diceSum - i, diceNum - 1);
		}
		
		return situations;
	}
	
	/**
	 * Print the probability of every possible of sum for number of dices
	 * DP implementation, using only two arrays rather than number of arrays
	 * 
	 * @param number
	 */
	public static void printSumProbabilityOfDiceDP(int number){
		if(number < 1)
			return;
		
		long totalSituations = (long)Math.pow(diceMax, number);
		
//		long[][] situations = new long[number][diceMax * number + 1];
		long[][] situations = new long[2][diceMax * number + 1]; // using one extra long to ease the index
		for(int i = 1; i <= 6; i++){
			situations[0][i] = 1;
		}
		
		int index = 0;
		for(int diceNum = 2; diceNum <= number; diceNum++){
			index = 1 - index;
			
			// Be careful about the starting point
			for(int i = diceNum; i <= diceNum * diceMax; i++){
				// Since we are repeatedly using this, should recalculate every round 
				situations[index][i] = 0;
				// Since we are calculating current dice number from last one, sum of last one is at least last dice number
				// i - j >= diceNum -1  
				for(int j = 1; j <= diceMax && j <= i - diceNum + 1; j++){
					situations[index][i] += situations[1 - index][i - j];
//					situations[diceNum - 1][i] += situations[diceNum - 2][i - j];
				}
			}			
		}
		
		System.out.println("Dice Sum" + "\t" + "Possibility");
		for(int i = number; i <= number * diceMax; i++){
			System.out.printf("%d \t %.5f\t\n", i, (double)situations[index][i]/(double)totalSituations); //Notice how the format is being handled
//			System.out.printf("%d \t %.5f\t\n", i, (double)situations[number - 1][i]/(double)totalSituations); //Notice how the format is being handled
		}
		
	}
	
	public static double power(double base, int power){
		if(power == 0)
			return 1.0;
		else if(power < 0)
			return 1.0 / powerAsPositiveInteger(base, (-1)*power);
		else
			return powerAsPositiveInteger(base, power);
	}
	
	public static double powerAsPositiveIntegerRecursively(double base, int power){
		if(power == 0)
			return 1.0;
		if(power == 1)
			return base;
		
		//Since we are doing integer division, we could multiply back what's left at the end
		double result = powerAsPositiveIntegerRecursively(base, power>>1);
		result *= result;
		if(power % 2 == 1)
			result *= base;
		
		return result;
	}
	
	private static double powerAsPositiveInteger(double base, int power){
		if(power == 0)
			return 1.0;
		if(power == 1)
			return base;
		
		MyIntegerBitmap bitmap = new MyIntegerBitmap(power);
		int bitsSet = bitmap.countSetBits();
		int bitIndex = 0;
		int bitCount = 0;
		double result = 1.0;
		double tmp = 1.0;
		
		while(bitCount < bitsSet){
			if(bitIndex == 0)
				tmp *= base;
			else
				tmp *= tmp;
			
			if(bitmap.getBit(bitIndex) == 1){
				result *= tmp;
				bitCount++;
			}
			bitIndex++;
		}
		return result;
	}
	
	public static boolean isPrime(int n){
		if(n < 2)
			return false;
		
		int sqrt = (int)Math.sqrt(n);
		for(int i = 2; i <= sqrt; i++){
			if(n % i == 0)
				return false;
		}
		
		return true;
	}
	
	public static ArrayList<Integer> generateListOfPrimeWithLimit(int max){
		if(max < 2)
			return null;
		boolean[] flags = new boolean[max + 1];
		ArrayList<Integer> primeList = new ArrayList<Integer>(max/6);
		
		int prime = 2;
		primeList.add(2);
		while(prime <= max){
			crossOffNonPrimes(flags, prime);
			
			prime = getNextPrime(flags, prime);
			if(prime == -1)
				break;
			else{
				primeList.add(prime);
			}
		}
		
		return primeList;
	}
	
	/**
	 * Sieve Of Eratos thenes approach, mark every number as prime or not
	 * @param max
	 * @return
	 */
	public static int generateLargestPrimeWithLimit(int max){
		if(max < 2)
			return -1;
		boolean[] flags = new boolean[max + 1];
		
		int prime = 2;
		int result = prime;
		while(prime <= max){
			crossOffNonPrimes(flags, prime);
			
			prime = getNextPrime(flags, prime);
			if(prime == -1)
				break;
			else{
				result = prime;
			}
		}
		
		return result;
	}
	
	private static void crossOffNonPrimes(boolean[] flags, int prime){
		for(int i = prime * prime; i < flags.length; i += prime){
			if(i % prime == 0)
				flags[i] = true;
		}
	}
	
	private static int getNextPrime(boolean[] flags, int prime){		
		for(int i = prime + 1; i < flags.length; i++){
			if(!flags[i])
				return i;
		}
		return -1;
	}
	
	/**
	 * find the primes one by one through its former one
	 * @param n
	 * @return
	 */
	public static int findNthPrime(int n){
		if(n < 0)
			return -1;
		else if(n == 1)
			return 2;
		
		int prime = 3;
		int count = 2;
		while(count < n){
			prime = getNextPrimeNum(prime);
			count++;
		}
		
		return prime;
	}
		
	public static int getNextPrimeNum(int prime){
		if(!isPrime(prime))
			return -1;
		
		int result = (prime % 6 > 1) ? prime + 2 : prime + 4;
		while(!isPrime(result)){
			result += 2;
		}
		return result;
	}
	
	/**
	 * Memoization approach to store all prior prime numbers calculated and deduce the next one
	 * @param n
	 * @return
	 */
	public static int findNthPrimeMemoization(int n){
		if(n < 0)
			return -1;
		if(n == 1)
			return 2;
		if(n == 2)
			return 3;
		
		int[] primes = new int[n + 1];
		primes[1] = 2;
		primes[2] = 3;
		int count = 2;
		while(count < n){
			int tmp = (primes[count] % 6 > 1) ? primes[count] + 2 : primes[count] + 4;
			while(!isPrime(tmp, primes)){
				tmp += 2; 
			}
			count++;
			primes[count] = tmp;
		}
		
		return primes[n];
	}
	
	private static boolean isPrime(int num, int[] primes){
		for(int i = 1; primes[i] * primes[i] <= num; i++){
			if(num % primes[i] == 0)
				return false;
		}
		return true;
	}
	
	public static int findNthNumberComposedByThreePrimesBruteForce(int n, int prime1, int prime2, int prime3){
		if(n < 0)
			return -1;
		if(!isPrime(prime1) || !isPrime(prime2) || !isPrime(prime3))
			return -1;
		
		int count = 0;
		int number = 1;
		
		while(count < n){
			number++;
			if(hasThreeFactors(number, prime1, prime2, prime3)){
				count++;
			}
		}
		return number;
	}
	
	private static boolean hasThreeFactors(int num, int prime1, int prime2, int prime3){
		while(num % prime1 == 0)
			num /= prime1;
		while(num % prime2 == 0)
			num /= prime2;
		while(num % prime3 == 0)
			num /= prime3;
		
		return (num == 1)?true:false;
	}
	
	/**
	 * 7.7
	 * Find the nth number that only contains factor of prime1, prime2, and prime3
	 * 
	 * DP ideas 
	 * 
	 * @param n
	 * @param prime1
	 * @param prime2
	 * @param prime3
	 * @return
	 */
	public static long findNthNumberComposedByThreePrimes(int n, int prime1, int prime2, int prime3){
		if(n <= 0)
			return -1;
		if(!isPrime(prime1) || !isPrime(prime2) || !isPrime(prime3))
			return -1;
		
		int[] primes = placeInOrder(prime1, prime2, prime3);	
		int[] curLargestIndex = new int[]{0, 0, 0};
		long[] numbers = new long[n + 1];
		numbers[0] = 1;
		
		int nextIndex = 1;
		long nextResult = 0;
		
		while(nextIndex <= n){
			nextResult = min(numbers[curLargestIndex[0]] * primes[0], 
					numbers[curLargestIndex[1]] * primes[1], numbers[curLargestIndex[2]] * primes[2]);
			numbers[nextIndex] = nextResult;
			
			/**
			 * The while loop here is problematic, we probably just need to move forward the pointer by one at a time
			 * so using judgment if is enough, but need proof
			 * 
			 * Whichever is used in this update will need to move one step forward
			 * so that next time numbers[curLargestIndex[i]] * primes[i] will be the first number after this one(the smallest number larger than this one)
			 */
			if(nextResult == (numbers[curLargestIndex[0]] * primes[0]))
				curLargestIndex[0]++;
			if(nextResult == (numbers[curLargestIndex[1]] * primes[1]))
				curLargestIndex[1]++;
			if(nextResult == (numbers[curLargestIndex[2]] * primes[2]))
				curLargestIndex[2]++;
			
			/**
			 * Keep track of the last index for each prime
			 */
//			while(numbers[curLargestIndex[0]] * primes[0] <= nextResult)
//				curLargestIndex[0]++;
//			while(numbers[curLargestIndex[1]] * primes[1] <= nextResult)
//				curLargestIndex[1]++;
//			while(numbers[curLargestIndex[2]] * primes[2] <= nextResult)
//				curLargestIndex[2]++;
			
			nextIndex++;
		}
		return numbers[n];
	}
	
	public static long findNthNumberComposedByThreePrimesLists(int n, int prime1, int prime2, int prime3){
		if(n <= 0)
			return -1;
		if(!isPrime(prime1) || !isPrime(prime2) || !isPrime(prime3))
			return -1;
		
		int[] primes = placeInOrder(prime1, prime2, prime3);	
		Queue<Long> firstQueue = new LinkedList<Long>();
		Queue<Long> secondQueue = new LinkedList<Long>();
		Queue<Long> thirdQueue = new LinkedList<Long>();
		firstQueue.add(1l);
		
		int curIndex = 0;
		long curResult = 0;
		
		while(curIndex <= n){
			long val1 = (firstQueue.size() > 0) ? firstQueue.peek() : Long.MAX_VALUE;
			long val2 = (secondQueue.size() > 0) ? secondQueue.peek() : Long.MAX_VALUE;
			long val3 = (thirdQueue.size() > 0) ? thirdQueue.peek() : Long.MAX_VALUE;
			
			/**
			 * using min to ensure order
			 */
			curResult = min(val1, val2, val3);
			
			/**
			 * using three queues and mutually exclusive if else statement to eliminate duplication
			 */
			if(curResult == val1){
				firstQueue.remove();
				firstQueue.add(curResult * primes[0]);
				secondQueue.add(curResult * primes[1]);
			}else if(curResult == val2){
				secondQueue.remove();
				secondQueue.add(curResult * primes[1]);
			}else if(curResult == val3){
				thirdQueue.remove();
			}
			thirdQueue.add(curResult * primes[2]);
			
			curIndex++;
		}
		return curResult;
	}
	
	public static int min(int one, int two, int three){
		return Math.min(Math.min(one, two), three);
	}
	
	public static long min(long one, long two, long three){
		return Math.min(Math.min(one, two), three);
	}
	
	private static int[] placeInOrder(int a, int b, int c){
		int[] array = new int[3];
		array[0] = a;
		array[1] = b;
		array[2] = c; 
		if(array[0] > array[1]){
			ArrayUtil.swap(array, 0, 1);
		}
		if(array[0] > array[2]){
			ArrayUtil.swap(array, 0, 2);
		}
		if(array[1] > array[2]){
			ArrayUtil.swap(array, 1, 2);
		}
		return array;
	}
}
