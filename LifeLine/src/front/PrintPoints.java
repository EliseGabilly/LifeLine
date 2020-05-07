package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import obj.Coord;
import obj.TownInterface;
import util.Printer;
import util.TXTReader;

public class PrintPoints {
	
	
	public static Map<Integer, TownInterface<?, ?>> rectCoordMap = new HashMap<Integer, TownInterface<?, ?>>(); //maps to verify if the click is inside a rectangle

	public static void main(String[] args) {
	//	public static void mainIterface(Map<Integer, Coord<?, ?>> cityCoordMap) {
	
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		Map<Integer, Coord<?, ?>> cityCoordMap = TXTReader.getCityCoord();
    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
    		Printer.printCityCoordMap(cityCoordMap);
    	
    	PaintInterface jc = new PaintInterface();
    	jc.setBackground(Color.WHITE);
    	
    	adjustCityCoordMap=adjustOnFrame(cityCoordMap);
    	int[] dimension = getDimension(adjustCityCoordMap);
    	
    	
		jc.setPreferredSize(new Dimension(dimension[0],dimension[1]));
    	jc.cityCoordMap = adjustCityCoordMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine");
 
	}
	
	
	
	public static int[] getDimension(Map<Integer, Coord<?, ?>> cityCoordMap) {
		int x=0;
    	int y=0;
    	int newX;
    	int newY;
		for(int number:cityCoordMap.keySet()) {
			
			newX=(int) cityCoordMap.get(number).getX();
			newY=(int) cityCoordMap.get(number).getY();
			if(newX>x) {
				x=newX;
			}
			if(newY>y) {
				y=newY;
			}		
		}	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		while(x>=width) {
			x-=100;
		}
		while(y>=height) {
			y-=100;
		}
		
		int[] dimension = {x+100, y+100};
		return dimension; 
		
	}
	
	public static Map<Integer, Coord<?, ?>> adjustOnFrame(Map<Integer, Coord<?, ?>> cityCoordMap) {
		
		int xMin=Integer.MAX_VALUE;
    	int yMin=Integer.MAX_VALUE;
    	for(int number:cityCoordMap.keySet()) {		
			
			if((int) cityCoordMap.get(number).getX()<xMin) {
				xMin =(int) cityCoordMap.get(number).getX();
			}
			if((int) cityCoordMap.get(number).getY()<yMin) {
				yMin =(int) cityCoordMap.get(number).getY();
			}	
		}
    	xMin-=50;
    	yMin=+20;
    	
    	return cityCoordMap=adjustMap(cityCoordMap, xMin, yMin);
	}
	
	public static Map<Integer, Coord<?, ?>>  adjustMap(Map<Integer, Coord<?, ?>> cityCoordMap, int deltaX, int deltaY) {
		
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		
		for(int number:cityCoordMap.keySet()) {
			int x =(int) cityCoordMap.get(number).getX();
			int y =(int) cityCoordMap.get(number).getY();
			
			Coord<?, ?> coord = new Coord<Object, Object>(x-deltaX, y-deltaY);
			TownInterface<?, ?> rect = new TownInterface<Object, Object>(x-deltaX, y-deltaY, x-deltaX+PaintInterface.width, y-deltaY+PaintInterface.width );
			
			adjustCityCoordMap.put(number, coord);
			rectCoordMap.put(number, rect);
		}
		return adjustCityCoordMap;
		
	}

}
