package advent_code_2021;

public class GameMove {
	int fromSpace;
	int toSpace;
	String newState;
	int cost;
	
	public GameMove(int fromSpace, int toSpace, String newState, int cost) {
		this.fromSpace = fromSpace;
		this.toSpace = toSpace;
		this.newState = newState;
		this.cost = cost;
	}
}
