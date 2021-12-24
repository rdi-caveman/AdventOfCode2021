package advent_code_2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Given a list of 3d cubes that are lit or not lit
 * apply them in order
 * determine number of points lit
*/


public class Day22 {
	private static final String DAY22_INPUT_TXT = "src/resources/day22_input.txt";
	
	
	public static void main(String[] args) {
		// part 1
		List<Cube> instructions = parseInputFile(DAY22_INPUT_TXT, true);
		long litCubes = calculateSum(instructions);
		System.out.println("Day 22 part 1 " + litCubes);

		// part 2
		instructions = parseInputFile(DAY22_INPUT_TXT, false);
		litCubes = calculateSum(instructions);
		System.out.println("Day 22 part 2 " + litCubes);
		
		
	}

	private static long calculateSum(List<Cube> instructions) {
		// intersections
		Map<Cube, Long> cubes = new HashMap<>() ;
		for (Cube i : instructions) {
			applyInstruction(i, cubes);
		}	
		return sumVolumes(cubes); 
	}
	
	private static long sumVolumes(Map<Cube, Long> cubes) {
		return cubes.entrySet().stream().mapToLong(e -> e.getKey().getVolume() * e.getValue()).sum();
	}
	
	private static void applyInstruction(Cube instruction, Map<Cube, Long> cubes) {
		List<Pair<Cube, Long>> offsets = new ArrayList<>();
		for(Cube c : cubes.keySet()) {
			Cube sharedVol = instruction.intersection(c);
			if (sharedVol != null) {
				offsets.add(new Pair<Cube,Long>(sharedVol, -cubes.get(c)));
			}	
		}	
		if (instruction.on) {
			cubes.compute(instruction, (k,v) -> (v==null ? 0 : v)+1);  // add new lit volume
		}
		for (Pair<Cube, Long> offset : offsets) {
			cubes.compute(offset.first, (k,v) -> (v==null ? 0 : v) + offset.second);  // add offsets to cube list
		}		
	}

	private static List<Cube> parseInputFile(String filename, boolean initialize) {
		List<Cube> cubes = FileUtility.readListOfString(filename).stream() 
			.map(s -> Cube.parse(s))
			.filter(c -> !initialize || Math.abs(c.coords[0][0]) <=50)
			.filter(c -> !initialize || Math.abs(c.coords[0][1]) <=50)
			.filter(c -> !initialize || Math.abs(c.coords[1][0]) <=50)
			.filter(c -> !initialize || Math.abs(c.coords[1][1]) <=50)
			.filter(c -> !initialize || Math.abs(c.coords[2][0]) <=50)
			.filter(c -> !initialize || Math.abs(c.coords[2][1]) <=50)
			.collect(Collectors.toList());
		return cubes;
	}
	

}
