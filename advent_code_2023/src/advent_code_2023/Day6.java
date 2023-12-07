package advent_code_2023;

import advent_code_common.FileUtility;
import advent_code_common.Pair;
/*
 * winning game: (time - x)*x - distance > 0
 * parabola  y = -x^2 + time*x - distance
 * quadratic y = ax^2 + bx + c
 * factors: a = -1, b = time, c = -distance 
 */

public class Day6 {
	private static final String DAY6_INPUT = "./src/resources/day6_input.txt";
	private static final String DAY6_TEST = "./src/resources/day6_test.txt";
	private static final int TIME = 0;
	private static final int DISTANCE = 1;

	public static void main(String[] args) {
		// parse input
		String[] input = FileUtility.readEntireFile(DAY6_INPUT)
			.replaceAll("[a-zA-Z]+:[ ]+","")  // strip labels
			.replaceAll("[ ]+"," ")           // strip extra spaces between numbers
			.split("\r?\n");                  // split lines
		String[] time = input[TIME].split(" ");
		String[] distance = input[DISTANCE].split(" ");
		// solve part 1
		long winningProduct = 1;
		for (int i=0; i<time.length; i++) {
			Pair<Double,Double> root = roots(-1L, Long.valueOf(time[i]), -Long.valueOf(distance[i]));
			int minWin = (root.first == Math.ceil(root.first)) ? root.first.intValue()+1 : (int) Math.ceil(root.first);
			int maxWin = (root.second == Math.floor(root.second)) ? root.second.intValue()-1 : (int) Math.floor(root.second);
			winningProduct *= (maxWin - minWin + 1);
		}
		System.out.println("part 1: " + winningProduct);
		// solve part 2
		long longTime = Long.valueOf(input[TIME].replaceAll(" ", ""));
		long longDist = Long.valueOf(input[DISTANCE].replaceAll(" ", ""));
		Pair<Double,Double> root = roots(-1L, longTime, -longDist);
		long minWin = (root.first == Math.ceil(root.first)) ? root.first.longValue()+1 : (long) Math.ceil(root.first);
		long maxWin = (root.second == Math.floor(root.second)) ? root.second.longValue()-1 : (long) Math.floor(root.second);
		System.out.println("part 2: " + (maxWin - minWin + 1));
	}

	private static Pair<Double, Double> roots(long a, long b, long c) {
		double root1 = (-b + Math.sqrt(b*b -4*a*c))/2*a;
		double root2 = (-b - Math.sqrt(b*b -4*a*c))/2*a;
		return new Pair(Math.min(root1, root2), Math.max(root1,root2));
	}

}
