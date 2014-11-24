package dwdm.intersection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DebugIO {
	
	public static void main(String[] args) throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("file.txt")));
		List<Entry> list = Utils.read(new FileInputStream(new File(args[0])));
		for(Entry e: list){
			writer.write(e.toString());
			writer.newLine();
		}
		writer.close();
	}
	

}
