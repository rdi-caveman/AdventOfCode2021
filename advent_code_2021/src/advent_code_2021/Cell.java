package advent_code_2021;

public class Cell implements Comparable<Cell> {
	int row;
	int col;
	int risk;
	
	public Cell(int row, int col, int risk) {
		this.row = row;
		this.col = col;
		this.risk = risk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + risk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Cell other = (Cell) obj;
		return (col == other.col && row == other.row && risk == other.risk);
	}

	@Override
	public int compareTo(Cell o) {
		if (this.risk != o.risk) return new Integer(this.risk).compareTo(new Integer(o.risk));
		else if (this.row != o.row) return new Integer(this.row).compareTo(new Integer(o.row));
		else return new Integer(this.col).compareTo(new Integer(o.col));
	}

	@Override
	public String toString() {
		return String.format("[%d,%d] %d", row, col, risk);
	}
}
