package advent_code_2021;

import java.util.Map;
import java.util.HashMap;

public class DiracDice2 {

	private static final int TEN_POW_4 = 10000;
	private static final int TEN_POW_6 = 1000000;
	private static int WINNING_SCORE = 21;

	// frequency of combinations totaling 3,4,5,6,7,8,9 when 3 3-sided dice are
	// rolled
	private static int[] dieRoll = new int[] { 1, 3, 6, 7, 6, 3, 1 };

	/*
	 * Table used to calculate next position, score row corresponds to die roll (0
	 * to 6) represents roll of 3 to 9 column corresponds to current position (1-10,
	 * 0 is unused)
	 */
	private static int[][] position = new int[][] { 
		    { 0, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3 }, // rolled 3
			{ 0, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4 }, // rolled 4
			{ 0, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5 }, // rolled 5
			{ 0, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6 }, // rolled 6
			{ 0, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7 }, // rolled 7
			{ 0, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8 }, // rolled 8
			{ 0, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9 }  // rolled 9
	}; 

	/*
	 * game state, count of games in this state
	 * AABBCCDD, 
	 * AA = player 1 position,  BB = player 2 position
	 * CC = player 1 score,  DD = player 2 score
	 */
	Map<Integer, Long> gameState = new HashMap<>();
	long[] winner = new long[2];

	public DiracDice2(int player1, int player2) {
		// initialize player positions, scores - universes = 1
		gameState.put(encodeGameState(player1, player2, 0, 0), 1L);  
	}

	int encodeGameState(int p1Position, int p2Position, int p1Score, int p2Score) {
		return p1Position * TEN_POW_6 + p2Position * TEN_POW_4 + p1Score * 100 + p2Score;
	}

	int encodeGameState(int[] s) {
		return s[0] * TEN_POW_6 + s[1] * TEN_POW_4 + s[2] * 100 + s[3];
	}

	int[] decodeState(int gameState) {
		return new int[] { gameState / TEN_POW_6, (gameState / TEN_POW_4) % 100, (gameState / 100) % 100,
				gameState % 100 };
	}

	public void play() {
		int player = 0;
		while (!gameState.isEmpty()) {  // while not all games won
			//System.out.println(gameState);
			gameState = move(player);   // player moves
			player = (player + 1) % 2;  // next player
		}
	}

	private Map<Integer, Long> move(int player) {
		int[] curState;
		Map<Integer, Long> newState = new HashMap<>();
		for (Integer gs : gameState.keySet()) {
			for (int d = 0; d < 7; d++) {
				curState = decodeState(gs);
				long gameCount = gameState.get(gs) * dieRoll[d]; // number of games to update/create
				curState[player] = position[d][curState[player]]; // move to new position
				curState[player + 2] += curState[player]; // add position to score
				if (curState[player + 2] >= WINNING_SCORE) {
					winner[player] += gameCount; // update count of wins
				} else {
					// create/update new game state
					newState.compute(encodeGameState(curState), (k, v) -> (v == null ? 0 : v) + gameCount); 
				}
			}
		}
		return newState;
	}
}
