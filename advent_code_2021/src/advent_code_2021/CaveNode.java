package advent_code_2021;

import java.util.ArrayList;
import java.util.List;

public class CaveNode {
	String name; // path e.g., start-aa-BC
	String nodeName; // current node, e.g., BC
	String parent = ""; // parent name e.g., start-aa
	int numSmallCavesRevisited;
	List<String> children = new ArrayList<>();
	
	public CaveNode(String name, String node, String parent, int numSmallCavesRevisited) {
		this.name = name;
		this.nodeName = node;
		this.parent = node;
		this.numSmallCavesRevisited = numSmallCavesRevisited;
	}
	
	public void addChild(String child) {
		children.add(child);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CaveNode other = (CaveNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
