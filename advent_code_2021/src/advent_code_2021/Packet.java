package advent_code_2021;

import java.util.ArrayList;
import java.util.List;

public class Packet {
	private static final int SUM = 0;
	private static final int PRODUCT = 1;
	private static final int MINIMUM = 2;
	private static final int MAXIMUM = 3;
	private static final int LITERAL = 4;
	private static final int GREATER_THAN = 5;
	private static final int LESS_THAN = 6;
	private static final int EQUAL = 7;
	private static final int BIT_5 = 16;
	private static final int LOW_4_BITS = 15;
	
	int version;
	int typeId;
	int lengthTypeId;
	long value;
	List<Packet> operands = new ArrayList<>();
	long length;
	
	public Packet(Bits bitMemory) {
		version = (int) bitMemory.getBits(3);
		typeId = (int) bitMemory.getBits(3);
		length += 6;
		if (typeId == LITERAL) {
			// Literal Value
			value = 0;
			long chunk = bitMemory.getBits(5);
			length += 5;
			while ((chunk & BIT_5) != 0) {
				value = value * 16 + (chunk & LOW_4_BITS);
				chunk = bitMemory.getBits(5);
				length += 5;
			}
			value = value * 16 + (chunk & LOW_4_BITS);
		}
		else {
			// Operator
			lengthTypeId = (int) bitMemory.getBits(1);
			length += 1;
			if (lengthTypeId == 0) {
				// packets by length
				long dataLength = bitMemory.getBits(15);
				length += 15;
				long finalLength = length+dataLength;
				while (length < finalLength) {
					Packet p = new Packet(bitMemory);
					length += p.length;
					operands.add(p);
				}			
			}
			else {
				// packets by count
				long packetCount = bitMemory.getBits(11);
				length += 11;
				for (int i=0; i<packetCount; i++) {
					Packet p = new Packet(bitMemory);
					length += p.length;
					operands.add(p);					
				}
			}
		}
	}
		
	public long versionCheckSum() {
		long sum = operands.stream()
				.mapToLong(p -> p.versionCheckSum())
				.sum();
		return sum+version;		
	}
	
	public long evaluate() {
		long returnValue = 0;
		switch(typeId) {
		case SUM:
			returnValue = operands.stream()
				.mapToLong(p -> p.evaluate())
				.sum();
		    break;
		case PRODUCT:
			returnValue = operands.stream()
				.mapToLong(p -> p.evaluate())
				.reduce(1, (a,b) -> a*b);
			break;
		case MINIMUM:	
			returnValue = operands.stream()
				.mapToLong(p -> p.evaluate())
				.min().getAsLong();
			break;
		case MAXIMUM:	
			returnValue = operands.stream()
				.mapToLong(p -> p.evaluate())
				.max().getAsLong();
			break;			
		case LITERAL:
			returnValue = value;
			break;
		case GREATER_THAN:
			returnValue = operands.get(0).evaluate() > operands.get(1).evaluate() ? 1 : 0;
			break;
		case LESS_THAN:
			returnValue = operands.get(0).evaluate() < operands.get(1).evaluate() ? 1 : 0;
			break;
		case EQUAL:
			returnValue = operands.get(0).evaluate() == operands.get(1).evaluate() ? 1 : 0;
			break;
		}
		return returnValue;
	}
	
	
	
	public String toString() {
		return String.format("Version:%d Type:%d length:%d %s", version, typeId, length, operands);
	}
}
