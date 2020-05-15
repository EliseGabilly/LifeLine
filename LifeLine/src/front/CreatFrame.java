package front;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class CreatFrame {
	
	public static void showOnFrame(JComponent component, String frameName, Boolean isSmallMap) {
		JFrame frame = new JFrame(frameName);
		
			if(!isSmallMap) {
				WindowAdapter wa = new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
					
				};
				frame.addWindowListener(wa);
			}else {
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		
		
		
		
		
		
		frame.getContentPane().add(component);
		frame.getContentPane().addMouseListener(new ClickListener((PaintInterface) component));
		frame.pack();
		frame.setVisible(true);
	}


}
