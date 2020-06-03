package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import obj.Coord;
import obj.Plan;
import obj.TownInterface;

public class ByRegion {
	
	protected static PaintInterface regionInterface = new PaintInterface();
	/**
	 * return the list of all town id in the selected region
	 * @param idRegion
	 * @param namesMap
	 * @return
	 */
	protected static List<Integer> getTownForSelectedRegion(int idRegion, Map<Integer, Object[]> namesMap) {
		
		List<Integer> idTownInRegion = new ArrayList<Integer>();
		
		for(int key : namesMap.keySet()) {
			Object [] rgInfo = namesMap.get(key);
			
			if((Integer)rgInfo[1]==idRegion) {
				idTownInRegion.add(key);
			}
		}
		return idTownInRegion;
	}
	
	/**
	 * Get the coordinates of all town in the selected regions
	 * @param idTownInRegion
	 * @param cityCoordMap
	 * @return
	 */
	protected static Map<Integer, Coord<?, ?>> getCoordForSelectedRegion(List<Integer> idTownInRegion, Map<Integer, Coord<?, ?>> cityCoordMap) {
			
			Map<Integer, Coord<?, ?>> regionCoordsMap = new HashMap<>();
			
			for(int idTown : idTownInRegion) {
				Coord<?, ?> coord = cityCoordMap.get(idTown);
				regionCoordsMap.put(idTown, coord);
			}
			return regionCoordsMap;
		}
	/**
	 * Create the frame for only one region --> dimension, adjust coords, create Plan for the selected region
	 * @param name
	 * @param regionCoordsMap
	 * @param key
	 * @return
	 */
	protected static Plan createFrameForRegion(String name, Map<Integer, Coord<?, ?>> regionCoordsMap, int key) {
		
		Plan plan = new Plan();
		Map<Integer, Coord<?, ?>> adjustRegionCoordMap = new HashMap<>();
		
		regionInterface.setBackground(Color.WHITE);
		regionInterface.setLayout(null);
		plan.setCityCoordMap(regionCoordsMap);
		adjustRegionCoordMap=CreateInterface.adjustOnFrame(plan);
		int[] dimensionRegion = CreateInterface.getDimension(adjustRegionCoordMap);
		
		addBtnBack(dimensionRegion);
		
		Map<Integer, Boolean> selectedTown = new HashMap<>();
		Map<Integer, TownInterface<?, ?>> rectMap = plan.getRectCoordMap();
		plan = new Plan(name,key,selectedTown, adjustRegionCoordMap, dimensionRegion,rectMap );
		
		regionInterface.setPreferredSize(new Dimension(dimensionRegion[0],dimensionRegion[1]));

		PaintInterface.forEntireMap = false;
		PaintInterface.idRegion=key;
		CreatFrame.showOnFrame(regionInterface,name, true, plan);
		return plan;
	}
	
	private static void addBtnBack(int [] dimensionRegion){
		regionInterface.removeAll();
		int width = dimensionRegion[0]/2;
		JButton goOnMainPageBtn = new JButton("Go to main page");
    	goOnMainPageBtn.setBounds(dimensionRegion[0]/4, dimensionRegion[1]-30, width, 25);
		goOnMainPageBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(regionInterface);
				  topFrame.dispose();
				  PaintInterface.idRegion=12;
					ClickListener.currentMap = CreateInterface.regionInfo.get(12);
					CreateInterface.ableBtn=true;
					CreateInterface.jc.repaint();
			  } 
			} );
    	
    	
		goOnMainPageBtn.setBackground(new Color(121,171,222));    	
    	regionInterface.add(goOnMainPageBtn);
	}
	
	/**
	 * Create the Plan object for the region without creating the frame --> adjust coords, dimension, maps to click on town
	 * @param selectedTownInRegion
	 * @param region
	 * @return
	 */
	protected static Plan createARegion(Map<Integer, Boolean> selectedTownInRegion, int region) {
	
		String name = (String) CreateInterface.regMap.get(region)[0];
		List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(region,CreateInterface.namesMap);
		Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,CreateInterface.cityCoordMap);
		
		Plan plan = new Plan();
		Map<Integer, Coord<?, ?>> adjustRegionCoordMap = new HashMap<>();
		
		
		plan.setCityCoordMap(regionCoordsMap);
		adjustRegionCoordMap=CreateInterface.adjustOnFrame(plan);
		int[] dimensionRegion = CreateInterface.getDimension(adjustRegionCoordMap);
		
		Map<Integer, TownInterface<?, ?>> rectMap = plan.getRectCoordMap();
		plan = new Plan(name,region,selectedTownInRegion, adjustRegionCoordMap, dimensionRegion,rectMap ); 
				
		return plan;
	}
	
	
	/**
	 * create a frame for a region that already have the Plan made
	 * @param plan
	 * @return
	 */
	protected static Plan recreateFrame(Plan plan) {
		
		
		regionInterface.setBackground(Color.WHITE);
		regionInterface.setLayout(null);
		int[] dimensionRegion = plan.getDimension();
				
		regionInterface.setPreferredSize(new Dimension(dimensionRegion[0],dimensionRegion[1]));
		
		addBtnBack(dimensionRegion);
	
		
		PaintInterface.forEntireMap = false;
		PaintInterface.idRegion=plan.getId();
		CreatFrame.showOnFrame(regionInterface,plan.getName(), true, plan);
		return plan;
	}

}
