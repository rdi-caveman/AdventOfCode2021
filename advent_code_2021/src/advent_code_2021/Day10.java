package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day10 {
	private static final String DAY10_INPUT_TXT = "src/resources/day10_input.txt";
	static String[] input;
	static String openChars = "([{<";
	static String closeChars = ")]}>";
	
	public static void main(String[] args) {
		initialize();
		// part 1
		long syntaxErrorScore = Arrays.stream(input)
			.map(s -> parse(s))
			.filter(p -> p.second.contains("instead"))
			.peek(p -> System.out.println(p.first + " " + p.second))
			.mapToLong(p -> p.first)
			.sum();
		System.out.println("Day 10 part 1 " + syntaxErrorScore);

		// part 2
		List<Long> scores = Arrays.stream(input)
			.map(s -> parse(s))
			.filter(p -> p.second.contains("Complete by"))
			.peek(p -> System.out.println(p.first + " " + p.second))
			.map(p -> p.first)
			.collect(Collectors.toList());
		Collections.sort(scores);
		System.out.println("Day 10 part 2 " + scores.get((scores.size()-1)/2));

	}
	
	private static void initialize() {
		try {
			input = (new String(Files.readAllBytes(Paths.get(DAY10_INPUT_TXT)))).split("\\s+");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static long invalidSyntaxScore(Character errorChar) {
		if (errorChar.charValue() == ')') return 3;
		if (errorChar.charValue() == ']') return 57;
		if (errorChar.charValue() == '}') return 1197;
		if (errorChar.charValue() == '>') return 25137;
		System.out.println("invalid character " + errorChar);
		return -1;
	}
	
	private static Pair<Long, String> parse (String input) {
		Stack<Character> stack = new Stack<>();
		char ch;
		for (int i=0; i<input.length(); i++) {
			ch = input.charAt(i);
			if (openChars.indexOf(ch) != -1) {
				// found open character, push on stack
				stack.push(new Character(ch));
			}
			else if (closeChars.indexOf(ch) != -1) {
				// found a closing character, check to see if it is expected
				if (stack.isEmpty()) {
					return new Pair(null, 
							String.format("%s - Found closing character %c when open character was expected.", input, ch));				
				}
				// check to see if it matches open char
				char openCh = stack.pop().charValue();			
				if (closeChars.indexOf(ch) != openChars.indexOf(openCh)) {
					return new Pair(invalidSyntaxScore(ch), 
							String.format("%s - Expected  %c, but found %s instead.",
							input, closeChars.charAt(openChars.indexOf(openCh)), ch));
				}	
				// everything is good - move on
			}
		}
		if (!stack.isEmpty()) {
			Pair<Long,String> score = incompleteSyntaxScore(stack);
			return new Pair(score.first, String.format("%s - Complete by Adding %s.", input, score.second));
		}
		return new Pair(null, input);
	}

	private static Pair<Long, String> incompleteSyntaxScore(Stack<Character> stack) {
		long score = 0;
		String returnValue = "";
		while(!stack.isEmpty()) {
			char ch = stack.pop().charValue();
			int i = openChars.indexOf(ch);
			score = 5*score + i + 1;
			returnValue += closeChars.charAt(i);
		}
		return new Pair(score, returnValue);
	}
	
}
