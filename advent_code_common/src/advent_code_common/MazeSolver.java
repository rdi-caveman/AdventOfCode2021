package advent_code_common;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class MazeSolver {
	// direction arrays for simplification of getting
	// neighbor
	final static int[] dRow = new int[] { -1, 0, 1, 0 };
	final static int[] dCol = new int[] { 0, 1, 0, -1 };

	public int solve(int length, int width, int startRow, 
			int startCol, int endRow, int endCol) {

		// cells to analyze
		SortedSet<Cell> cells = new TreeSet<>();

		// initialize cost
		int[][] cost = new int[length][width];
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				cost[row][col] = Integer.MAX_VALUE;
			}
		}

		// insert start cell with 0 distance
		cells.add(new Cell(startRow, startCol, 0));

		// initialize risk of (0, 0) with 0
		cost[startRow][startCol] = 0; // don't count the cost of your starting position unless you enter it

		// loop for dijkstra's algorithm
		while (!cells.isEmpty()) {
			// get the cell with minimum cost and delete
			// it from the set
			Cell minCell = cells.first();
			if (minCell.row == endRow && minCell.col == endCol) {
				break;
			}
			cells.remove(minCell);

			// check neighbors
			for (int i = 0; i < 4; i++) {
				int row = minCell.row + dRow[i];
				int col = minCell.col + dCol[i];

				// if not inside boundary, ignore them
				if (!isInsideGrid(row, col, length, width))
					continue;
				
				if (!isNavigable(minCell, row, col)) {
					continue;
				}

				// If distance from current cell is smaller, then
				// update cost of neighbor cell
				if (cost[row][col] > cost[minCell.row][minCell.col] + moveCost(row,col)) {
					// If cell is already there in set, then
					// remove it
					if (cost[row][col] != Integer.MAX_VALUE)
						cells.remove(new Cell(row, col, cost[row][col]));

					// update the distance and insert new updated
					// cell in set
					cost[row][col] = cost[minCell.row][minCell.col] + moveCost(row,col);
					cells.add(new Cell(row, col, cost[row][col]));
				}
			}
			//System.out.println(cells);
		}
		return cost[endRow][endCol];
	}
	
	private static boolean isInsideGrid(int row, int col, int length, int width) {
		return (row >= 0 && row < length && col >= 0 && col < width);
	}
	
	abstract public boolean isNavigable(Cell minCell, int row, int col);
	
	abstract public int moveCost (int row, int col);

}
