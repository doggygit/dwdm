package dwdm.index;

import net.sf.jsi.Rectangle;

import static java.lang.Float.parseFloat;

public class Utils {

	/**
	 * Converts a line from the input file to a rectangle of
	 * jsi.
	 * A longitude value is mapped to x, a latitude value is mapped to y.
	 * @param line
	 * @return
	 */
	public static Rectangle parseRectangle(String line){
		String[] lineArgs = line.split(" ");
		
		float x1 = parseFloat(lineArgs[13]);
		float y1 = parseFloat(lineArgs[12]);
		
		float x2 = parseFloat(lineArgs[15]);
		float y2 = parseFloat(lineArgs[14]);
		
		return new Rectangle(x1, y1, x2, y2);
	}
}
