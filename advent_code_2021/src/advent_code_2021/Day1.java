package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
	

	private static final String DAY1_INPUT_TXT = "src/resources/day1_input.txt";

	public static void main(String args[]) {
		List<Integer> ints = FileUtility.readListOfInteger(DAY1_INPUT_TXT);
		int increases = 0;
		for (int i=1; i<ints.size(); i++ ) {
			if (ints.get(i-1) < ints.get(i)) {
				increases++;
			}
		}
		System.out.println("day1 part1 is " + increases);
		increases = 0;
		for (int i=3; i<ints.size(); i++ ) {
			if (ints.get(i-3) < ints.get(i)) { // comparing (i-3 + i-2 + -1) to (i-3 + i-1 = i) reduces to this
				increases++;
			}
		}
		System.out.println("day1 part2 is " + increases);
	}
}
