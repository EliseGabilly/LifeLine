package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import obj.Coord;

public class ByRegion {
	
	static PaintInterface regionInterface = new PaintInterface();
	/**
	 * return the list of all town id in the selected region
	 * @param idRegion
	 * @param namesMap
	 * @return
	 */
	public static List<Integer> getTownForSelectedRegion(int idRegion, Map<Integer, Object[]> namesMap) {
		
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
	public static Map<Integer, Coord<?, ?>> getCoordForSelectedRegion(List<Integer> idTownInRegion, Map<Integer, Coord<?, ?>> cityCoordMap) {
			
			Map<Integer, Coord<?, ?>> regionCoordsMap = new HashMap<>();
			
			for(int idTown : idTownInRegion) {
				Coord<?, ?> coord = cityCoordMap.get(idTown);
				regionCoordsMap.put(idTown, coord);
			}
			return regionCoordsMap;
		}
	
	public static void createFrameForRegion(String name, Map<Integer, Coord<?, ?>> regionCoordsMap) {
		
		Map<Integer, Coord<?, ?>> adjustCityCoordMap = new HashMap<>();
		
		regionInterface.setBackground(Color.WHITE);
		regionInterface.setLayout(null);
		
		adjustCityCoordMap=PrintPoints.adjustOnFrame(regionCoordsMap);
		int[] dimension = PrintPoints.getDimension(adjustCityCoordMap);
		
		
		regionInterface.setPreferredSize(new Dimension(dimension[0],dimension[1]+100));
		regionInterface.cityCoordMap = adjustCityCoordMap;
		
		//TODO
		/*
		
		JButton goOnMainPageBtn = new JButton("Go to main page");
    	
		goOnMainPageBtn.setBounds(dimension[0]/4,dimension[1],150,30);
		goOnMainPageBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  
			  } 
			} );
    	
    	
		goOnMainPageBtn.setBackground(new Color(121,171,222));    	
    	regionInterface.add(goOnMainPageBtn);*/
	
		
    	
		CreatFrame.showOnFrame(regionInterface,name, true);
	}

}
