package edu.upenn.yiranqin.mathrelated;

public class Matrix3By3 {
	public long m00;
	public long m01;
	public long m02;
	public long m10;
	public long m11;
	public long m12;
	public long m20;
	public long m21;
	public long m22;
	
	public Matrix3By3(long _m00, long _m01, long _m02, long _m10, long _m11,
			long _m12, long _m20, long _m21, long _m22){
		m00 = _m00;
		m01 = _m01;
		m02 = _m02;
		m10 = _m10;
		m11 = _m11;
		m12 = _m12;
		m20 = _m20;
		m21 = _m21;
		m22 = _m22;
	}

	public Matrix3By3 multiply(Matrix3By3 other){
		return new Matrix3By3(
							m00 * other.m00 + m01 * other.m10 + m02 * other.m20,
							m00 * other.m01 + m01 * other.m11 + m02 * other.m21,
							m00 * other.m02 + m01 * other.m12 + m02 * other.m22,
							m10 * other.m00 + m11 * other.m10 + m12 * other.m20,
							m10 * other.m01 + m11 * other.m11 + m12 * other.m21,
							m10 * other.m02 + m11 * other.m12 + m12 * other.m22,
							m20 * other.m00 + m21 * other.m10 + m22 * other.m20,
							m20 * other.m01 + m21 * other.m11 + m22 * other.m21,
							m20 * other.m02 + m21 * other.m12 + m22 * other.m22 );
	}
	
	public Matrix3By3 power(int n){
		if(n < 0)
			return null;
		else if( n == 0)
			return new Matrix3By3(1, 0, 0, 0, 1, 0, 0, 0, 1);
		
		Matrix3By3 matrix = this;
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
