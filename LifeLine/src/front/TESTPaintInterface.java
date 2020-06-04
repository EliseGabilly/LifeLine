package front;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import obj.Coord;
import obj.Plan;
import obj.TownInterface;

public class TESTPaintInterface {
	
	@Test
	public void setNameSelectedtownTest() {
		CreateInterface.namesMap = new HashMap<>();
		CreateInterface.namesMap.put(0,  new Object[] {"Ville1", 0});
		CreateInterface.namesMap.put(1,  new Object[] {"Ville4", 1});
		CreateInterface.namesMap.put(2,  new Object[] {"Ville5", 2});
		
		List<String> expected1= Arrays.asList( "Ville2", "Ville3");
		PaintInterface.listOfNamesForTownsSelected= new ArrayList<String>();
		PaintInterface.listOfNamesForTownsSelected.add("Ville1");
		PaintInterface.listOfNamesForTownsSelected.add("Ville2");
		PaintInterface.listOfNamesForTownsSelected.add("Ville3");
		PaintInterface.setNameSelectedtown(0);
		
		assertEquals(expected1,PaintInterface.listOfNamesForTownsSelected );
		List<String> expected2= Arrays.asList( "Ville2", "Ville3","Ville4");
		
		PaintInterface.setNameSelectedtown(1);
		assertEquals(expected2,PaintInterface.listOfNamesForTownsSelected );
		
	}
	
	@Test
	public void addTownInEntireMapTest() {
		
		PaintInterface.listOfNamesForTownsSelected= new ArrayList<String>();
		PaintInterface.listOfNamesForTownsSelected.add("Ville1");
		
		CreateInterface.namesMap.put(3,  new Object[] {"Ville6", 3});
		Map<Integer, Boolean> selectedTown = new HashMap<>();
		selectedTown.put(0, true);
		selectedTown.put(1, false);
		Map<Integer, Coord<?, ?>> cityCoordMap = new HashMap<>();
		int[] dimension = {0,0};
		Map<Integer, TownInterface<?, ?>> rectCoordMap= new HashMap<>();
		Plan plan =new Plan("test", 12, selectedTown, cityCoordMap, dimension, rectCoordMap);
		CreateInterface.regionInfo.put(12, plan);
		Map<Integer, Boolean> expected= new HashMap<>();
		expected.put(0, true);
		expected.put(1, false);
		expected.put(3, true);
		
		PaintInterface.addTownInEntireMap(3, true);
		assertEquals(expected,CreateInterface.regionInfo.get(12).getSelectedTown());
		
		
		PaintInterface.addTownInEntireMap(3, false);
		Map<Integer, Boolean> expected1= new HashMap<>();
		expected1.put(0, true);
		expected1.put(1, false);
		expected1.put(3, false);
		assertEquals(expected1,CreateInterface.regionInfo.get(12).getSelectedTown());
	}
	
	
	@Test
	public void getCoordsInListTest() {
		Map<Integer, Coord<?, ?>> cityCoordMap = new HashMap<>();
		cityCoordMap.put(0, new Coord<>(0,0));
		cityCoordMap.put(1, new Coord<>(1,1));
		
		int[] result = PaintInterface.getCoordsInList(0,cityCoordMap);
		int[] expected = {0,0};
		assertEquals(expected[0],result[0]);
		assertEquals(expected[1],result[1]);
	}


}
