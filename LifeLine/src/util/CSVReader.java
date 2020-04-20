package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;

public class CSVReader {
	static NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

	public static Map<Integer, List<Adjacent<?, ?>>> getWeitedAdj() {
		String csvFile = "sandboxAdjacency.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\t";
		int node1 = 0;
		int node2 = 0;
		Number weight = 0;
		float weightFloat = 0;

		Map<Integer, List<Adjacent<?, ?>>> weitedAdjMap = new HashMap<Integer, List<Adjacent<?, ?>>>();
		List<Adjacent<?, ?>> oldAdjList;
		Adjacent<Object, Object> adj;
		List<Adjacent<?, ?>> newAdjList;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use ; as separator
//				System.out.println(line);
				String[] edge = line.split(cvsSplitBy);
				node1 = Integer.parseInt(edge[0]);
				node2 = Integer.parseInt(edge[1]);
				weight=format.parse(edge[3]);
				weightFloat = weight.floatValue();

				// add node2 to adjacent of node1
				adj = new Adjacent<Object, Object>(node2, weightFloat);
				newAdjList = new ArrayList<Adjacent<?, ?>>(Arrays.asList(adj));
				if (weitedAdjMap.containsKey(node1)) {
					oldAdjList = weitedAdjMap.get(node1);
					oldAdjList.addAll(newAdjList);
					weitedAdjMap.put(node1, oldAdjList);
				} else {
					weitedAdjMap.put(node1, newAdjList);
				}
				// add node1 to adjacent of node2
				adj = new Adjacent<Object, Object>(node1, weightFloat);
				newAdjList = new ArrayList<Adjacent<?, ?>>(Arrays.asList(adj));
				if (weitedAdjMap.containsKey(node2)) {
					oldAdjList = weitedAdjMap.get(node2);
					oldAdjList.addAll(newAdjList);
					weitedAdjMap.put(node2, oldAdjList);
				} else {
					weitedAdjMap.put(node2, newAdjList);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return weitedAdjMap;
	} // public static void getWeitedAdj ()

	public static Map<Integer, Coord<?, ?>> getCityCoord() {
		String csvFile = "sandboxCoords.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\t";
		int node = 0;
		Number x = 0;
		Number y = 0;
		float xFloat = 0;
		float yFloat = 0;

		Map<Integer, Coord<?, ?>> cityCoordMap = new HashMap<Integer, Coord<?, ?>>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] edge = line.split(cvsSplitBy);
				node = Integer.parseInt(edge[2]);
				x=format.parse(edge[0]);
				xFloat = x.floatValue();
				y=format.parse(edge[1]);
				yFloat = y.floatValue();
				Coord<?, ?> coord = new Coord<Object, Object>(xFloat, yFloat);

				cityCoordMap.put(node, coord);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cityCoordMap;
	} // public static void getCityCoord ()

}
