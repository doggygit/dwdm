package dwdm.intersection;

import java.io.File;
import java.io.IOException;

public class Main {

	/**
	 * input files
	 */
	public static File file1, file2;

	/**
	 * folder to buffer data on the hard disk.
	 */
	public static File tmpFolder;

	public static File outputFile;

	/**
	 * Does only the argument processing, the actual programm
	 * will be executed in {@link #main()}
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		for (int i = 0; i < args.length; i++) {
			if ("-i".equals(args[i])) {
				File f = exists(new File(args[i + 1]));
				if (f.isDirectory()) {
					file1 = exists(new File(f, "file1.txt"));
					file2 = exists(new File(f, "file2.txt"));
				} else {
					file1 = f;
					file2 = exists(new File(args[i + 1]));
				}
				break;
			}
			
		}
		
		//if no "-i" arg has been found
		if(file1 == null || file2 == null){
			System.err.println("Error, no input file found");
			System.out.println("Please specify the input file with a -i flag");
			System.out.println("You can either write");
			System.out.println("$> java " + Main.class.getName() + " -i <path-to-file1.txt> <path-to-file2.txt>");
			System.out.println("or ");
			System.out.println("$> java " + Main.class.getName() + " -i <path-to-folder");
			System.out.println("With the second solution, it is expected, that the folder contains two files named \"file1.txt\" and \"file2.txt\"");
		}
		
		for(int i = 0; i < args.length; i++){
			if("-t".equals(args[i])){
				tmpFolder = exists(new File(args[i + 1])); 
				break;
			}
		}
		
		if(tmpFolder == null){
			tmpFolder = File.createTempFile("join", "parts");
			if(!tmpFolder.mkdir()){
				System.err.println("Failed to create temporary directory in");
			}
		}
		
		for(int i = 0; i < args.length; i++){
			if("-o".equals(args[i])){
				outputFile = exists(new File(args[i + 1]));
			}
		}
		
		if(outputFile == null){
			outputFile = new File("join.txt");
		}
		
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		
		main();

	}
	
	
	/**
	 * execute the actual program
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main() throws IOException, InterruptedException{
		long start = System.currentTimeMillis();
		File tmp1 = new File(tmpFolder, "file1");
		tmp1.mkdir();
		File tmp2 = new File(tmpFolder, "file2");
		tmp2.mkdir();
		
		Chunker chunker1 = new Chunker(file1, tmp1);
		chunker1.setChunks(24);
		
		Chunker chunker2 = new Chunker(file2, tmp2);
		chunker2.setChunks(24);
		
		Thread t1 = new Thread(chunker1);
		Thread t2 = new Thread(chunker2);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		long stop = System.currentTimeMillis();
		
		long diff = stop - start;
		System.out.println("Used " + diff + " milliseconds (" + (diff/1000) + " s)");
		System.out.println("chunk files are in " + tmpFolder.getAbsolutePath());
	}

	/**
	 * Asserts, that the file exists.
	 * If file not exists, it just exits.
	 * @param f will be returned, if file exists.
	 * @return
	 */
	private static File exists(File f) {
		if (!f.exists()) {
			System.err.println("File " + f.getAbsolutePath()
					+ " does not exist");
			
			// I know it is evil to call this, but it is just a homework :)
			System.exit(1);
		}
		return f;
	}

}
