package pkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import obj.Adjacent;
import obj.Coord;
import util.NodeComparator;
import util.ValidityChecker;

public class PathFinder_AStar {

	public final Logger logger = Logger.getGlobal();
	
	Map<Integer, Coord<?, ?>> cityCoordMap;
	Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap;
	Map<Integer, Float> heurysticMap;
	
	int start;
	int goal;
	
	PathFinder_AStar(Map<Integer, Coord<?, ?>> cityCoordMap, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		this.cityCoordMap = cityCoordMap;
		this.weightedAdjMap = weightedAdjMap;
	}

	public List<Integer> findPathBetweenTwoNode(int start, int goal){  
		this.start = start;
		this.goal=goal;
		boolean isValid = ValidityChecker.areNodeExisting(start, goal, cityCoordMap);
		if (!isValid) {
			logger.warning("Start or goal doesn't exist");
			return null;
		}
		this.heurysticMap = Calcul.makeHeuristic(cityCoordMap, goal);
		List<Integer> myPath=A_Star(start, goal);
		return myPath;
	} // public List<?> findPath(int start, int goal)

	private List<Integer> reconstruct_path( Map<Integer, Integer> cameFrom, int current){
		List<Integer> total_path = new ArrayList<Integer>();
		total_path.add(current);
		
	    while(cameFrom.keySet().contains(current) && current!=start) {
	        current = cameFrom.get(current);
	        total_path.add(0, current); // add at the beginning of the list
	    }
	    return total_path;
	}
	
	/** 
	 * A* finds a path from start to goal.
	 * h is the heuristic function. h(n) estimates the cost to reach goal from node n.
	 * @param start
	 * @param goal
	 * @return
	 */
	private List<Integer> A_Star(int start, int goal){
		// The set of discovered nodes that may need to be (re-)expanded.
	    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
		NodeComparator comparator = new NodeComparator(this);
		//TODO first number is the initial capacity of the queue, find logical value to initialize it
        PriorityQueue<Integer> openSet = new PriorityQueue<Integer>(10, comparator);
        // Initially, only the start node is known.
        openSet.add(start);
        
        // For node n, cameFrom[n] is the node immediately preceding it 
        // on the cheapest path from start to n currently known.
        Map<Integer, Integer> cameFrom=new HashMap<Integer, Integer>();
	    // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
        Map<Integer, Float>gScore=new HashMap<Integer, Float>();
	    // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
	    // how short a path from start to finish can be if it goes through n.
        Map<Integer, Float>fScore=new HashMap<Integer, Float>();

	    float inf = (float) Double.POSITIVE_INFINITY;        
        int city;
        for(Map.Entry<Integer, Float> entry : heurysticMap.entrySet()) {
			city = entry.getKey();
			cameFrom.put(city, null); // initialize map 
			gScore.put(city, inf); // initialize map with default value of Infinity
			fScore.put(city, inf); // initialize map with default value of Infinity
		}
        gScore.replace(start, (float) 0);
        fScore.replace(start, (float) 0);
        
        int current;
        List<Adjacent<?, ?>> currentNeighbors;
        int neighborCode;
	    while (!openSet.isEmpty()) {
	        // This operation can occur in O(1) time if openSet is a min-heap or a priority queue
	    	current = getCurrent(openSet, fScore);
	        if (current == goal) {
	            return reconstruct_path(cameFrom, current);
	        }
	        
	        openSet.remove(current);
	        currentNeighbors = weightedAdjMap.get(current);
	        for(Adjacent<?, ?> neighbor : currentNeighbors) {
	            // neighbor.getWeight() is the weight of the edge from current to neighbor
	            // tentative_gScore is the distance from start to the neighbor through current
	            float tentative_gScore = gScore.get(current) + neighbor.getWeight();
	            neighborCode=neighbor.getCity();
            	
	            if (tentative_gScore < gScore.get(neighborCode)) {

	                // This path to neighbor is better than any previous one. Record it!
	                cameFrom.replace(neighborCode, current);
	                gScore.replace(neighborCode, tentative_gScore);
	                fScore.replace(neighborCode, (gScore.get(neighborCode)+neighbor.getWeight()));

	                if (!openSet.contains(neighborCode)) {
	                	openSet.add(neighborCode);
	                }
	                    
	            }
	        }
	    }
	    logger.warning("Open set is empty but goal was never reached");
		return null;
	}
	
	private int getCurrent(PriorityQueue<Integer> openSet, Map<Integer, Float>fScore ) {
		//return the node in openSet having the lowest fScore value
    	int min = (int) Double.POSITIVE_INFINITY;
    	float minScore = (float) Double.POSITIVE_INFINITY;
    	float value;
    	for(Integer itm : openSet) {
    		value=fScore.get(itm);
    		if(value<minScore) {
    			minScore=value;
    			min=itm;
    		}
    	}
    	if (min == (float) Double.POSITIVE_INFINITY) {
    		logger.warning("getCurrent min is Infinity");
    	}
		return min;
	}
	
	
	public Map<Integer, List<Adjacent<?, ?>>> getWeightedAdjMap() {
		return weightedAdjMap;
	}
	
	public float getNodeHeurystic(int node){
		float h=heurysticMap.get(node);
		return h;
	}
	
	public int getStart() {
		return start;
	}
	public int getgoal() {
		return goal;
	}

}
