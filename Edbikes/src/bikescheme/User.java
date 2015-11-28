package bikescheme;

public class User {
	
	//declare fields here
	//private String name;
	private String keyID;
	private boolean bikestat;
	
	
	public User (String keyID) {
		//this.name = name;
		this.keyID = keyID;
		bikestat = false;
	}
	
	//public String getName(){return name;}
	public String getUID() {return keyID;}
	public boolean getHasBike(){return bikestat;}
	public void setHasBike(boolean val) {bikestat = val;}

}
