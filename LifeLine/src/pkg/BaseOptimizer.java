package pkg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import obj.Coord;
import obj.Kmeans;

public class BaseOptimizer {

	Map<Integer, Coord<?, ?>> coordinates;
	Set<Integer> finalBases;
	
	public BaseOptimizer(Map<Integer, Coord<?, ?>> cityCoordMap) {
		this.coordinates = cityCoordMap;
	}
	
	public List<Integer> getBasesList(int number_of_bases, int max_iterations, int method) {
		
		switch (method) {
		case 1:
			while (true) {
				try {
					ComputeBases(this.coordinates, number_of_bases, max_iterations);
					break;
				} catch (Exception e) {
				}
			}
			break;
		case 2:
			double max=1000;
			int iterations = 1;
			outerloop:
			while (true) {
				try {
					max = ComputeBases(this.coordinates, iterations, max_iterations);
					if(max<number_of_bases || iterations == 100) {
						break outerloop;
					}
					iterations = iterations+1;
					System.out.println(iterations);

				} catch (Exception e) {
					
				}
			}
		}
		
		int n = this.finalBases.size(); 
	    List<Integer> aList = new ArrayList<Integer>(n); 
	    for (Integer x : this.finalBases) 
	      aList.add(x);
		return aList;
		
	}

	private double ComputeBases(Map<Integer, Coord<?, ?>> cityCoordMap, int number_of_bases, int max_iterations) {
		Kmeans k = new Kmeans(cityCoordMap, number_of_bases);
		Map<Integer, List<Integer>> placedClusters;
		placedClusters = k.fit(cityCoordMap, max_iterations);
		int iterations = k.iterations;

		describeClusters(placedClusters);
		System.out.println(iterations + " iterations");
		
		
		this.finalBases = placedClusters.keySet();
		double max = computeMaxDistance(placedClusters, k);
		return max;
	}

	public double computeMaxDistance(Map<Integer, List<Integer>> clusters, Kmeans k) {
		double maxDistance = 0;
		Set<Integer> cluster_IDs = clusters.keySet();
		for (Integer cluster_ID : cluster_IDs) {
			float capital_X = coordinates.get(cluster_ID).getX();
			float capital_Y = coordinates.get(cluster_ID).getY();
			Coord<?, ?> cluster_capital_coordinates = new Coord(capital_X, capital_Y);

			List<Integer> Cluster_cities_IDs = clusters.get(cluster_ID);
			for (Integer Cluster_city_ID : Cluster_cities_IDs) {
				float city_X = coordinates.get(Cluster_city_ID).getX();
				float city_Y = coordinates.get(Cluster_city_ID).getY();
				Coord<?, ?> city_coordinates = new Coord(city_X, city_Y);

				double d = k.euclidianDistance(city_coordinates, cluster_capital_coordinates);
				if (d > maxDistance) {
					maxDistance = d;
				}
			}
		}
		System.out.println("Max Distance :" + Math.round(maxDistance)+"km");
		return maxDistance;

	}

	public void describeClusters(Map<Integer, List<Integer>> clusters) {
		Set<Integer> centroids = clusters.keySet();
		int n = 1;

		for (Integer centroidID : centroids) {
			List<Integer> clusterCitiesID = clusters.get(centroidID);
			int clusterSize = clusterCitiesID.size();
			System.out.println("Cluster n°" + n + " - " + clusterSize + " cities - capital:" + centroidID);
			n = n + 1;

			for (Integer cityID : clusterCitiesID) {
				Coord<?, ?> cityCoordinates = coordinates.get(cityID);
				float city_X = cityCoordinates.getX();
				float city_Y = cityCoordinates.getY();
				// System.out.println("City " + cityID + " X:" + city_X + " Y:" + city_Y);
			}
		}

	}

}
