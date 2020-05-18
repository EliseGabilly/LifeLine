package util;

import java.util.List;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;

public class ValidityChecker {

	/**
	 * @param start (int id of city)
	 * @param goal (int id of city)
	 * @param cityCoordMap
	 * @return a boolean telling if start and goal nodes are in the node map
	 */
	public static boolean areNodeExisting(int start, int goal, Map<Integer, Coord<?, ?>> cityCoordMap) {
		boolean isStartOk = cityCoordMap.containsKey(start);
		boolean isGoalOk = cityCoordMap.containsKey(goal);
		return isStartOk && isGoalOk;
	} //public static boolean areNodeExisting(int start, int goal, Map<Integer, Coord<?, ?>> cityCoordMap) 

	/**
	 * @param path (List of int id of city)
	 * @param start (int id of city)
	 * @param goal (int id of city)
	 * @param weightedAdjMap
	 * @return boolean according if the path is a solution and a valide path
	 */
	public static boolean isPathValidSolution(List<Integer> path, int start, int goal,
			Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		return isSolusion(path, start, goal) && isPathValid(path, weightedAdjMap);
	} // public static boolean isPathValidSolution

	/**
	 * @param path (List of int id of city)
	 * @param start (int id of city)
	 * @param goal (int id of city)
	 * @return return a boolean verifying if the path begin at the start and end at the goal
	 */
	private static boolean isSolusion(List<Integer> path, int start, int goal) {
		int lastPathNode = path.get(path.size() - 1);
		int firstPathNode = path.get(0);
		return lastPathNode == goal && firstPathNode == start;
	} // private static boolean isSolusion

	/**
	 * @param path (List of int id of city)
	 * @return if the path is valid, meaning are all edges taken existing
	 */
	private static boolean isPathValid(List<Integer> path, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		int s;
		int e;
		boolean allEdgeValid = true;
		boolean oneEdgeValide;
		List<Adjacent<?, ?>> neighborsList;
		for (int i = 0; i < path.size() - 1; i++) {
			s = path.get(i);
			e = path.get(i + 1);
			// for each couple of node/edges
			// we check if the edge is in the adjency map
			neighborsList = weightedAdjMap.get(s);
			oneEdgeValide = false;
			for (Adjacent<?, ?> neighbor : neighborsList) {
				if (neighbor.getCity() == e) {
					oneEdgeValide = true;
					continue;
				}
			}
			allEdgeValid = allEdgeValid && oneEdgeValide;
		}
		return true;
	} // public boolean isPathValid(List<?> path)
}
