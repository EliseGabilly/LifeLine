package front;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JTextArea;

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
		Boolean toBeAdd = getNode(e.getX(),e.getY(), this.panel);
		
		if(toBeAdd && !CreateInterface.isResults) {
			panel.addTown(key, currentMap);
		}	
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		getNode(e.getX(),e.getY(), this.panel);
		
	}
	
	private static void removeLabel(PaintInterface panel) {
	Component[] components = panel.getComponents();
	
	for(Component component: components) {
		String componentName = component.getName();
		
		if(componentName!=null) {
			if(componentName.equals("labelName")) {
				panel.remove(component);
			}
		}
		
	}
	

}
	
		private static void showName(Boolean onTown, int key, int x, int y,PaintInterface panel) {
	
	removeLabel(panel);
	JTextArea labelName=new JTextArea();
	labelName.setOpaque(true);
	labelName.setEditable(false);
	labelName.setName("labelName");
	
	labelName.setFont(new Font("Serif",Font.PLAIN,25));
	if(currentMap.getId()==12) {
		labelName.setBounds(10, 100, 200,40);	
	}
	else {
		labelName.setBounds((int) (currentMap.getDimension()[0]/2.5), currentMap.getDimension()[1]-80, 200,40);
		
	}
	
	
	//labelName.setBounds(x+10, y+10, 200,30);
	if(onTown) {
		String name = (String) CreateInterface.namesMap.get(key)[0];
		labelName.setText(name);
		panel.add(labelName);
		panel.repaint();
	}
	
	
	
	
}
	
	
	/**
	 * Check if the click is inside a square
	 * @param x
	 * @param y
	 * @return
	 */
	private static Boolean getNode(int x, int y,PaintInterface panel) {
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
			showName(inside, key,x,y, panel);
			
			
		}
		return inside;
		
	}
}