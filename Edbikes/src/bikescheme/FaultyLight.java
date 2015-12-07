package bikescheme;


	import java.util.ArrayList;
	import java.util.List;

	/**
	 * Red faulty light output device 
	 * 
	 * @author Lex
	 *
	 */
	public class FaultyLight extends AbstractOutputDevice {
	    
	    public FaultyLight(String instanceName) {
	        super(instanceName);
	    }
	    
	    public void on() {
	        logger.fine(getInstanceName());
	        
	        String deviceClass = "FaultyLight";
	        String deviceInstance = getInstanceName();
	        String messageName = "on";
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
