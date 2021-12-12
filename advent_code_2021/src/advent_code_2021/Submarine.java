package advent_code_2021;

public class Submarine {
	long depth = 0;
	long horizontalPosition = 0;
	long aim = 0;
	
	public void move(SubCommand cmd) {
		switch(cmd.direction) {
		case "forward":
			horizontalPosition += cmd.distance;
			break;
		case "down":
			depth += cmd.distance;
			break;
		case "up":
			depth -= cmd.distance;
			break;
		default:
			System.out.println("unknown direction" + cmd.direction);
		}
	}

	public void move2(SubCommand cmd) {
		switch(cmd.direction) {
		case "forward":
			horizontalPosition += cmd.distance;
			depth += aim*cmd.distance;
			break;
		case "down":
			aim += cmd.distance;
			break;
		case "up":
			aim -= cmd.distance;
			break;
		default:
			System.out.println("unknown direction" + cmd.direction);
		}
	}
	
	public void reset() {
		depth = 0;
		horizontalPosition = 0;
		aim = 0;
	}

}
