package advent_code_2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Scanner {
	int id;
	int[] location = new int[] {0,0,0};
	int[] orientation = new int[] {0,0,0}; // 0-3 rotations around x axis, 0-3 rotations around y axis, 0-3 rotations around x axis;
	List<int[]> beacons = new ArrayList<>();
	
	public Scanner(int id) {
		this.id = id;
	}

	public static Scanner parse(String r) {
		String[] input = r.split("\\r?\\n");
		int id = Integer.parseInt(input[0].split("\\s")[2]);
		Scanner scanner = new Scanner(id);
		for (int i=1; i<input.length; i++) {
			String[] values = input[i].split(",");
			scanner.beacons.add(new int[] {new Integer(values[0]), new Integer(values[1]), new Integer(values[2])});
		}
		return scanner;
	}
	
	public Triple<Boolean, int[], int[]> compareBeacons(Scanner s) {
		Triple<Boolean, int[], int[]> result = null;
		int[] orientation;
		loop:
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=0; k<4; k++) {
					if (j==0 && k>0) continue;
					orientation = new int[] {i,j,k};
					result = compareBeacons(s, orientation);
					if (result.first) {
						break loop;
					}
				}
			}
		}
		return result;
	}
	
	public Triple<Boolean, int[], int[]> compareBeacons(Scanner s, int[] orientation) {
		int[] distance = new int[3];
		Map<String, Integer> overlap = new HashMap<>();
		for(int[] a : this.beacons) {
			a = rotateBeacon(a, this.orientation);
			a = add(a, this.location);
			for (int[] b : s.beacons) {
				b = rotateBeacon(b, orientation);
				distance = subtract(a,b);
				overlap.compute(String.format("%d,%d,%d",distance[0],distance[1],distance[2]), (k,v) -> (v==null ? 0 : v) + 1);
			}
		}
		// do we have an overlap of 12+
		Optional<Object> dist = overlap.entrySet().stream()
			.filter(e -> e.getValue() >= 12)
			.findFirst()
			.map(e -> e.getKey());
		if (dist.isPresent()) {
			String[] d = ((String)dist.get()).split(",");
			int[] newLocation = new int[] {Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2])};
			//newLocation = subtract(newLocation, location);
			return new Triple(true, newLocation, orientation);
		}
		else {
			return new Triple(false, new int[]{0,0,0}, orientation);
		}
	}
	
	
	public String toString() {
		return String.format("Scanner %d: location %s, orientation %s, beacons %s", id, Arrays.toString(location), Arrays.toString(orientation), beaconsToString());
	}

	private Object beaconsToString() {
		return beacons.stream()
			.map(a -> Arrays.toString(a))
			.collect(Collectors.toList())
			.toString();
	}
	
	public static int[] rotateBeacon(int[] pos, int[] rot) {
		pos = rotateX(pos, rot[0]);
		pos = rotateY(pos, rot[1]);
		pos = rotateX(pos, rot[2]);
		return pos;
	}


	private static int[] rotateX(int[] pos, int num) {
		switch(num % 4) {
		case 0: return pos;
		case 1: return new int[] {pos[1], -pos[0], pos[2]};
		case 2: return new int[] {-pos[0], -pos[1], pos[2]};
		case 3: return new int[] {-pos[1], pos[0], pos[2]};
		}
		return pos;
	}
	
	private static int[] rotateY(int[] pos, int num) {
		switch(num % 4) {
		case 0: return pos;
		case 1: return new int[] {pos[0], pos[2], -pos[1]};
		case 2: return new int[] {pos[0], -pos[1], -pos[2]};
		case 3: return new int[] {pos[0], -pos[2], pos[1]};
		}
		return pos;
	}
	public static int[] add(int[] a, int[] b) {
		int[] c = new int[3];
		for (int i=0; i<3; i++) c[i]=a[i]+b[i];
		return c;
	}

	public static int[] subtract(int[] a, int[] b) {
		int[] c = new int[3];
		for (int i=0; i<3; i++) c[i]=a[i]-b[i];
		return c;
	}

}
