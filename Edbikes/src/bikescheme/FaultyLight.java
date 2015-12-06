package bikescheme;


	import java.util.ArrayList;
	import java.util.List;

	/**
	 * Red faulty light output device 
	 * 
	 * @author pbj
	 *
	 */
	public class FaultyLight extends AbstractOutputDevice {
	    
	    public FaultyLight(String instanceName) {
	        super(instanceName);
	    }
	    
	    public void shine() {
	        logger.fine(getInstanceName());
	        
	        String deviceClass = "FaultyLight";
	        String deviceInstance = getInstanceName();
	        String messageName = "shone";
	        List<String> valueList = new ArrayList<String>();
	 
	        super.sendEvent(
	            new Event(
	                Clock.getInstance().getDateAndTime(), 
	                deviceClass,
	                deviceInstance,
	                messageName,
	                valueList));
	        
	    }

}
