package front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import obj.Coord;
import obj.Plan;

public class Results {
	
	protected static Boolean isEnd = false;
	public static void printResultsOnMap(Graphics g) {
		
		List<Integer> fullPath = CreateInterface.fullPath;
		List<String> pathNames = new ArrayList<String>();
		Plan country = CreateInterface.regionInfo.get(12);
		Map<Integer, Coord<?, ?>> cityCoordMap  = country.getCityCoordMap();
		int xStart;
		int yStart;
		int xEnd;
		int yEnd;
		int i=0;

		for( i=0; i< fullPath.size()-1; i++) {
			
			int startedTown = fullPath.get(i);
			int endingTown = fullPath.get(i+1);
				xStart = (int) cityCoordMap.get(startedTown).getX();
				yStart = (int) cityCoordMap.get(startedTown).getY();
				
				xEnd = (int) cityCoordMap.get(endingTown).getX();
				yEnd = (int) cityCoordMap.get(endingTown).getY();
				g.setColor(Color.BLUE);
				g.drawLine(xStart+(PaintInterface.width/2), yStart+(PaintInterface.width/2), xEnd+(PaintInterface.width/2), yEnd+(PaintInterface.width/2));
				pathNames.add( (String) CreateInterface.namesMap.get(fullPath.get(i))[0]);
		
		}
		pathNames.add( (String) CreateInterface.namesMap.get(fullPath.get(i))[0]);
		printResultsAsText(g,pathNames);
		
		
		if(isEnd)CreateInterface.jc.repaint(); //When we already show the results we stop the processus
	}
	
	/**
	 * add the name of towns in the path to the scrollable Jpanel
	 * @param g
	 * @param pathNames
	 */
public static void printResultsAsText(Graphics g,List<String> pathNames) {

	JTextArea townName=new JTextArea();
	String names = "";
		for(String name : pathNames) {
			names=names+" -> "+name;
		}
		
	Plan country = CreateInterface.regionInfo.get(12);
	townName.setOpaque(false);
	townName.setBounds(0, 0,(int) (country.getDimension()[0]*0.70), 100);
	townName.setText(names);
	townName.setLineWrap(true);
	townName.setWrapStyleWord(true);
	townName.setEditable(false);
	townName.setFont(new Font("Serif",Font.PLAIN,19));
	CreateInterface.showResults.add(townName);
	
	}

}
