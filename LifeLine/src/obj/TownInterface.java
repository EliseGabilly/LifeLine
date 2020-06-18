package obj;

public class TownInterface<T,A> {

	
	private final float x1;
	private final float y1;
	
	private final float x2;
	private final float y2;

	public TownInterface(int x1, int y1,int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public float getX1() {
		return x1;
	}
	public float getY1() {
		return y1;
	} 
	public float getX2() {
		return x2;
	}
	public float getY2() {
		return y2;
	} 
	
	
	

}
