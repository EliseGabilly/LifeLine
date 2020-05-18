package util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;
import pkg.Calcul;
import pkg.Main;
import pkg.PathFinder_AStar;

public class Printer {

	/**
	 * print cities coordinates map
	 * @param cityCoordMap 
	 */
	public static void printCityCoordMap(Map<Integer, Coord<?, ?>> cityCoordMap) {
		System.out.println("Printing CityCoordMap");
		int city;
		Coord<?, ?> coord;
		for (Map.Entry<Integer, Coord<?, ?>> entry : cityCoordMap.entrySet()) {
			city = entry.getKey();
			coord = entry.getValue();
			System.out.println(city + " : " + coord.toString());
		}
		System.out.println("Total nodes : " + cityCoordMap.size());
		System.out.println("");
	} // public void printCityCoordMap(Map<Integer, Coord<?, ?>> cityCoordMap )

	/**
	 *  print weighted adjacent map
	 * @param weightedAdjMap
	 */
	public static void printWeightedAdjMap(Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap) {
		System.out.println("Printing Adjency map");
		int edgesCounter = 0;
		int city;
		List<Adjacent<?, ?>> adjList;
		for (Map.Entry<Integer, List<Adjacent<?, ?>>> entry : weightedAdjMap.entrySet()) {
			city = entry.getKey();
			adjList = entry.getValue();
			System.out.print(city + " : ");
			for (Adjacent<?, ?> adj : adjList) {
				System.out.print(adj.toString() + " - ");
				edgesCounter++;
			}
			System.out.println("");
		}
		edgesCounter /= 2;
		System.out.println("Total edges : " + edgesCounter);
		System.out.println("");
	} // public void printWeightedAdjMap(Map<Integer, List<Adjacent<?, ?>>>
		// weightedAdjMap)
	
	/**
	 * print the list of bases
	 * @param basesMap
	 */
	public static void printBasesMap(List<Integer> basesMap) {
		System.out.println("Printing base map");
		int city;
		for (Integer  entry : basesMap) {
			city = entry;
			System.out.print(city+", " );
		}
		System.out.println("");
	} // public static void printBasesMap(Map<Integer, String> basesMap) 
	
	/**
	 * print all city id, their regions and their names
	 * @param namesMap
	 */
	public static void printDataMap(Map<Integer, Object[]> namesMap) {
		System.out.println("Printing data map");
		int city;
		Object[] data;
		for (Map.Entry<Integer, Object[]> entry : namesMap.entrySet()) {
			city = entry.getKey();
			data = entry.getValue();
			System.out.println(city + " : " +data[0]+", "+data[1]);
		}
		System.out.println("");
	} // public static void printBasesMap(Map<Integer, String[]> namesMap) 

	/**
	 * Print heuristic map for a given goal
	 * @param heuristicMap
	 * @param goal         
	 */
	public static void printHeuristicdMap(Map<Integer, Float> heuristicMap, int goal) {
		System.out.println("Printing HeuristicMap for " + goal);
		int city;
		float weight;
		for (Map.Entry<Integer, Float> entry : heuristicMap.entrySet()) {
			city = entry.getKey();
			weight = entry.getValue();
			System.out.println(city + " : " + weight);
		}
		System.out.println("");
	} // public void printCityCoordMap(Map<Integer, Coord<?, ?>> cityCoordMap )

	/**
	 * Print a path and his cost  
	 * @param path
	 * @param pFinder
	 */
	public static void printPathData(List<Integer> path, PathFinder_AStar pFinder) {
		System.out.print(path);
		float cost = Calcul.getCost(path, pFinder.getWeightedAdjMap());
		System.out.println(" -> cost : " + cost);
    	if (Arrays.asList(Main.getArgs()).contains("-pv") || Arrays.asList(Main.getArgs()).contains("-printVerif")) {
    		boolean isValid = ValidityChecker.isPathValidSolution(path, pFinder.getStart(), pFinder.getgoal(),
    				pFinder.getWeightedAdjMap());
    		System.out.println("Valide solution : "+isValid);}
	}//public static void printPathData(List<Integer> path, PathFinder_AStar pFinder) 

	/**
	 * Print a map
	 * @param map
	 */
	public static void printMap(Map<?, ?> map) {
		System.out.println("Printing map");
		Object a;
		Object b;
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			a = entry.getKey();
			b = entry.getValue();
			System.out.println(a + " : " + b);
		}
		System.out.println("");
	} // public void printCityCoordMap(Map<Integer, Coord<?, ?>> cityCoordMap )

}
