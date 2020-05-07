package front;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickListener extends MouseAdapter {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("x : " + e.getX() +", y : "+ e.getY());
	}

}
