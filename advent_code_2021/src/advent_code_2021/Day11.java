package advent_code_2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {
	private static final String DAY11_INPUT_TXT = "src/resources/day11_input.txt";
	private static final int GRID_SIZE = 10;
	private static int[][] octopus = new int[GRID_SIZE][GRID_SIZE];
	private static long flashes = 0;


	public static void main(String[] args) {
		part1();
		part2();
	}

	private static void part1() {
		initialize();
		//displayOctopuses(0);
		for (int i=1; i<=100; i++) {
			step();
			//displayOctopuses(i);
		}
		System.out.println("Day 11 part 1 " + flashes);
	}
	
	private static void part2() {
		initialize();
		long step = 0;
		while (!allFlashed()) {
			step();
			step++;
		}
		System.out.println("Day 11 part 1 " + step);
	}
	
	private static boolean allFlashed() {
		for (int row=0; row<GRID_SIZE; row++) {
			for (int col=0; col<GRID_SIZE; col++) {
				if (octopus[row][col] >0) {
					return false;
				}
			}
		}
		return true;
	}

	public static void step() {
		boolean[][] flashed = new boolean[GRID_SIZE][GRID_SIZE];
		for (int row=0; row<GRID_SIZE; row++) {
			for (int col=0; col<GRID_SIZE; col++) {
				increase(flashed, row,col);
			}
		}
	}
	
	public static void increase(boolean[][] flashed, int row, int col) {
		if (!flashed[row][col]) {
			octopus[row][col]++;
			if (octopus[row][col] > 9) {
				flashed[row][col] = true;
				octopus[row][col] = 0;
				flashes++;
				// increase neighbors
				for (int i = Math.max(0, row-1); i<=Math.min(GRID_SIZE-1,row+1); i++) {
					for (int j = Math.max(0, col-1); j<=Math.min(GRID_SIZE-1,col+1); j++) {
						increase(flashed,i,j);
					}	
				}
			}
		}
	}
	
	public static void initialize() {
		flashes = 0;
		octopus = FileUtility.readIntArray(DAY11_INPUT_TXT, GRID_SIZE, GRID_SIZE);
	}

	public static void displayOctopuses(int step) {
		System.out.println("\nStep: " + step);
		for (int row=0; row<GRID_SIZE; row++) {
			for (int col=0; col<GRID_SIZE; col++) {
				System.out.print(octopus[row][col]);
			}
			System.out.println();
		}
	}
	
}
