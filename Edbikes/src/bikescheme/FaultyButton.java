package bikescheme;

/**
 * Faulty Button at a docking point
 * 
 * @author Lex
 *
 */

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
        
        if (e.getMessageName().equals("pressFaulty") 
                && e.getMessageArgs().size() == 1) {
            
            String dpoint = e.getMessageArg(0);
            press(dpoint);
            
        } else {
            super.receiveEvent(e);
        }
    }
    
    /**
     * Model of a press function, in which the faulty button of a DPoint is pressed
     * 
     * @param dpoint: a button is assigned to a unique docking point
     */
    public void press(String dpoint) {
        logger.fine(getInstanceName());
        
        observer.faultyBikeReceived(dpoint);
    }

    /*
     * 
     *  METHODS FOR HANDLING NON-TRIGGERING INPUT
     *  
     */
    
 

	
	
}
