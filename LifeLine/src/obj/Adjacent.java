package obj;

public class Adjacent<T, A> {

	private final int city;
	private final float weight;

	public Adjacent(int city, float weight2) {
		this.city = city;
		this.weight = weight2;
	}

	public int getCity() {
		return city;
	}
	public float getWeight() {
		return weight;
	} 
	
	public String toString() {
		return city+", "+weight;
	}

	public boolean equals(Adjacent<?, ?> o) {
			return this.city == o.getCity() && this.weight == o.getWeight();
	}

}