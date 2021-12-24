package advent_code_2021;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cube {
	
	int[][] coords = new int[3][2];
	boolean on;
	
	private static Pattern cubePattern = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
	private static Matcher cubeMatcher = cubePattern.matcher("");

	
	
	public String toString() {
		return String.format("([%d,%d],[%d,%d],[%d,%d])",
				coords[0][0], coords[0][1],
				coords[1][0], coords[1][1],
				coords[2][0], coords[2][1]);									
	}
	
	public static Cube parse(String s) {
		Cube c = new Cube();
		cubeMatcher.reset(s);
		if (cubeMatcher.matches()) {
			c.on = cubeMatcher.group(1).equals("on");
			c.coords[0][0] = Integer.parseInt(cubeMatcher.group(2));
			c.coords[0][1] = Integer.parseInt(cubeMatcher.group(3));
			c.coords[1][0] = Integer.parseInt(cubeMatcher.group(4));
			c.coords[1][1] = Integer.parseInt(cubeMatcher.group(5));
			c.coords[2][0] = Integer.parseInt(cubeMatcher.group(6));
			c.coords[2][1] = Integer.parseInt(cubeMatcher.group(7));
		}

		return c;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(coords);
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
		Cube other = (Cube) obj;
		if (!Arrays.deepEquals(coords, other.coords))
			return false;
		return true;
	}

	public long getVolume() {
		return (coords[0][1]-coords[0][0]+1L) * (coords[1][1]-coords[1][0]+1L) * (coords[2][1]-coords[2][0]+1L); 
	}


	public Cube intersection(Cube b) {
		if (intersectionVolume(b) == 0) {
			return null;
		}
		Cube c = new Cube();
		c.coords[0] = intersectCoords(this.coords[0], b.coords[0]);
		c.coords[1] = intersectCoords(this.coords[1], b.coords[1]);
		c.coords[2] = intersectCoords(this.coords[2], b.coords[2]);
		return c;
	}
	
	private int[] intersectCoords(int[] a, int[] b) {
		int[] c = new int[2];
		c[0] = Math.max(a[0], b[0]);
		c[1] = Math.min(a[1], b[1]);
		return c;
	}


	public long intersectionVolume(Cube b) {
		return intersection(this.coords[0], b.coords[0]) * intersection(this.coords[1], b.coords[1]) * intersection(this.coords[2], b.coords[2]);
	}

	private static long intersection(int[] a, int[] b) {
		 return Math.max(1 + Math.min(a[1],b[1]) - Math.max(a[0],b[0]),0);
	}
	
 }
