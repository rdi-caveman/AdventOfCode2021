package advent_code_2021;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Day25 {

	private static final char EMPTY = '.';
	private static final String DAY25_INPUT_TXT = "src/resources/day25_input.txt";
	private static final char EAST = '>';
	private static final char SOUTH = 'v';
	
	public static void main(String[] args) {
		char[][] map = FileUtility.readCharArray(DAY25_INPUT_TXT);
		boolean updated = true;
		int count = 0;
		//displayMap(map, count);
		while (updated) {
			Pair<Boolean, char[][]> returnValue = move(map);
			updated = returnValue.first;
			map = returnValue.second;
			count++;
			//displayMap(map, count);
		}
		System.out.println("Day 25 part 1 " + count);
		
	}
	
	private static  Pair<Boolean,char[][]> move(char[][] map) {
		Pair<Boolean, char[][]> returnValue = moveEast(map);
		boolean updatedEast = returnValue.first;
		returnValue = moveSouth(returnValue.second);
		returnValue.first = returnValue.first || updatedEast;
		return returnValue;
	}
	
	private static Pair<Boolean,char[][]> moveEast(char[][] map) {
		AtomicBoolean updated = new AtomicBoolean(false);
		int length = map.length;
		int width = map[0].length;
		char[][] returnMap = new char[length][width];
		IntStream.range(0,length).forEach(i ->{
			IntStream.range(0,width).forEach(j -> {
				if (map[i][j] == EMPTY  && map[i][(j+width-1) % width] == EAST) {
					returnMap[i][j] = EAST;
					updated.set(true);
				}
				else if (map[i][j] == EAST  && map[i][(j+1) % width] == EMPTY) {
					returnMap[i][j] = EMPTY;
					updated.set(true);
				}
				else {
					returnMap[i][j] = map[i][j];
				}
			});
		});
		return new Pair(updated.get(), returnMap);	
	}

	private static Pair<Boolean,char[][]> moveSouth(char[][] map) {
		AtomicBoolean updated = new AtomicBoolean(false);
		int length = map.length;
		int width = map[0].length;
		char[][] returnMap = new char[length][width];
		IntStream.range(0,length).forEach(i ->{
			IntStream.range(0,width).forEach( j -> {
				if (map[i][j] == EMPTY  && map[(i+length-1) % length][j] == SOUTH) {
					returnMap[i][j] = SOUTH;
					updated.set(true);
				}
				else if (map[i][j] == SOUTH  && map[(i+1) % length][j] == EMPTY) {
					returnMap[i][j] = EMPTY;
					updated.set(true);
				}
				else {
					returnMap[i][j] = map[i][j];
				}
			});
		});
		return new Pair(updated.get(), returnMap);	
	}
	
	private static void displayMap(char[][] map, int round) {
		System.out.println("\ncount: " + round);
		for (int i=0; i<map.length; i++) {
			System.out.println(new String(map[i]));
		}		
	}
}
