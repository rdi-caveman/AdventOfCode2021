package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day16 {
	private static final String DAY16_INPUT_TXT = "src/resources/day16_input.txt";
	
	
	public static void main(String[] args) {
		Bits bitMemory = initialize();
		Packet p = new Packet(bitMemory);
		System.out.println("Day 16 part 1 " + p.versionCheckSum());
		System.out.println("Day 16 part 2 " + p.evaluate());
	}

	private static void test1() {
		Bits bitMemory = new Bits("D2FE28");
		Packet p = new Packet(bitMemory);
		System.out.println(p);
	}
	
	private static void test2() {
		Bits bitMemory = new Bits("38006F45291200");
		Packet p = new Packet(bitMemory);
		System.out.println(p);
	}

	private static void test3() {
		Bits bitMemory = new Bits("EE00D40C823060");
		Packet p = new Packet(bitMemory);
		System.out.println(p);
	}
	
	public static Bits initialize() {
		Bits bitMemory;
		try {
			bitMemory = new Bits(new String(Files.readAllBytes(Paths.get(DAY16_INPUT_TXT))));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bitMemory;
	}
}
