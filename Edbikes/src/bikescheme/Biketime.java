package bikescheme;

public class Biketime {
	private int day, hour, minute;
	
	public Biketime (int day,int hour,int minute){
		this.day=day;
		this.minute=minute;
		this.hour=hour;
	}
	
	
	int getDay(){return day;}
	int getMinute(){return minute;}
	int getHour(){return hour;}
	
	public String toString(){
		return day + " " + hour + ":" + minute;
	}
}
