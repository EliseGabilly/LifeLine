package front;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import obj.Plan;

public class CreatFrame {
	
	/**
	 * Create and show the frame
	 * Add the on close event : stop program or go bakc to the entire Rwanda maps parameters
	 * @param component
	 * @param frameName
	 * @param isSmallMap
	 * @param plan
	 */
	public static void showOnFrame(JComponent component, String frameName, Boolean isSmallMap, Plan plan) {
		JFrame frame = new JFrame(frameName);
		WindowAdapter wa;
		
			if(!isSmallMap) {
				 wa = new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
					
				};
				
				
			}else {
				wa = new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						PaintInterface.idRegion=12;
						ClickListener.currentMap = CreateInterface.regionInfo.get(12);
					}
				};
			}
		
		
		
		
		frame.addWindowListener(wa);
		frame.getContentPane().add(component);
		frame.getContentPane().addMouseListener(new ClickListener((PaintInterface) component,plan));
		frame.pack();
		frame.setVisible(true);
	}


}
