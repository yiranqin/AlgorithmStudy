package edu.upenn.yiranqin.mathrelated;

import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;
import edu.upenn.yiranqin.mathrelated.GeometryUtil.Point;
import edu.upenn.yiranqin.mathrelated.GeometryUtil.PointPair;
import edu.upenn.yiranqin.scalabilityrelated.MyBitmap;

public class MathHandler {
	
	public static void main(String[] argv){
		new MathHandler().start();
	}
	
	public void start(){

//		bitManipulationTest();		
		geometryTest();
//		numToStringUtilTest();
	}
	
	public void numToStringUtilTest(){
		System.out.println(NumToStringUtil.convertIntegerToEnglish(2019340123));
		NumToStringUtil.numberToAlphabeticalString();
	}
	
	public void geometryTest(){
		Point[] points = new Point[]{new Point(1,0), new Point(1.1,1), new Point(6,6), new Point(2, 0.1),
				new Point(4, 0.2), new Point(3,2), new Point(5, 5), new Point(3.3, 3.3), new Point(0, 7),
				new Point(1.5, 8.5), new Point(2.5, 9.5), new Point(0.5, 7.5), new Point(4.1, 11.1),
				new Point(2.9, 9.9), new Point(5.2, 5.1), new Point(5.3, 5.3), new Point(3.8, 10.8),
				new Point(4.6, 4.6), new Point(2.8, 2.8), new Point(16.5, 11), new Point(11, 12), new Point(12, 13)};
		
		PointPair pair = GeometryUtil.closestPointPair(points);
		System.out.println(pair);
		ArrayUtil.shuffleArray(points);
		System.out.println(pair);
		
		Point[] kClosest = GeometryUtil.findKPointsClosestToOrigin(points, 5);
		ArrayUtil.printArray(kClosest);
		ArrayUtil.shuffleArray(points);
		kClosest = GeometryUtil.findKPointsClosestToOriginHeap(points, 5);
		ArrayUtil.printArray(kClosest);
	}
	
	public void javaAPITest(){
		int example = 51;
		System.out.println(example + " % 32:" + example%32 + ", " + example + " & 31:" + String.valueOf(example&MyBitmap.bitOffset));
		System.out.println("-5 % 7 = "+ ((-5) % 7));
		
		BitSet set = new BitSet(15);
		LinkedList<String> list = new LinkedList<String>();
		
		set.set(0);
		list.offerFirst("good");
	}
	
	public void arithmeticOperationTest(){
		System.out.println(MathUtil.add(-15, 31));
		System.out.println(MathUtil.addRecursively(-15, -31));
		
		System.out.println(MathUtil.gcd(54, 24));
		System.out.println(MathUtil.gcdRecursively(54, 26));
		
		System.out.println(MathUtil.nFactorials(8));
		System.out.println(MathUtil.nSum(100));
		
		System.out.println(MathUtil.max(Integer.MIN_VALUE, 10000));
		System.out.println(MathUtil.max(10000, Integer.MAX_VALUE));
		

		System.out.println(MathUtil.combination(4, 1));
		System.out.println(MathUtil.permutation(4, 3));
		
		System.out.println(MathUtil.powerAsPositiveIntegerRecursively(3, 7));
		
		System.out.println(MathUtil.negate(-1));
		System.out.println(MathUtil.divide(15, -3));
	}
	
	public void findNthNumberComposedByThreePrimesTest(){
		int n = 100;
		int prime1 = 3;
		int prime2 = 5;
		int prime3 = 7;
		int repetition = 1;
		
		long start = System.currentTimeMillis();
		System.out.println(MathUtil.findNthNumberComposedByThreePrimesBruteForce(n, prime1, prime2, prime3));
		long end1 = System.currentTimeMillis();
		System.out.println("1 Brute Force findNthNumberComposedByThreePrimes Approach:" + (end1 - start));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.findNthNumberComposedByThreePrimes(n, prime1, prime2, prime3);
		}
		System.out.println(MathUtil.findNthNumberComposedByThreePrimes(n, prime1, prime2, prime3));
		long end2 = System.currentTimeMillis();
		System.out.println(repetition +" findNthNumberComposedByThreePrimes DP Approach:" + (end2 - end1));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.findNthNumberComposedByThreePrimesLists(n, prime1, prime2, prime3);
		}
		System.out.println(MathUtil.findNthNumberComposedByThreePrimesLists(n, prime1, prime2, prime3));
		long end3 = System.currentTimeMillis();
		System.out.println(repetition +" findNthNumberComposedByThreePrimes Queue Approach:" + (end3 - end2));
	}
	
	public void primeTest(){
		ArrayUtil.printArray(MathUtil.generateListOfPrimeWithLimit(23));
		System.out.println(MathUtil.getNextPrimeNum(31));
		
		/**
		 * The fastest is the third implementation, when n is large( n > 3000)
		 *  while when n is small, the first implementation is the fastest
		 */
		int n = 1500;
		int repetition = 100;
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < repetition; i++){
			MathUtil.generateLargestPrimeWithLimit(12553);
		}
		System.out.println(MathUtil.generateLargestPrimeWithLimit(12553));
		long end1 = System.currentTimeMillis();
		System.out.println(repetition +" Prime Boolean Elimination Approach:" + (end1 - start));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.findNthPrime(n);
		}
		System.out.println(MathUtil.findNthPrime(n));
		long end2 = System.currentTimeMillis();
		System.out.println(repetition +" Find Next Based On Current Approach(sqrt):" + (end2 - end1));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.findNthPrimeMemoization(n);
		}
		System.out.println(MathUtil.findNthPrimeMemoization(n));
		long end3 = System.currentTimeMillis();
		System.out.println(repetition +" Find Next Based On Current(memoization):" + (end3 - end2));
		
	}
	
	public void matrixTest(){
		int[][] matrix = MatrixUtil.generateNonDupMatrix(5, 7);
		MatrixUtil.printMatrix(matrix);
		MatrixUtil.printMatrixClockwise(matrix);
		MatrixUtil.printMatrixClockwiseRecursively(matrix, 0, 0, matrix[0].length - 1, matrix.length - 1, 0);
	}
	
	public void diceTest(){
		MathUtil.printSumProbabilityOfDiceRecursively(10);
		MathUtil.printSumProbabilityOfDiceDP(10);
	}
	
	public void stairJumpTest(){
		/**
		 * the code below is fairly easy to overflow, although Matrix implementation has O(logn) runtime,
		 * but the operation for each iteration is large
		 * while DP implementation has less per iteration operation.
		 * 
		 * when limit is large, matrix implementation will win
		 */
		
		int limit = 45568;
		int repetition = 10000;
		
		long start = System.currentTimeMillis();
		for(int i = 0; i < repetition; i++){
			MathUtil.fibonacci(limit);
		}
		System.out.println(MathUtil.fibonacci(limit));
		long end1 = System.currentTimeMillis();
		System.out.println(repetition + " DP fibonacci:" + (end1 - start));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.fibonacciMatrix(limit);
		}
		System.out.println(MathUtil.fibonacciMatrix(limit));
		long end2 = System.currentTimeMillis();
		System.out.println(repetition + " Matrix fibonacci:" + (end2 - end1));
		
		System.out.println(MathUtil.countWaysToJumpNStairs(limit));
		System.out.println(MathUtil.countWaysToJumpNStairsMatrix(limit));
		
		System.out.println(MathUtil.countJumpNStairsInThreeWays(limit));
		System.out.println(MathUtil.countJumpNStairsInThreeWaysMatrix(limit));
	}
	
	public void digitNumTest(){
		/**
		 * Best choice here is that for counting digit appearance use elegant way, while counting numbers containing digit, use combination
		 * Dynamic Programming Implementation in this case shows no advantage over elegant way when limit is small
		 * 
		 * When limit is large, DP will win 
		 */
		int digit = 0;
		int repetition = 10000;
//		int limit = 1423957;
		int limit = 97965432;
		
		long start = System.currentTimeMillis();
		System.out.println(MathUtil.countNumOfMInRange(limit, digit));
		long end1 = System.currentTimeMillis();
		System.out.println("Brute Force Num of Digit:" + (end1 - start));

		for(int i = 0; i < repetition; i++){
			MathUtil.countDigitMInRangeN(limit, digit);
		}
		System.out.println(MathUtil.countDigitMInRangeN(limit, digit));
		long end2 = System.currentTimeMillis();
		System.out.println(repetition + " Elegant Num of Digit:" + (end2 - end1));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.countDigitMInRangeNCombination(limit, digit);
		}		
		System.out.println(MathUtil.countDigitMInRangeNCombination(limit, digit));
		long end3 = System.currentTimeMillis();
		System.out.println(repetition +" Combination Num of Digit:" + (end3 - end2));
		
		
		long start2 = System.currentTimeMillis();
		System.out.println(MathUtil.countNumContainsMInRange(limit, digit));
		long end21 = System.currentTimeMillis();
		System.out.println("Brute Force Number Count Contains Digit:" + (end21 - start2));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.countNumContainsDigitMInRange(limit, digit);
		}
		System.out.println(MathUtil.countNumContainsDigitMInRange(limit, digit) + " Invocation: " + MathUtil.invocation);
		MathUtil.invocation = 0;
		long end22 = System.currentTimeMillis();
		System.out.println(repetition +" Elegant Number Count Contains Digit:" + (end22 - end21));
		
		for(int i = 0; i < repetition; i++){
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
			MathUtil.countNumContainsDigitMInRangeDP(limit, digit, map);
		}
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		System.out.println(MathUtil.countNumContainsDigitMInRangeDP(limit, digit, map) + " Invocation: " + MathUtil.invocation);
		long end23 = System.currentTimeMillis();
		System.out.println(repetition +" DP implementation of Elegant Number Count Contains Digit:" + (end23 - end22));
		
		for(int i = 0; i < repetition; i++){
			MathUtil.countNumContainsDigitMInRangeCombination(limit, digit);
		}
		System.out.println(MathUtil.countNumContainsDigitMInRangeCombination(limit, digit));
		long end24 = System.currentTimeMillis();
		System.out.println(repetition +" Combination Number Count Contains Digit:" + (end24 - end23));
	}
	
	public void bitManipulationTest(){
		System.out.println(Integer.toBinaryString(BitManipulationUtil.insertMIntoN(-1, 19, 2, 6)));
		
		System.out.println(BitManipulationUtil.doubleBinaryString(0.375));
		
		int[] array = ArrayUtil.generateArrayWithMask(0, 16, new int[]{13});
		ArrayUtil.printArray(array);
		System.out.println(BitManipulationUtil.findMissingInteger(array));
		System.out.println(BitManipulationUtil.findMissingIntegerRecursively(array));
		System.out.println(BitManipulationUtil.findMissingIntegerInIncreasingSequence(array, 0, 15));
		
		int[] testArray1 = {1, 1, 0, 2, 3, 3, 0, 5, 5, 6, 7, 8, 7 , 10, 6, 8, 9};
		int[] testArray2 = {1, 2, 3};
		ArrayUtil.printArray(BitManipulationUtil.findThreeIntegersAppearOnce(testArray1));
		ArrayUtil.printArray(BitManipulationUtil.findThreeIntegersAppearOnce(testArray2));
		
		byte[] screen = new byte[4];
		System.out.println(ArrayUtil.toBinaryString(screen));
		System.out.println(ArrayUtil.toBinaryString(BitManipulationUtil.drawLine(screen, 32, 1, 30, 0)));
		
		int test = Integer.MAX_VALUE - ((int)Math.pow(2, 10) - 1);
		System.out.println(Integer.toBinaryString(test));
		System.out.println(Integer.toBinaryString(BitManipulationUtil.findNextLarger(test)));
		System.out.println(Integer.toBinaryString(BitManipulationUtil.findNextLarger(9)));
		
		System.out.println(Integer.toBinaryString(BitManipulationUtil.findNextSmaller((int)Math.pow(2, 10) - 1)));
		System.out.println(Integer.toBinaryString(BitManipulationUtil.findNextSmaller(9)));
	}
	
	public void josephRingTest(){
		System.out.println(MathUtil.josephRing(6, 3));
		System.out.println(MathUtil.josephRing(7, 3));
		System.out.println(MathUtil.josephRing(2, 3));
		
		System.out.println(Integer.toBinaryString(Byte.MAX_VALUE));
	}
		
}
