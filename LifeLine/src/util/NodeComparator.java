package util;

import java.util.Comparator;

import pkg.PathFinder_AStar;

/**
 * A comparison function, which imposes an ordering on id of cities 
 * This comparator is passed to a sort method
 */
public class NodeComparator implements Comparator<Integer> {

	PathFinder_AStar pFinder;
	
	public NodeComparator(PathFinder_AStar pFinder) {
		this.pFinder=pFinder;
	}
	
	/**
	 *Compare two node (given their id) based on their hueristic
	 *Whitch one is the closest to the goal
	 */
	@Override
	public int compare(Integer n1, Integer n2) {
		if (pFinder.getNodeHeurystic(n1) < pFinder.getNodeHeurystic(n2))
			return 1;
		else if (pFinder.getNodeHeurystic(n1) > pFinder.getNodeHeurystic(n2))
			return -1;
		return 0;
	}
}