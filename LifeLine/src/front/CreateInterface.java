package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

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
	static int yMaxOfTown; 
	static int xMaxOfTown;
	
	
	 
	
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
    	
    	
    	createTextAreaAndRefresh(dimensionCountry);
    	createRegionButton(dimensionCountry);
    	  	
    	Map<Integer, Boolean> selectedTown = new HashMap<>();
    	Map<Integer, TownInterface<?, ?>> rectMap = forEntireMap.getRectCoordMap();
    	forEntireMap = new Plan("All",12, selectedTown,adjustCityCoordMap, dimensionCountry, rectMap);
    	regionInfo.put(12,forEntireMap);
    	
		jc.setPreferredSize(new Dimension(dimensionCountry[0]+dimensionCountry[0]/12,dimensionCountry[1]+100));
    	jc.namesXRegions = namesMap;
    	jc.regions = regMap;
    	
		CreatFrame.showOnFrame(jc,"LifeLine", false, forEntireMap);
 
	}
	
	/**
	 * Creates the button for each region
	 * @param btnX
	 * @param btnY
	 */
	public static void createRegionButton( int[] dimensionCountry) {
		
		
		int heigh = getBtnDimX(dimensionCountry);
		
		int btnsDimension =(20 + heigh + heigh/2);
		int btnX=(int) ( dimensionCountry[0]*0.28);
    	int btnY= yMaxOfTown+btnsDimension ;
    	
    	int width =getBtnDimY(dimensionCountry);
    	
		for(int key :regMap.keySet() ) {
			String name = (String) regMap.get(key)[0];
			JButton button = new JButton(name);
			
			 List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(key,namesMap);
			 Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,cityCoordMap);
			 
			button.setFont(new Font("Serif",Font.BOLD,12));
			button.setBounds((int) btnX,btnY, width,heigh);
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
	    	btnX+=width+ width/6 ;
	    	if(key==4) {
	    		btnY+=  heigh + heigh/3;
	    		btnX =(int) ( dimensionCountry[0]*0.28) - (width+ width/6);
	    	}
	    	jc.add(button);
    	}
	
	}
	
	
	/**
	 * creates the text area and refresh button
	 * @param btnX
	 * @param btnY
	 */
	public static void createTextAreaAndRefresh( int[] dimensionCountry) {
		
		
		
		JLabel title=new JLabel("Rwandata map : "); 
		title.setBounds(10, 10, 200,30);
		JLabel info=new JLabel("Choose the towns that you want to deliver: "); 
		info.setBounds(10, 40, 400,30);
		
		int heigh = getBtnDimX(dimensionCountry);
		JLabel area=new JLabel("Zoom on a region: ", SwingConstants.CENTER); 
		int btnsDimension =(heigh + heigh/2);
		
		int btnY= yMaxOfTown+20 ;
		int btnX=(int) ( dimensionCountry[0]*0.28);
		int width =getBtnDimY(dimensionCountry);
		
    	area.setBounds(10, btnY+btnsDimension, btnX - 10,30); 
    	
    	JButton refreshBtn = new JButton("Refresh");
    	refreshBtn.setBounds(btnX,btnY+(heigh/4),width,heigh);
    	refreshBtn.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 

				jc.repaint();
				 
			  } 
			} );
    	
    	
    	
    	JButton validatesChoicesBtn = new JButton("Confirm my choices");
    	validatesChoicesBtn.setBounds(btnX+ width + width/6,btnY+(heigh/4), 200,heigh);
    	validatesChoicesBtn.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 

				/**
				 * 
				 */
				 
			  } 
			} );
    	
    	
    	info.setFont(new Font("Serif",Font.BOLD,19));
    	area.setFont(new Font("Serif",Font.BOLD,19));
    	title.setFont(new Font("Serif",Font.BOLD,19));
    	refreshBtn.setFont(new Font("Serif",Font.BOLD,15));
    	validatesChoicesBtn.setFont(new Font("Serif",Font.BOLD,15));
    	jc.add(info);
    	jc.add(title);
    	jc.add(area);
    	jc.add(refreshBtn);
    	jc.add(validatesChoicesBtn);
    	
	}
	
	public static int getBtnDimY(int[] dimensionCountry) {	
		
		int height = (int) ((0.75*xMaxOfTown)/5);
		return height;
	}
	
	public static int getBtnDimX(int[] dimensionCountry) {	
		
		
		int width =  (dimensionCountry[1]-yMaxOfTown)/4;

		return width;
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
		double width = screenSize.getWidth()-((screenSize.getWidth()/5));
		double height = screenSize.getHeight()-(screenSize.getHeight()/3);
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
	 * finds the variable that will be use to read just the coordinates to the top right of the frame
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
    	double width = screenSize.getWidth()-((screenSize.getWidth()/5));
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
        	if(xMax>width-100 || yMax>height-(height/4) ) {
        		delta+=0.5;
        		adjust( delta,  plan);
        	}
    		
    	}while(xMax>width-100 || yMax>height-(height/4));

    	xMin=xMin-50;
    	yMin=yMin-20;
    	yMaxOfTown=yMax;
    	xMaxOfTown=xMax;
 
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
