package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class Day13 {
	private static final String DAY13_INPUT_TXT = "src/resources/day13_input.txt";
	private static Map<Point, Integer> paper = new HashMap<>();
	private static List<Pair<String, Integer>> instructions = new ArrayList<>();

	public static void main(String[] args) {
		initialize();
		System.out.println(paper);
		paper = fold(instructions.get(0));
		System.out.println("Day 13 part 1 " + paper.size());

		for (int i = 0; i < instructions.size(); i++) {
			paper = fold(instructions.get(i));
		}
		System.out.println("\nDay 13 part 2: ");
		displayCode();
	}

	private static void displayCode() {
		OptionalInt maxX = paper.keySet().stream().mapToInt(p -> p.x).max();
		OptionalInt maxY = paper.keySet().stream().mapToInt(p -> p.y).max();
		for (int i = 0; i <= maxY.getAsInt(); i++) {
			for (int j = 0; j <= maxX.getAsInt(); j++) {
				System.out.print(paper.containsKey(new Point(j, i)) ? "#" : ".");
			}
			System.out.println();
		}
	}

	public static Map<Point, Integer> fold(Pair<String, Integer> instruction) {
		Map<Point, Integer> foldedPaper = new HashMap<>();
		paper.entrySet().stream().forEach(e -> {
			if (instruction.first.equals("y") && e.getKey().y > instruction.second) {
				// fold vertical
				Point p = new Point(e.getKey().x, 2 * instruction.second - e.getKey().y);
				foldedPaper.put(p, paper.getOrDefault(p, 0) + 1);
			} else if (instruction.first.equals("x") && e.getKey().x > instruction.second) {
				// fold horizontal
				Point p = new Point(2 * instruction.second - e.getKey().x, e.getKey().y);
				foldedPaper.put(p, paper.getOrDefault(p, 0) + 1);
			} else {
				// above (to left of) fold
				foldedPaper.put(e.getKey(), e.getValue());
			}
		});
		return foldedPaper;
	}

	public static void initialize() {
		String input[] = FileUtility.readEntireFile(DAY13_INPUT_TXT).split("\r?\n\r?\n");
		Arrays.stream(input[0].split("\\s+")).map(s -> s.split(","))
				.forEach(a -> paper.put(new Point(Integer.parseInt(a[0]), Integer.parseInt(a[1])), 1));
		Arrays.stream(input[1].split("\r?\n")).map(s -> s.split("="))
				.forEach(a -> instructions.add(new Pair(a[0].replace("fold along ", ""), Integer.parseInt(a[1]))));

	}
}
