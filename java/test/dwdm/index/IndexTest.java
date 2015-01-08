package dwdm.index;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import gnu.trove.procedure.TIntProcedure;
import net.sf.jsi.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import org.junit.Test;
import org.junit.Before;

public class IndexTest {
	
	SpatialIndex index;
	
	Rectangle[] rectangles = new Rectangle[4];
	
	ResultFilter filter = new ResultFilter();
	
	@Before
	public void setUp(){
		index = new RTree();
		index.init(null);
		
		rectangles[0] = new Rectangle(0f, 0f, 1f, 1f);
		rectangles[1] = new Rectangle(2f, 2f, 2.1f, 2.1f);
		rectangles[2] = new Rectangle(-1f, 0f, 0f, -1f);
		rectangles[3] = new Rectangle(32f, 32f, 33f, 33f);
		
		for(int i = 0; i < rectangles.length; i++){
			index.add(rectangles[i], i);
		}
	}

	@Test
	public void findsNoRectangle() {
		index.intersects(new Rectangle(-0.5f, -0.5f, 0.5f, 0.5f), filter);
		assertFalse(filter.rect.isEmpty());
	}
	
	@Test
	public void findOneOverlapping() {
		index.intersects(new Rectangle(-0.5f, 0.5f, 0.5f, 1.5f), filter);
		assertEquals((int)filter.rect_id.get(0), 0);
		assertEquals(filter.rect.size(), 1);
	}
	
	@Test
	public void queryAreaInsideARect(){
		index.intersects(new Rectangle(0.1f, 0.5f, 0.2f, 0.6f), filter);
		assertEquals((int) filter.rect_id.get(0), 0);
		assertEquals(filter.rect_id.size(), 1);
	}
	
	class ResultFilter implements TIntProcedure{
		List<Rectangle> rect = new LinkedList<Rectangle>();
		List<Integer> rect_id = new LinkedList<>();

		@Override
		public boolean execute(int id) {
			rect.add(rectangles[id]);
			rect_id.add(id);
			return true;
		}
		
	}

}
