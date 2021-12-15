package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day15 {
	private static final String DAY15_INPUT_TXT = "src/resources/day15_input.txt";
	
	public static void main(String[] args) {
		int[][] inputMap = initialize();
		System.out.println("Day 15 part 1 " + leastRisk(inputMap));
		int[][] expandedMap = expandMap(inputMap, 5);
		System.out.println("Day 15 part 2 " + leastRisk(expandedMap));
	}

	private static int[][] expandMap(int[][] inputMap, int scale) {
		int length = inputMap.length;
		int width = inputMap[0].length;
		int risk;
		int [][] outputMap = new int[length*scale][width*scale];
		for (int row=0; row<scale*length; row++ ) {
			for (int col=0; col<scale*width; col++) {
				 risk = inputMap[row%length][col%width]+row/length+col/width;
				 if (risk>9) risk -= 9;
				 outputMap[row][col] = risk;
			}
		}
		return outputMap;
	}

	private static int leastRisk(int[][] map) {
		int length = map.length;
		int width = map[0].length;
		// direction arrays for simplification of getting
		// neighbor
		int[] dRow = new int[] { -1, 0, 1, 0 };
		int[] dCol = new int[] { 0, 1, 0, -1 };

		// cells to analyze
		SortedSet<Cell> cells = new TreeSet<>();

		// initialize risk
		int[][] risk = new int[length][width];
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				risk[row][col] = Integer.MAX_VALUE;
			}
		}

		// insert (0, 0) cell with 0 distance
		cells.add(new Cell(0, 0, 0));

		// initialize risk of (0, 0) with 0
		risk[0][0] = 0; // don't count the risk level of your starting position unless you enter it

		// loop for dijkstra's algorithm
		while (!cells.isEmpty()) {
			// get the cell with minimum risk and delete
			// it from the set
			Cell minCell = cells.first();
			cells.remove(minCell);

			// check neighbors
			for (int i = 0; i < 4; i++) {
				int row = minCell.row + dRow[i];
				int col = minCell.col + dCol[i];

				// if not inside boundary, ignore them
				if (!isInsideGrid(row, col, length, width))
					continue;

				// If risk from current cell is smaller, then
				// update risk of neighbour cell
				if (risk[row][col] > risk[minCell.row][minCell.col] + map[row][col]) {
					// If cell is already there in set, then
					// remove it
					if (risk[row][col] != Integer.MAX_VALUE)
						cells.remove(new Cell(row, col, risk[row][col]));

					// update the distance and insert new updated
					// cell in set
					risk[row][col] = risk[minCell.row][minCell.col] + map[row][col];
					cells.add(new Cell(row, col, risk[row][col]));
				}
			}
			//System.out.println(cells);
		}
		return risk[length - 1][width - 1];
	}

	private static int[][] initialize() {
		int[][] inputMap = null;
		String input[];
		try {
			input = (new String(Files.readAllBytes(Paths.get(DAY15_INPUT_TXT)))).split("\r?\n");
			int length = input.length;
			int width = input[0].length();
			inputMap = new int[length][width];
			for (int row = 0; row < length; row++) {
				for (int col = 0; col < width; col++) {
					inputMap[row][col] = input[row].charAt(col) - '0';
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return inputMap;
	}

	private static boolean isInsideGrid(int row, int col, int length, int width) {
		return (row >= 0 && row < length && col >= 0 && col < width);
	}
}
