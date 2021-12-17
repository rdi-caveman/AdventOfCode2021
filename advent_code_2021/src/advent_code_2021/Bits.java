package advent_code_2021;

public class Bits {
	String memory;
	int bytePointer;
	int bitPointer;
	int currentByte;
	int[] mask = new int[] {8,4,2,1};
	
	public Bits(String input) {
		memory = input;
		bytePointer = 0;
		bitPointer = 0;
	}

	public long getBits(int length) {
		long value = 0;
		for (int i=0; i<length; i++) {
			value = 2*value + getNextBit();
		}
		return value;
	}
	
	private int getNextBit() {
		int returnValue;
		if (bitPointer == 0) {
			currentByte = Integer.parseInt(String.valueOf(memory.charAt(bytePointer++)),16);
		}
		returnValue = (currentByte & mask[bitPointer]) == 0 ? 0 : 1;
		bitPointer = (bitPointer + 1) % 4;
		return returnValue;
	}
}
