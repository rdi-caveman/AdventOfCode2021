package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Day9 {
	private static final String DAY9_INPUT_TXT = "src/resources/day9_input.txt";
	private static String[] heightMap;  // puzzle input
	private static int[][] heightCount; // count of higher neighbors
	private static int[][] basinMap;    // map of basins
	private static Map<Integer,Integer> basinSizes = new HashMap<>(); // basin # and size
	private static int mapWidth;
	private static int mapHeight;
	
	public static void main (String[] args) {
		initialize();
		part1();	
		part2();
		
	}

	private static void part1() {
		analyzeLowSpots();
		int risk = analyzeRisk();
		System.out.println("Day 9 part 1 " + risk);
	}

	private static void part2() {
		analyzeBasins();  // return map of basin # and size
		List<Integer> sizes = new ArrayList<>();  
		sizes.addAll(basinSizes.values());  // get all the sizes
		Collections.sort(sizes);            // sort
		Collections.reverse(sizes);         // in revers order
		printBasinMap(sizes.get(0),sizes.get(1), sizes.get(2));  // visualize top 3  
		System.out.println("Day 9 part 2 " + (sizes.get(0) * sizes.get(1) * sizes.get(2)));  // output product of top 3
	}
		
	private static int analyzeRisk() {
		int sum = 0;
		for (int i=0; i<mapHeight; i++) {
			for (int j=0; j<mapWidth; j++) {
				if (heightCount[i][j] == 0) {
					sum += Integer.parseInt(heightMap[i].substring(j,j+1)) + 1;
				}
			}
		}	
		return sum;
	}

	private static void analyzeBasins() {
		// populate basin map with just peaks (-1)
		// remaining locations are 0
		for (int i=0; i<mapHeight; i++) {
			for (int j=0; j<mapWidth; j++) {
				if (heightMap[i].charAt(j) == '9') {
					basinMap[i][j] = -1;
				}
			}
		}
		int basinNumber = 1;
		// when we find a 0 flood fill the basin with basinNumber
		// and calculate size size o basin
		for (int i=0; i<heightCount.length; i++) {
			for (int j=0; j<heightCount[i].length; j++) {
				if (basinMap[i][j] == 0) {
					basinSizes.put(basinNumber,fillBasin(i,j,basinNumber));
					basinNumber++;
				}
			}
		}
	}
	
	private static Integer fillBasin(int i, int j, int basinNumber) {
		// i = x = row, j = y = col with 0,0 at top left
		int size = 0;
		Set<Point> s = new HashSet<>();  // set is simple way to keep from processing same point twice
		Point p; 
		s.add(new Point(i,j)); // add starting point to basin
		// fill the basin
		while (!s.isEmpty()) {
			p = s.iterator().next();  // get a point in the basin
			// check neighboring points and add if they belong to basin
			if (p.x > 0 && basinMap[p.x-1][p.y] == 0) {
				s.add(new Point(p.x-1,p.y));
			}
			if (p.x < mapHeight-1 && basinMap[p.x+1][p.y] == 0) {
				s.add(new Point(p.x+1,p.y));
			}
			if (p.y > 0 && basinMap[p.x][p.y-1] == 0) {
				s.add(new Point(p.x,p.y-1));
			}
			if (p.y < mapWidth-1 && basinMap[p.x][p.y+1] == 0) {
				s.add(new Point(p.x,p.y+1));
			}
			// mark as added to basin
			basinMap[p.x][p.y] = basinNumber;
			size ++;
			s.remove(p);
		}
		return size;
	}

	private static void analyzeLowSpots() {
		// for each cell count the neighbors it is equal or taller to
		for (int i=0; i<mapHeight; i++) {
			for (int j=0; j<heightMap[i].length(); j++) {
				heightCount[i][j] += (j>0 && heightMap[i].charAt(j-1) <= heightMap[i].charAt(j)) ? 1 : 0;
				heightCount[i][j] += (j<mapWidth-1 && heightMap[i].charAt(j+1) <= heightMap[i].charAt(j)) ? 1 : 0;
				heightCount[i][j] += (i>0 && heightMap[i-1].charAt(j) <= heightMap[i].charAt(j)) ? 1 : 0;
				heightCount[i][j] += (i<mapHeight-1 && heightMap[i+1].charAt(j) <= heightMap[i].charAt(j)) ? 1 : 0;				
			}
		}
	}
	
	private static void initialize() {
		try {
			heightMap = (new String(Files.readAllBytes(Paths.get(DAY9_INPUT_TXT)))).split("\\s+");
			mapHeight = heightMap.length;
			mapWidth = heightMap[0].length();
			heightCount = new int[mapHeight][mapWidth];
			basinMap = new int[mapHeight][mapWidth];
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void printBasinMap(int x, int y, int z) {
		Set<Integer> topThree = basinSizes.entrySet().stream()
				.filter(b -> b.getValue() == x || b.getValue() == y || b.getValue() == z)
				.map(b -> b.getKey())
				.collect(Collectors.toSet());
		System.out.println();
		for (int i=0; i<mapHeight; i++) {
			for (int j=0; j<mapWidth; j++) {
				if (topThree.contains(basinMap[i][j])) System.out.print("#");
				else if (basinMap[i][j] == -1) System.out.print("@");
				else System.out.print(".");
			}
			System.out.println();
		}
		System.out.println();
	}
}
