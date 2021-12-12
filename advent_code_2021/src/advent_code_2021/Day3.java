package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
	private static final String DAY3_INPUT_TXT = "src/resources/day3_input.txt";
	//private static final String DAY3_INPUT_TXT = "src/resources/day3_test.txt";
	private static int BIT_WIDTH;

	public static void main(String[] args) {
		// Initialize list
		List<char[]> diagnostics = initialize();
		// part 1
		int[] zeroCount = new int[BIT_WIDTH];
		int[] oneCount = new int[BIT_WIDTH];
		diagnostics.stream()
			.forEach(diagnostic -> {
				for (int i=0; i<BIT_WIDTH; i++) {
					zeroCount[i] += diagnostic[i] == '0' ? 1 : 0;
					oneCount[i] += diagnostic[i] == '0' ? 0 : 1;
				}
			});
		long gammaRate = 0;
		long epsilonRate = 0;
		for (int i=0; i<BIT_WIDTH; i++) {
			int pow2 = 1 << (BIT_WIDTH - 1 - i);
			if (oneCount[i] > zeroCount[i]) {
				gammaRate += pow2;
			}
			else {
				epsilonRate += pow2;
			}
		}
		//System.out.println("Gamma: " + gammaRate + " Eplison: " + epsilonRate);
		System.out.println("Day 3 part 1 " + gammaRate * epsilonRate);
		// part 2
		long oxygenRating = decodeRating(diagnostics, true);
		long co2ScrubberRating = decodeRating(diagnostics, false);
		//System.out.println("O2 " + oxygenRating + " CO2 " + co2ScrubberRating);
		System.out.println("Day 3 part 2 " + oxygenRating*co2ScrubberRating);
	}

	private static List<char[]> initialize() {
		List<char[]> diagnostics = null;
		try (Stream<String> stream = Files.lines(Paths.get(DAY3_INPUT_TXT))) {
			diagnostics = stream.map(s -> s.toCharArray())
					.collect(Collectors.toList());
		} catch (IOException e) {
		  System.out.println(e.getMessage());
		}  
		BIT_WIDTH = diagnostics.get(0).length;
		return diagnostics;
	}

	private static int decodeRating(List<char[]> diagnostics, boolean more) {
		List<char[]> remainingDiagnostics = new ArrayList<>();
		remainingDiagnostics.addAll(diagnostics);
		for (int i=0; i<BIT_WIDTH; i++) {
			int zeroCount = 0;
			int oneCount = 0;
			for (char[] diagnostic : remainingDiagnostics) {
				zeroCount += diagnostic[i] == '0' ? 1 : 0;
				oneCount += diagnostic[i] == '0' ? 0 : 1;
			}
			char keepDigit = ((more && oneCount >= zeroCount) || (!more & oneCount < zeroCount)) ? '1' : '0';
			int length = remainingDiagnostics.size();
			for (int j=length-1; j>=0; j--) {
				if(remainingDiagnostics.get(j)[i] != keepDigit) {
					remainingDiagnostics.remove(j);
				}
			}
			if (remainingDiagnostics.size() == 1) {
				break;
			}		
		}
		return Integer.parseInt(String.valueOf(remainingDiagnostics.get(0)),2);
	}
}
