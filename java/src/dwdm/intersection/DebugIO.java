package dwdm.intersection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DebugIO {
	
	public static void main(String[] args) throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("file.txt")));
		List<Entry> list1 = Utils.read(new FileInputStream(new File(args[0])));
		Collections.sort(list1);
		
		List<Entry> list2 = Utils.read(new FileInputStream(new File(args[1])));
		Collections.sort(list2);
		
		for(Entry e: list1){
			writer.write(e.toString());
			writer.newLine();
		}
		writer.close();
	}
	

}
