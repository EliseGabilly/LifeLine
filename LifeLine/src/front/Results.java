package front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import obj.Coord;
import obj.Plan;

public class Results {
	
	
	public static void printResultsOnMap(Graphics g) {
		
		List<Integer> fullPath = CreateInterface.fullPath;

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
				 
				 if(j==5) {
					 x=(int) ( country.getDimension()[0]*0.22)-100;
					 y = y+40;
					 j=0;
				 }
				printResultsAsText(g,fullPath,i,x,y);
				x = x+140;
				j++;
		}
		printResultsAsText(g,fullPath,i,x,y);
		CreateInterface.jc.repaint();
	}
	
	
public static void printResultsAsText(Graphics g,List<Integer> fullPath, int i, int x, int y ) {
	
	
		g.drawLine(x-10, y+23,x-5 , y+25);
		g.drawLine(x-40, y+25,x-5 , y+25);
		g.drawLine(x-10, y+27,x-5 , y+25);
		
		
		String name = (String) CreateInterface.namesMap.get(fullPath.get(i))[0];
		JLabel townName=new JLabel(name, SwingConstants.CENTER); 
		townName.setFont(new Font("Serif",Font.PLAIN,19));
		townName.setBounds(x, y, 100,40);
		CreateInterface.jc.add(townName);
			
		
	}

}
