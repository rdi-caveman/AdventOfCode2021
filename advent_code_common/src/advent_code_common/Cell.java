package advent_code_common;

public class Cell implements Comparable<Cell> {
	public int row;
	public int col;
	public int cost;
	
	public Cell(int row, int col, int cost) {
		this.row = row;
		this.col = col;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Cell o) {
		if (this.equals(o)) return 0;
		if (this.cost == o.cost) {
			if (this.row == o.row) {
				return this.col < o.col ? -1 : 1;
			}
			return this.row < o.row ? -1 : 1;
		}
		return this.cost < o.cost ? -1 : 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + cost;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (cost != other.cost)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [row=" + row + ", col=" + col + ", cost=" + cost + "]";
	}








}
