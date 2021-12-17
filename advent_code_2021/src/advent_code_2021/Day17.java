package advent_code_2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day17 {
	private static final String input = "target area: x=135..155, y=-102..-78";
	private static final Pattern numPattern = Pattern.compile("(-?\\d+)");
	private static final Matcher numMatcher = numPattern.matcher("");

	// for successful xPath, initial x velocity and time to finish
	private static List<Pair<Integer, Integer>> xPaths = new ArrayList<>();
	// for successful yPath, initial y velocity, time to finish and max height)
	private static List<Triple<Integer, Integer, Integer>> yPaths = new ArrayList<>();
 	// minimum length of xPath that is stable (ends at velocity 0 in target area)
	private static int minStableXPath = Integer.MAX_VALUE;
	
	
	public static void main(String[] args) {
		// read input
		int[] area = new int[4]; // xMin, xMax, yMin, yMax
		numMatcher.reset(input);
		int i=0;
		while(numMatcher.find()) {
			area[i++] = Integer.parseInt(numMatcher.group(1));
		}
		
		// find all x and y paths
		calculatePaths(area);
				
		// Part 1 - find max Height
		System.out.println("Day 17 part 1 " + getMaxHeight());
		
		// Part 2 - find total starting vectors
		System.out.println("Day 17 part 2 " + getInitialTrajectoryCount());
	}

	private static int getMaxHeight() {
		// find durations of x paths
		Set<Integer> validDuration = xPaths.stream().map(p -> p.second)
				.collect(Collectors.toSet());
		// find max height of Y Paths with valid duration
		int maxHeight = yPaths.stream()
				.filter(t -> validDuration.contains(t.second))
				.mapToInt(t -> ((Integer)t.third).intValue())
				.max()
				.getAsInt();
		return maxHeight;
	}

	private static int getInitialTrajectoryCount() {
		Set<Pair<Integer, Integer>> initialTrajectory = new HashSet<>();
		xPaths.stream()
				.forEach(p ->{
					yPaths.stream()
						.filter(t -> t.second.equals(p.second))
						.forEach( t -> initialTrajectory.add(new Pair(p.first,t.first)));
				});
		return initialTrajectory.size();
	}
	
	private static void calculatePaths(int[] area) {
		findYPaths(area);
		System.out.println(yPaths);
		int maxDuration = yPaths.stream().mapToInt(t -> t.second.intValue()).max().getAsInt();
		findXPaths(area, maxDuration);
		System.out.println(xPaths);
	}

	private static void findXPaths(int[] area, int maxDuration) {
		for (int init_v=1; init_v<=area[1]; init_v++) {
			int x=0;
			int i=0;
			int v=init_v;
			while(x <= area[1] && i<maxDuration) {
				i++;
				x += v;
				v = Math.max(0, v-1);				
				if (area[0] <= x && x <= area[1]) {
					xPaths.add(new Pair(init_v, i));
				}
			}
		}
	}
	
	private static void findYPaths(int[] area) {
		for(int init_v=area[2]; init_v < -area[2]; init_v++) {
			int y=0;
			int i=0;
			int v=init_v;
			int maxY = 0;
			while(y>area[2]) {
				i++;
				y += v;
				v--;
				maxY = Math.max(maxY, y);
				if (area[2] <= y && y <= area[3]) {
					yPaths.add(new Triple(init_v, i, maxY));
				}
			}
		}
	}

}
