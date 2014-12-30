package dwdm.index;

@SuppressWarnings("serial")
public class ParameterException extends Exception {
	private final String example;
	
	

	public ParameterException(String reason, String example) {
		super(reason);
		this.example = example;
	}
	
	
	public String getExample(){
		return example;
	}
	
	public void print(){
		System.out.println(getMessage());
		System.out.println("");
		System.out.println("Execution example:");
		System.out.println("$> java " + getExample());
	}

	
}
