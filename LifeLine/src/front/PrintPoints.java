package front;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.Map;

import obj.Coord;
import util.CSVReader;
import util.Printer;

public class PrintPoints {

	public static void main(String[] args) {
	
		
		Map<Integer, Coord<?, ?>> cityCoordMap = CSVReader.getCityCoord();
    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
    		Printer.printCityCoordMap(cityCoordMap);
    	TestInterface jc = new TestInterface();
    	jc.setBackground(Color.WHITE);
		jc.setPreferredSize(new Dimension(1000,1000));
    	jc.cityCoordMap = cityCoordMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine");
    	
    		
			
			
			
			
			
			
			
		

	}

}
