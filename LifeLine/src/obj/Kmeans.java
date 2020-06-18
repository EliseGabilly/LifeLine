package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Kmeans {

	private static final Random random = new Random();
	private Map<Integer, Centroid> centroids;
	private Map<Integer, List<Integer>> clusters;
	private Map<Integer, Coord<?, ?>> cities;
	private int k;
	public int iterations;

	public Kmeans(Map<Integer, Coord<?, ?>> coordinates, int k) {
		this.cities = coordinates;
		this.k = k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getK() {
		return this.k;
	}

	public Map<Integer, List<Integer>> fit(Map<Integer, Coord<?, ?>> coordinates, int maxIterations) {

		this.centroids = getRandomCentroids(coordinates);

		Set<Integer> centroids_IDs = centroids.keySet();
		this.clusters = new HashMap<>();
		Map<Integer, List<Integer>> lastState = new HashMap<>();
		Set<Integer> city_IDs = coordinates.keySet();

		// iterate for a pre-defined number of times
		for (int i = 0; i < maxIterations; i++) {
			boolean isLastIteration = i == maxIterations - 1;

			// in each iteration we should find the nearest centroid for each record
			for (Integer city_ID : city_IDs) {
				int closestCentroid = nearestCentroidFromThisCity(centroids_IDs, city_ID);
				assignToCluster(city_ID, closestCentroid);
			}

			// if the assignments do not change, then the algorithm terminates GOOD
			boolean shouldTerminate = isLastIteration || this.clusters.equals(lastState);
			lastState = this.clusters;
			if (shouldTerminate) {
				this.iterations = i;
				break;
			}

			// at the end of each iteration we should relocate the centroids
			centroids = relocateCentroids(clusters);
			clusters = new HashMap<>();
			this.iterations = i;

		}
		
		Map<Integer, List<Integer>> lastStateWithBases = new HashMap<>();

		List<Integer> lastBases = findBases(lastState);
		Set<Integer> clusterIDs = lastState.keySet();
		
		for(Integer clusterID : clusterIDs) {
			List<Integer> a = lastState.get(clusterID);
			lastStateWithBases.put(lastBases.get(clusterID), a);
		}
		return lastStateWithBases;
	}

	public Map<Integer, Centroid> getRandomCentroids(Map<Integer, Coord<?, ?>> coordinates) {

		Map<Integer, Centroid> centroids = new HashMap<>();

		float min_X = coordinates.get(37).getX();
		float min_Y = coordinates.get(37).getY();
		float max_X = coordinates.get(37).getX();
		float max_Y = coordinates.get(37).getY();

		for (Coord<?, ?> city : coordinates.values()) {
			if (city.getX() <= min_X) {
				min_X = city.getX();
			}
			if (city.getY() <= min_Y) {
				min_Y = city.getY();
			}
			if (city.getX() >= max_X) {
				max_X = city.getX();
			}
			if (city.getY() >= max_Y) {
				max_Y = city.getY();
			}
		}

		for (int i = 0; i < this.k; i++) {
			float X = random.nextFloat() * (max_X - min_X) + min_X;
			float Y = random.nextFloat() * (max_Y - min_Y) + min_Y;
			centroids.put(i, new Centroid(X, Y));
		}

		return centroids;
	}

	private int nearestCentroidFromThisCity(Set<Integer> centroids_ID, Integer cityID) {

		int clusterID = 0;
		double shortestDistance = Double.MAX_VALUE;
		Coord<?, ?> cityCoordinates = cities.get(cityID);
		int i = 0;

		for (Integer centroidID : centroids_ID) {
			float centroid_X = this.centroids.get(centroidID).getX();
			float centroid_Y = this.centroids.get(centroidID).getY();
			Coord<?, ?> centroidCoordinates = new Coord(centroid_X, centroid_Y);

			double D = euclidianDistance(cityCoordinates, centroidCoordinates);
			if (D <= shortestDistance) {
				shortestDistance = D;
				clusterID = i;
			}
			i += 1;
		}

		return clusterID;

	}

	private void assignToCluster(Integer cityID, Integer centroidID) {

		// If cluster is empty, create the list with single city in it
		List<Integer> a = this.clusters.get(centroidID);
		boolean b = a == null;

		if (b) {
			List<Integer> city = new ArrayList<Integer>();
			city.add(cityID);
			this.clusters.put(centroidID, city);
		} else {
			Set<Integer> clusterKeys = clusters.keySet();
			for (Integer clusterKey : clusterKeys) {
				if (clusterKey == centroidID) {
					List<Integer> cities = clusters.get(centroidID);
					cities.add(cityID);
					clusters.put(centroidID, cities);
				}
			}
		}

	}

	private Centroid average(Integer centroidID, List<Integer> citiesInThisCluster) {
		
		int clusterSize = citiesInThisCluster.size();
		if (clusterSize == 0) {
			return centroids.get(centroidID);
		}

		// If it contains just one city, update centroid coordinates
		float avgX = 0, avgY = 0;
		for (Integer cityID : citiesInThisCluster) {
			avgX = avgX + this.cities.get(cityID).getX();
			avgY = avgY + this.cities.get(cityID).getY();
		}
		avgX = avgX / clusterSize;
		avgY = avgY / clusterSize;

		return new Centroid(avgX, avgY);
	}

	public double euclidianDistance(Coord<?, ?> City1, Coord<?, ?> City2) {
		float X1 = City1.getX();
		float Y1 = City1.getY();
		float X2 = City2.getX();
		float Y2 = City2.getY();
		return Math.sqrt(Math.pow(Y2 - Y1, 2) + Math.pow(X2 - X1, 2));
	}

	private Map<Integer, Centroid> relocateCentroids(Map<Integer, List<Integer>> clusters) {

		for (int i = 0; i < clusters.keySet().size(); i++) {
			centroids.put(i, average(i, clusters.get(i)));
		}
		return centroids;
	}
	
	public List<Integer> findBases(Map<Integer, List<Integer>> clusters) {
		Set<Integer> cluster_keys = clusters.keySet();
		List<Integer> cluster_capitals_IDs = new ArrayList();

		for (Integer cluster_key : cluster_keys) {

			float avg_X = 0, avg_Y = 0;

			List<Integer> cluster_city_IDs = clusters.get(cluster_key);
			for (Integer cluster_city_ID : cluster_city_IDs) {
				avg_X = avg_X + this.cities.get(cluster_city_ID).getX();
				avg_Y = avg_Y + this.cities.get(cluster_city_ID).getY();
			}
			avg_X = avg_X / cluster_city_IDs.size();
			avg_Y = avg_Y / cluster_city_IDs.size();

			int closest_city = closestCityFromCenter(avg_X, avg_Y);
			cluster_capitals_IDs.add(closest_city);
		}

		return cluster_capitals_IDs;

	}
	
	private int closestCityFromCenter(float avg_X, float avg_Y) {
		Set<Integer> city_keys = cities.keySet();
		double min_distance = 10000;
		int best_candidate = 0;
		Coord<?, ?> center = new Coord(avg_X, avg_Y);

		for (Integer city_ID : city_keys) {
			float city_X = cities.get(city_ID).getX();
			float city_Y = cities.get(city_ID).getY();
			Coord<?,?> city_coords = new Coord(city_X,city_Y);
			double distance = euclidianDistance(city_coords, center);
			if (distance < min_distance) {
				best_candidate = city_ID;
				min_distance = distance;
			}
		}
		return best_candidate;
	}
	

}