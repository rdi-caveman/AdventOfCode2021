package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.function.IntFunction;

/*
 * Day 7 minimum cost to align crabs
 * Ideas: 1) data structure - treemap<Integer, Integer) of location, # crabs
 * 		 	 allows first(lowest) and last(highest) elements to be accessed directly
 *        2) binary search to find minima
 */

public class Day7 {
	private static final String DAY7_INPUT_TXT = "src/resources/day7_input.txt";
	private static TreeMap<Integer, Integer> crabs = new TreeMap<>();

	public static void main(String[] args) {
		initialize();
		part1();
		part2();
	}

	private static void part1() {
		int min = crabs.firstKey();
		int max = crabs.lastKey();
		int result = search(min, max, Day7::calculateFuel);
		System.out.println("Day 7 part 1 " + result);
	}

	private static void part2() {
		int min = crabs.firstKey();
		int max = crabs.lastKey();
		int result = search(min, max, Day7::calculateFuel2);
		System.out.println("Day 7 part 1 " + result);
	}

	private static Integer calculateFuel(int location) {
		return crabs.entrySet().stream().mapToInt(e -> (Math.abs(location - e.getKey()) * e.getValue())).sum();
	}

	private static Integer calculateFuel2(int location) {
		return crabs.entrySet().stream().mapToInt(e -> calcFuel(location, e.getKey()) * e.getValue()).sum();
	}

	private static int calcFuel(int a, int b) {
		int n = Math.abs(a - b);
		return (n * (n + 1)) / 2;
	}

	/*
	 * Find midpoint between min and max. calculate cost of mid point and it's two
	 * neighbors. If curve slopes down toward left neighbor, reevaluate with mid
	 * point as new max If curve slopes down toward right neighbor, reevaluate with
	 * mid point as new min If midpoint is minimum stop
	 * 
	 */
	private static int search(int min, int max, IntFunction<Integer> fuelFunction) {
		int mid;
		int midm1;
		int midp1;
		int a;
		int b;
		int c;
		// search for low point
		while ((max - min) > 0) {
			mid = min + (max - min) / 2;
			midm1 = mid - 1;
			midp1 = mid + 1;
			a = fuelFunction.apply(midm1);
			b = fuelFunction.apply(mid);
			c = fuelFunction.apply(midp1);
			// System.out.println(String.format("%d %d %d %d %d : %d %d %d", min, midm1,
			// mid, midp1, max, a, b, c));
			if (a < b) {
				max = mid;
			} else if (c < b) {
				min = mid;
			} else {
				return b;
			}
		}
		return fuelFunction.apply(min); // min = max
	}

	private static void initialize() {

		String input = FileUtility.readEntireFile(DAY7_INPUT_TXT);
		Arrays.stream(input.split(",")).mapToInt(s -> Integer.parseInt(s))
				.forEach(i -> crabs.put(i, crabs.getOrDefault(i, 0) + 1));

	}

}
