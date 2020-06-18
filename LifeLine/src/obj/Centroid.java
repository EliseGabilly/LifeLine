package obj;

public class Centroid {

	private float X;
	private float Y;

	public Centroid( float X, float Y) {
		this.X = X;
		this.Y = Y;
	}

	public void setX(float nX) {
		this.X = nX;
	}

	public void setY(float nY) {
		this.Y = nY;
	}

	public float getX() {
		return X;
	}

	public float getY() {
		return Y;
	}
	
	public Coord<?,?> getCoords(){
		return new Coord(this.X,this.Y);
	}

}
