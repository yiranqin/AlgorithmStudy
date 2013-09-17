package edu.upenn.yiranqin.mathrelated;

import edu.upenn.yiranqin.arrayrelated.ArrayUtil;

public class MatrixUtil {
	public static int[][] generateIncreasingOrderMatrix(int row, int colomn){
		int[][] result = new int[row][colomn];
		
		for(int i = 0; i < row; i++){
			for(int j = 0; j < colomn; j++){
				result[i][j] = i*colomn + j + 1;
			}
		}
		
		return result;
	}
	
	public static int[][] generateNonDupMatrix(int row, int colomn){
		int[] tmp = ArrayUtil.generateDistinctIntArray(row*colomn);
		int[][] result = new int[row][colomn];
		
		for(int i = 0; i < row; i++){
			for(int j = 0; j < colomn; j++){
				result[i][j] = tmp[i*colomn + j];
			}
		}
		
		return result;
	}
	
	public static int[][] generateRandomMatrix(int row, int colomn){
		int[] tmp = ArrayUtil.generateRandomArray(row*colomn);
		int[][] result = new int[row][colomn];
		
		for(int i = 0; i < row; i++){
			for(int j = 0; j < colomn; j++){
				result[i][j] = tmp[i*colomn + j];
			}
		}
		
		return result;
	}
	
	public static void printMatrix(int[][] matrix){
		int rows = matrix.length;
		int colomns = matrix[0].length;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < colomns; j++){
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}			
	}
	
	/**
	 * 100/51
	 * 
	 * Print matrix clockwise
	 * @param matrix
	 */
	public static void printMatrixClockwise(int[][] matrix){
		if(matrix == null)
			return;
		
		int topLeft = 0;
		int bottomRightCol = matrix[0].length -1;
		int bottomRightRow = matrix.length -1;
		
		int count = 0;
		/**
		 * The boundary conditions are way too complex
		 * Better(and maybe the only) way is to use a count to terminate it
		 */
		while(count < matrix[0].length * matrix.length){
			/**
			 * The first line include the last number so that better handle the innermost element
			 * Also to better handle the single row or column we cover all elements for top and right line 
			 */
			for(int i = topLeft; i <= bottomRightCol; i++){
				count++;
				System.out.print(matrix[topLeft][i] + " ");
			}
			for(int i = topLeft + 1; i <= bottomRightRow; i++){
				count++;
				System.out.print(matrix[i][bottomRightCol] + " ");
			}
			
			if(count < matrix[0].length * matrix.length){
				for(int i = bottomRightCol - 1; i > topLeft; i--){
					count++;
					System.out.print(matrix[bottomRightRow][i] + " ");
				}
			}
			if(count < matrix[0].length * matrix.length){
				for(int i = bottomRightRow; i > topLeft; i--){
					count++;
					System.out.print(matrix[i][topLeft] + " ");
				}
			}
			
			topLeft++;
			bottomRightRow--;
			bottomRightCol--;
		}
		System.out.println();
	}
	
	public static void printMatrixClockwiseRecursively(int[][] matrix, int left, int top, int right, int bottom, int count){
		if(matrix == null || left > right || top> bottom )
			return;
		if(count >= matrix[0].length * matrix.length){
			System.out.println();
			return;
		}
		
		for(int i = left; i <= right; i++){
			count++;
			System.out.print(matrix[top][i] + " ");
		}
		for(int i = top + 1; i <= bottom; i++){
			count++;
			System.out.print(matrix[i][right] + " ");
		}
		if(count < matrix[0].length * matrix.length){
			for(int i = right - 1; i > left; i--){
				count++;
				System.out.print(matrix[bottom][i] + " ");
			}
		}
		if(count < matrix[0].length * matrix.length){
			for(int i = bottom; i > top; i--){
				count++;
				System.out.print(matrix[i][left] + " ");
			}
		}
		printMatrixClockwiseRecursively(matrix, left+1, top+1, right-1, bottom-1, count);
	}
}
