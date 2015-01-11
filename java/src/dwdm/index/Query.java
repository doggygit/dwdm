package dwdm.index;

import net.sf.jsi.Rectangle;

public class Query {

	private final Rectangle rectangle;
	
	private final int time1, time2;
	
	public Query(Rectangle rect, int time1, int time2){
		this.rectangle = rect;
		this.time1 = time1;
		this.time2 = time2;
	}
	
	public Rectangle getRectangle(){
		return rectangle;
	}

	public int getTime1() {
		return time1;
	}

	public int getTime2() {
		return time2;
	}
	
	public String toString(){
		return rectangle.toString() + " time: " + time1 + "," + time2;
	}
	
}
