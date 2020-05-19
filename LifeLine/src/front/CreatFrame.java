package front;


import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import obj.Plan;

public class CreatFrame {
	protected static JFrame  frame;
	protected static ClickListener mouseListener;
	
	/**
	 * Create and show the frame
	 * Add the on close event : stop program or go bakc to the entire Rwanda maps parameters
	 * @param component
	 * @param frameName
	 * @param isSmallMap
	 * @param plan
	 */
	protected static void showOnFrame(JComponent component, String frameName, Boolean isSmallMap, Plan plan) {
		  frame = new JFrame(frameName);
		WindowAdapter wa;
			
		frame.setResizable(false);
		
		
		component.revalidate();
		
			if(!isSmallMap) {
				 frame.setPreferredSize(new Dimension(CreateInterface.dimensionCountry[0]+CreateInterface.dimensionCountry[0]/8, CreateInterface.dimensionCountry[1]+100));
					
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
						CreateInterface.ableBtn=true;
						CreateInterface.jc.repaint();
					}
				};
			}
			
		mouseListener = new ClickListener((PaintInterface) component, plan);
		frame.addWindowListener(wa);
		frame.getContentPane().add(component);
		frame.getContentPane().addMouseListener(mouseListener);
		frame.getContentPane().addMouseMotionListener(mouseListener);
		frame.pack();
		frame.setVisible(true);
	}



}
