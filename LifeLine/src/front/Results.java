package front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

import obj.Coord;
import obj.Plan;

public class Results {
	
	protected static List<String> pathNames;
	protected static Boolean isEnd = false;
	public static void printResultsOnMap(Graphics g) {
		
		List<Integer> fullPath = CreateInterface.fullPath;
		pathNames = new ArrayList<String>();
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
		
		
		
	}
	
	/**
	 * add the name of towns in the path to the scrollable Jpanel
	 * @param g
	 * @param pathNames
	 */
public static void printResultsAsText(Graphics g,List<String> pathNames) {
System.out.println("ici");
	JTextArea townName=new JTextArea();
	String names = "";
	int i = 1;
		for(String name : pathNames) {
			names=names+ i+") "+name+"	";
			i++;
		}
		
	townName.setOpaque(false);
	townName.setBounds(0, 0,120, 100);
	townName.setText(names);
	townName.setLineWrap(true);
	townName.setWrapStyleWord(true);
	townName.setEditable(false);
	townName.setFont(new Font("Serif",Font.PLAIN,17));
	
	
	CreateInterface.showResults.add(townName);
	}

/**
 * Show first and last bases
 * @param g
 * @param pathNames
 */
	public static void printFirstAndLast() {
	
		Plan country = CreateInterface.regionInfo.get(12);
		
		CreateInterface.firstBase.setOpaque(false);
		CreateInterface.firstBase.setBounds((int)(country.getDimension()[0]*0.20), country.getDimension()[1]-30,400, 400);
		CreateInterface.firstBase.setText("Starting base : " + pathNames.get(0) );
		CreateInterface.firstBase.setEditable(false);
		CreateInterface.firstBase.setFont(new Font("Serif",Font.BOLD,20));
		CreateInterface.jc.add(CreateInterface.firstBase);
		
		CreateInterface.lastBase.setOpaque(false);
		CreateInterface.lastBase.setBounds((int)(country.getDimension()[0]*0.20), country.getDimension()[1],400, 400);
		CreateInterface.lastBase.setText("Arrival base : " + pathNames.get(pathNames.size()-1) );
		CreateInterface.lastBase.setEditable(false);
		CreateInterface.lastBase.setFont(new Font("Serif",Font.BOLD,20));
		CreateInterface.jc.add(CreateInterface.lastBase);	
	}
	

}
