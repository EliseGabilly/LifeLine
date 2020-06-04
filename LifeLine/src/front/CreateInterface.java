package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import obj.Plan;
import obj.Adjacent;
import obj.Coord;
import obj.TownInterface;
import pkg.Calcul;
import pkg.Main;
import pkg.PathOptimizer;

public class CreateInterface implements ActionListener {
	
	protected static Map<Integer, Coord<?, ?>> cityCoordMap;
	protected static Map<Integer, Object[]> namesMap;
	protected static Map<Integer, Object[]> regMap;
	protected static Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap;
	protected static List<Integer>  basesList;
	protected static PaintInterface jc = new PaintInterface();
	protected static Map<Integer, Plan> regionInfo = new HashMap<>(); // List of all individual maps and the entire one
	private static Plan regionMap = new Plan();
	protected static int yMaxOfTown; 
	protected static int xMaxOfTown;
	private static double width;
	private static double height;
	protected static List<Integer> fullPath;

	protected static Boolean isResults = false;
	protected static float cost;
	private static Plan forEntireMap = new Plan();
	protected static int[] dimensionCountry;
	protected static JPanel showResults;
	protected static JScrollPane scroller;
	protected static JTextArea selectedTowns;
	protected static Boolean ableBtn=true;
	
	private static int btnY;
	private static int btnX;
	private static int heigh ;
	public static boolean restart=false;
	
	protected static JTextArea firstBase = new JTextArea();
	protected static JTextArea lastBase =new JTextArea();
	
	private static JLabel error=new JLabel("", SwingConstants.CENTER);
	  
	/**
	 * Initialises variables 
	 * @param cityCoordMap
	 * @param namesMap
	 * @param regMap
	 */
	public static  void mainIterface(Map<Integer, Coord<?, ?>> cityCoordMapOrigin, Map<Integer, Object[]> namesMapOrigin, Map<Integer, Object[]> regMapOrigin, List<Integer>  basesListOrigin, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMapOrigin ) {
		basesList = basesListOrigin;
		cityCoordMap=cityCoordMapOrigin;
		namesMap=namesMapOrigin;
		regMap=regMapOrigin;
		weightedAdjMap =weightedAdjMapOrigin;
		createMap();
	}
	
/*
 * Creates text area and the btn to search
 */
	protected static void searchWithTxt() {
		btnY= yMaxOfTown+20 ;
		btnX=(int) ( dimensionCountry[0]*0.28);
		JTextArea searchTxt = new JTextArea();
		searchTxt.setText("");
		searchTxt.setFont(new Font("Serif",Font.BOLD,19));
		searchTxt.setBounds(btnX +getBtnDimY()*2+2*getBtnDimY()/6, btnY+5, getBtnDimY()*2+getBtnDimY()/6 ,getBtnDimX());
		
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		searchTxt.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  String searchedTown = searchTxt.getText();
					  for(int key : namesMap.keySet()) {
						 if(namesMap.get(key)[0].equals(searchedTown)) {
							jc.addTown(key,forEntireMap);
							searchTxt.setText("");
						}
				  }
			}});
    	
		searchBtn.setFont(new Font("Serif",Font.BOLD,15));
		searchBtn.setBounds(btnX +getBtnDimY()*4+4*getBtnDimY()/6, btnY+5, getBtnDimY() ,getBtnDimX());
		jc.add(searchBtn);
		
		
		
		
		jc.add(searchTxt);
	}
	
	
	/**
	 * creates the button and areas depending if it is the page to show the results or not
	 */
	public static void createMap() {

		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		jc.setFont(new Font("Serif",Font.BOLD,19));
		jc.setBackground(Color.white);
		jc.setLayout(null);
    	
		if(!isResults) {
			dimensionCountry = createPlanForCountry(adjustCityCoordMap);
		}
		createTextArea(dimensionCountry);
		jc.setPreferredSize(new Dimension(dimensionCountry[0]+dimensionCountry[0]/12,dimensionCountry[1]+100));
    	PaintInterface.namesXRegions = namesMap;
    	PaintInterface.regions = regMap;
    	
    	if(!isResults) {
    		createRegionButton(dimensionCountry);
    		searchWithTxt();
    		CreatFrame.showOnFrame(jc,"LifeLine",false , forEntireMap);
    	}
    	else {//create the scrollPane
    		    		
    		createLabelAndScrollerForPath();
    		 createRestartbtn();
    		CreatFrame.showOnFrame(jc, "Results",false, forEntireMap);
    	} 
	}
	
	/**
	 * creates restart button
	 */
	private static void createRestartbtn() {
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Frame[] frames = Frame.getFrames();
					
					for(Frame frame: frames) {
							jc.removeAll();
							frame.dispose();
							
						}
					reinitializeVariables();
					CreateInterface.mainIterface(cityCoordMap,  namesMap,  regMap,  basesList, weightedAdjMap );
					} 
			} );
    	
		btnRestart.setFont(new Font("Serif",Font.BOLD,20));
		btnRestart.setBounds((int) (dimensionCountry[0]-70), (int) (dimensionCountry[1]-50), 150,50);
		jc.add(btnRestart);
	}
/**
 * creates label, JPanel et Scroller for path as result
 */
	private static void createLabelAndScrollerForPath() {
		JLabel labelForpath=new JLabel("Your Path : ", SwingConstants.CENTER); 
		labelForpath.setFont(new Font("Serif",Font.BOLD,19));
		labelForpath.setBounds(dimensionCountry[0]+dimensionCountry[0]/8-180, 40, 120,40);
		CreateInterface.jc.add(labelForpath);
		
		showResults = new JPanel();
		
		scroller = new JScrollPane(CreateInterface.showResults, 
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				   JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		scroller.setViewportView(CreateInterface.showResults);
		scroller.setBounds(dimensionCountry[0]+dimensionCountry[0]/8-180, 80, 160,dimensionCountry[1]-200);
			
		scroller.setPreferredSize(new Dimension(100,400));
		jc.add(scroller);
		showResults.setVisible(true);
	}
	
	private static int[] createPlanForCountry(Map<Integer, Coord<?, ?>> adjustCityCoordMap) {
		forEntireMap.setCityCoordMap(cityCoordMap);
    	adjustCityCoordMap=adjustOnFrame(forEntireMap);
    	int[] dimensionCountry = getDimension(adjustCityCoordMap);
    	
		Map<Integer, Boolean> selectedTown = new HashMap<>();
    	Map<Integer, TownInterface<?, ?>> rectMap = forEntireMap.getRectCoordMap();
    	forEntireMap = new Plan("All",12, selectedTown,adjustCityCoordMap, dimensionCountry, rectMap);
    	regionInfo.put(12,forEntireMap);
    	return dimensionCountry;
	}
	
	/**
	 * Creates the button for each region (position + dimensions + action...)
	 * @param btnX
	 * @param btnY
	 */
	private static void createRegionButton( int[] dimensionCountry) {

		int btnHeigh = getBtnDimX();
		int spaceForNextButton =20 + btnHeigh + btnHeigh/2;
		int btnX=(int) ( dimensionCountry[0]*0.28);
    	int btnY= yMaxOfTown+spaceForNextButton ;
    	
    	int btnWidth =getBtnDimY();
    	
		for(int key :regMap.keySet() ) {
			String name = (String) regMap.get(key)[0];
			JButton button = new JButton(name);
			
			 List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(key,namesMap);
			 Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,cityCoordMap);
	
			button.addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
					  if(ableBtn) {
						  ableBtn=false;
						  if(regionInfo.containsKey(key)) {
							  ByRegion.recreateFrame(regionInfo.get(key));
						  }else {
							  regionMap = ByRegion.createFrameForRegion(name, regionCoordsMap, key);
			    			  regionInfo.put(key, regionMap);
						  }
					  }  
					  
				  } 
				} );
	    	
			button.setFont(new Font("Serif",Font.BOLD,12));
			button.setBounds((int) btnX,btnY, btnWidth,btnHeigh);
	    	button.setBackground(chooseColor(key));
	    	
	    	btnX+=btnWidth+ btnWidth/6 ;
	    	if(key==4) {
	    		btnY+=  btnHeigh + btnHeigh/3;
	    		btnX =(int) ( dimensionCountry[0]*0.28) - (btnWidth+ btnWidth/6);
	    	}
	    	jc.add(button);
    	}
	
	}
	
	
	/**
	 * creates labels and button
	 * @param btnX
	 * @param btnY
	 */
	private static void createTextArea( int[] dimensionCountry) {
		
		btnY= yMaxOfTown+20 ;
		btnX=(int) ( dimensionCountry[0]*0.28);
		heigh = getBtnDimX();
		int btnsDimension =(heigh + heigh/2);
		JLabel info = new JLabel();
		prepareSelectedTownsArea();
		JLabel title=new JLabel("Rwanda map : "); 
		title.setBounds(10, 10, 200,30);
		
    	if(!isResults) {
    		
    		info=new JLabel("Choose the towns that you want to deliver: "); 
    		info.setBounds(10, 40, 400,30);
    		selectedTowns.setBounds(dimensionCountry[0]+dimensionCountry[0]/8-160, 40, 150,dimensionCountry[1]-300);
    		creatBtnAndlabelIfisNoteResults( btnsDimension);
    		
    	}
    	else {
    		Plan country = regionInfo.get(12);
    		int x = (int) ((int) country.getDimension()[0]*0.20);
    		int y = (int)country.getDimension()[1]-80;
    		selectedTowns.setBounds(x, y,600, 50);
    		
    		info=new JLabel("Here is the path you should take: "); 
    		info.setBounds(10, 40, 400,30);
    		
    	}
    	
    	title.setFont(new Font("Serif",Font.BOLD,25));
    	info.setFont(new Font("Serif",Font.BOLD,20));
    	jc.add(info);
    	jc.add(title);
    	jc.add(selectedTowns);
	}
	
	/*
	 * prepare the text area that will be use to display the current selected town
	 */
	private static void prepareSelectedTownsArea() {
		selectedTowns = new JTextArea();
		selectedTowns.setEditable(false);
		selectedTowns.setLineWrap(true);
		selectedTowns.setWrapStyleWord(true);
		selectedTowns.setText("Selected towns: ");
		selectedTowns.setFont(new Font("Serif",Font.BOLD,20));
	}
	/**
	 * creates the labels for for regions btn and the validate btn
	 */
	private static void creatBtnAndlabelIfisNoteResults(int btnsDimension) {
		JLabel area=new JLabel("Zoom on a region: ", SwingConstants.CENTER); 
    	area.setBounds(10, btnY+btnsDimension, btnX - 10,30);     	
 
    	JButton validatesChoicesBtn = new JButton("Confirm my choices");
    	validatesChoicesBtn.setBounds(btnX ,btnY+(heigh/4), getBtnDimY()*2+getBtnDimY()/6 ,heigh);
    	

    	validatesChoicesBtn.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 
			  if(ableBtn) {
				  if(PaintInterface.listOfNamesForTownsSelected.size()>0) {
					  launchAlgo();  
				  }else {
					  createErrorLabel("You must select at least one town" );
				  }
			  }
			  } 
			} );
    	
    	jc.setOpaque(true);
    	jc.add(validatesChoicesBtn);
    	jc.add(area);
	}
	/**
	 * change the text of the label and show it on the frame
	 * @param msg
	 */
	protected static void createErrorLabel(String msg) {
		error.setText(msg);
		error.setForeground(Color.red);
		if(ableBtn) {
			error.setFont(new Font("Serif",Font.BOLD,20));
			error.setBounds((int) (dimensionCountry[0]*0.40),dimensionCountry[1]+30, 300,heigh);
			jc.add(error);
			jc.repaint();
			
		}else {
			Dimension dimRegion = ByRegion.regionInterface.getPreferredSize();
			error.setFont(new Font("Serif",Font.BOLD,15));
			error.setBounds((int) (dimRegion.width*0.05),dimRegion.height-50, 300,heigh);
			ByRegion.regionInterface.add(error);
			ByRegion.regionInterface.repaint();
		}
	
	}
	
	/*
	 * Launch the selected path finding algo
	 */
	private static void launchAlgo(){
		List<Integer> nodeRequierment = new ArrayList<Integer>();
		Plan map = regionInfo.get(12);
		Map<Integer, Boolean>  selectedTown= map.getSelectedTown();
		
		for(int key :selectedTown.keySet() ) {
		
			if(selectedTown.get(key)) {
				nodeRequierment.add(key);
			}
		}
		
		fullPath = Main.myPathOptimizer.findPath(nodeRequierment);
		cost = Calcul.getCost(fullPath, weightedAdjMap);
		
		isResults=true;
		Frame[] frames = Frame.getFrames();
		
		for(Frame frame: frames) {
			if(frame.getTitle().equals("LifeLine")) {
				jc.removeAll();
				frame.dispose();
				createMap();
			}
		}
		
		jc.repaint();
		
				
	}
	/**
	 * get the height of the buttons
	 * @param dimensionCountry
	 * @return
	 */
	protected static int getBtnDimY() {	
		
		int height = (int) ((0.75*xMaxOfTown)/5);
		return height;
	}
	
	/**
	 * get the width of the buttons
	 * @param dimensionCountry
	 * @return
	 */
	protected static int getBtnDimX() {	
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
protected static int[] getDimension(Map<Integer, Coord<?, ?>> cityCoordMap) {
		int x = 0, y = 0;
    	int newX, newY;
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
		
		height= height-(height/3);
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
	 * Initialises the maximum value of x and Y for the map (not the frame)
	 * @param plan
	 * @return
	 */
	protected static Map<Integer, Coord<?, ?>> adjustOnFrame(Plan plan) {
		
		
		int xMax=0;
    	int yMax=0;
    	float delta = 0;
    	
    	int xMin=Integer.MAX_VALUE;
    	int yMin=Integer.MAX_VALUE;
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	width = screenSize.getWidth()-((screenSize.getWidth()/5));
		height = screenSize.getHeight();
    	
    	
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
	
	/**
	 * Adjusts the coordinate depending on the resolution of the frame
	 * @param delta
	 * @param plan
	 * @return
	 */
	private static Map<Integer, Coord<?, ?>>  adjust(float delta, Plan plan) {
		
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
	 * Changes the coords stored in the the list containing towns coordinates in the selected area that are not bases
	 * @param deltaX
	 * @param deltaY
	 * @param plan
	 * @return
	 */
	private static Map<Integer, Coord<?, ?>>  adjustMap( int deltaX, int deltaY, Plan plan) {
		
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
	protected static Color chooseColor(int region) {
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

	/**
	 * reinitialize variable to restart program
	 */
	private static void reinitializeVariables() {
		
		Main.myPathOptimizer = new PathOptimizer(cityCoordMap, weightedAdjMap, basesList);
		regionInfo = new HashMap<>();
		regionMap = new Plan();
		yMaxOfTown=0; 
		xMaxOfTown=0;
		width=0;
		height=0;
		fullPath= new ArrayList<Integer>();

		isResults = false;
		cost=0;
		forEntireMap = new Plan();
		ableBtn=true;
		
		Results.isEnd=false;
		Results.pathNames = new ArrayList<String>();
		PaintInterface.regions = new HashMap<>();
		PaintInterface.listOfNamesForTownsSelected=new ArrayList<String>();
		PaintInterface.namesXRegions = new HashMap<>();
		
	}
	


}
