package edu.upenn.yiranqin.mathrelated;

public class Matrix2By2 {
	public long m00;
	public long m01;
	public long m10;
	public long m11;
	
	public Matrix2By2(long _m00, long _m01, long _m10, long _m11){
		m00 = _m00;
		m01 = _m01;
		m10 = _m10;
		m11 = _m11;
	}

	public Matrix2By2 multiply(Matrix2By2 other){
		return new Matrix2By2(m00 * other.m00 + m01 * other.m10,
							m00 * other.m10 + m01 * other.m11,
							m10 * other.m00 + m11 * other.m10,
							m10 * other.m01 + m11 * other.m11);
	}
	
	public Matrix2By2 power(int n){
		if(n < 0)
			return null;
		else if( n == 0)
			return new Matrix2By2(1, 0, 1, 0);
		
		Matrix2By2 matrix = this;
		if(n == 1)
			matrix = this;
		else if(n % 2 == 0){
			matrix = power(n/2);
			matrix = matrix.multiply(matrix);
		}else if(n % 2 == 1){
			matrix = power((n -1) /2);
			matrix = matrix.multiply(matrix);
			matrix = this.multiply(matrix);
		}
		
		return matrix;
	}
}
