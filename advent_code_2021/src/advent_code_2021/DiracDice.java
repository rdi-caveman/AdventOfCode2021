package advent_code_2021;

import java.util.Arrays;

public class DiracDice {
	int dieSize = 100;
	int trackLength = 10;
	int die=0;
	int pos[];
	int score[];
    int winningScore = 1000;
    int dieRolls = 0;
    int numPlayers = 0;
    
	
	public DiracDice(int[] playerPositions) {
		pos = playerPositions;
		numPlayers = pos.length;
		score = new int[numPlayers];
	}

	
	/*
	 * play the game until a player has a winning score
	 * return winning player
	 */
	public int play() {
		int player = 0;
		while (true) {
			if (move(player) >= winningScore) {
				return player;				
			}
			player = (player +1) % numPlayers;
		}
	}
	
	private int move(int player) {
		int move = (roll() + roll() + roll()) % trackLength;
		pos[player] = (pos[player] + move) > trackLength ? (pos[player] + move) - trackLength : (pos[player] + move);
		score[player] += pos[player];
		//System.out.println(String.format("Player %d rolls %d and moves to %d for a total score of %d", player+1, move, pos[player], score[player]));
		return score[player];
	}
	
	
	private int roll() {
		die++;
		dieRolls++;
		return die % dieSize;
	}
	
	public int scoreGame() {
		return Arrays.stream(score).min().getAsInt() * dieRolls;
	}
	
}
