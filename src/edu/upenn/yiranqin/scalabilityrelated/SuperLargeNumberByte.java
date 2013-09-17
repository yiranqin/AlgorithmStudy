package edu.upenn.yiranqin.scalabilityrelated;

/*
 * Super large number implemented with byte array, the number is with size digit
 * each byte store at most 99
 * 
 * The super large number implemented is only for positive ones
 */

public class SuperLargeNumberByte implements Comparable<Object>{
	private byte[] value;
	private int numDigit = 1;
	
	public SuperLargeNumberByte(int size, boolean max){
		int byteNum = (size % 2 == 0) ? (size/2) : (size/2 + 1); 
		value = new byte[byteNum];
		if(max) {
			numDigit = size;
			setToMax();
		}
	}
	
	public SuperLargeNumberByte(long num){
		String numStr = Long.toString(num);
		numDigit = numStr.length();
		form(num);
	}
	
	public SuperLargeNumberByte(byte[] content){
		value = content;
		numDigit = countDigit();				
	}
	
	public void setValue(byte[] content){
		value = content;
		numDigit = countDigit();
	}
	
	private void form(long num){
		int byteNum = (numDigit % 2 == 0) ? (numDigit/2) : (numDigit/2 + 1); 
		value = new byte[byteNum];
		int i = value.length - 1; 
		while(num > 0 && i >= 0){
			value[i] = (byte)(num % 100);
			num /= 100;
			i--;
		}
	}
	
	private int countDigit(){
		int i = 0;
		for(i = 0; i < value.length; i++){
			if(value[i] > 0)
				break;
		}
		/**
		 * Notice the case when the number is 0 and all the value bytes are 0
		 */
		if(i == value.length && value[value.length - 1] == 0)
			return 1;
		
		int count = (value.length - 1 - i) * 2 + ((value[i] >= 10) ? 2 : 1);
		return count;
	}
	
	public void setToMax(){
		for(int i = value.length - 1; i > 0; i--){
			value[i] = 99;
		}
		value[0] = (byte)((numDigit % 2 == 0) ? 99 : 9);
			
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		boolean flag = false; //indicate whether there is non-zero value before this byte 
		
		if(numDigit <= 2){
			buffer.append(value[value.length - 1]);
		}else{
			for(int i = 0; i < value.length; i++){
				if(flag){
					if(value[i] < 10){
						buffer.append("0" + value[i]);
					}else{
						buffer.append(value[i]);
					}
				}else if(value[i] > 0){
					buffer.append(value[i]);
					flag = true;
				}
			}
		}
			
		return buffer.toString();
	}
	
	public void increment(){
		int index = value.length - 1;
		int MSDIndex = (numDigit % 2 == 0)?(index - numDigit/2 + 1) : (index - numDigit/2);
		
		value[index]++;
		
		if(numDigit == 1 && value[index] == 10)
			numDigit++;
		else if(value[index] == 100){		
			while(index >= 1 && value[index] == 100){
				value[index] = 0;			
				value[--index] += 1;
			}
			
			// increment too large, need to expand twice of its original size
			if(value[0] == 100){
				int originalLen = value.length;
				value = new byte[2 * originalLen];
				value[originalLen - 1] = 1;
				numDigit++;
			}
			
			// when the most significant digit is 1 or 10, digit number should increment
			if(index == MSDIndex && value[MSDIndex] == 10){
				numDigit++;
			}else if(MSDIndex >= 1 && index == MSDIndex - 1 && value[MSDIndex - 1] == 1){
				numDigit++;
			}
		}
	}
	
	public SuperLargeNumberByte add(SuperLargeNumberByte other){
		SuperLargeNumberByte larger;
		SuperLargeNumberByte smaller;
		if(this.numDigit >= other.getNumDigit()){
			larger = this;
			smaller = other;
		}else{
			larger = other;
			smaller = this;
		}
		
		byte[] smallerValue = smaller.getValue();
		byte[] largerValue = larger.getValue();
		if(smallerValue.length > largerValue.length)
			return null;
		
		int offset = largerValue.length - smallerValue.length;
		byte[] returnValue = new byte[largerValue.length];
		
		byte lastCarry = 0;
		
		for(int i = smallerValue.length - 1; i >= 0 ; i--){
			/* Caution about the offset */
			int tmp = smallerValue[i] + largerValue[i + offset] + lastCarry;
			if(tmp >= 100){
				lastCarry = 1;
				returnValue[i + offset] = (byte)(tmp - 100);
			}else{
				lastCarry = 0;
				returnValue[i + offset] = (byte)tmp;
			}		
		}
		
		for(int i = offset - 1; i >= 0; i--){
			int tmp = largerValue[i] + lastCarry;
			if(tmp >= 100){
				lastCarry = 1;
				returnValue[i] = (byte)(tmp - 100);
			}else{
				lastCarry = 0;
				returnValue[i] = (byte)tmp;
			}		
		}
		
		if(lastCarry == 0){
			return new SuperLargeNumberByte(returnValue);			
		}else{
			byte[] returnValueExtra = new byte[returnValue.length + 1];
			for(int i = 0; i < returnValue.length; i++){
				returnValueExtra[i+1] = returnValue[i];
			}
			returnValueExtra[0] = 1;
			return new SuperLargeNumberByte(returnValueExtra);
		}
	}

	public SuperLargeNumberByte multiply(SuperLargeNumberByte other){
		SuperLargeNumberByte smaller;
		SuperLargeNumberByte larger;
		if(this.compareTo(other) == -1){
			smaller = this;
			larger = other;
		}else{ 
			smaller = other;
			larger = this;
		}
		
		byte[] counterValue = new byte[smaller.getValue().length];
		byte[] resultValue = new byte[larger.getValue().length];
		SuperLargeNumberByte counter = new SuperLargeNumberByte(counterValue);
		SuperLargeNumberByte result = new SuperLargeNumberByte(resultValue);
		while(counter.compareTo(smaller) == -1){
			counter.increment();
			result = result.add(larger);
		}
		
		return result;
	}
	
	public int getNumDigit(){
		return numDigit;
	}
	
	public byte[] getValue(){
		return value;
	}
	
	/**
	 * Deep copy all the memory related to this object and new a different one
	 */
	public SuperLargeNumberByte clone(){
		return new SuperLargeNumberByte(value.clone());
	}

	@Override
	public int compareTo(Object o) {
 		SuperLargeNumberByte other = (SuperLargeNumberByte) o;
		
 		if(numDigit > other.getNumDigit()){
			return 1;
		}else if(numDigit < other.getNumDigit()){
			return -1;
		}else{
			byte[] otherValue = other.getValue();
			int MSDIndex = (numDigit % 2 == 0)?(value.length - numDigit/2) : (value.length -1 - numDigit/2);
			int MSDIndexOther = (numDigit % 2 == 0)?(otherValue.length - numDigit/2) : (otherValue.length -1 - numDigit/2);
			while(MSDIndex < value.length && MSDIndexOther < otherValue.length){
				if(value[MSDIndex] > otherValue[MSDIndexOther])
					return 1;
				if(value[MSDIndex] < otherValue[MSDIndexOther])
					return -1;
				
				MSDIndex++;
				MSDIndexOther++;
			}
		}
		
		return 0;
	}	
}
