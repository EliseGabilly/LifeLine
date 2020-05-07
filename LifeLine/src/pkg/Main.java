package pkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import front.PrintPoints;
import obj.Adjacent;
import obj.Coord;
import util.TXTReader;
import util.Printer;

public class Main {
	private static String[] savedArgs;
    public static String[] getArgs() {
        return savedArgs;
    }

    public static void main(String[] args) {
    	savedArgs=args;
    	
    	// read BDD
    	Map<Integer, Coord<?, ?>> cityCoordMap = TXTReader.getCityCoord();
    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
    		Printer.printCityCoordMap(cityCoordMap);
    	Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap = TXTReader.getWeitedAdj();
    	if (Arrays.asList(args).contains("-pe") || Arrays.asList(args).contains("-printEdge"))
    		Printer.printWeightedAdjMap(weightedAdjMap);
    	Map<Integer, String> basesMap = TXTReader.getBase();
    	if (Arrays.asList(args).contains("-pb") || Arrays.asList(args).contains("-printBases"))
    		Printer.printBasesMap(basesMap);
    	Map<Integer, Object[]> namesMap = TXTReader.getNames();
    	if (Arrays.asList(args).contains("-pna") || Arrays.asList(args).contains("-printNames"))
    		Printer.printDataMap(namesMap);
    	Map<Integer, Object[]> regMap = TXTReader.getRegions();
    	if (Arrays.asList(args).contains("-pr") || Arrays.asList(args).contains("-printRegions"))
    		Printer.printDataMap(regMap);
    	
    	
    	
    	//call the interface
    	
    //	PrintPoints.mainIterface(cityCoordMap);
    	
    	// initialize list of cities we have to visit
    	int min = Collections.min(cityCoordMap.keySet());
    	int max = Collections.max(cityCoordMap.keySet());
    	Random rn = new Random();
    	List<Integer> nodeRequierment = new ArrayList<Integer>();
    	for (int i=0; i<3; i++) {
    		nodeRequierment.add(rn.nextInt(max - min + 1) + min);
    	}
    	int mainCity = rn.nextInt(max - min + 1) + min; // we start and end in the same city

    	// launch the path optimizer
    	//TODO define main city
    	//TODO total cost
    	PathOptimizer myPathOptimizer = new PathOptimizer(cityCoordMap, weightedAdjMap);
    	myPathOptimizer.findPath(nodeRequierment, mainCity);
//    	List<Integer> fullPath = myPathOptimizer.findPath(nodeRequierment, mainCity);

        
    }

}