package front;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import obj.Coord;
import obj.Plan;

public class PaintInterface extends JPanel{
	
	static boolean forEntireMap =true;	
	static int idRegion =12;
	Map<Integer, Object[]> namesXRegions ;
	Map<Integer, Object[]> regions ;

	private static final long serialVersionUID = 1L;
	
	public static int width = 10;
	
	@Override
	public void paint(Graphics g) {
		
super.paint(g);
		
		Color color = PrintPoints.chooseColor(idRegion);
		

		Plan currentMap = PrintPoints.regionInfo.get(idRegion);
		Map<Integer, Coord<?, ?>> cityCoordMap = currentMap.getCityCoordMap();
		Map<Integer, Boolean> selectedTown = currentMap.getSelectedTown();
		
		int x;
		int y;
		int region;
		
		for(int number:cityCoordMap.keySet()) {
			
			x=(int) cityCoordMap.get(number).getX();
			y=(int) cityCoordMap.get(number).getY();
			
			if(namesXRegions!=null) {
				region = (int) namesXRegions.get(number)[1];
				color = PrintPoints.chooseColor(region);
			}
			
			
			g.setColor(color);
			g.fillRect(x, y, width, width);	
		}
		for(int key : selectedTown.keySet()) {
			if(selectedTown.get(key)) {
				
				int[] coords = completeList(key, cityCoordMap);
				g.setColor(Color.red);
				g.fillRect(coords[0], coords[1], width, width);
			}
		}
		
	}
	public void addTownInEntireMap(int key, Boolean selected) {
		
		Map<Integer, Boolean> selectedTownInCountry =  PrintPoints.regionInfo.get(12).getSelectedTown();
		if(selectedTownInCountry!=null && !selectedTownInCountry.isEmpty()&&selectedTownInCountry.containsKey(key)) {
			//townsAllCountry.add(coords);
			if(selected) {
				selectedTownInCountry.put(key,true);
			}else {
				selectedTownInCountry.put(key,false);
			}
		}else {
			selectedTownInCountry.put(key,true);
		}
		
	}
	
	
	public void addTown(int key, Plan plan) {
		Map<Integer, Boolean> selectedTown = plan.getSelectedTown();
		Boolean isSelected;
		if(selectedTown!=null && !selectedTown.isEmpty()&&selectedTown.containsKey(key)) {
			if(!selectedTown.get(key)) {
				selectedTown.put(key,true);
				isSelected=true;

			}else {
				selectedTown.put(key,false);
				isSelected=false;
			}
		}else {
			selectedTown.put(key,true);
			isSelected=true;
		}
		if(plan.getId()!=12) {
			addTownInEntireMap(key,isSelected);
		}else {
			int region = (int) namesXRegions.get(key)[1];
			if(PrintPoints.regionInfo.containsKey(region)) {
				Map<Integer, Boolean> selectedTownInRegion = PrintPoints.regionInfo.get(region).getSelectedTown();
				if(selectedTownInRegion!=null ) {
					selectedTownInRegion.put(key, isSelected);
				}
			}else {
				Map<Integer, Boolean> selectedTownInRegion = new HashMap<>();	
				selectedTownInRegion.put(key, isSelected);
				
				Plan newRegion = ByRegion.createForRegion(selectedTownInRegion, region );
				PrintPoints.regionInfo.put(region, newRegion);
			}
			
			
		}
		
		this.repaint();

	}
	
	public int[] completeList(int key, Map<Integer, Coord<?, ?>> cityCoordMap) {
		int x=(int) cityCoordMap.get(key).getX();
		int y=(int) cityCoordMap.get(key).getY();
		int[] coords = {x, y};

		return coords;
	}


}
