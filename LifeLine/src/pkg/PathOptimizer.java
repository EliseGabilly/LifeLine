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
	Map<Integer, List<Adjacent<?, ?>>> neededWeightedAdjMap = new HashMap<Integer, List<Adjacent<?, ?>>>();
	List<List<Integer>> thePath = new ArrayList<List<Integer>>(); // all of sub path
	List<Integer> nodeRequired;

	public PathOptimizer(Map<Integer, Coord<?, ?>> cityCoordMap, Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		this.cityCoordMap = cityCoordMap;
		this.weightedAdjMap = weightedAdjMap;
	}

	public List<Integer> findPath(List<Integer> nodeRequired, int mainCity) {
		System.out.print("Main city : " + mainCity);
		System.out.println("   node requierd : " + nodeRequired);
		List<Integer> subPath; // path between two needed cities

		// make neededWeightedAdjMap
		makeNeededWeightedAdjMap(nodeRequired, mainCity);
		// get the path to use with main city at the end and the beginning
		List<Integer> sortedNodeRequired = sortNeededCities(nodeRequired, mainCity);

		// find path between each needed cities
		System.out.println("Finding all sub path for "+sortedNodeRequired);
		PathFinder myPathFinder = new PathFinder(cityCoordMap, weightedAdjMap);
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
		System.out.println("");
		System.out.println("Final path : "+flatPath);
		System.out.println("Cost : "+Calcul.getCost(flatPath, weightedAdjMap));
		return flatPath;
	}

	private void makeNeededWeightedAdjMap(List<Integer> nodeRequired, int mainCity) {
		List<Integer> allNode = new ArrayList<Integer>(nodeRequired);
		allNode.add(mainCity);
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

	}

	private List<Integer> flatenThePath(List<List<Integer>> thePath) {
		List<Integer> flatPath = new ArrayList<Integer>();
		flatPath.add(thePath.get(0).get(0));
		for (List<Integer> subPath : thePath) {
			flatPath.addAll(subPath.subList(1, subPath.size()));
		}
		return flatPath;
	}

	/**
	 * Choose the sorting method
	 * 
	 * @param nodeRequired
	 * @return
	 */
	private List<Integer> sortNeededCities(List<Integer> nodeRequired, int mainCity) {
		List<Integer> sortedPath = new ArrayList<Integer>();
		if (nodeRequired.size() < 5) {
			sortedPath = sortFewNeededCities(nodeRequired, mainCity);
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
	private List<Integer> sortFewNeededCities(List<Integer> nodeRequired, int mainCity) {
		System.out.println("");
		System.out.println("Cheking all path : ");
		// get all path possible
		List<List<Integer>> permutationPath = permute(nodeRequired);
		// get the cheapest path
		List<Integer> cheapestPermutation = new ArrayList<Integer>(); 
		float cheapestCost = (float) Double.POSITIVE_INFINITY;

		List<Integer> testPermutation;
		float testCost;
		for (List<Integer> onePath : permutationPath) {
			testPermutation = onePath;
			testPermutation.add(mainCity);
			testPermutation.add(0, mainCity); // add main city at the begining and the end
			testCost = Calcul.getCost(testPermutation, neededWeightedAdjMap);
			System.out.println(testPermutation + " for " + testCost);
			if (testCost < cheapestCost) {
				cheapestCost = testCost;
				cheapestPermutation = testPermutation;
			}
		}
		System.out.println("");
		return cheapestPermutation;
	}

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
	}

	/**
	 * Approximate the shortest path
	 * 
	 * @param nodeRequired
	 * @return
	 */
	private List<Integer> sortManyNeededCities(List<Integer> nodeRequired) {
		System.out.println("Not implamented yet : sortManyNeededCities ");
		return null;
	}

}
