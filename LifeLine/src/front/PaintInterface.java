package front;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import obj.Coord;
import obj.TownInterface;

public class PaintInterface extends JPanel{
	
	Map<Integer, Coord<?, ?>> cityCoordMap ;
	Map<Integer, Object[]> namesXRegions ;
	Map<Integer, Object[]> regions ;

	private static final long serialVersionUID = 1L;
	
	private List<int[]> towns = new LinkedList<>();
	public static int width = 10;


	@Override
	public void paint(Graphics g) {
			
		super.paint(g);
		Color c = g.getColor();
		g.setColor(c);
		
		
			super.paint(g);
	
			int x;
			int y;
			int region;
			
			for(int number:cityCoordMap.keySet()) {
				
				x=(int) cityCoordMap.get(number).getX();
				y=(int) cityCoordMap.get(number).getY();
				
				
				region = (int) namesXRegions.get(number)[1];
				Color color = PrintPoints.chooseColor(region);
				
				g.setColor(color);
				g.fillRect(x, y, width, width);	
			}
			for(int[] town : towns) {
				g.setColor(Color.red);
				g.fillRect(town[0], town[1], width, width);
			}
	}
	
	
	public void addTown(int key) {
		int x=(int) cityCoordMap.get(key).getX();
		int y=(int) cityCoordMap.get(key).getY();
		int[] coords = {x, y};
		towns.add(coords);
		this.repaint();
	}


}
