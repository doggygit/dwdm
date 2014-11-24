package dwdm.intersection
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * If we think of a Hash Table as an array then a hash function is used to
 * generate a unique key for eve ry item in the array. The position the item
 * goes in is known as the slot. Hashing doesn't work very well in situations in
 * which duplicate data is stored. Also it isn't good for searching for anything
 * except a specific key. However a Hash Table is a data structure that offers
 * fast insertion and searching capabilities.
 * 
 * @author Rafael Aviles
 * 
 */
public class BasicJoin {
	
	private static final String fileEntry = "/Users/rafael/Downloads/f1/file1.txt";
	private static final String fileEntry2 = "/Users/rafael/Downloads/f2/fila4.txt";
	static List<Long> arr1 = new ArrayList<Long>();	
	static List<Long> arr2 = new ArrayList<Long>();	

	static String[] theArray;
	int a;

	
	
	private final static File folder1 = new File("/Users/rafael/Downloads/f1/file4.txt");
	private final static File folder2 = new File("/Users/rafael/Downloads/f2/fila4.txt");
	 

	public static void main(String[] args) throws IOException {

		//Set<Long> list = new HashSet<Long>();
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/rafael/Downloads/outal.txt")));
		
		//for (File fileEntry : folder1.listFiles()) {
			//HashSet<String> ar1 = new HashSet<String>();
			BufferedReader reader = new BufferedReader(new FileReader(fileEntry));
			while (reader.readLine() != null) {
				String lino = (reader.readLine().substring(1));
				Long line = Long.parseLong(lino);
				arr1.add(line);
				//Collections.sort(ar1);
			}
			//for (final File fileEntry2 : folder2.listFiles()) {
				//HashSet<String> ar2 = new HashSet<String>();
				BufferedReader reader2 = new BufferedReader(new FileReader(fileEntry2));
				while (reader2.readLine() != null) {
					String lino = (reader2.readLine().substring(1));
					Long line = Long.parseLong(lino);
					//System.out.print(lino + "\n");
					arr1.add(line);
					//Collections.sort(ar2);
				}
				
				//Collections.sort(arr1);
				System.out.print(arr1.size());
				
			    
			    Long current = arr1.get(0);
			    boolean found = false;

			    for (int i = 0; i < arr1.size(); i++) {
			        if (current == arr1.get(i) && !found) {
			            found = true;
			            writer.write(Long.toString(current) + "\n");
			        } else if (current != arr1.get(i)) {
			        	//writer.write(current + "\n");
			            current = arr1.get(i);
			            writer.write(Long.toString(current) + "\n");
			            found = false;
			        }
			    }
				
				
				arr2 = null;
				reader2.close();
			
			
			arr1 = null;
			
		
		
		reader.close();
		
		
		writer.close();
		
		
	
	}
