package bikescheme;

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
	public String toString(){
		return starttime + "," + startstation + "," + endstation + "," + duration; 
	}
	public List<String> toStringList(){
		return l;
	}
	
	
	/*
	 * calculates the cost of a trip:
	 * 1 lb for the first half-hour, 2 lbs for subsequent half-hours
	 * @param start
	 * @param end
	 * 
	 * @return the cost in pounds
	 */
	//OBSOLETE, REMOVE BEFORE WE SUBMIT. NO REALLY, THIS WOULD BE EMBARRASSING.
	public static int calcCost(Biketime start, Biketime end){
		//i am so sorry.
		int numperiods = 0;
		int sd, sh, sm, ed, eh, em;
		int cost=0;
		sd = start.getDay();
		ed = end.getDay();
		sh = start.getHour();
		eh = end.getHour();
		sm = start.getMinute();
		em = end.getMinute();
		
		if (em-sm<0){
			em+=60;
			eh--;
		}
		if (eh-sh<0){
			eh+=12;
			ed--;
		}
		numperiods+=(ed-sd)*48;
		numperiods+=(eh-sh)*2;
		numperiods+=((em/30 - sm/30)+1);
		cost+=1;
		numperiods--;
		cost+=numperiods*2;
		return cost;
	}
	
}
