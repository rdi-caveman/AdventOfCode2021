package advent_code_2021;

//	  0:      1:      2:      3:      4:
//	 aaaa    ....    aaaa    aaaa    ....
//	b    c  .    c  .    c  .    c  b    c
//	b    c  .    c  .    c  .    c  b    c
//	 ....    ....    dddd    dddd    dddd
//	e    f  .    f  e    .  .    f  .    f
//	e    f  .    f  e    .  .    f  .    f
//	 gggg    ....    gggg    gggg    ....
//	
//	  5:      6:      7:      8:      9:
//	 aaaa    aaaa    aaaa    aaaa    aaaa
//	b    .  b    .  .    c  b    c  b    c
//	b    .  b    .  .    c  b    c  b    c
//	 dddd    dddd    ....    dddd    dddd
//	.    f  e    f  .    f  e    f  .    f
//	.    f  e    f  .    f  e    f  .    f
//	 gggg    gggg    ....    gggg    gggg

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day8 {
	private static final String DAY8_INPUT_TXT = "src/resources/day8_input.txt";
	private static List<String[]> inputs = new ArrayList<>();
	private static List<String[]> outputs = new ArrayList<>();
	
	
	public static void main(String[] args) {
		initialize();
		part1();	
		part2();
			
			
	}

	private static void part1() {
		long count = outputs.stream()
			.flatMap(a -> Arrays.stream(a))
			.mapToInt(s -> s.length())
			.filter(i -> i == 2 || i == 3 || i == 4 || i == 7)
			.count();
		System.out.println("Day 8 part 1 " + count);
	}
	
	private static void part2() { 
		int sum = IntStream.range(0, inputs.size())
				.map(i -> decode(inputs.get(i), outputs.get(i)))
				.sum();
		System.out.println("Day 8 part 2 " + sum);
	}	

	private static int decode(String[] input, String[] output) {
		Map<String, String> segmentMap = getSegmentMapping(input);
		int value = 0;
		for (int i=0; i< output.length; i++) {
			String segments = Arrays.stream(output[i].split(""))
				.map(s -> segmentMap.get(s))
				.sorted()
				.collect(Collectors.joining(""));
			value = value * 10 + LED.getBySegments(segments).value();
		}
		return value;
	}

	/*
	 * 	frequency counts of segments can identify segments b, e, f
	 *  a:8 b:6 c:8 d:7 e:4 f:9 g:7
	 *  segment c is in one (length 2) (not mapped already)
	 *  segment a is in seven (length 3) (not mapped already)
	 *  segment d is in four (length 4)(not mapped already)
	 *  segment g is in eight (length 7) (not mapped already)
	 */
	
	private static Map<String, String> getSegmentMapping(String[] input) {
		Map<String, Integer> frequencyMap = new HashMap<>(); 
		Map<String, String> segmentMapping = new HashMap<>(); 
		Arrays.stream(input)
			.flatMap(s -> Arrays.stream(s.split("")))
			.forEach(s -> frequencyMap.put(s, frequencyMap.getOrDefault(s,0)+1));
		for (String s : frequencyMap.keySet()) {
			if (frequencyMap.get(s).equals(6)) {
				segmentMapping.put(s, "b");
			}
			else if (frequencyMap.get(s).equals(4)) {
				segmentMapping.put(s, "e");
			}
			else if (frequencyMap.get(s).equals(9)) {
				segmentMapping.put(s, "f");
			}
		}
		findUnmappedSegment(input, segmentMapping, frequencyMap, LED.ONE.segments().length(), "c");
		findUnmappedSegment(input, segmentMapping, frequencyMap, LED.SEVEN.segments().length(), "a");
		findUnmappedSegment(input, segmentMapping, frequencyMap, LED.FOUR.segments().length(), "d");
		findUnmappedSegment(input, segmentMapping, frequencyMap, LED.EIGHT.segments().length(), "g");
		return segmentMapping;
	}

	private static void findUnmappedSegment(String[] input, Map<String, String> segmentMapping,
			Map<String, Integer> frequencyMap, int length, String toSegment) {
		Optional<String> number = Arrays.stream(input)
				.filter(s -> s.length() == length )
				.findFirst();
		Optional<String> segment = Arrays.stream(number.get().split(""))
			.filter(s -> !segmentMapping.containsKey(s))
			.findFirst();
		segmentMapping.put(segment.get(), toSegment);
	}

	private static void initialize() {

		try (Stream<String> stream = Files.lines(Paths.get(DAY8_INPUT_TXT))) {
			inputs.clear();
			outputs.clear();
			stream.forEach(s ->{
				String[] input = s.split(" \\| ")[0].split(" ");
				String[] output = s.split(" \\| ")[1].split(" ");
				inputs.add(input);
				outputs.add(output);
			});
		} catch (IOException e) {
		  System.out.println(e.getMessage());
		}
	}

	public enum LED {
		ZERO("abcefg", 0),
		ONE("cf", 1),
		TWO("acdeg", 2),
		THREE("acdfg", 3),
		FOUR("bcdf", 4),
		FIVE("abdfg", 5),
		SIX("abdefg", 6),
		SEVEN("acf", 7),
		EIGHT("abcdefg", 8),
		NINE("abcdfg", 9);
		
		private static Map<String,LED> map = new HashMap<>();
		
		private final String segments;
		private final int value;
		private LED(String segments, int value) {
			this.segments = segments;
			this.value = value;
		}
		
		static {
			for (LED led : LED.values()) {
				map.put(led.segments, led);
			}
		}
			
		public String segments() {
			return segments;
		}
		
		public int value() {
			return value;
		}
		
		public static LED getBySegments(String s) {
			return map.get(s);
		}
	}
	
}
