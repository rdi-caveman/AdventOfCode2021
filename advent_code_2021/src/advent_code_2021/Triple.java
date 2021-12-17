package advent_code_2021;

import java.util.Objects;

public class Triple<T1, T2, T3> {
	T1 first;
	T2 second;
	T3 third;
	
	public Triple(T1 first, T2 second, T3 third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		return (obj instanceof Triple && ((Triple)obj).first.equals(first) && ((Triple)obj).second.equals(second) && ((Triple)obj).third.equals(third));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.toString());
	}
	
	@Override
	public String toString() {
		return first.toString() + "," + second.toString() + "," + third.toString();
	}
}
