package dwdm.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jsi.rtree.RTree;

public class Creator {
	
	static CreatorConfig config;
	
	public static void main(String[] args){
		try {
			config = new CreatorConfig(args);
			create();
			System.out.println("Wrote index to file " + config.getOutputFile().getAbsolutePath());
		} catch (CreatorParameterException e) {
			e.print();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void create() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(config.getInputFile())));
		RTree rtree = new RTree();
		rtree.init(null);
		int counter = 0;
		String line = reader.readLine();
		while(line != null){
			rtree.add(Utils.parseRectangle(line), counter);
			counter++;
			line = reader.readLine();
		}
		
		reader.close();
		
		OutputStream output = new FileOutputStream(config.getOutputFile());
		ObjectOutputStream objectStream = new ObjectOutputStream(output);
		objectStream.writeObject(rtree);
		objectStream.close();
	}

	
	
	/**
	 * Takes care of reading input arguments
	 * @author Christian Windolf
	 *
	 */
	static class CreatorConfig{
		public static final Map<String, String> databasePaths;
		
		static{
			databasePaths = new HashMap<>();
			//Christians defualt path
			databasePaths.put("doggy", "/home/doggy/data/141017.so6");
			
			//Rafa, Michael, you can hard code your locations here 
			//defaultPaths.put("rafa", "/path/to/141017.so6");
			//defaultPaths.put("michael", "/path/to/141017.so6");
		}
		
		private final File inputFile;
		private final File outputFile;
		
		
		
		CreatorConfig(String[] args) throws CreatorParameterException{
			String inputFile = null, outputFile = null;
			for(int i = 0; i < args.length; i++){
				if(args[i].equals("-i") && i < args.length - 1){
					inputFile = args[++i];
				}
				if(args[i].equals("-o") && i < args.length - 1){
					outputFile = args[++i];
				}
			}
			if(inputFile == null){
				inputFile = databasePaths.get(System.getProperty("user.name"));
			}
			if(inputFile == null){
				throw new CreatorParameterException("you must specifiy an input file with the -i flag");
			}
			this.inputFile = new File(inputFile);
			if(!this.inputFile.exists()){
				throw new CreatorParameterException("The file " + this.inputFile.getAbsolutePath() + " does not exist!");
			}
			if(outputFile == null){
				outputFile = "141017.ind";
			}
			this.outputFile = new File(outputFile);
		}
		public File getInputFile(){
			return inputFile;
		}
		
		public File getOutputFile(){
			return outputFile;
		}
	}
	
	
	@SuppressWarnings("serial")
	static class CreatorParameterException extends ParameterException{
		public CreatorParameterException(String reason) {
			super(reason, "java " + Creator.class.getName() + " -i <inputfile> -o <outputfile>");
		}
	}
}
