package front;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import obj.Plan;
import obj.TownInterface;

public class ClickListener extends MouseAdapter {
	
	private PaintInterface panel;
	private static int key;
	protected static Plan currentMap; 
	
	public ClickListener(PaintInterface panel, Plan plan) {
		super();
		this.panel = panel;
		ClickListener.currentMap =plan;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Boolean toBeAdd = getNode(e.getX(),e.getY());
		
		if(toBeAdd && !CreateInterface.isResults) {
			panel.addTown(key, currentMap);
		}	
	}
	
	
	/**
	 * Check if the click is inside a square
	 * @param x
	 * @param y
	 * @return
	 */
	private static Boolean getNode(int x, int y) {
		Map<Integer, TownInterface<?, ?>> rectCoordMap = currentMap.getRectCoordMap();
		
		int currentX1;
		int currentX2;
		int currentY1;
		int currentY2;
		Boolean inside = false;
		
		for(int number : rectCoordMap.keySet()) {
			currentX1 = (int) rectCoordMap.get(number).getX1();
			currentX2 = (int) rectCoordMap.get(number).getX2();
			
			currentY1 = (int) rectCoordMap.get(number).getY1();
			currentY2 = (int) rectCoordMap.get(number).getY2();
			if((currentX2>x)&&(currentX1<x)&&(currentY2>y)&&(currentY1<y)) {
				key = number;
				inside = true;		
			}	
			
		}
		return inside;
		
	}

}
