package bikescheme;

/**
 * Trip to be recorded in user's information
 * 
 * @author Lex and Owen
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class Trip {

	private String starttime, endtime, startstation, endstation;
	private int duration;
	private List<String> l;
	
	public Trip(String st, String et, String ss, String es, int duration){
		starttime = st;
		endtime = et;
		startstation = ss;
		endstation = es;
		this.duration = duration;
		l = new ArrayList<String>();
		l.add(starttime);
		l.add(startstation);
		l.add(endstation);
		l.add(""+duration);
	}
	
	public List<String> toStringList(){
		return l;
	}
	
	
	
}
