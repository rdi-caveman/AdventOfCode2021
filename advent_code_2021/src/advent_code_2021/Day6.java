package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
	private static final String DAY6_INPUT_TXT = "src/resources/day6_input.txt";
	private static int DAYS = 80;
	private static int DAYS2 = 256;
	private static long[] fish = new long[9];
	
	public static void main(String[] args) {
		initialize();
		for (int i=1; i<=DAYS2; i++) {
			ageFish();	
			if (i == DAYS) {
				System.out.println("Day 6 part 1 " + countFish());
			}
		}
		System.out.println("Day 6 part 2 " + countFish());
	}
	
	private static long countFish() {
		long sum = 0;
		for (int i=0; i<=8; i++) {
			sum += fish[i];
		}
		return sum;
	}
	
	private static void ageFish() {
		long zeroFish = fish[0];
		for (int i=0; i<8; i++) {
			fish[i] = fish[i+1]; // all fish age 1 day
		}
		fish[6] += zeroFish; // reset fish timer
		fish[8] = zeroFish; // new fish
	}
	
	private static void initialize() {
		try {
			String input = new String(Files.readAllBytes(Paths.get(DAY6_INPUT_TXT)));
			Arrays.stream(input.split(","))
				.mapToInt(s -> Integer.parseInt(s))
				.forEach(i -> fish[i]++);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
