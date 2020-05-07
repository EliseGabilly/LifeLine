package front;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import obj.Coord;
import util.Printer;
import util.TXTReader;

public class PrintPoints {

	public static void main(String[] args) {
	
		
		
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		Map<Integer, Coord<?, ?>> cityCoordMap = TXTReader.getCityCoord();
    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
    		Printer.printCityCoordMap(cityCoordMap);
    	
    	TestInterface jc = new TestInterface();
    	jc.setBackground(Color.WHITE);
    	
    	adjustCityCoordMap=adjustOnFrame(cityCoordMap);
    	
    	
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
		 y+=100;
		 x+=100;
    	
    	
		jc.setPreferredSize(new Dimension(x,y));
    	jc.cityCoordMap = cityCoordMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine");
 
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
    	
    	return cityCoordMap=adjustMap(cityCoordMap, xMin, yMin);
	}
	
	public static Map<Integer, Coord<?, ?>>  adjustMap(Map<Integer, Coord<?, ?>> cityCoordMap, int deltaX, int deltaY) {
		
		for(int number:cityCoordMap.keySet()) {
			int x =(int) cityCoordMap.get(number).getX();
			int y =(int) cityCoordMap.get(number).getY();
			
			Coord<?, ?> coord = new Coord<Object, Object>(x-deltaX, y-deltaY);
			
			cityCoordMap.replace(number, coord);
			
		}
		return cityCoordMap;
		
	}

}
