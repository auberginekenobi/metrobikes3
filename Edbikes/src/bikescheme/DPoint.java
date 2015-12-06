/**
 * 
 */
package bikescheme;

import java.util.Date;
import java.util.logging.Logger;

/**
 *  
 * Docking Point for a Docking Station.
 * 
 * @author pbj
 *
 */

// FaultyButtonObserver
public class DPoint implements KeyInsertionObserver, BikeDockingObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private KeyReader keyReader; 
    private OKLight okLight;
    private String instanceName;
    private int index;
    private BikeLock bikeLock;
    private BikeSensor bikeSensor;
    private FaultyButton faultyButton;
    private FaultyLight faultyLight;

    private DStation dstation;
    private Hub hub;
 
    private Bike bike;
 
    /**
     * 
     * Construct a Docking Point object with a key reader and green ok light
     * interface devices.
     * 
     * @param instanceName a globally unique name
     * @param index of reference to this docking point  in owning DStation's
     *  list of its docking points.
     */
    public DPoint(String instanceName, int index, DStation dstation) {

     // Construct and make connections with interface devices
        
        keyReader = new KeyReader(instanceName + ".kr");
        keyReader.setObserver(this);
        okLight = new OKLight(instanceName + ".ok");
        this.instanceName = instanceName;
        this.index = index;
        bikeLock = new BikeLock(instanceName + ".bl");
        bikeSensor = new BikeSensor(instanceName + ".bs");
        bikeSensor.setObserver(this);
        faultyButton = new FaultyButton(instanceName + ".fb");
        //faultyButton.setObserver(this);
        faultyLight = new FaultyLight(instanceName + ".fl");
        
        
        this.dstation = dstation;
        this.hub = dstation.getHub();

        
    }
       
    public void setDistributor(EventDistributor d) {
        keyReader.addDistributorLinks(d); 
        bikeSensor.addDistributorLinks(d);
        faultyButton.addDistributorLinks(d);
    }
    
    public void setCollector(EventCollector c) {
        okLight.setCollector(c);
        bikeLock.setCollector(c);
        faultyLight.setCollector(c);
        
    }
    
    
    public String getInstanceName() {
        return instanceName;
    }
    public int getIndex() {
        return index;
    }
    
    
    /** 
     * Runs the hireBike use case when the key is inserted.
     * 
     * unlock bike
     * update user info
     * flash light
     */
    public void keyInserted(String keyId) {
        logger.fine(getInstanceName());
        bikeLock.unlock();
        User user = hub.userMap.get(keyId);
        user.setCurDStation(dstation.getInstanceName());
        user.setCurTime(Clock.getInstance().getDateAndTime());
        user.setBike(bike);
        bike.setUser(user);
        dstation.decNumPointsOcc();
        bike = null;
        okLight.flash();       
    }
    
    /**
     * Runs the addBike and returnBike use cases.
     * 
     */
    
    public void bikeDocked(String bikeId){
    	//System.out.println(bikeId);
    	dstation.incNumPointsOcc();
    	if (hub.bikeMap.containsKey(bikeId)){
    		returnBike(bikeId);
    	}
    	else{
    		addBike(bikeId);
    	}
    }
    
    private void addBike(String bikeId){
    	Bike bike = new Bike(bikeId);
    	hub.bikeMap.put(bikeId, bike);
    	this.bike = bike;
    	bikeLock.lock();
    	okLight.flash();
    }
    
    private void returnBike(String bikeId){
    	logger.fine(getInstanceName());
    	User user = hub.bikeMap.get(bikeId).getUser();
    	Date endtime = (Clock.getInstance().getDateAndTime());
    	Date starttime = user.getCurTime();
    	Trip trip = new Trip(Clock.format(starttime), Clock.format(endtime),
    			user.getCurDStation(),
    			dstation.getInstanceName(),
    			Clock.minutesBetween(starttime,endtime)); 
    	user.addTrip(trip);
    	bike = user.getBike();
    	user.setBike(null);
    	bikeLock.lock();
    	//String dpoint = getInstanceName();
    	okLight.flash();
    	//if bike is faulty show red light, if not show red light
    	/*if (Clock.minutesBetween(endtime, (Clock.getInstance().getDateAndTime())) > 2){}
    	else{
    			reportFaulty(dpoint);
    		}*/
    		
    }
    	
    
   /* private void reportFaulty(String dpoint){
    	faultyButton.press(dpoint);
    	faultyLight.shine();
    }*/
    
    public static int calcCost(Date endtime, Date starttime){
    	int periods = (Clock.minutesBetween(starttime, endtime))/30+1;
    	int cost = 2*(periods-1)+1;
    	return cost;
    }
    
}
