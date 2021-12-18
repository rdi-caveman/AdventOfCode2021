package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;

public class Day14 {
	private static final String DAY14_INPUT_TXT = "src/resources/day14_input.txt";
	private static Map<String, Long> pairs = new HashMap<>(); // ab -> 1
	private static Map<String, Long> elementCount = new HashMap<>(); // a -> 1
	private static Map<String, String> rules = new HashMap<>(); // ab -> c
	private static Map<String, Pair<String, String>> rulePairs = new HashMap<>(); // rule ab -> c becomes rulePair ab ->
																					// (ac, ca)

	public static void main(String[] args) {
		initialize();
		System.out.println("pairs: " + pairs);
		System.out.println("elementCount: " + elementCount);
		System.out.println("rules: " + rules);
		System.out.println("rulePairs: " + rulePairs);

		// part 1
		for (int i = 1; i <= 10; i++) {
			pairInsertion();
			// System.out.println(i + ": " + pairs);
			// System.out.println(i + ": " + elementCount);
		}
		OptionalLong min = elementCount.values().stream().mapToLong(l -> Long.valueOf(l)).min();
		OptionalLong max = elementCount.values().stream().mapToLong(l -> Long.valueOf(l)).max();
		System.out.println("Day 14 part 1 " + (max.getAsLong() - min.getAsLong()));

		// part 2
		for (int i = 11; i <= 40; i++) {
			pairInsertion();
		}
		min = elementCount.values().stream().mapToLong(l -> Long.valueOf(l)).min();
		max = elementCount.values().stream().mapToLong(l -> Long.valueOf(l)).max();
		System.out.println("Day 14 part 2 " + (max.getAsLong() - min.getAsLong()));
	}

	private static void pairInsertion() {
		Map<String, Long> result = new HashMap<>();
		pairs.entrySet().stream().forEach(e -> {
			if (rulePairs.containsKey(e.getKey())) {
				result.put(rulePairs.get(e.getKey()).first,
						result.getOrDefault(rulePairs.get(e.getKey()).first, 0L) + e.getValue());
				result.put(rulePairs.get(e.getKey()).second,
						result.getOrDefault(rulePairs.get(e.getKey()).second, 0L) + e.getValue());
				elementCount.put(rules.get(e.getKey()),
						elementCount.getOrDefault(rules.get(e.getKey()), 0L) + e.getValue());
			} else {
				result.put(e.getKey(), e.getValue());
			}
		});
		pairs = result;
	}

	private static void initialize() {
		String[] input = FileUtility.readEntireFile(DAY14_INPUT_TXT).split("\r?\n\r?\n");
		for (int i = 1; i < input[0].length(); i++) {
			pairs.put(input[0].substring(i - 1, i + 1), pairs.getOrDefault(input[0].substring(i - 1, i + 1), 0L) + 1);
		}
		for (int i = 0; i < input[0].length(); i++) {
			elementCount.put(input[0].substring(i, i + 1),
					elementCount.getOrDefault(input[0].substring(i, i + 1), 0L) + 1);
		}
		Arrays.stream(input[1].split("\r?\n")).map(s -> s.split(" -> ")).forEach(a -> {
			rules.put(a[0], a[1]);
			rulePairs.put(a[0], new Pair(a[0].substring(0, 1) + a[1], a[1] + a[0].substring(1, 2)));
		});

	}
}
