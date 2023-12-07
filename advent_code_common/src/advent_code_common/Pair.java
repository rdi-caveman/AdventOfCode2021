package advent_code_common;

import java.util.Objects;

public class Pair<T1, T2> {
	public T1 first;
	public T2 second;
	
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return (obj instanceof Pair && ((Pair)obj).first.equals(first) && ((Pair)obj).second.equals(second));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.toString());
	}
	
	@Override
	public String toString() {
		return first.toString() + "," + second.toString();
	}
}
