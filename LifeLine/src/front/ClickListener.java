package front;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import obj.Coord;
import obj.TownInterface;

public class ClickListener extends MouseAdapter {
	
	private PaintInterface panel;
	private static int key;
	
	public ClickListener(PaintInterface panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Boolean toBeAdd = getNode(e.getX(),e.getY());
		
		if(toBeAdd) {
			panel.addTown(key);
		}	
	}
	
	
	/**
	 * Check if the clikc is inside a square
	 * @param x
	 * @param y
	 * @return
	 */
	public static Boolean getNode(int x, int y) {
		
		int currentX1;
		int currentX2;
		int currentY1;
		int currentY2;
		Boolean inside = false;
		
		for(int number : PrintPoints.rectCoordMap.keySet()) {
			currentX1 = (int) PrintPoints.rectCoordMap.get(number).getX1();
			currentX2 = (int) PrintPoints.rectCoordMap.get(number).getX2();
			
			currentY1 = (int) PrintPoints.rectCoordMap.get(number).getY1();
			currentY2 = (int) PrintPoints.rectCoordMap.get(number).getY2();
			if((currentX2>x)&&(currentX1<x)&&(currentY2>y)&&(currentY1<y)) {
				key = number;
				inside = true;		
			}	
			
		}
		return inside;
		
	}

}
