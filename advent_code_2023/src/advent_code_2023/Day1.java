package advent_code_2023;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import advent_code_common.FileUtility;

public class Day1 {
	private static final String DAY1_INPUT = "./src/resources/day1_input.txt";
	private static final String DAY1_TEST = "./src/resources/day1_test.txt";
	private static final Pattern numbersPattern = Pattern.compile("(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)");
	private static final Matcher numbersMatcher = numbersPattern.matcher("");
	


	public static void main(String[] args) {
		//part 1
		long sum = 0;
		sum =  FileUtility.readListOfString(DAY1_INPUT).stream()		
			.map(s -> s.replaceAll("[^\\d]","")) // remove all non-digit
			.filter(s -> s.length() > 0)  // has digits
			.map(s -> s.substring(0,1) + s.substring(s.length()-1)) // first and last digit
			.mapToLong(s -> Long.valueOf(s))
			.sum();
		System.out.println("part 1: " + sum);
		// part 2
		sum =  FileUtility.readListOfString(DAY1_INPUT).stream()
				.map(s -> firstNumber(s) + lastNumber(s))
				.map(s -> s.replaceAll("[^\\d]","")) // remove all non-digit
				.filter(s -> s.length() > 0)  // has digits
				.map(s -> s.substring(0,1) + s.substring(s.length()-1)) // first and last digit
				.mapToLong(s -> Long.valueOf(s))
				.sum();
		System.out.println("part 2: " + sum);
	}


	private static String firstNumber(String s) {
		// find and return first match
		String retValue = "";
		numbersMatcher.reset(s);
		if (numbersMatcher.find()) {
			try {
				retValue = value(numbersMatcher.group(1));
			} catch (Exception e) {
				return "";
			}
		}
		return retValue;
	}

	private static String lastNumber(String input) {
		String retValue = "";
		for (int startChar = input.length()-1; startChar >=0; startChar--) {
			retValue = firstNumber(input.substring(startChar));
			if (!retValue.equals("")) {
				return retValue;
			}
		}
		return retValue;
	}

	private static String value(String number) throws Exception {
		switch(number) {
		case "one": return "1";
		case "two": return "2";
		case "three": return "3";
		case "four": return "4";
		case "five": return "5";
		case "six": return "6";
		case "seven": return "7";
		case "eight": return "8";
		case "nine": return "9";
		default:
			return number;
		}	
	}

}
