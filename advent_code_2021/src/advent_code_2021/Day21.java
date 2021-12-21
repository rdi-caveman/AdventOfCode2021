package advent_code_2021;

public class Day21 {
		
	/* Player input
	 * Player 1 starting position: 1
     * Player 2 starting position: 3
	 */
	
	public static void main(String[] args) {
		
		// part 1
		DiracDice game = new DiracDice(new int[] {1,3});
		int winner = game.play();
		System.out.println("Day 1 part 1 " +  game.scoreGame());
		
		// part 2
		DiracDice2 game2 = new DiracDice2(1,3);
		game2.play();
		System.out.println( "player 1 wins; " + game2.winner[0]);
		System.out.println( "player 2 wins; " + game2.winner[1]);
		System.out.println( "Day2 part 2 " + Math.max(game2.winner[0],game2.winner[1]));
		
	}
	

	
	 
}
