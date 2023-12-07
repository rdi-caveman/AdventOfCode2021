package advent_code_2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advent_code_common.FileUtility;

public class Day4 {
	private static final String DAY4_INPUT = "./src/resources/day4_input.txt";
	private static final String DAY4_TEST = "./src/resources/day4_test.txt";
	
	public static void main(String[] args) {
		// read input
		List<String> cards = FileUtility.readListOfString(DAY4_INPUT);
		// part 1
		Long sum = cards.stream()
				.mapToLong(s -> matchingNumbers(s))
				.map(m -> m == 0 ? 0 : (long) Math.pow(2L, m-1))
				.sum();
		System.out.println("part 1: " + sum);
		// part 2
		Map<Long, Long> cardCount = new HashMap<>();
		long curGame = 1; // should read this, but just increment
		for (String card : cards) {
			cardCount.merge(curGame, 1l, Long::sum);
			long cardPoints = matchingNumbers(card);
			for (long i=curGame+1; i<= Math.min(curGame+cardPoints,cards.size()); i++) {
				cardCount.merge(i, cardCount.get(curGame), Long::sum);
			}
			curGame++;
		}
		long sumCardCount = (cardCount.values().stream())
				.mapToLong(l -> l)
				.sum();
		System.out.println("part 2: " + sumCardCount);
	}
	
	private static long matchingNumbers(String s) {
		String[] data = s.split("[:\\|]\\s+");
		Set<Integer> winningNumbers = splitToSet(data[1]);
		Set<Integer> myNumbers = splitToSet(data[2]);
		return myNumbers.stream()
			.filter(i -> winningNumbers.contains(i))
			.count();
	}
	
	private static Set<Integer> splitToSet(String numbers) {
		Set<Integer> returnSet = new HashSet<>();
		String[] input = numbers.split("\\s+");
		Arrays.asList(input).stream()
			.mapToInt(s -> Integer.valueOf(s))
			.forEach(i -> returnSet.add(i));
		return returnSet;	
	}
}
