package advent_code_2021;

import java.util.Objects;

public class Point {
	int x;
	int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || this.getClass() != obj.getClass())
	        return false; 
		return (this.x == ((Point)obj).x && this.y == ((Point)obj).y);		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.toString());
	}
	
	@Override
	public String toString() {
		return x + "," + y;
	}
}
