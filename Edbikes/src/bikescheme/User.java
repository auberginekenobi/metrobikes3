package bikescheme;

import java.util.Date;
import java.util.ArrayList;

public class User {
	
	//declare fields here
	//private String name;
	private String keyID;
	private Bike bike;
	//private int curNorth;
	//private int curEast;
	private String curDStation;
	private Date curTime;
	private ArrayList<String> trips;
	//private ArrayList<Trip> trips;
	//mke into dictionary? need to add trip things
	
	
	public User (String keyID) {
		//this.name = name;
		this.keyID = keyID;
		bike = null;
		//curNorth = 0;
		//curEast= 0;
		curDStation = "";
		curTime= new Date();
		trips = new ArrayList<String>();
		//trips = new ArrayList<Trip>();
	}
	
	//public String getName(){return name;}
	public String getUID() {return keyID;}
	public Bike getBike(){return bike;}
	public void setBike(Bike val) {bike = val;}
	//public void setCurNorth(int var){curNorth = var;}
	//public int getCurNorth(){return curNorth;}
	//public void setCurEast(int var){curEast = var;}
	//public int getCurEast(){return curEast;}
	public String getCurDStation(){return curDStation;}
	public void setCurDStation(String station){curDStation = station;}
	public void setCurTime(Date x){curTime = x;}
	public Date getCurTime(){return curTime;}

	
	public void addTrip(Trip trip){
		trips.addAll(trip.toStringList());
	}
	
	public ArrayList<String> getTrips(){
		ArrayList<String> currentTrips = new ArrayList<String>();
		for (int i= 0; i<trips.size(); i+=4){
			String curDay =  Clock.format(Clock.getInstance().getDateAndTime());
			String curTrip = trips.get(i);
			Character ch = new Character(curDay.charAt(0)); // how is it this hard to get the first char in a string
			if (curTrip.startsWith(ch.toString())){
			// extract the date char from the string
			// convert to int using Integer.parseInt()
			//String tripDay = curTrip.starttime
				currentTrips.add(curTrip);
				currentTrips.add(trips.get(i+1));
				currentTrips.add(trips.get(i+2));
				currentTrips.add(trips.get(i+3));	
			}
		}
		
		return currentTrips;
	}

	
	
}
