package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {
	private final static String DAY2_INPUT_TXT = "src/resources/day2_input.txt";
	private final static Pattern subCmdPattern = Pattern.compile("([a-z]+) (\\d+)");
	private final static Matcher subCmdMatcher = subCmdPattern.matcher("");

	public static void main(String[] args) {
		List<SubCommand> commands = new ArrayList<>();
		initialize(commands);
		// begin part 1
		Submarine sub = new Submarine();
		commands.stream()
			.forEach(cmd -> sub.move(cmd));
		System.out.println("Day2 part1 is " + sub.depth * sub.horizontalPosition);
		// begin part 2
		sub.reset();
		commands.stream()
			.forEach(cmd -> sub.move2(cmd));
		System.out.println("Day2 part2 is " + sub.depth * sub.horizontalPosition);
	}

	private static void initialize(List<SubCommand> commands) {
		try (Stream<String> stream = Files.lines(Paths.get(DAY2_INPUT_TXT))) {
			stream.forEach(s -> {
				subCmdMatcher.reset(s);
				if (subCmdMatcher.matches()) {
					commands.add(new SubCommand(subCmdMatcher.group(1), Long.parseLong(subCmdMatcher.group(2))));
				}
				else {
					System.out.println("unparseable message " + s);
				}
			});
		} catch (IOException e) {
		  System.out.println(e.getMessage());
		}
	}

}
