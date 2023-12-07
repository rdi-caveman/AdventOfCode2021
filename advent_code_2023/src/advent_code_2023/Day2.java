package advent_code_2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import advent_code_common.FileUtility;

public class Day2 {
	private static final String GREEN = "green";
	private static final String BLUE = "blue";
	private static final String RED = "red";
	private static final String DAY2_INPUT = "./src/resources/day2_input.txt";
	private static final String DAY2_TEST = "./src/resources/day2_test.txt";
	
	public static void main(String[] args) {
		List<Game> games = parseInput(DAY2_INPUT);
		// Part 1
		Map<String,Integer> actualCubes = Map.of(
				RED, 12,
				GREEN, 13,
				BLUE, 14);
	    int impossibleGameCount = games.stream()
	    	.filter(g -> possible(g, actualCubes))
	    	.mapToInt(g -> g.number)
	    	.sum();
	    System.out.println("part 1: " + impossibleGameCount);
	    // Part 2
	    long power = games.stream()	
	    	.mapToLong(g -> power(g))
	    	.sum();
		System.out.println("part 2: " + power);
	
	}
	
	private static boolean possible(Game g, Map<String, Integer> limit) {
		for (Map<String, Integer> cubeSet : g.cubeSets) {
			for (String color : cubeSet.keySet()) {
				if (cubeSet.get(color) > limit.get(color)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static long power(Game g) {
		// initialize result
		Map<String,Integer> max = new HashMap<>();
		max.put(RED,0);
		max.put(BLUE, 0);
		max.put(GREEN, 0);
		// find max number of each color
		for (Map<String, Integer> cubeSet : g.cubeSets) {
			for (String color : cubeSet.keySet()) {
				if (cubeSet.get(color) > max.get(color)) {
					max.put(color, cubeSet.get(color));
				}
			}
		}
		// return power
		return max.get(RED) * max.get(BLUE) * max.get(GREEN);		
	}
	
	private static List<Game> parseInput(String filename) {
		List<Game> games = new ArrayList<>();
		for (String record : FileUtility.readListOfString(filename)) {
			String[] inputParts = record.split("[:;] ");
			// get game number
			int gameNum = Integer.valueOf(inputParts[0].split(" ")[1]);
			List<Map<String, Integer>> gameResults = new ArrayList<>();
			for (int i=1; i<inputParts.length; i++) {
				String[] results = inputParts[i].split(", ");
				Map<String,Integer> colors = new HashMap<>();
				for (int j=0; j<results.length; j++) {
					String[] colorResult = results[j].split(" ");
					colors.put(colorResult[1], Integer.valueOf(colorResult[0]));
				}
				gameResults.add(colors);
			}
			Game game = new Game(gameNum, gameResults);
			games.add(game);
		
		}
		return games;
	}

	public record Game(int number, List<Map<String,Integer>> cubeSets) {}

	
}
