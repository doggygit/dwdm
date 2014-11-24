package dwdm.intersection;

import static java.lang.Long.parseLong;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	private static final long prime = 47_055_833_459L;
	
	private static final long a = 37_329_742;
	private static final long b = 98_272_284;
	
	private static final int chunks = 24;
	
	public static int hashLong(long x){
		return (int) ((((a*x) + b) % prime) % chunks);
	}
	
	public static int hashLong(String s){
		long l = parseLong(s.substring(1));
		return hashLong(l);
	}
	
	public static List<Entry> read(InputStream input) throws IOException{
		List<Entry> list = new ArrayList<>(32_000);
		byte[] buffer = new byte[6144];
		int len = input.read(buffer);
		while(len != -1){
			for(int i = 0; i < len / 6; i++){
				list.add(new Entry(buffer, i * 6));
			}
			len = input.read(buffer);
		}
		input.close();
		return list;
		
	}

}
