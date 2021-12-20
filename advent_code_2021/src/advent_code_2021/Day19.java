package advent_code_2021;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class Day19 {
	private static final String DAY19_INPUT_TXT = "src/resources/day19_input.txt";
	private static final String RECORD_SEPARATOR = "\\r?\\n\\r?\\n";
	
	public static void main(String[] args) {
		// read in list of scanners
		List<Scanner> scanners = readScanners(DAY19_INPUT_TXT);
		
		// determine relative locations
		List<Scanner> located = scanners.subList(0, 1);
		System.out.println("located " + located.get(0));
		Deque<Scanner> unlocated = new ArrayDeque();
		unlocated.addAll(scanners.subList(1,scanners.size()));
		Scanner s;
		while (!unlocated.isEmpty()) { 
			s = unlocated.removeFirst();
			boolean found = false;
			for(Scanner reference : located) {
				Triple<Boolean, int[], int[]> result = reference.compareBeacons(s);
				if (result.first) {
					found = true;
					s.location = result.second;
					s.orientation = result.third;
					located.add(s);
					System.out.println("located " + s);
					break;
				}
			}
			if (!found) {
				unlocated.addLast(s);
			}
		}
		
		// compile complete list of beacons
		Map<String, int[]> allBeacons = new HashMap<>();
		scanners.stream()
			.forEach(sc ->{
				sc.beacons.stream()
					.map(b -> Scanner.rotateBeacon(b, sc.orientation))
					.map(b -> Scanner.add(b, sc.location))
					.forEach(b -> allBeacons.put(Arrays.toString(b), b));
			});
		System.out.println(allBeacons.keySet());
		System.out.println("Day 19 part 1 " + allBeacons.size());
		
		// find max manhattan distance BETWEEN SCANNERS ( NOT BEACONS )
		int maxManhattanDistance = 0;
		for (int i=0; i<scanners.size()-1; i++) {
			for (int j=i+1; j<scanners.size(); j++) {
				maxManhattanDistance = Math.max(maxManhattanDistance, manhattanDistance(scanners.get(i).location, scanners.get(j).location));
			}
		}
		System.out.println("Day 19 part 2 " + maxManhattanDistance);
	}

	private static List<Scanner> readScanners(String filename) {
		List<Scanner> scanners = null;
		List<String> records = FileUtility.readRecords(filename,RECORD_SEPARATOR);
		scanners = records.stream()
				.map(r -> Scanner.parse(r))
				.collect(Collectors.toList());		
		return scanners;
	}
	
	private static int manhattanDistance(int[] a, int[] b) {
		return Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]) + Math.abs(a[2]-b[2]);
	}
	

	
}
