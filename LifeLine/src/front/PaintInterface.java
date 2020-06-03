package front;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import obj.Coord;
import obj.Plan;

public class PaintInterface extends JPanel{
	
	protected static boolean forEntireMap =true;	
	protected static int idRegion =12;
	
	protected static Map<Integer, Object[]> regions ;
	private static final long serialVersionUID = 1L;
	protected static int width = 10;
	protected static List<String> listOfNamesForTownsSelected=new ArrayList<String>();
	protected static Map<Integer, Object[]> namesXRegions ;

	
	private static void setNameSelectedtown(int key) {
		String name = (String) CreateInterface.namesMap.get(key)[0];
		if(!listOfNamesForTownsSelected.contains(name)){
			listOfNamesForTownsSelected.add(name);
		}else {
			listOfNamesForTownsSelected.remove(name);
		}
	}
	/**
	 * Print all town with the right the color depending on the region and if the town is selected or a base
	 * base --> circle
	 *town --> square
	 *Selected --> red
	 */
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		Color color = CreateInterface.chooseColor(idRegion);
		
		Plan currentMap = CreateInterface.regionInfo.get(idRegion);
		Map<Integer, Coord<?, ?>> cityCoordMap = currentMap.getCityCoordMap();
		Map<Integer, Boolean> selectedTown = currentMap.getSelectedTown();
		
		int x;
		int y;
		int region;
		
		for(int number:cityCoordMap.keySet()) {
			
			x=(int) cityCoordMap.get(number).getX();
			y=(int) cityCoordMap.get(number).getY();
			
			if (CreateInterface.basesList.contains(number)) {
				g.setColor(Color.black);
				g.fillOval(x, y, width, width);	
			}else {
				if(namesXRegions!=null) {
					region = (int) namesXRegions.get(number)[1];
					color = Color.gray;
					if(!CreateInterface.isResults) color = CreateInterface.chooseColor(region);
				}
				g.setColor(color);
				g.fillRect(x, y, width, width);	
			}
		
		}
		if(CreateInterface.isResults) {
			for(int townInThePath : CreateInterface.fullPath) {
				if (!CreateInterface.basesList.contains(townInThePath)) {
					x=(int) cityCoordMap.get(townInThePath).getX();
					y=(int) cityCoordMap.get(townInThePath).getY();
					
					g.setColor(Color.GREEN);
					g.fillRect(x, y, width, width);	
				}
			}
			
				
				Results.printResultsOnMap(g);
				Results.isEnd=true;
		}
		String names = "Selected towns:  ";
		if(CreateInterface.isResults) {
			for( int i=0; i<listOfNamesForTownsSelected.size();i++) {
				String name="";
				if(i!=listOfNamesForTownsSelected.size()-1) {
					name = listOfNamesForTownsSelected.get(i) + ", ";
				}else {
					name = listOfNamesForTownsSelected.get(i);
				}
				
				names = names +name;
			}
		}else {
			for(String name:listOfNamesForTownsSelected) {
				names = names +name+"	 ";
				CreateInterface.selectedTowns.setText(names+" ");
			}
		}
		

		CreateInterface.selectedTowns.setText(names+" ");
		for(int key : selectedTown.keySet()) {
			if(selectedTown.get(key)) {
				
				int[] coords = getCoordsInList(key, cityCoordMap);
				g.setColor(Color.red);
				g.fillRect(coords[0], coords[1], width, width);
			}	
		}
		
	}
	
	
	
	/**
	 * Add town in the list which indicates if its selected
	 * True--> selected
	 * False--> unselected
	 * @param key
	 * @param selected
	 */
	private void addTownInEntireMap(int key, Boolean selected) {
		
		Map<Integer, Boolean> selectedTownInCountry =  CreateInterface.regionInfo.get(12).getSelectedTown();
		if(selectedTownInCountry!=null && !selectedTownInCountry.isEmpty()&&selectedTownInCountry.containsKey(key)) {
			if(selected) {
				selectedTownInCountry.put(key,true);
				
			}else {
				selectedTownInCountry.put(key,false);
			}
		}else {
			selectedTownInCountry.put(key,true);
		}
		setNameSelectedtown(key);
	}
	
	/**
	 * Add the selected town (or change the value to selected or not) in the region map and the country map
	 * @param key
	 * @param plan
	 */
	
	protected void addTown(int key, Plan plan) {
		Map<Integer, Boolean> selectedTown = plan.getSelectedTown();
		Boolean isSelected=true;
		if(selectedTown!=null && !selectedTown.isEmpty()&&selectedTown.containsKey(key)) {
			if(!selectedTown.get(key)&& listOfNamesForTownsSelected.size()<4) {
				selectedTown.put(key,true);
				isSelected=true;

			}else {
				selectedTown.put(key,false);
				isSelected=false;
			}
		}else if (listOfNamesForTownsSelected.size()<4) {
			selectedTown.put(key,true);
			isSelected=true;
		}
		
		if(!isSelected || (isSelected && listOfNamesForTownsSelected.size()<4)) {
			CreateInterface.createErrorLabel("");
			if(plan.getId()!=12) {
				addTownInEntireMap(key,isSelected);
			}else {
				setNameSelectedtown(key);
				int region = (int) namesXRegions.get(key)[1];
				if(CreateInterface.regionInfo.containsKey(region)) {
					Map<Integer, Boolean> selectedTownInRegion = CreateInterface.regionInfo.get(region).getSelectedTown();
					if(selectedTownInRegion!=null ) {
						selectedTownInRegion.put(key, isSelected);
					}
				}else {
					Map<Integer, Boolean> selectedTownInRegion = new HashMap<>();	
					selectedTownInRegion.put(key, isSelected);
					
					Plan newRegion = ByRegion.createARegion(selectedTownInRegion, region );
					CreateInterface.regionInfo.put(region, newRegion);
				}	
			}
		}else if(!(isSelected && listOfNamesForTownsSelected.size()<4)) {
			CreateInterface.createErrorLabel("You can't select more than 4 towns" );
		}
		
		this.repaint();
	}
	
	/**
	 * get the coords of the town selected in the list of coords depending on the area we are working on
	 * @param key
	 * @param cityCoordMap
	 * @return
	 */
	private int[] getCoordsInList(int key, Map<Integer, Coord<?, ?>> cityCoordMap) {
		int x=(int) cityCoordMap.get(key).getX();
		int y=(int) cityCoordMap.get(key).getY();
		int[] coords = {x, y};

		return coords;
	}


}
