package pkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;
import util.Printer;

public class PathOptimizer {

	Map<Integer, Coord<?, ?>> cityCoordMap;
	Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap;
	List<Integer> basesList;
	Map<Integer, Integer> closetBaseList = new HashMap<Integer, Integer>();
	Map<Integer, List<Adjacent<?, ?>>> neededWeightedAdjMap = new HashMap<Integer, List<Adjacent<?, ?>>>();
	List<List<Integer>> thePath = new ArrayList<List<Integer>>(); // all of sub path

	public PathOptimizer(Map<Integer, Coord<?, ?>> cityCoordMap, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap,
			List<Integer> basesList) {
		this.cityCoordMap = cityCoordMap;
		this.weightedAdjMap = weightedAdjMap;
		this.basesList = basesList;
	}

	/**
	 * @param nodeRequired
	 * @return
	 */
	public List<Integer> findPath(List<Integer> nodeRequired) {
		System.out.print("Node requierd : " + nodeRequired);
		List<Integer> subPath; // path between two needed cities

		// make neededWeightedAdjMap
		makeNeededWeightedAdjMap(nodeRequired);
		// make closetBase map
		initClosetBases(nodeRequired);
		// get the path to use with main city at the end and the beginning
		List<Integer> sortedNodeRequired = sortNeededCities(nodeRequired);

		// find path between each needed cities
		System.out.println("Finding all sub path for " + sortedNodeRequired);
		PathFinder_AStar myPathFinder = new PathFinder_AStar(cityCoordMap, weightedAdjMap);
		int start;
		int end;
		for (int i = 0; i < sortedNodeRequired.size() - 1; i++) {
			start = sortedNodeRequired.get(i);
			end = sortedNodeRequired.get(i + 1);
			subPath = myPathFinder.findPathBetweenTwoNode(start, end);
			Printer.printPathData(subPath, myPathFinder);
			thePath.add(subPath);
		}

		List<Integer> flatPath = flatenThePath(thePath);
		return flatPath;
	} // public List<Integer> findPath(List<Integer> nodeRequired) 

	/**
	 * init the variable closet base list
	 * based on the distance between each node and distencies to bases
	 * @param nodeRequiered (list of id we have to go throught)
	 */
	private void initClosetBases(List<Integer> nodeRequiered) {
		float weight;
		float minWeight = (float) Double.MAX_VALUE;
		int minBase = 0;

		for (Integer oneNode : nodeRequiered) {
			for (Integer aNode : basesList) {
				weight = Calcul.getDistance(cityCoordMap.get(oneNode), cityCoordMap.get(aNode));
				if (weight < minWeight) {
					minBase = aNode;
					minWeight = weight;
				}
			}
			closetBaseList.put(oneNode, minBase);
		}
	} // private void initClosetBases(List<Integer> nodeRequiered)

	/**
	 * initialize the adgency between all requierd node
	 * 
	 * @param nodeRequired (list of id we have to go through)
	 */
	private void makeNeededWeightedAdjMap(List<Integer> nodeRequired) {
		List<Integer> allNode = new ArrayList<Integer>(nodeRequired);
		allNode.addAll(basesList);
		LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(allNode);
		allNode = new ArrayList<>(hashSet);

		List<Adjacent<?, ?>> lAdj;
		Adjacent<?, ?> oneAdj;
		float weight;
		for (Integer oneNode : allNode) {
			lAdj = new ArrayList<Adjacent<?, ?>>();
			for (Integer aNode : allNode) {
				weight = Calcul.getDistance(cityCoordMap.get(oneNode), cityCoordMap.get(aNode));
				oneAdj = new Adjacent<Integer, Integer>(aNode, weight);
				lAdj.add(oneAdj);
			}
			neededWeightedAdjMap.put(oneNode, lAdj);
		}
	} // private void makeNeededWeightedAdjMap(List<Integer> nodeRequired)

	/**
	 * from List<List<Integer>> to List<Integer>
	 */
	private List<Integer> flatenThePath(List<List<Integer>> thePath) {
		List<Integer> flatPath = new ArrayList<Integer>();
		flatPath.add(thePath.get(0).get(0));
		for (List<Integer> subPath : thePath) {
			flatPath.addAll(subPath.subList(1, subPath.size()));
		}
		return flatPath;
	} // private List<Integer> flatenThePath(List<List<Integer>> thePath)

	/**
	 * Choose the sorting method
	 * 
	 * @param nodeRequired
	 * @return
	 */
	private List<Integer> sortNeededCities(List<Integer> nodeRequired) {
		List<Integer> sortedPath = new ArrayList<Integer>();
		if (nodeRequired.size() < 5) {
			sortedPath = sortFewNeededCities(nodeRequired);
		} else {
			sortManyNeededCities(nodeRequired);
		}
		return sortedPath;
	}

	/**
	 * Test all possible path and calculate their cost
	 * 
	 * @param nodeRequired
	 * @return
	 */
	private List<Integer> sortFewNeededCities(List<Integer> nodeRequired) {
		System.out.println("");
		System.out.println("Cheking all path : ");
		// get all path possible
		List<List<Integer>> permutationPath = permute(nodeRequired);
		// get the cheapest path
		List<Integer> cheapestPermutation = new ArrayList<Integer>();
		float cheapestCost = (float) Double.POSITIVE_INFINITY;

		List<Integer> testPermutation;
		float testCost;
		int closetBase;
		for (List<Integer> onePath : permutationPath) {
			testPermutation = onePath;
			System.out.print(testPermutation + " -> ");
			// add closet base from the first city
			closetBase = closetBaseList.get(testPermutation.get(0));
			testPermutation.add(0, closetBase);
			// add closet base from the last city
			closetBase = closetBaseList.get(testPermutation.get(testPermutation.size() - 1));
			testPermutation.add(closetBase);

			testCost = Calcul.getCost(testPermutation, neededWeightedAdjMap);
			System.out.println(testPermutation + " for " + testCost);
			if (testCost < cheapestCost) {
				cheapestCost = testCost;
				cheapestPermutation = testPermutation;
			}
		}
		System.out.println("");
		return cheapestPermutation;
	} // private List<Integer> sortFewNeededCities(List<Integer> nodeRequired) 

	/**
	 * Calculate all possible permutation of a list
	 * @param original list
	 * @return a list of all possible permutation of a list
	 */
	public static List<List<Integer>> permute(List<Integer> original) {
		if (original.isEmpty()) {
			List<List<Integer>> result = new ArrayList<>();
			result.add(new ArrayList<>());
			return result;
		}
		int firstElement = original.remove(0);
		List<List<Integer>> returnValue = new ArrayList<>();
		List<List<Integer>> permutations = permute(original);
		for (List<Integer> smallerPermutated : permutations) {
			for (int index = 0; index <= smallerPermutated.size(); index++) {
				List<Integer> temp = new ArrayList<>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	} //public static List<List<Integer>> permute(List<Integer> original)

	/**
	 * Approximate the shortest path
	 * 
	 * @param nodeRequired (list of id we have to visit)
	 * @return order the list of id 
	 */
	private List<Integer> sortManyNeededCities(List<Integer> nodeRequired) {
		System.out.println("Not implamented yet : sortManyNeededCities ");
		System.out.println("Complexity of n!");
		return null;
	} // private List<Integer> sortManyNeededCities(List<Integer> nodeRequired)
}
