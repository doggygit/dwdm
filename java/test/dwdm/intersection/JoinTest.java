package dwdm.intersection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.hasItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class JoinTest {
	
	String[] testStrings1 = {
			"Y0000000000",
			"F0000000001",
			"J0000000025",
			"K0000000027",
			"D1234567892",
			"I5328062530",
			"A9999999999"
			};
	
	String[] testStrings2 = {
			"B0000000001",
			"G0000000001",
			"A0000000002"
	};
	
	List<Entry> list1;
	List<Entry> list2;
	
	@Before
	public void setUp(){
		list1 = new ArrayList<>();
		
		for(String s : testStrings1){
			list1.add(new Entry(s));
		}
		
		
		list2 = new ArrayList<>();
		for(String s: testStrings2){
			list2.add(new Entry(s));
		}
	
	}
	
	@Test
	public void testListIsEmpty(){
		List<Entry> joined = Utils.join(list1, new ArrayList<Entry>());
		assertTrue(joined.isEmpty());
	}
	
	@Test
	public void testJoin1(){
		List<Entry> joined = Utils.join(list1, list2);
		assertThat(joined, hasItem(new Entry("F0000000001")));
	}
	
	@Test
	public void testJoin2(){
		List<Entry> joined = Utils.join(list1, list2);
		assertThat(joined, hasItem(new Entry("B0000000001")));
		assertThat(joined, hasItem(new Entry("G0000000001")));
	}
	
	@Test
	public void testJoin3(){
		List<Entry> joined = Utils.join(list2, list1);
		assertThat(joined, hasItem(new Entry("F0000000001")));
		assertThat(joined, hasItem(new Entry("B0000000001")));
		assertThat(joined, hasItem(new Entry("G0000000001")));
	}
	
	@Test
	public void testJoin4(){
		List<Entry> joined = Utils.join(list1, list2);
		assertEquals(3, joined.size());
	}
	
	@Test
	public void testJoin5() {
		List<Entry> joined = Utils.join(list2, list1);
		assertEquals(3, joined.size());
	}
	
	@Test
	public void testAgainstDuplicates1(){
		list2.add(new Entry("K0000000027"));
		List<Entry> joined = Utils.join(list2, list1);
		assertEquals(4, joined.size());
		assertThat(joined, hasItem(new Entry("F0000000001")));
		assertThat(joined, hasItem(new Entry("B0000000001")));
		assertThat(joined, hasItem(new Entry("G0000000001")));
		assertThat(joined, hasItem(new Entry("K0000000027")));
	}
	
	@Test
	public void testAgainstDuplicates2(){
		list2.add(new Entry("K0000000027"));
		List<Entry> joined = Utils.join(list1, list2);
		assertEquals(4, joined.size());
		assertThat(joined, hasItem(new Entry("F0000000001")));
		assertThat(joined, hasItem(new Entry("B0000000001")));
		assertThat(joined, hasItem(new Entry("G0000000001")));
		assertThat(joined, hasItem(new Entry("K0000000027")));
	}
	
	@Test
	public void testAgainsDuplicates3(){
		list2.add(new Entry("A9999999999"));
		List<Entry> joined = Utils.join(list1, list2);
		assertEquals(4, joined.size());
		
	}
	
	@Test
	public void testAgainsDuplicates4(){
		list2.add(new Entry("A9999999999"));
		List<Entry> joined = Utils.join(list2, list1);
		assertEquals(4, joined.size());
		
	}
	
	@Test
	public void testJoinItSelf(){
		List<Entry> joined = Utils.join(list1, list1);
		for(Entry e : list1){
			assertThat(joined, hasItem(e));
		}
		assertEquals(joined.size(), list1.size());
				
	}
	


}
