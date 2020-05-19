package front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import obj.Plan;

public class CreatFrame {
	protected static JFrame  frame;
	
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
				 frame.setPreferredSize(new Dimension(CreateInterface.dimensionCountry[0]+CreateInterface.dimensionCountry[0]/12, CreateInterface.dimensionCountry[1]+100));
					
				 wa = new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
