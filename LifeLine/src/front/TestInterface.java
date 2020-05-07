package front;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

import obj.Coord;

public class TestInterface extends JPanel{
	
	public Map<Integer, Coord<?, ?>> cityCoordMap ;

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
			
		super.paint(g);
		Color c = g.getColor();
		g.setColor(c);
		
		int x;
		int y;
		
		for(int number:cityCoordMap.keySet()) {
			
			x=(int) cityCoordMap.get(number).getX();
			y=(int) cityCoordMap.get(number).getY();
			System.out.println("x = "+ x);
			
			g.fillOval(x,y,40,40);
			
			
		}
		
		
		
	}
	
	


}
