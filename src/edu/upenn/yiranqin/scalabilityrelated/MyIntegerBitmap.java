package edu.upenn.yiranqin.scalabilityrelated;

public class MyIntegerBitmap {	
	private int _value = 0; 
	
	public MyIntegerBitmap(){}
	
	public MyIntegerBitmap(int value){
		_value = value;
	}
		
	public static boolean notInt(int bit){
		return (bit < 0 || bit > 31); 
	}
	
	public int getValue(){
		return _value;
	}
	
	public void setValue(int value){
		_value = value;
	}
	/** 
	 * @param bit
	 * @return whether bit set successfully
	 */
	public boolean setBit(int bit){
		if(notInt(bit))
			return false;
		
		_value |= 1<<bit;
		return true;
	}
	
	/** 
	 * @param bit
	 * @return whether bit set successfully
	 */
	public boolean clearBit(int bit){
		if(notInt(bit))
			return false;
		
		_value &= ~(1<<bit);
		return true;
	}
	
	/** 
	 * get bit value
	 * @param bit
	 * @return bit value, -1 if error
	 */
	public int getBit(int bit){
		if(notInt(bit))
			return -1;
		
		//caution: should notice that simply using & will not make it
		return ((_value & 1<<bit) == 0) ? 0 : 1;
	}

	public int getFirstSetBit(){
		int bit = 0;
		while((_value & 1<<bit) == 0 && bit < 31){
			bit++;
		}
		if(bit > 31)
			return -1;
		else if(bit == 31){
			if((_value & 1<<bit) > 0)
				return 31;
			else
				return -1;
		}
		else
			return bit;
	}
	
	/** 
	 * toggle bit value
	 * @param bit
	 * @return whether toggle bit successfully
	 */
	public boolean toggleBit(int bit){
		if(notInt(bit))
			return false;
		
		_value ^= 1<<bit;
		return true;
	}
	
	/** 
	 * update bit value
	 * @param bit
	 * @return whether bit updated successfully
	 */
	public boolean updateBit(int bit, int value){
		if(notInt(bit) || (value != 0 && value != 1))
			return false;
		
		_value = (_value & ~(1 << bit)) | (value << bit);
		return true;
	}
	
	/**
	 * count the bits for 2 bits, 4 bits, 8 bits, 16bits and finally 32bits
	 * @return
	 */
	public int countSetBits(){
		int count = _value;
		count = (count & 0x55555555) + ((count >> 1) & 0x55555555);
		count = (count & 0x33333333) + ((count >> 2) & 0x33333333);
		count = (count & 0x0f0f0f0f) + ((count >> 4) & 0x0f0f0f0f);
		count = (count & 0x00ff00ff) + ((count >> 8) & 0x00ff00ff);
		count = (count & 0x0000ffff) + ((count >> 16) & 0x0000ffff);
		return count;
	}
	
	/**
	 * clear bits from i through 0 bit(inclusive)
	 * @param bit
	 * @return
	 */
	public boolean clearBitsIthrough0(int bit){
		if(bit == 31){
			_value = 0;
			return true;
		}
		if(notInt(bit)){
			return false;
		}
		
			
		_value &= ~((1 << (bit + 1)) -1);
		return true;		
	}
	
	/**
	 * clear bits from most significant bit through i(inclusive)
	 * @param bit
	 * @return
	 */
	public boolean clearBitsMSthroughI(int bit){
		if(notInt(bit)){
			return false;
		}
		
		_value &= (1<<bit) -1;
		return true;
	}
	/**
	 * 
	 * @param n
	 * @return number of bits actually get cleared
	 */
	public int clearNBitsFromRight(int n){
		if(notInt(n)){
			return -1;
		}
		
//		if(_value < 0){
//			int i = 0;
//			while(n > 0 && i <= 31){
//				if(getBit(i) == 1){
//					clearBit(i);
//					n--;
//				}
//				i++;
//				System.out.println("i: " + i + " n: " + n);
//			}			
//		}
		
		int count = 0;
		while(n > 0 && _value != 0){
			_value = _value & (_value - 1);
			n--;
			count ++;
		}
		return count;
	}
	
	public boolean isPowerOfTwo(){
		return (_value & (_value - 1)) == 0;
	}
}
