package obj;

public class Coord<T, A> {

	private final float x;
	private final float y;

	public Coord(float xFloat, float yFloat) {
		this.x = xFloat;
		this.y = yFloat;
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	} 
	
	public String toString() {
		return x+", "+y;
	}

	public boolean equals(Coord<?, ?> o) {
			return this.x == o.getX() && this.y == o.getY();
	}

}