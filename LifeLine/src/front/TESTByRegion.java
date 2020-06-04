package front;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import obj.Coord;
import obj.Plan;
import obj.TownInterface;


public class TESTByRegion {
	
	
	@Test
	public void returnListOfTownInOneRegion() {
		int idRegion = 3;
		Map<Integer, Object[]> namesMap = new HashMap<>();
		
		Object[] data = new Object[] {"ville1", 0};
		namesMap.put(0, data);
		data = new Object[] {"ville2", 2};
		namesMap.put(1, data);
		data = new Object[] {"ville3", 3};
		namesMap.put(3, data);
		data = new Object[] {"ville4", 2};
		namesMap.put(4, data);
		data = new Object[] {"ville5", 3};
		namesMap.put(5, data);
		List<Integer> result = ByRegion.getTownForSelectedRegion(idRegion, namesMap);
		List<Integer> expected = Arrays.asList(3, 5);
		assertEquals(expected, result);
	}	

	@Test
	public void getCoordForSelectedRegionTest() {
		List<Integer> idTownInRegion = Arrays.asList(3, 5);
		Map<Integer, Coord<?, ?>> cityCoordMap = new HashMap<>();
		Coord<?, ?> coords = new Coord<>(1,1);
		cityCoordMap.put(0, coords);
		cityCoordMap.put(1, coords);
		cityCoordMap.put(2, coords);
		coords = new Coord<>(2,2);
		cityCoordMap.put(3, coords);
		coords = new Coord<>(1,1);
		cityCoordMap.put(4, coords);
		coords = new Coord<>(3,3);
		cityCoordMap.put(5, coords);
		
		 Map<Integer, Coord<?, ?>> expected = new HashMap<>();
		 expected.put(3, new Coord<>(2,2));
		 expected.put(5, new Coord<>(3,3));
		 Map<Integer, Coord<?, ?>> result = ByRegion.getCoordForSelectedRegion(idTownInRegion, cityCoordMap);
		 assertEquals(expected.get(3).getX(), result.get(3).getX());
		 assertEquals(expected.get(5).getX(), result.get(5).getX());
		 assertEquals(expected.get(3).getY(), result.get(3).getY());
		 assertEquals(expected.get(5).getY(), result.get(5).getY());
		 assertEquals(expected.keySet(), result.keySet());
		
	}
	
	
	
	@Test
	public void createARegionTest() {
		int region = 0;
		Map<Integer, Boolean> selectedTownInRegion = new HashMap<>();
		CreateInterface.cityCoordMap = new HashMap<>();
		CreateInterface.namesMap = new HashMap<>();
		CreateInterface.basesList= Arrays.asList(30, 50);
		CreateInterface.regMap = new HashMap<>();
		CreateInterface.regMap.put(0, new Object[] {"Kigali", 0});
		CreateInterface.regMap.put(1, new Object[] {"Autre", 1});
		
		
		CreateInterface.cityCoordMap.put(0,new Coord<>(0,0));
		CreateInterface.cityCoordMap.put(1,new Coord<>(1,1));
		CreateInterface.cityCoordMap.put(2,new Coord<>(2,2));
		selectedTownInRegion.put(0 ,true);
		selectedTownInRegion.put(1 , false);
		
		
		CreateInterface.namesMap.put(0, new Object[] {"ville1", 0});
		CreateInterface.namesMap.put(1, new Object[] {"ville2", 2});
		CreateInterface.namesMap.put(3, new Object[] {"ville3", 3});
		CreateInterface.namesMap.put(4, new Object[] {"ville4", 2});
		CreateInterface.namesMap.put(5, new Object[] {"ville5", 3});
		
		List<Integer> idTownInRegion = ByRegion.getTownForSelectedRegion(region,CreateInterface.namesMap);
		Map<Integer, Coord<?, ?>> regionCoordsMap = ByRegion.getCoordForSelectedRegion(idTownInRegion,CreateInterface.cityCoordMap);
		
		Plan expected =new Plan();
		expected.setCityCoordMap(regionCoordsMap);
		Map<Integer, Coord<?, ?>> adjustRegionCoordMap=CreateInterface.adjustOnFrame(expected);
		int[] dimensionRegion = CreateInterface.getDimension(adjustRegionCoordMap);
		Map<Integer, TownInterface<?, ?>> rectMap = expected.getRectCoordMap();
		
		Plan result =ByRegion.createARegion( selectedTownInRegion,  region);
		expected = new Plan("Kigali",region,selectedTownInRegion, CreateInterface.adjustOnFrame(expected), dimensionRegion,rectMap ); 
		assertEquals(expected.getName(), result.getName());
		assertEquals(expected.getCityCoordMap().get(0).getX(), result.getCityCoordMap().get(0).getX());
		assertEquals(expected.getCityCoordMap().get(0).getY(), result.getCityCoordMap().get(0).getY());
		assertEquals(expected.getDimension()[0], result.getDimension()[0]);
		assertEquals(expected.getDimension()[1], result.getDimension()[1]);
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getSelectedTown(), result.getSelectedTown());
	}
	

}
