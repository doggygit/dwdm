package dwdm.index;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.Arrays;

import net.sf.jsi.Rectangle;

public class Lookup {
	
	static LookupConfig config;
	
	public static void main(String[] args){
		try{
			config = new LookupConfig(args);
			System.out.println(config);
		} catch(ParameterException ex){
			ex.print();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	

	
	static class LookupConfig{
		//default path on delphi
		String indexPath = "/vol/fob-vol15/nebenf14/christia/141017.ind";
		String databasePath = "/vol/fob-vol15/nebenf14/christia/141017_eq.so6";
		
		File index;
		File database;
		
		float lat1, lon1;
		float lat2, lon2;
		
		int time1, time2;
		
		LookupConfig(String[] args) throws LookupParameterException{
			if(args.length < 6){
				throw new LookupParameterException("At least six parameters are needed"); 
			}
			
			try{
				lat1 = parseFloat(args[0]);
			} catch (NumberFormatException ex){
				throw new LookupParameterException("Can't parse '" + args[0] + "' into a float");
			}
			
			try{
				lon1 = parseFloat(args[1]);
			} catch (NumberFormatException ex) {
				throw new LookupParameterException("Can't parse '"+  args[1] + "' into a float");
			}
			
			try{
				lat2 = parseFloat(args[2]);
			} catch (NumberFormatException ex){
				throw new LookupParameterException("Can't parse '" + args[2] + "' into a float");
			}
			
			try{
				lon2 = parseFloat(args[3]);
			} catch (NumberFormatException ex){
				throw new LookupParameterException("Can't parse '" + args[3] + "' into a float");
			}
			
			try{
				time1 = parseInt(args[4]);
			} catch (NumberFormatException ex){
				throw new LookupParameterException("Can't parse '" + args[4] + "' into an integer");
			}
			
			try{
				time2 = parseInt(args[5]);
			} catch (NumberFormatException ex){
				throw new LookupParameterException("Can't parse '" + args[5] + "' into an integer");
			}
			
			if(args.length > 6){
				args = Arrays.copyOfRange(args, 6, args.length - 6);
				for(int i = 0; i < args.length; i++){
					if(args[i].equals("-i") && i < args.length - 1){
						indexPath = args[++i];
					}
					if(args[i].equals("-d") && i < args.length - 1){
						databasePath = args[++i];
					}
				}
			}
			
			index = new File(indexPath);
			if(!index.isFile()){
				throw new LookupParameterException("Failed to find index file '" + index.getAbsolutePath() + "'!");
			}
			
			database = new File(databasePath);
			if(!database.isAbsolute()){
				throw new LookupParameterException("Failed to find database file '" + database.getAbsolutePath() + "'!");
			}
			
		}
		
		public String getIndexPath(){
			return indexPath;
		}
		
		public String getDatabasePath(){
			return databasePath;
		}
		
		public File getIndex(){
			return index;
		}
		
		public File getDatabase(){
			return database;
		}
		
		public float getLat1(){
			return lat1;
		}
		
		public float getLon1(){
			return lon1;
		}
		
		public float getLat2(){
			return lat2;
		}
		
		public float getLon2(){
			return lon2;
		}
		
		public int getTime1(){
			return time1;
		}
		
		public int getTime2(){
			return time2;
		}
		
		public Rectangle getRectangle(){
			return new Rectangle(lon1, lat1, lon2, lat2);
		}
		
		public String toString(){
			StringBuilder builder = new StringBuilder("LookupConfig{");
			builder.append("lat1: ").append(lat1).append(", ");
			builder.append("lon1: ").append(lon1).append(", ");
			builder.append("lat2: ").append(lat2).append(", ");
			builder.append("lon2: ").append(lon2).append(", ");
			builder.append("time1: ").append(time1).append(", ");
			builder.append("time2: ").append(time2).append(", ");
			builder.append("index: ").append(getIndexPath()).append(", ");
			builder.append("database: ").append(getDatabasePath()).append("}");
			return builder.toString();
		}
		
		
	}
	
	@SuppressWarnings("serial")
	static class LookupParameterException extends ParameterException{

		static final String example = "<Latitude1> <Longitude1> <Latitude2> <Longitude2> <Time1> <Time2> [-i indexfile] [-d databasefile]";
		public LookupParameterException(String reason) {
			super(reason, example);

		}
		
	}
}
