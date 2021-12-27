package advent_code_2021;

public class GameSpace {
	int row;
	int col;
	int charOffset;
	boolean isHall;
	boolean isRoom;
	boolean isForbidden;
	Character homeFor = null;
	
	public GameSpace(int row, int col, int charOffset, boolean isHall, boolean isRoom, boolean isForbidden, Character homeFor) {
		this.row = row;
		this.col = col;
		this.charOffset = charOffset;
		this.isHall = isHall;
		this.isRoom = isRoom;
		this.isForbidden = isForbidden;
		this.homeFor = homeFor;
	}
		
}
