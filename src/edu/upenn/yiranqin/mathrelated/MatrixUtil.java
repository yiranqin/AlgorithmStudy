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
		if(matrix == null)
			return;
		
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
		
		if(count < matrix[0].length * matrix.length){
			printMatrixClockwiseRecursively(matrix, left+1, top+1, right-1, bottom-1, count);
		}else
			System.out.println();
	}
	
	/**
	 * Rotate the square matrix by 90 degree
	 * @param matrix
	 */
	public static void rotateSquareMatrix(int[][] matrix){
		if(matrix == null)
			return;
		else if(matrix.length != matrix[0].length)
			return;
		
		int layer = 0;
		for(layer = 0; layer < matrix.length/2; layer++){
			int offset = matrix.length - 1 - layer;
			for(int i = layer + 1; i <= offset; i++){
				int tmp = matrix[layer][i];
				
				matrix[layer][i] = matrix[offset - i][layer];
				matrix[offset - i][layer] = matrix[matrix.length - layer - 1][offset - i];
				matrix[matrix.length - layer - 1][offset - i] = matrix[i][matrix.length - layer - 1];
				matrix[i][matrix.length - layer - 1] = tmp;
			}
		}
	}
	
	/**
	 * Rotate the random matrix by 90 degree
	 * @param matrix
	 */
	public static int[][] rotateRandomMatrix(int[][] matrix){
		if(matrix == null)
			return null;
		int rows = matrix.length;
		int columns = matrix[0].length;
		int totalElements = rows * columns;
		int[][] newMatrix = new int[columns][rows];
		
		int layer = 0;
		int count = 0;
		while(count < totalElements){
			for(int i = layer; i < columns; i++, count++)
				newMatrix[i][newMatrix[0].length - 1 - layer] = matrix[layer][i];
			
			for(int i = layer + 1; i < rows; i++, count++)
				newMatrix[newMatrix.length - 1 - layer][newMatrix[0].length - 1 - i] = matrix[i][columns - 1 - layer];
			
			if(count < totalElements){
				for(int i = layer; i < columns - 1 ; i++, count++)
					newMatrix[i][layer] = matrix[rows - 1 - layer][i];	
			}
			if(count < totalElements){
				for(int i = layer + 1; i < rows - 1 ; i++, count++)
					newMatrix[layer][newMatrix[0].length - 1 - i] = matrix[i][layer];	
			}
		}
		
		return newMatrix;
	}
	
	/**
	 * N queue problem solved by permutation
	 * @param n
	 */
	public static void nQueenPermutation(int n){
		int[] columnIndex = ArrayUtil.generateArray(0, n);
		System.out.println(evaluateAllPermutation(columnIndex, 0, n-1, 0));
	}
	
	private static int evaluateAllPermutation(int[] columnIndex, int start, int end, int result){
		if(start == end){
			if(isValid(columnIndex)){
//				ArrayUtil.printArray(columnIndex);
				return result + 1;
			}
		}
		
		for(int i = start; i <= end; i++){
			ArrayUtil.swap(columnIndex, start, i);
			result = evaluateAllPermutation(columnIndex, start + 1, end, result);
			ArrayUtil.swap(columnIndex, start, i);
		}
		
		return result;
	}
	
	private static boolean isValid(int[] ColumnIndex){
		int length = ColumnIndex.length;
	    for(int i = 0; i < length; ++ i){
	        for(int j = i + 1; j < length; ++ j){
	            if((i - j == ColumnIndex[i] - ColumnIndex[j])
	                || (j - i == ColumnIndex[i] - ColumnIndex[j]))
	            return false;
	        }
	    }
	 
	    return true;
	}
	
	public static void nQueenBackTracking(int n){
		int[] columnIndex = new int[n];
		int validNum = 0;
		for(int column = 0; column < n; column++){
			columnIndex[0] = column;
			validNum = placeQueens(columnIndex, 0, validNum);
		}
		System.out.println(validNum);
		System.out.println(totalRecursion);
		System.out.println(MathUtil.nFactorials(n));
	}
	
	private static long totalRecursion = 0;
	private static int placeQueens(int[] columnIndex, int row, int result){
		totalRecursion++;
		if(isPromising(columnIndex, row)){
			if(row == columnIndex.length -1){
//				ArrayUtil.printArray(columnIndex);
				result++;
			}else{
				for(int column = 0; column < columnIndex.length; column++){
					columnIndex[row + 1] = column;
					result = placeQueens(columnIndex, row + 1, result);
				}
			}
		}
		
		return result;
	} 
	
	private static boolean isPromising(int[] columnIndex, int row1){
	    for(int row2 = 0; row2 < row1; ++ row2){
	    		if(columnIndex[row1] == columnIndex[row2] ||
	    				Math.abs(columnIndex[row1] - columnIndex[row2]) == row1 - row2)
	            return false;
	    }
	    return true;
	}
}
