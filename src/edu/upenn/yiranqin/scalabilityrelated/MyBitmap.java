package edu.upenn.yiranqin.scalabilityrelated;

/**
 * Be extremely careful about the priority of bit manipulation
 * @author qyr1987
 *
 */

public class MyBitmap {
	private MyIntegerBitmap[] _bitmapSet;
	private int _bitNum;
	public static final int bitOffset = 0x1f; // A mod B = A & B-1 
	
	public MyBitmap(int bitNum){
		_bitNum = bitNum;
		// the parenthesis is necessary here
		if(bitNum % 32 == 0){
			_bitmapSet = new MyIntegerBitmap[bitNum >> 5];
		}else{
			_bitmapSet = new MyIntegerBitmap[(bitNum>>5) + 1];
		} 
		for(int i = 0; i < _bitmapSet.length; i++){
			_bitmapSet[i] = new MyIntegerBitmap();
		}
	}
	
	public MyBitmap(int[] values){
		_bitNum = values.length << 5;
		_bitmapSet = new MyIntegerBitmap[values.length];
		// most significant bit is to the left
		for(int i = 0; i < values.length; i++){
			_bitmapSet[values.length - 1 - i] = new MyIntegerBitmap(values[i]);
		}
	}
	/* check if all the bit are clear */
	public boolean isAllClear(){
		for(MyIntegerBitmap cur : _bitmapSet){
			if(cur.getValue() != 0)
				return false;
		}
		return true;
	}
	
	public boolean notInRange(int bit){
		return (bit < 0 || bit > _bitNum); 
	}
	
	/** 
	 * @param bit
	 * @return whether bit set successfully
	 */
	public boolean setBit(int bit){
		if(notInRange(bit))
			return false;
		
		_bitmapSet[bit >> 5].setBit(bit & 0x1F);
		return true;
	}
	
	/** 
	 * @param bit
	 * @return whether bit set successfully
	 */
	public boolean clearBit(int bit){
		if(notInRange(bit))
			return false;
		
		_bitmapSet[bit >> 5].clearBit(bit & 0x1F);
		return true;
	}
	
	/** 
	 * get bit value
	 * @param bit
	 * @return bit value, -1 if error
	 */
	public int getBit(int bit){
		if(notInRange(bit))
			return -1;
		
		return _bitmapSet[bit >> 5].getBit(bit & 0x1F);
	}
	
	/** 
	 * toggle bit value
	 * @param bit
	 * @return whether toggle bit successfully
	 */
	public boolean toggleBit(int bit){
		if(notInRange(bit))
			return false;
		
		_bitmapSet[bit >> 5].toggleBit(bit & 0x1F);
		return true;
	}
	
	public int getFirstSetBit(){
		int bit = 0;
		for(MyIntegerBitmap bitmap : _bitmapSet){
			int tmpBit = bitmap.getFirstSetBit();
			if(tmpBit == -1)
				bit += 32;
			else{
				bit += tmpBit;
				break;
			}
		}
		if(bit > _bitNum)
			return -1;
		else
			return bit;
	}
	
	/** 
	 * update bit value
	 * @param bit
	 * @return whether bit updated successfully
	 */
	public boolean updateBit(int bit, int value){
		if(notInRange(bit))
			return false;
		
		_bitmapSet[bit >> 5].updateBit(bit & 0x1F, value);
		return true;
	}
	
	public int countSetBits(){
		int count = 0;
		for (MyIntegerBitmap bitmap : _bitmapSet){
			count += bitmap.countSetBits();
		}
		return count;
	}
	
	/**
	 * clear N bits from 0th bit
	 * @param n
	 * @return return number of bits actually get cleared
	 */
	public int clearNBitsFromRight(int n){
		if(notInRange(n)){
			return -1;
		}
		
		int index = 0;
		int count = 0;
		while(n > 0 && index < _bitmapSet.length){
			count += _bitmapSet[index].clearNBitsFromRight(n);
			n -= count;
			index++;
		}
		
		return count;
	}
	
	public String toBinaryString(){
		StringBuffer buffer = new StringBuffer();
		for(int i = _bitmapSet.length - 1; i >= 0 ; i-- ){			
			String cur = Integer.toBinaryString(_bitmapSet[i].getValue());
			StringBuffer padding = new StringBuffer();
			int paddingLen = 32 - cur.length();
			while(paddingLen > 0){
				padding.append(0);
				paddingLen--; 
			}
			buffer.append(padding.toString());
			buffer.append(cur);
			buffer.append(" ");
		}
		return buffer.toString();
	}
}
