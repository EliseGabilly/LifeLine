package pkg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;
import util.Printer;

public class Calcul {


	/**
	 * @param goal
	 * @param cityCoordMap
	 * @return the map fill with heuristic distance for each node
	 */
	public static Map<Integer, Float> makeHeuristic(Map<Integer, Coord<?, ?>> cityCoordMap, int goal){
		Coord<?, ?> goalCoord = cityCoordMap.get(goal);
		Map<Integer, Float> heurysticMap = new HashMap<Integer, Float>();
		int city;
	    Coord<?, ?> coord;
	    float distance;
		for(Map.Entry<Integer, Coord<?, ?>> entry : cityCoordMap.entrySet()) {
			city = entry.getKey();
		    coord =  entry.getValue();
		    distance = getDistance(coord, goalCoord);
		    if (city==0) {
		    	distance=0; //TODO if it's the start heuristic = 0
		    }
		    heurysticMap.put(city, distance);
		}
    	if (Arrays.asList(Main.getArgs()).contains("-ph") || Arrays.asList(Main.getArgs()).contains("-printHeuristic"))
    		Printer.printHeuristicdMap(heurysticMap, goal);
		return heurysticMap; 		
	} // public Map<Integer, Float> makeHeuristic(int goal, Map<Integer, Coord<?, ?>> cityCoordMap)
	
	/**
	 * @param node1 
	 * @param node2
	 * @return the strait distance between two points 
	 */
	public static float getDistance (Coord<?, ?> node1, Coord<?, ?> node2) {
		float x1 = node1.getX();
		float y1 = node1.getY();
		float x2 = node2.getX();
		float y2 = node2.getY();
		float xSquare = (int) Math.pow((x2-x1), 2);
		float ySquare = (int) Math.pow((y2-y1), 2);
		float distance = (float) Math.sqrt(xSquare+ySquare);
		return distance;
	} //public float getDistance (int node1, int node2) 


	public static float getCost(List<Integer> path, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		float totalCost =0;
		int s;
		int e;
		float cost;
		for (int i=0; i<path.size()-1; i++) {
			s = path.get(i);
			e= path.get(i+1);
			cost=getEdgeCost(s, e, weightedAdjMap);
			totalCost+=cost;
		}
		return totalCost;
	}
	public static float getEdgeCost(int start, int end, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		List<Adjacent<?, ?>> adjList = weightedAdjMap.get(start);
		float weight=0;
		for (Adjacent<?, ?> a : adjList) {
			if(a.getCity()==end) {
				weight=a.getWeight();
			}
		}
		return weight;
	}
	
}
