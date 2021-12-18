package advent_code_2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day12 {
	private static final String DAY12_INPUT_TXT = "src/resources/day12_input.txt";
	private static Map<String, Cave> caves = new HashMap<>();
	private static List<String> allRoutes = new ArrayList<>();
	
	public static void main(String[] args) {
		initialize();
		CaveNode start = new CaveNode("start","start","",0);
		addRoutes(start,0);
		System.out.println("Day 12 part 1 " + allRoutes.size());
		
		allRoutes.clear();
		addRoutes(start,1);
		System.out.println("Day 12 part 2 " + allRoutes.size());
	}
	
	public static void addRoutes(CaveNode node, int maxSmallCavesRevisited) {
		if (node.nodeName.equals("end")) {
			// we are at end of route
			System.out.println(node.name + " " + node.numSmallCavesRevisited);
			allRoutes.add(node.name);
			return;
		}
		// recursively add routes to children
		for (String neighbor : caves.get(node.nodeName).getNeighbors()) {
			if (neighbor.equals("start")) {
				// can't go back to start
				continue;
			}
			if (isSmall(neighbor) && node.name.contains(neighbor) && node.numSmallCavesRevisited >= maxSmallCavesRevisited) {
				// can't revisit this small cave
				continue;
			}
			node.addChild(neighbor);		
			CaveNode newNode = new CaveNode(node.name+"-"+neighbor, neighbor, node.name, isSmall(neighbor) && node.name.contains(neighbor) ? node.numSmallCavesRevisited + 1 : node.numSmallCavesRevisited);
			addRoutes(newNode, maxSmallCavesRevisited);
		}		
	}


	private static boolean isSmall(String caveName) {
		return caveName.toLowerCase().equals(caveName) && !caveName.equals("end");
	}
	
	public static void initialize() {
		FileUtility.readListOfString(DAY12_INPUT_TXT).stream()
			.map(s -> s.split("-"))
			.forEach(a -> {
				if (!caves.containsKey(a[0])) {
					caves.put(a[0], new Cave(a[0]));
				}
				if (!caves.containsKey(a[1])) {
					caves.put(a[1], new Cave(a[1]));
				}
				caves.get(a[0]).addNeighbor(a[1]);
				caves.get(a[1]).addNeighbor(a[0]);
			});

	}
}
