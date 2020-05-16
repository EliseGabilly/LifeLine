package obj;

import java.util.Map;

public class Plan {
	
	private  String name;
	private  int id;
	private  Map<Integer, Boolean> selectedTown;	
	private  Map<Integer, Coord<?, ?>> cityCoordMap ;
	private  int[] dimension;
	private  Map<Integer, TownInterface<?, ?>> rectCoordMap;

	public Plan() {
		this.selectedTown = null;
		/**
		 * Empty constructor
		 */
	}

	public Plan(String name, int id, Map<Integer, Boolean> selectedTown , Map<Integer, Coord<?, ?>> cityCoordMap,int[] dimension, Map<Integer, TownInterface<?, ?>> rectCoordMap ) {
		this.selectedTown = null;
		this.name = name;
		this.selectedTown = selectedTown;
		this.cityCoordMap = cityCoordMap;
		this.id = id;
		this.dimension = dimension;
		this.rectCoordMap = rectCoordMap;
	}

	public void setCityCoordMap(Map<Integer, Coord<?, ?>> cityCoordMap) {
		this.cityCoordMap = cityCoordMap;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Map<Integer, Boolean> getSelectedTown() {
		return selectedTown;
	}

	public Map<Integer, Coord<?, ?>> getCityCoordMap() {
		return cityCoordMap;
	}
	
	public Map<Integer, TownInterface<?, ?>> getRectCoordMap() {
		return rectCoordMap;
	}

	public void setRectCoordMap(Map<Integer, TownInterface<?, ?>> rectCoordMap) {
		this.rectCoordMap = rectCoordMap;
	}

	public int[] getDimension() {
		return dimension;
	}

}
