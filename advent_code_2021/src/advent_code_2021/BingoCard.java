package advent_code_2021;

import java.util.Arrays;

public class BingoCard {
	private static final int CARD_SIZE = 5;
	
	int[][] numbers = new int[CARD_SIZE][CARD_SIZE];
	boolean[][] played = new boolean[CARD_SIZE][CARD_SIZE];
	
	public BingoCard(String card) {
		String values[] = card.trim().split("\\s+"); 
		for(int row = 0; row < CARD_SIZE; row++) {
			for (int col = 0; col < CARD_SIZE; col++) {
				numbers[row][col] = Integer.parseInt(values[row*CARD_SIZE + col]);
			}
		}
	}
	
	public boolean play(int number) {
		for(int row = 0; row < CARD_SIZE; row++) {
			for (int col = 0; col < CARD_SIZE; col++) {
				if (numbers[row][col] == number) {
					played[row][col] = true;
				}
			}
		}
		return isWinner();
	}
	
	public boolean isWinner() {
		boolean winner = false;
		for (int i=0; i<CARD_SIZE; i++) {
			// check rows and columns
			if (filled(i,0,0,1) || filled(0,i,1,0)) {
				winner = true;
				break;
			}
			// check diagonals
			if (filled(0,0,1,1) || filled(0,CARD_SIZE-1, 1,-1)) {
				winner = true;
				break;
			}
		}
		return winner;
	}
	
	private boolean filled(int startRow, int startCol, int rowIncrement, int colIncrement) {
		boolean winner = true;
		for (int i=0; i<CARD_SIZE; i++) {
			if (!played[startRow + i*rowIncrement][startCol+i*colIncrement]) {
				winner = false;
				break;
			}
		}
		return winner;
	}
	
	public int sumUnmarkedNumbers() {
		int sum=0;
		for(int row = 0; row < CARD_SIZE; row++) {
			for (int col = 0; col < CARD_SIZE; col++) {
				sum += played[row][col] ? 0 : numbers[row][col];
			}
		}
		return sum;
	}
	
}
