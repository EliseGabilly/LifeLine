package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import obj.Adjacent;
import obj.Coord;

public class TXTReader {
	static NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

	/**
	 * Get the list of adjacent for every node
	 * @return Map<Integer, List<Adjacent<?, ?>>>
	 * key : node code
	 * value : list of adjacent (node, weight)
	 */
	public static Map<Integer, List<Adjacent<?, ?>>> getWeitedAdj() {
		String csvFile = "sandboxAdjacency.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\s+";
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
				try {
					String[] edge = line.split(cvsSplitBy);
					node1 = Integer.parseInt(edge[0]);
					node2 = Integer.parseInt(edge[1]);
					weight = format.parse(edge[3]);
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
				} catch (Exception e) {
					System.out.println("Ligne vide ou titre");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	/**
	 * Get the list of coordonnes for every node
	 * @return Map<Integer, Coords<?, ?>>
	 * key : node code
	 * value : coord(x, y)
	 */
	public static Map<Integer, Coord<?, ?>> getCityCoord() {
		String csvFile = "Coordinates.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\s+";
		int node = 0;
		Number x = 0;
		Number y = 0;
		float xFloat = 0;
		float yFloat = 0;

		Map<Integer, Coord<?, ?>> cityCoordMap = new HashMap<Integer, Coord<?, ?>>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				try {
					String[] edge = line.split(cvsSplitBy);
					node = Integer.parseInt(edge[2]);
					x = format.parse(edge[0]);
					xFloat = x.floatValue();
					y = format.parse(edge[1]);
					yFloat = y.floatValue();
					Coord<?, ?> coord = new Coord<Object, Object>((xFloat/2)+100, (yFloat/3));

					cityCoordMap.put(node, coord);
				} catch (Exception e) {
					System.out.println("Ligne vide ou titre");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	/**
	 * Get the list of bases and theires names
	 * @return Map<Integer,String>
	 * key : node code
	 * value : node name
	 */
	public static Map<Integer, String> getBase() {
		String csvFile = "sandboxBases.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\s+";
		int node = 0;
		String name = "";

		Map<Integer, String> basesMap = new HashMap<Integer, String>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				try {
					String[] edge = line.split(cvsSplitBy);
					node = Integer.parseInt(edge[0]);
					name = edge[1];
					basesMap.put(node, name);

				} catch (Exception e) {
					System.out.println("Ligne vide ou titre");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return basesMap;
	} // public static void getBase ()


	/**
	 * Get the list of data for every node
	 * @return Map<Integer, Object[]>
	 * key : node code
	 * value : [name (string), code region(int)]
	 */
	public static Map<Integer, Object[]> getNames() {
		String csvFile = "NamesXRegions.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\s+";
		int node = 0;
		String name = "";
		int region = 0;

		Map<Integer, Object[]> namesMap = new HashMap<Integer, Object[]>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] edge = null;
				try {
					edge = line.split(cvsSplitBy);
					node = Integer.parseInt(edge[0]);
					name = edge[2];
					region = Integer.parseInt(edge[1]);
					Object[] data = new Object[] {name, region};
					namesMap.put(node, data);

				} catch (Exception e) {
					System.out.println("Ligne vide ou titre");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return namesMap;
	} // public static void getNames ()
	
	/**
	 * Get the list of data for every regions
	 * @return Map<Integer, Object[]>
	 * key : region code
	 * value : [name (string), dencity (int)]
	 */
	public static Map<Integer, Object[]> getRegions() {
		String csvFile = "Table 5 - Regions.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\\s+";
		int node = 0;
		String name = "";
		int dencity = 0;

		Map<Integer, Object[]> namesMap = new HashMap<Integer, Object[]>();

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] edge = null;
				try {
					edge = line.split(cvsSplitBy);
					node = Integer.parseInt(edge[0]);
					name = edge[1];
					dencity = Integer.parseInt(edge[2]);
					Object[] data = new Object[] {name, dencity};
					namesMap.put(node, data);

				} catch (Exception e) {
					System.out.println("Ligne vide ou titre");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return namesMap;
	} // public static void getRegions ()

}
