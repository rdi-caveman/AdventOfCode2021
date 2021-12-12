package advent_code_2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.Collectors;

import advent_code_2021.BingoCard;

public class Day4 {
	private static final String DAY4_INPUT_TXT = "src/resources/day4_input.txt";
	private static final String recordSeparator = "\\r?\\n";
	
	private static List<BingoCard> bingoCards = new ArrayList<>();
	private static List<Integer> bingoNumbers = null;
	
	public static void main(String args[]) {
		initialize();
		part1();
		part2();
	}
	
	private static void part2() {
		List<BingoCard> winners = null;
		OptionalInt sum;
		boolean loserFound = false;
		for (int draw : bingoNumbers) {
			winners = bingoCards.stream()
				.filter(bc -> bc.play(draw))
				.collect(Collectors.toList());
			winners.stream()
				.forEach(bc -> bingoCards.remove(bc));
			if (bingoCards.size() == 0) {
				loserFound = true;
				System.out.println("Day 4 part 2 " + draw * winners.get(0).sumUnmarkedNumbers());
				break;
			}
		}
		if (!loserFound) {
			System.out.println("Day 4 part 1 has no loser.");
		}
	}

	private static void part1() {
		List<BingoCard> winners = null;
		OptionalInt sum;
		boolean winnerFound = false;
		for (int draw : bingoNumbers) {
			winners = bingoCards.stream()
				.filter(bc -> bc.play(draw))
				.collect(Collectors.toList());
			if (winners != null && !winners.isEmpty()) {
				winnerFound = true;
				sum = winners.stream()
					.mapToInt(bc -> bc.sumUnmarkedNumbers())
					.max();
				System.out.println("Day 4 part 1 " + draw * sum.orElse(0));
				break;
			}
		}
		if (!winnerFound) {
			System.out.println("Day 4 part 1 has no winner.");
		}
	}
	
	private static void initialize() {
		try (Scanner sc = new Scanner(new File(DAY4_INPUT_TXT)).useDelimiter(recordSeparator + recordSeparator)) {
			if (sc.hasNext()) {
				// read bingo numbers
				bingoNumbers = Arrays.stream(sc.next().split(","))
						.map(s -> Integer.parseInt(s))
						.collect(Collectors.toList());
			}
			while (sc.hasNext()) {
				bingoCards.add(new BingoCard(sc.next()));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
