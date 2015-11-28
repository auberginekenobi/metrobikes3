package bikescheme;

import java.sql.Time;

public class Trip {

	private Biketime starttime, endtime;
	private int startnorth, endnorth, starteast, endeast;
	private int cost;
	
	public Trip(Biketime st, Biketime et, int sn, int en, int se, int ee){
		starttime = st;
		endtime = et;
		startnorth = sn;
		endnorth = en;
		starteast = se;
		endeast = ee;
		cost = calcCost(starttime,endtime);
	}
	public String toString(){
		return starttime.toString() + endtime.toString() + "(" + startnorth + "," + starteast + ") ("
		+ endnorth + "," + endeast + ") " + cost; 
	}
	
	
	/*
	 * calculates the cost of a trip:
	 * 1 lb for the first half-hour, 2 lbs for subsequent half-hours
	 * @param start
	 * @param end
	 * 
	 * @return the cost in pounds
	 */
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
