package advent_code_2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtility {

	public static List<Integer> readListOfInteger(String filename) {
		List<Integer> ints = null;
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			ints = stream.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ints;
	}

	public static List<String> readListOfString(String filename) {
		List<String> strings = null;
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			strings = stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return strings;
	}

	public static String readEntireFile(String filename) {
		try {
			return new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static int[][] readIntArray(String filename) {
		int[][] inputMap = null;
		String[] input = FileUtility.readEntireFile(filename).split("\r?\n");
		int length = input.length;
		int width = input[0].length();
		inputMap = new int[length][width];
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				inputMap[row][col] = input[row].charAt(col) - '0';
			}
		}
		return inputMap;
	}

	public static int[][] readIntArray(String filename, int numRow, int numCol) {
		int[][] returnArray = null;
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			for (int row = 0; row < numRow; row++) {
				String line = br.readLine();
				for (int col = 0; col < numCol; col++) {
					returnArray[row][col] = line.charAt(col) - '0';
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnArray;
	}
	
	public static List<String> readRecords(String filename, String delimiter) {
		List<String> records = new ArrayList<>();
		try (Scanner sc = new Scanner(new File(filename)).useDelimiter(delimiter)) {
			while (sc.hasNext()) {
				records.add(sc.next());
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
}
