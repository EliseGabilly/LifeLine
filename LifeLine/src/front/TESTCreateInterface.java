package front;

import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class TESTCreateInterface {

	@Test
	public void chooseColorTest() {
		Color expected = new Color(168, 86, 69);
		Color result = CreateInterface.chooseColor(1);
		assertEquals(expected,result);
	}
	
	@Test
	public void getBtnDimYTest() {
		CreateInterface.xMaxOfTown = 100;
		assertEquals(15,CreateInterface.getBtnDimY());
	}
	
	@Test
	public void getBtnDimXTest() {
		CreateInterface.yMaxOfTown = 100;
		int [] dim = {200,200};
		CreateInterface.dimensionCountry = dim;
		assertEquals(25,CreateInterface.getBtnDimX());
	}
	
	
		
	
	
}


