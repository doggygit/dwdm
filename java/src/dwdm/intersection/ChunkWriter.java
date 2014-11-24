package dwdm.intersection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class ChunkWriter {
	
	public static final NumberFormat format = new DecimalFormat("000");
	
	private final OutputStream output;
	
	//space for 32 000 entries
	public byte[] buffer = new byte[24_000];
	
	private int pointer = 0;
	
	public static String filename(int id){
		return "chunk" + format.format(id) + ".cpr";
	}
	
	public ChunkWriter(File folder, int id) throws IOException{
		File f = new File(folder, "chunk" + format.format(id)+ ".cpr");
		f.createNewFile();
		this.output = new FileOutputStream(f);
	}
	
	public int getNextIndex() throws IOException{
		int index = this.pointer * 6;
		this.pointer++;
		if(this.pointer == 3_000){
			output.write(buffer);
			output.flush();
			pointer = 0;
			index = 0;
			Arrays.fill(this.buffer, (byte) 0);
		}
		
		return index;
	}
	
	public void close() throws IOException{
		output.write(buffer, 0, pointer * 6);
		output.flush();
		output.close();
	}
	
	
	
	

}
