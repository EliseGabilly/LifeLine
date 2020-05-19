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
		int x=(int) ( country.getDimension()[0]*0.22);
		int y=(int) ( country.getDimension()[1]-80);
		int j = 0;
		int i=0;
		
		JLabel labelForpath=new JLabel("Your Path : ", SwingConstants.CENTER); 
		labelForpath.setFont(new Font("Serif",Font.BOLD,19));
		labelForpath.setBounds(x-170, y, 120,40);
		CreateInterface.jc.add(labelForpath);
		
		
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
		CreateInterface.jc.repaint();
	}
	
	
public static void printResultsAsText(Graphics g,List<String> pathNames) {

	JTextArea townName=new JTextArea();
	String names = "";
		for(String name : pathNames) {
			names=names+" -> "+name;
		}
		
		Plan country = CreateInterface.regionInfo.get(12);
		int x = (int) ((int) country.getDimension()[0]*0.20);
		int y = (int)country.getDimension()[1]-80;
	townName.setOpaque(false);
	townName.setBounds(0, 0,(int) (country.getDimension()[0]*0.70), 100);
	townName.setText(names);
	townName.setLineWrap(true);
	townName.setEditable(false);
	townName.setFont(new Font("Serif",Font.PLAIN,19));
	CreateInterface.showResults.add(townName);
	
	}

}
