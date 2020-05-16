package obj;

import java.util.Map;

public class Plan {
	

	private  String name;

	private  int id;
	
	private  Map<Integer, Boolean> selectedTown;	
	private  Map<Integer, Coord<?, ?>> cityCoordMap ;
	
	private  int[] dimension;

	public int[] getDimension() {
		return dimension;
	}

	public Plan() {
		this.selectedTown = null;
		/**
		 * 
		 */
	}


	public Plan(String name, int id, Map<Integer, Boolean> selectedTown , Map<Integer, Coord<?, ?>> cityCoordMap,int[] dimension ) {
		this.selectedTown = null;
		this.name = name;
		this.selectedTown = selectedTown;
		this.cityCoordMap = cityCoordMap;
		this.id = id;
		this.dimension = dimension;
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

}
