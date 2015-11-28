/**
 * 
 */
package bikescheme;

import java.util.logging.Logger;

/**
 *  
 * Docking Point for a Docking Station.
 * 
 * @author pbj
 *
 */
public class DPoint implements KeyInsertionObserver, BikeDockingObserver{
    public static final Logger logger = Logger.getLogger("bikescheme");

    private KeyReader keyReader; 
    private OKLight okLight;
    private String instanceName;
    private int index;
    private BikeLock bikeLock;
    private BikeSensor bikeSensor;

    private DStation dstation;
 
 
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
        
        this.dstation = dstation;

        
    }
       
    public void setDistributor(EventDistributor d) {
        keyReader.addDistributorLinks(d); 
    }
    
    public void setCollector(EventCollector c) {
        okLight.setCollector(c);
        
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    public int getIndex() {
        return index;
    }
    
    /** 
     * Dummy implementation of docking point functionality on key insertion.
     * 
     * Here, just flash the OK light.
     */
    public void keyInserted(String keyId) {
        logger.fine(getInstanceName());
        //add trip start
        bikeLock.unlock();
        okLight.flash();       
    }
    
    public void bikeDocked(String keyId){
    	logger.fine(getInstanceName());
    	User user = dstation.getHub().userMap.get(keyId);
    	user.setHasBike(false);
    	//add trip end 
    	bikeLock.lock();
    	okLight.flash();	
    }
    
}
