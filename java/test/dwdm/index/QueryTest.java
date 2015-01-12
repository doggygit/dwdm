package dwdm.index;

import static org.junit.Assert.*;
import static org.hamcrest.core.StringStartsWith.startsWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.sf.jsi.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class QueryTest {
	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	String[] lines = { 
			"$APkm_$APkn EGLF KAPA FA7X 001357 001449 110 90 1 N250LG 141017 141017 2394.583333 -6252.583333 2391.416667 -6258.333333 181210770 53 5.431014 0",
			"URNIL_LARKI LFPG LLBG B738 002508 002749 350 350 2 AIZ5388 141017 141017 2261.100000 1591.716667 2243.933333 1610.300000 181220037 59 22.623201 0",
			"UVIVI_AGALU VHHH LFPG B77W 014344 014640 360 360 2 AFR185 141017 141017 3095.250000 1073.000000 3088.283333 1043.133333 181211101 59 19.843502 0",
			"*TRL_DILMO KIAD HAAB B788 000316 001300 390 390 2 ETH501 141017 141017 2244.233333 1340.416667 2166.983333 1388.600000 181211823 44 86.356811 0"

	};
	
	String[] lines_eq;

	SpatialIndex index;
	
	QueryEngine engine;

	@Before
	public void setUp() throws IOException {
		File f = tmp.newFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		index = new RTree();
		index.init(null);
		lines_eq = new String[lines.length];
		for(int i = 0; i < lines.length; i++){
			lines_eq[i] = ensureLength(lines[i]);
			writer.write(lines_eq[i]);
			writer.newLine();
			index.add(Utils.parseRectangle(lines_eq[i]), i);
		}
		writer.flush();
		writer.close();
		engine = new QueryEngine(index, f);

	}

	
	
	String ensureLength(String s){
		StringBuffer buffer = new StringBuffer(s);
		if(s.length() < 151){
			for(int i = s.length(); i < 150; i++){
				buffer.append(" ");
			}
		}
		return buffer.toString();
	}

	@Test
	public void testOneMatch() throws FileNotFoundException, IOException{
		Query query = new Query(new Rectangle(-6251, 2391, -6258, 2394), 1300, 1400);
		List<String> result = engine.execute(query);
		assertEquals(1, result.size());
		assertThat(result.get(0), startsWith(lines[0]));
	}
	
	@Test
	public void testNoMatch() throws FileNotFoundException, IOException{
		Query query = new Query(new Rectangle(-6251, 2391, -6858, 2394), 1900, 1901);
		List<String> result = engine.execute(query);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testNoMatch2() throws FileNotFoundException, IOException {
		Query query = new Query(new Rectangle(2, -10, 50, 22), 1300, 1400);
		List<String> result = engine.execute(query);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testNoMatch3() throws FileNotFoundException, IOException{
		Query query = new Query(new Rectangle(2, -10, 450, 22), 1900, 1901);
		List<String> result = engine.execute(query);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void testMultipleMatches() throws FileNotFoundException, IOException {
		Query query = new Query(new Rectangle(-7000, 2000, 1600, 2400), 1300, 3000);
		List<String> result = engine.execute(query);
		assertEquals(3, result.size());
	}
}
