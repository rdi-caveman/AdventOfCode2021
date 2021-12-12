package advent_code_2021;

import java.util.TreeSet;
import java.util.Iterator;
import java.util.Set;

public class Cave implements Comparable<Cave> {
	String name;
	Set<String> neighbors = new TreeSet<>();
	
	public Cave(String name) {
		this.name = name;
	}
	
	public void addNeighbor(String cave) {
		neighbors.add(cave);
	}
	
	public Set<String> getNeighbors() {
		return neighbors;
	}
	
	public boolean isSmall() {
		return name.toLowerCase().equals(name);
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
		Cave other = (Cave) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Cave arg0) {
		return name.compareTo(arg0.name);
	}
	
	@Override
	public String toString() {
		return String.format("%s : %s", name, neighbors);
	}

}
