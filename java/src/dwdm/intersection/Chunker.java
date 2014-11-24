package dwdm.intersection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Chunker implements Runnable {
	private File inputFile;
	private File outputFolder;

	ChunkWriter[] writer;

	public Chunker(File inputFile, File outputFolder) throws FileNotFoundException {
		if(!inputFile.exists()){
			throw new FileNotFoundException("File " + inputFile.getAbsolutePath() + " does not exist");
		}
		this.inputFile = inputFile;
		if(!outputFolder.isDirectory()){
			throw new FileNotFoundException("File " + inputFile.getAbsolutePath() + " does not exist");
		}
		this.outputFolder = outputFolder;
	}

	public Chunker setChunks(int chunks) throws IOException {
		writer = new ChunkWriter[chunks];
		for (int i = 0; i < writer.length; i++) {
			writer[i] = new ChunkWriter(outputFolder, i);
		}
		return this;
	}

	@Override
	public void run() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line = reader.readLine();
			while(line != null){
				int chunk = Utils.hashLong(line);
				Entry.toByte(writer[chunk], line);
				line = reader.readLine();
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

}
