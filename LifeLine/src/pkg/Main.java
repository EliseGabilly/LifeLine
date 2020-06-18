package pkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import front.CreateInterface;
import obj.Adjacent;
import obj.Coord;
import util.Printer;
import util.TXTReader;

public class Main {
	private static String[] savedArgs;
    public static String[] getArgs() {
        return savedArgs;
    }
    public static PathOptimizer myPathOptimizer;
    public static BaseOptimizer myBaseOptimizer;

    public static void main(String[] args) {
    	
    	savedArgs=args;
    	// read BDD
    	Map<Integer, Coord<?, ?>> cityCoordMap = TXTReader.getCityCoord();
    	if (Arrays.asList(args).contains("-pn") || Arrays.asList(args).contains("-printNode"))
    		Printer.printCityCoordMap(cityCoordMap);
    	
    	Map<Integer, List<Adjacent<?, ?>>> weightedAdjMap = TXTReader.getWeitedAdj();
    	if (Arrays.asList(args).contains("-pe") || Arrays.asList(args).contains("-printEdge"))
    		Printer.printWeightedAdjMap(weightedAdjMap);
    	
    	List<Integer>  basesList = TXTReader.getBase();
    	if (Arrays.asList(args).contains("-pb") || Arrays.asList(args).contains("-printBases"))
    		Printer.printBasesMap(basesList);
    	
    	Map<Integer, Object[]> namesMap = TXTReader.getNames();
    	if (Arrays.asList(args).contains("-pna") || Arrays.asList(args).contains("-printNames"))
    		Printer.printDataMap(namesMap);
    	
    	Map<Integer, Object[]> regMap = TXTReader.getRegions();
    	if (Arrays.asList(args).contains("-pr") || Arrays.asList(args).contains("-printRegions"))
    		Printer.printDataMap(regMap);
    
    	myBaseOptimizer = new BaseOptimizer(cityCoordMap);
    	basesList = myBaseOptimizer.getBasesList(8, 10000, 1);

    	myPathOptimizer = new PathOptimizer(cityCoordMap, weightedAdjMap, basesList);

    	CreateInterface.mainIterface(cityCoordMap,  namesMap,  regMap,  basesList, weightedAdjMap );
    }

}