package dwdm.index;

import static java.lang.Integer.parseInt;
import gnu.trove.procedure.TIntProcedure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sf.jsi.SpatialIndex;

public class QueryEngine {

	private final SpatialIndex index;
	private final File database;
	private RandomAccessFile randomAccessFile;

	QueryEngine(SpatialIndex index, File database) {
		this.index = index;
		this.database = database;
	}

	public List<String> execute(Query query) throws FileNotFoundException,
			IOException {
		Collector collector = new Collector();
		index.intersects(query.getRectangle(), collector);
		List<String> results = new LinkedList<>();

		randomAccessFile = new RandomAccessFile(database, "r");
		try (FileChannel fc = randomAccessFile.getChannel()) {
			for (Integer id : collector.results) {
				fc.position(id * 151);
				ByteBuffer buffer = ByteBuffer.allocate(150);
				fc.read(buffer);

				String line = new String(buffer.array());
				if (insideTimeRange(line, query)) {
					results.add(line);
				}
			}
			fc.close();
		}
		return results;
	}

	boolean insideTimeRange(String line, Query query) {
		String[] elements = line.split(" ");
		int begin = parseInt(elements[4]);
		int end = parseInt(elements[5]);
		System.out.println("begin: " + begin + " end: " + end);
		return between(begin, query.getTime1(), end) || between (begin, query.getTime2(), end); 

	}
	
	boolean between(int before, int x, int after){
		return x >= before && x <= after;
	}

	class Collector implements TIntProcedure {
		List<Integer> results = new LinkedList<>();

		@Override
		public boolean execute(int arg0) {
			results.add(arg0);
			return true;
		}

	}

}
