package bikescheme;

public class FaultyButton extends AbstractInputDevice {
	
	private FaultyButtonObserver observer;
	
	public FaultyButton(String instanceName){
		super(instanceName);
	}
	/*
     * 
     *  METHODS FOR HANDLING TRIGGERING INPUT
     *  
     */
    
    /**
     * @param o
     */
    public void setObserver(FaultyButtonObserver o) {
        observer = o;
    }
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
        
        if (e.getMessageName().equals("faultyBike") 
                && e.getMessageArgs().size() == 1) {
            
            String bikeId = e.getMessageArg(0);
            faultyBike(bikeId);
            
        } else {
            super.receiveEvent(e);
        }
    }
    
    /**
     * Model insert key operation on a key reader object
     * 
     * @param keyId
     */
    public void faultyBike(String bikeId) {
        logger.fine(getInstanceName());
        
        observer.faultyBike(bikeId);
    }

    /*
     * 
     *  METHODS FOR HANDLING NON-TRIGGERING INPUT
     *  
     */
    
 

	
	
}
