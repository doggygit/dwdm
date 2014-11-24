package dwdm.intersection;

import static java.lang.Long.parseLong;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class Utils {

	private static final long prime = 47_055_833_459L;

	private static final long a = 37_329_742;
	private static final long b = 98_272_284;


	public static int hashLong(long x) {
		return (int) ((((a * x) + b) % prime) % Main.NUMBER_OF_BUCKETS);
	}

	public static int hashLong(String s) {
		long l = parseLong(s.substring(1));
		return hashLong(l);
	}

	public static List<Entry> read(InputStream input) throws IOException {
		List<Entry> list = new ArrayList<>(32_000);
		byte[] buffer = new byte[6144];
		int len = input.read(buffer);
		while (len != -1) {
			for (int i = 0; i < len / 6; i++) {
				try {
					list.add(new Entry(buffer, i * 6));
				} catch (IllegalArgumentException ignored) {

				}
			}
			len = input.read(buffer);
		}
		input.close();
		return list;
	}

	public static List<Entry> join(List<Entry> list1, List<Entry> list2) {
		List<Entry> joined = new LinkedList<>();
		ListIterator<Entry> iterator1 = list1.listIterator();
		ListIterator<Entry> iterator2 = list2.listIterator();
		Entry x, y;
		if (iterator1.hasNext() && iterator2.hasNext()) {
			x = iterator1.next();
			y = iterator2.next();
		} else {
			return joined;
		}
		while (iterator1.hasNext() && iterator2.hasNext()) {

			while (x.getNumber() < y.getNumber() && iterator1.hasNext()) {

				x = iterator1.next();
			}
			while (x.getNumber() > y.getNumber() && iterator2.hasNext()) {
				y = iterator2.next();
			}
			if(!iterator2.hasNext()){
				while(x.getNumber() < y.getNumber()){
					x = iterator1.next();
				}
			}
			if (x.getNumber() == y.getNumber()) {
				Set<Character> set = new HashSet<Character>();
				long number = x.getNumber();
				joined.add(x);
				set.add(x.getChar());
				if (!set.contains(y.getChar())) {
					joined.add(y);
					set.add(y.getChar());
				}
				if (iterator2.hasNext()) {
					y = iterator2.next();
				}
				while (y.getNumber() == number) {

					if (!set.contains(y.getChar())) {
						joined.add(y);
						set.add(y.getChar());
					}
					if (iterator2.hasNext()) {
						y = iterator2.next();
					} else {
						break;
					}
				}

				if (iterator1.hasNext()) {
					x = iterator1.next();
				}
				while (x.getNumber() == number) {

					if (!set.contains(x.getChar())) {
						joined.add(x);
						set.add(x.getChar());
					}
					if (iterator1.hasNext()) {
						x = iterator1.next();
					} else {
						break;
					}
				}
				
			}
		}


		return joined;
	}

}
