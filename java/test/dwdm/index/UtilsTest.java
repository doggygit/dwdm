package dwdm.index;

import static org.junit.Assert.*;


import net.sf.jsi.Rectangle;

import org.junit.Test;

public class UtilsTest {

	String line = "FALKA_TASMI OMDB LTBA B77W 002014 002513 360 360 2 THY761 141017 141017 1766.200000 2898.300000 1801.333333 2875.083333 181220665 16 40.506816 0";
	
	@Test
	public void test(){
		Rectangle rec = Utils.parseRectangle(line);
		
		assertEquals(rec.minX, 2875.083333f, 0.01f);
		assertEquals(rec.minY, 1766.200000f, 0.01f);
		
		assertEquals(rec.maxX, 2898.300000f, 0.01f);
		assertEquals(rec.maxY, 1801.333333f, 0.01f);
	}
}
