package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextArea;

import obj.Plan;
import obj.Coord;
import obj.TownInterface;
import util.Printer;
import util.TXTReader;

public class PrintPoints implements ActionListener {
	
	
	//public static Map<Integer, TownInterface<?, ?>> rectCoordMap = new HashMap<Integer, TownInterface<?, ?>>(); //maps to verify if the click is inside a rectangle

	static PaintInterface jc = new PaintInterface();
	static Map<Integer, Plan> regionInfo = new HashMap<>();
	//private static Map<Integer, Boolean> selectedTown; 
	private static Plan regionMap = new Plan();
	
	
	public static  void mainIterface(Map<Integer, Coord<?, ?>> cityCoordMap, Map<Integer, Object[]> namesMap, Map<Integer, Object[]> regMap ) {
		
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		
		Plan forEntireMap = new Plan();
    	jc.setBackground(Color.WHITE);
    	jc.setLayout(null);
    	forEntireMap.setCityCoordMap(cityCoordMap);
    	adjustCityCoordMap=adjustOnFrame(forEntireMap);
    	int[] dimensionCountry = getDimension(adjustCityCoordMap);
    	
    	int btnX=300;
    	int btnY= dimensionCountry[1];
    	
    	JTextArea area=new JTextArea("Zoom on a region: "); 
    	
    	area.setBounds(10,btnY+20, 250,200); 
    	area.setFont(area.getFont().deriveFont(25f));
    	area.setEnabled(false);
    	
    	for(int key :regMap.keySet() ) {
    		String name = (String) regMap.get(key)[0];
    		JButton button = new JButton(name);
    		Map<Integer, Boolean> selectedTown = new HashMap<>();
    		
    		 List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(key,namesMap);
			 Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,cityCoordMap);
    		 
    		
    		button.setBounds(btnX,btnY,150,30);
    		button.addActionListener(new ActionListener() { 
    			  public void actionPerformed(ActionEvent e) { 

    				 regionMap = ByRegion.createFrameForRegion(name, regionCoordsMap, key);
    				 regionInfo.put(key, regionMap);
    				 
    			  } 
    			} );
	    	
	    	
	    	button.setBackground(chooseColor(key));
	    	btnX+=155;
	    	if(key==5) {
	    		btnY+= 50;
	    		btnX =350;
	    	}
	    	jc.add(button);
    	}
    	Map<Integer, Boolean> selectedTown = new HashMap<>();
    	 Map<Integer, TownInterface<?, ?>> rectMap = forEntireMap.getRectCoordMap();
    	forEntireMap = new Plan("All",12, selectedTown,adjustCityCoordMap, dimensionCountry, rectMap);
    	regionInfo.put(12,forEntireMap);
    	jc.add(area);
		jc.setPreferredSize(new Dimension(dimensionCountry[0],dimensionCountry[1]+100));
    	//jc.cityCoordMap = adjustCityCoordMap;
    	jc.namesXRegions = namesMap;
    	jc.regions = regMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine", false, forEntireMap);
 
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jc.setBackground(Color.black);
		
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
	
	public static Map<Integer, Coord<?, ?>> adjustOnFrame(Plan plan) {
		
		Map<Integer, Coord<?, ?>> cityCoordMap =plan.getCityCoordMap();
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
    	
    	return adjustMap(xMin, yMin, plan);
	}
	
	public static Map<Integer, Coord<?, ?>>  adjustMap( int deltaX, int deltaY, Plan plan) {
		
		Map<Integer, Coord<?, ?>> cityCoordMap= plan.getCityCoordMap();
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		 Map<Integer, TownInterface<?, ?>> rectMap = new HashMap<Integer, TownInterface<?, ?>>();
		for(int number:cityCoordMap.keySet()) {
			int x =(int) cityCoordMap.get(number).getX();
			int y =(int) cityCoordMap.get(number).getY();
			
			Coord<?, ?> coord = new Coord<Object, Object>(x-deltaX, y-deltaY);
			TownInterface<?, ?> rect = new TownInterface<Object, Object>(x-deltaX, y-deltaY, x-deltaX+PaintInterface.width, y-deltaY+PaintInterface.width );
			
			adjustCityCoordMap.put(number, coord);
			
			rectMap.put(number, rect);
		}
		plan.setRectCoordMap(rectMap);
		return adjustCityCoordMap;
		
	}


	
	
	public static Color chooseColor(int region) {
		Color color = new Color(0,0,0);
		
		switch(region) {
		case 0:
			color= new Color(169, 180, 255);
			break;
		case 1:
			color=new Color(168, 86, 69);
			break;
		case 2:
			color=new Color(40, 133, 29);
			break;
		case 3:
			color=Color.MAGENTA;
			break;
		case 4:
			color=Color.lightGray ;
			break;
		case 5:
			color=Color.ORANGE;
			break;
		case 6:
			color=Color.PINK ;
			break;
		case 7:
			color=new Color(92, 182, 150);
			break;
		case 8:
			color=Color.CYAN;
			break;
		case 9:
			color=new Color(249, 233, 150);
			break;
		case 10:
			color=new Color(251, 135, 150);
			break;
		}
		return color;
	}
	
	public static void main(String[] args) {
		//lecture de la bdd qui a terme sera dans le main de Elise
				Map<Integer, Coord<?, ?>> cityCoordMap = TXTReader.getCityCoord();
		    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
		    		Printer.printCityCoordMap(cityCoordMap);
		    	Map<Integer, Object[]> namesMap = TXTReader.getNames();
		    	if (Arrays.asList(args).contains("-pna") || Arrays.asList(args).contains("-printNames"))
		    		Printer.printDataMap(namesMap);
		    	Map<Integer, Object[]> regMap = TXTReader.getRegions();
		    	if (Arrays.asList(args).contains("-pr") || Arrays.asList(args).contains("-printRegions"))
		    		Printer.printDataMap(regMap);
		    	
		    	mainIterface(cityCoordMap,  namesMap, regMap );
	}

}
