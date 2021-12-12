package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {
	private static final String DAY5_INPUT_TXT = "src/resources/day5_input.txt";
//	private static final String DAY5_INPUT_TXT = "src/resources/day5_test.txt";
	private static Pattern linePattern = Pattern.compile("(\\d+),(\\d+)\\s+->\\s+(\\d+),(\\d+)");
	private static Matcher lineMatcher = linePattern.matcher("");
	static Map<Point,Integer> ventMap = new HashMap<>();
	
	public static void main(String[] args) {
		part1();
		part2();

	}

	private static void initialize(boolean diagonalAllowed) {
		ventMap.clear();
		try (Stream<String> stream = Files.lines(Paths.get(DAY5_INPUT_TXT))) {
			stream.forEach(s -> addLine(s, diagonalAllowed));
		} catch (IOException e) {
		  System.out.println(e.getMessage());
		}
	}
	
	private static void part1() {
		initialize(false);
		long crossedLines = ventMap.values().stream()
			.filter(i -> i > 1)
			.count();
		System.out.println("Day 5 part 1 " + crossedLines);
	}
	
	private static void part2() {
		initialize(true);
		long crossedLines = ventMap.values().stream()
			.filter(i -> i > 1)
			.count();
		System.out.println("Day 5 part 2 " + crossedLines);
	}

	private static void addLine(String line, boolean diagonalAllowed) {
		lineMatcher.reset(line);
		if (lineMatcher.matches()) {
			int startX = Integer.parseInt(lineMatcher.group(1));
			int startY = Integer.parseInt(lineMatcher.group(2));
			int endX = Integer.parseInt(lineMatcher.group(3));
			int endY = Integer.parseInt(lineMatcher.group(4));
			if (!diagonalAllowed && startX != endX && startY != endY) {
				return;
			}
			int incX = Integer.signum(endX - startX);
			int incY = Integer.signum(endY - startY);
			int x = startX;
			int y = startY;
			Point p = new Point(x,y);
			ventMap.put(new Point(x,y), ventMap.getOrDefault(p,0)+1);
			while (x != endX || y != endY) {
				x += incX;
				y += incY;
				p = new Point(x,y);
				ventMap.put(p, ventMap.getOrDefault(p,0)+1);
			}
		}
		else {
			System.out.println("lineMatcher didn't match " + line);
		}
	}
}
