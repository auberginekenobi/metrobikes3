package bikescheme;

public class Bike {
	private String bikeID;
	private User user;
	
	public Bike(String bikeID) {
		this.bikeID = bikeID;
	}
	
	public String getBikeID(){return bikeID;}
	public User getUser(){return user;}
	public void setUser(User user){this.user = user;}
	
	
}