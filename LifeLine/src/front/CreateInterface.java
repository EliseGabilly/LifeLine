package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CreateInterface implements ActionListener {
	
	public static Map<Integer, Coord<?, ?>> cityCoordMap;
	public static Map<Integer, Object[]> namesMap;
	public static Map<Integer, Object[]> regMap;
	public static List<Integer>  basesList;
	public static PaintInterface jc = new PaintInterface();
	static Map<Integer, Plan> regionInfo = new HashMap<>(); // List of all individual maps and the entire one
	private static Plan regionMap = new Plan();
	
	
	 
	
	/**
	 * Initialises variables to creates all interfaces
	 * @param cityCoordMap
	 * @param namesMap
	 * @param regMap
	 */
	public static  void mainIterface(Map<Integer, Coord<?, ?>> cityCoordMapOrigin, Map<Integer, Object[]> namesMapOrigin, Map<Integer, Object[]> regMapOrigin, List<Integer>  basesListOrigin ) {
		basesList = basesListOrigin;
		cityCoordMap=cityCoordMapOrigin;
		namesMap=namesMapOrigin;
		regMap=regMapOrigin;
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		
		Plan forEntireMap = new Plan();
    	jc.setBackground(Color.WHITE);
    	jc.setLayout(null);
    	forEntireMap.setCityCoordMap(cityCoordMap);
    	adjustCityCoordMap=adjustOnFrame(forEntireMap);
    	int[] dimensionCountry = getDimension(adjustCityCoordMap);
    	
    	int btnX=300;
    	int btnY= dimensionCountry[1];
    	createTextAreaAndRefresh(btnX, btnY);
    	createRegionButton(btnX , btnY);
    	  	
    	Map<Integer, Boolean> selectedTown = new HashMap<>();
    	Map<Integer, TownInterface<?, ?>> rectMap = forEntireMap.getRectCoordMap();
    	forEntireMap = new Plan("All",12, selectedTown,adjustCityCoordMap, dimensionCountry, rectMap);
    	regionInfo.put(12,forEntireMap);
    	
		jc.setPreferredSize(new Dimension(dimensionCountry[0],dimensionCountry[1]+100));
    	jc.namesXRegions = namesMap;
    	jc.regions = regMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine", false, forEntireMap);
 
	}
	
	/**
	 * creates the text area and refresh button
	 * @param btnX
	 * @param btnY
	 */
	public static void createTextAreaAndRefresh(int btnX, int btnY) {
		JTextArea area=new JTextArea("Zoom on a region: "); 
    	
    	area.setBounds(10,btnY+20, 250,200); 
    	area.setFont(area.getFont().deriveFont(25f));
    	area.setEnabled(false);
    	
    	JButton refreshBtn = new JButton("Refresh");
    	refreshBtn.setBounds(btnX,btnY-50,150,30);
    	refreshBtn.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 

				jc.repaint();
				 
			  } 
			} );
    	
    	JButton validatesChoicesBtn = new JButton("Confirm my choices");
    	validatesChoicesBtn.setBounds(btnX+250,btnY-50,200,30);
    	validatesChoicesBtn.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 

				/**
				 * 
				 */
				 
			  } 
			} );
    	
    	jc.add(area);
    	jc.add(refreshBtn);
    	jc.add(validatesChoicesBtn);
    	
	}
	
	public static int getBtnDim( Map<Integer, Coord<?, ?>> cityCoordMap) {
		
		int [] dim = getDimension(cityCoordMap);
		int width = dim[1]/5;
		return width;
	}
	
	/**
	 * Creates the button for each region
	 * @param btnX
	 * @param btnY
	 */
	public static void createRegionButton( int btnX, int btnY) {

		for(int key :regMap.keySet() ) {
			String name = (String) regMap.get(key)[0];
			JButton button = new JButton(name);
			
			 List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(key,namesMap);
			 Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,cityCoordMap);
			 
			
			button.setBounds(btnX,btnY, getBtnDim(regionCoordsMap),30);
			button.addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
					  if(regionInfo.containsKey(key)) {
						  ByRegion.recreateFrame(regionInfo.get(key));
					  }else {
						  regionMap = ByRegion.createFrameForRegion(name, regionCoordsMap, key);
		    			  regionInfo.put(key, regionMap);
					  }

				  } 
				} );
	    	
	    	
	    	button.setBackground(chooseColor(key));
	    	btnX+=getBtnDim(regionCoordsMap)+ getBtnDim(regionCoordsMap)/6 ;
	    	if(key==5) {
	    		btnY+= 50;
	    		btnX =350;
	    	}
	    	jc.add(button);
    	}
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		jc.setBackground(Color.black);
		
	}
	
	
/**
 * gets the dimension for the selected area
 * @param cityCoordMap
 * @return
 */
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
		double width = screenSize.getWidth()-100;
		double height = screenSize.getHeight()-300;
		while(x>=width) {
			x-=100;
		}
		while(y>=height) {
			y-=100;
		}
		
		int[] dimension = {x+100, y+100};
		return dimension; 
		
	}
	
	/**
	 * finds the variable that will be use to readjust the coordinates to the top right of the frame
	 * @param plan
	 * @return
	 */
	public static Map<Integer, Coord<?, ?>> adjustOnFrame(Plan plan) {
		
		
		int xMax=0;
    	int yMax=0;
    	float delta = 0;
    	
    	int xMin=Integer.MAX_VALUE;
    	int yMin=Integer.MAX_VALUE;
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
    	
    	
    	do {
    		 xMax=0;
        	 yMax=0;
        	
        	 xMin=Integer.MAX_VALUE;
        	 yMin=Integer.MAX_VALUE;
        	
    		Map<Integer, Coord<?, ?>> cityCoordMap =plan.getCityCoordMap();
    		
        	for(int number:cityCoordMap.keySet()) {		
    			
    			if((int) cityCoordMap.get(number).getX()<xMin) {
    				xMin =(int) cityCoordMap.get(number).getX();
    			}
    			if((int) cityCoordMap.get(number).getY()<yMin) {
    				yMin =(int) cityCoordMap.get(number).getY();
    			}
    			
    			if((int) cityCoordMap.get(number).getX()>xMax) {
    				xMax =(int) cityCoordMap.get(number).getX();
    			}
    			if((int) cityCoordMap.get(number).getY()>yMax) {
    				yMax =(int) cityCoordMap.get(number).getY();
    			}	
    		}
        	if(xMax>width-100 || yMax>height-300 ) {
        		delta+=1.1;
        		adjust( delta,  plan);
        	}
    		
    	}while(xMax>width-100 || yMax>height-300);

    	xMin-=50;
    	yMin=+20;
 
    	return adjustMap(xMin, yMin , plan);
	}
	
	public static Map<Integer, Coord<?, ?>>  adjust(float delta, Plan plan) {
		
		Map<Integer, Coord<?, ?>> cityCoordMap= plan.getCityCoordMap();
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		
		for(int number:cityCoordMap.keySet()) {
			int x =(int) cityCoordMap.get(number).getX();
			int y =(int) cityCoordMap.get(number).getY();
			
			Coord<?, ?> coord = new Coord<Object, Object>(x/delta, y/delta);
			
			adjustCityCoordMap.put(number, coord);
			plan.setCityCoordMap(adjustCityCoordMap);
		}
		return adjustCityCoordMap;
	}
	
	
	
	/**
	 * Changes the coords stored in the the lsit containing twons coordinates in the selected area 
	 * @param deltaX
	 * @param deltaY
	 * @param plan
	 * @return
	 */
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
			if(!CreateInterface.basesList.contains(number)) {
				rectMap.put(number, rect);
			}
			
		}
		plan.setRectCoordMap(rectMap);
		return adjustCityCoordMap;
		
	}


	
	/**
	 * Chooses the color depending of the region
	 * @param region
	 * @return
	 */
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

	


}
