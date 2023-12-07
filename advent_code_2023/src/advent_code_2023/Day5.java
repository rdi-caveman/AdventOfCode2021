package advent_code_2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import advent_code_common.FileUtility;

public class Day5 {
	private static final String DAY5_INPUT = "./src/resources/day5_input.txt";
	private static final String DAY5_TEST = "./src/resources/day5_test.txt";

	public static void main(String[] args) {
		// read almanac
		List<Long> seeds = null;
		Map<String,List<RangeMap>> ranges = new HashMap<>();
		List<String> almanac = FileUtility.readListOfString(DAY5_INPUT);
		int lineNum=0;
		String inputType = null;
		while (lineNum < almanac.size()) {
			if (almanac.get(lineNum).contains(":")) {
				inputType = almanac.get(lineNum).split(":\s*")[0];
				if (inputType.equals("seeds")) {
					// get seeds
					String[] numbers = almanac.get(lineNum).split(": ")[1].split(" ");
					seeds = Arrays.asList(numbers).stream()
							.map(s -> Long.valueOf(s))
							.toList();
					lineNum++;
					continue;		
				}
				// new input type
				ranges.put(inputType, new ArrayList<>());
				lineNum++;
			}
			if (!almanac.get(lineNum).isBlank()) {
				// process ranges
				String[] numbers = almanac.get(lineNum).split(" ");
				ranges.get(inputType).add(new RangeMap(Long.valueOf(numbers[0]),Long.valueOf(numbers[1]),Long.valueOf(numbers[2])));
			}
			lineNum++;
		}
		// part 1
		long minLocation = seeds.stream()
			.map(i -> mapUsingRange(i, ranges.get("seed-to-soil map")))
			.map(i -> mapUsingRange(i, ranges.get("soil-to-fertilizer map")))
			.map(i -> mapUsingRange(i, ranges.get("fertilizer-to-water map")))
			.map(i -> mapUsingRange(i, ranges.get("water-to-light map")))
			.map(i -> mapUsingRange(i, ranges.get("light-to-temperature map")))
			.map(i -> mapUsingRange(i, ranges.get("temperature-to-humidity map")))
			.map(i -> mapUsingRange(i, ranges.get("humidity-to-location map")))
			.min((i,j) -> i.compareTo(j))
			.orElse(0L);
		System.out.println("part 1: " + minLocation);
		// part 2
		List<Range> range = new ArrayList<>();
		for (int x=0; x<seeds.size(); x+=2) {
			range.add(new Range(seeds.get(x), seeds.get(x) + seeds.get(x+1) -1));
		}

		range = remap(range, ranges.get("seed-to-soil map"));
		range = remap(range, ranges.get("soil-to-fertilizer map"));
		range = remap(range, ranges.get("fertilizer-to-water map"));
		range = remap(range, ranges.get("water-to-light map"));
		range = remap(range, ranges.get("light-to-temperature map"));
		range = remap(range, ranges.get("temperature-to-humidity map"));
		range = remap(range, ranges.get("humidity-to-location map"));
		minLocation = range.stream()
			.map(r -> r.min)
			.min((i,j) -> i.compareTo(j))
			.orElse(0L);
		System.out.println("part 2: " + minLocation);	
	}
	
	private static List<Range> remap(List<Range> in, List<RangeMap> maps) {
		List<Range> out = new ArrayList<>();
		return applyMaps(new RangePair(in,out), maps);
	}
	
	private static List<Range> applyMaps(RangePair in, List<RangeMap> maps) {
		RangePair rp = in;
		for (RangeMap map : maps) {
			rp = applyMap(rp, map);
		}
		rp.out.addAll(rp.in);
		return rp.out;
	}
	
	private static RangePair applyMap(RangePair source, RangeMap map) {
		List<Range> in = new ArrayList<>();
		List<Range> out = source.out;
		for (Range r : source.in) {
			if (r.max < map.in || r.min >+ map.in + map.range ) {
				//no overlap with range
				in.add(new Range(r.min, r.max));
				continue;
			}
			long min = r.min;
			long max = r.max;
			if (min < map.in) { 
				in.add(new Range(min, map.in-1));
				min = map.in();
			}
			if (max >= map.in + map.range) {
				in.add(new Range( map.in + map.range, max));
				max = map.in + map.range -1;
			}
			long offset = map.out - map.in;
			out.add(new Range(min + offset, max + offset));
		}
		return new RangePair(in,out);
	}
	
    private static long mapUsingRange(final Long i, final List<RangeMap> rangeMap) {
    	return rangeMap.stream()
    		.filter(m -> m.in <= i && i < m.in + m.range)
    		.mapToLong(m -> i + (m.out-m.in) )
    		.findFirst()
    		.orElse(i);
    }
	
	record RangeMap(long out, long in, long range) {}
	record Range(long min, long max) {}
	record RangePair(List<Range> in, List<Range> out) {}

}
