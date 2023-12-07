package advent_code_2023;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import advent_code_common.FileUtility;

public class Day3 {
	private static List<PartNumber> partNumbers = new ArrayList<>();
	private static List<Symbol> symbols = new ArrayList<>();
	private static String DAY3_TEST = "./src/resources/day3_test.txt";
	private static String DAY3_INPUT = "./src/resources/day3_input.txt";	private static Pattern numberPattern = Pattern.compile("(\\d+)");
	private static Pattern symbolPattern = Pattern.compile("([^.\\d]+)");
	private static Matcher numberMatcher = numberPattern.matcher("");
	private static Matcher symbolMatcher = symbolPattern.matcher("");
	
	public static void main(String[] args) {
		parseInput(DAY3_INPUT);
		// part 1
		long partNumberSum = partNumbers.stream()
				.filter(n -> adjacentToSymbol(n))
				.mapToLong(n -> n.partNum)
				.sum();
		System.out.println("part 1: " + partNumberSum);
		// part 2
		long gearRatioSum = symbols.stream()
				.mapToLong(s -> gearNumber(s))
				.sum();
		System.out.println("part 2: " + gearRatioSum);
	}
	
	private static boolean adjacentToSymbol(PartNumber n) {
		String sym = symbols.stream()
			.filter(s -> (s.row >= n.row-1 && s.row <= n.row+1)
					&& (s.col >= n.startCol-1 && s.col <= n.endCol+1))
			.findFirst()
			.map(s -> s.symbol)
			.orElse(null);
		return sym != null;
	}
	
	private static long gearNumber(Symbol s) {
		List<PartNumber> adjacentParts = partNumbers.stream()
			.filter(n -> (s.row >= n.row-1 && s.row <= n.row+1)
						&& (s.col >= n.startCol-1 && s.col <= n.endCol+1))
			.toList();
		return (adjacentParts.size()) == 2 ? adjacentParts.get(0).partNum * adjacentParts.get(1).partNum : 0;
	}

	private static void parseInput(String filename) {
		List<String> input = FileUtility.readListOfString(filename);
		for (int row=0; row<input.size(); row++) {
			// find numbers
			numberMatcher.reset(input.get(row));
			while (numberMatcher.find()) {
				partNumbers.add(new PartNumber(row,
						numberMatcher.start(), 
						numberMatcher.end()-1, 
						Long.valueOf(numberMatcher.group(1))
				));
			}
			// find Symbols
			symbolMatcher.reset(input.get(row));
			while (symbolMatcher.find()) {
				symbols.add(new Symbol(row, symbolMatcher.start(), symbolMatcher.group(1)));
			}
		}
	}
	
	public record PartNumber(int row, int startCol, int endCol, long partNum) {}
	public record Symbol(int row, int col, String symbol) {}

}
