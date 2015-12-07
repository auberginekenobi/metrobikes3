/**
 * 
 */
package bikescheme;

// import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
// import org.junit.AfterClass;
import org.junit.Test;

import java.util.Date;
/**
 * @author pbj
 *
 */
public class SystemTest {
    private static final String LS = System.getProperty("line.separator");
    private static Logger logger;
    
    private EventDistributor distributor;
    private EventCollector collector;
    
    private List<Event> expectedOutputEvents;
    
    /*
     * 
     * INSERT SYSTEM TESTS HERE
     * 
     * 
     * 
     */
    
    
    
    /**
     * 
     * Setup demonstration system configuration:
     * 
     * Clock clk ----------------->
     * HubTerminal ht <-----------> Hub  -------->   HubDisplay d
     *                              |   
     *                              |   
     *                              |   
     *                              v
     * DSTouchScreen x.ts <---->  
     * CardReader x.cr <------->  DStation x   -------> KeyIssuer x.ki
     *                          |  x in {A,B}
     *                          |
     *                          |
     *                          v
     * KeyReader x.k.kr ---> DPoint x.k    ------> OKLight x.k.ok
     *                       for x.k in {A.1 ... A.5,
     *                                   B.1 ... B.3}
     *  
     *  This configuration is used in all the demonstration tests.
     *  
     *  It is inserted explicitly into each @Test block rather than the 
     *  @Before block so that alternate configurations can also be set up
     *  in this same test class.
     *   
     */
    
    
    public void setupDemoSystemConfig() {
        input("1 07:00, HubTerminal, ht, addDStation, A,   0,   0, 5");
        input("1 07:00, HubTerminal, ht, addDStation, B, 400, 300, 4");
        input("1 07:00, HubTerminal, ht, addDStation, C, 100, 500, 1");
        
        input ("1 7:30, BikeSensor, B.2.bs, dockBike, bike-1");
    	expect("1 7:30, BikeLock, B.2.bl, locked");
    	expect("1 7:30, OKLight, B.2.ok, flashed");
    	input ("1 7:30, BikeSensor, B.1.bs, dockBike, bike-2");
    	expect("1 7:30, BikeLock, B.1.bl, locked");
    	expect("1 7:30, OKLight, B.1.ok, flashed");
    	input ("1 7:30, BikeSensor, C.1.bs, dockBike, bike-3");
    	expect("1 7:30, BikeLock, C.1.bl, locked");
    	expect("1 7:30, OKLight, C.1.ok, flashed");
        
        input ("1 08:00, DSTouchScreen, A.ts, startReg, Brian");
        expect("1 08:00, CardReader, A.cr, enterCardAndPin");
        input ("1 08:01, CardReader, A.cr, checkCard, Brian-card-auth");
        expect("1 08:01, KeyIssuer, A.ki, keyIssued, A.ki-1");
        
       
    }
    
    

    /**
     *  Run the "Register User" use case.
     * 
     */
    @Test
    public void testRegisterUser() {
        logger.info("Starting test: testRegisterUser");

        setupDemoSystemConfig();
        
        // Set up input and expected output.
        // Interleave input and expected output events so that sequence 
        // matches that when describing the use case main success scenario.
        logger.info("registerUser");
        
        input ("2 08:00, DSTouchScreen, A.ts, startReg, Alice");
        expect("2 08:00, CardReader, A.cr, enterCardAndPin");
        input ("2 08:01, CardReader, A.cr, checkCard, Alice-card-auth");
        expect("2 08:01, KeyIssuer, A.ki, keyIssued, A.ki-2");
        
    }
    /**
     *  Run a show high/low occupancy test.
     *  
     *  Display event is scheduled to run only when minutes is multiple of 5,
     *  so only one of the input events should trigger the display. 
     * 
     */
        
    @Test 
    public void testShowHighLowOccupancy() {
        logger.info("Starting test: testShowHighLowOccupancy");
        
        setupDemoSystemConfig();

        input ("2 08:00, Clock, clk, tick");
        input ("2 08:01, Clock, clk, tick");
        input ("2 08:02, Clock, clk, tick");
        expect("2 08:00, HubDisplay, hd, viewOccupancy, unordered-tuples, 6,"
             + "DSName, East, North, Status, #Occupied, #DPoints,"
             + "A,0,0,LOW,0,5," 
        	 + "C,100,500,HIGH,1,1");
    }
    
    /**
     * Run a test to demonstrate basic docking point interface
     * functionality.
     * 
     */
    @Test
    public void testHireBike() {
        logger.info("Starting test: testHireBike");
        
        setupDemoSystemConfig();
        
        input ("2 09:30, KeyReader, B.2.kr, insertKey, A.ki-1"); 
        expect("2 09:30, BikeLock, B.2.bl, unlocked");
        expect("2 09:30, OKLight,   B.2.ok, flashed");
        
    }
    
    @Test
    public void testAddBike(){
    	logger.info("Starting test: testAddBike");
    	setupDemoSystemConfig();
    	input ("1 7:30, BikeSensor, B.3.bs, dockBike, bike-4");
    	expect("1 7:30, BikeLock, B.3.bl, locked");
    	expect("1 7:30, OKLight, B.3.ok, flashed");
    }
    
    
   /**
    * A test for return bike
    */
    
   @Test
    public void testReturnBike(){
    	logger.info("Starting test: testReturnBike");
    	setupDemoSystemConfig();
    	
    	//Brian hires a bike
    	input ("2 09:30, KeyReader, B.2.kr, insertKey, A.ki-1"); 
        expect("2 09:30, BikeLock, B.2.bl, unlocked");
        expect("2 09:30, OKLight,   B.2.ok, flashed");
    	
        //Brian returns a bike
    	input ("2 10:30, BikeSensor, B.2.bs, dockBike, bike-1");
    	expect ("2 10:30, BikeLock, B.2.bl, locked");
    	expect ("2 10:30, OKLight, B.2.ok, flashed");
    }
    
   @Test
    public void testViewActivity(){
    	logger.info("Starting test: testViewOccupancy");
    	setupDemoSystemConfig();
  
    	//Brian hires a bike
    	input ("2 09:30, KeyReader, B.2.kr, insertKey, A.ki-1"); 
        expect("2 09:30, BikeLock, B.2.bl, unlocked");
        expect("2 09:30, OKLight,   B.2.ok, flashed");
    	
        //Brian returns a bike
    	input ("2 10:30, BikeSensor, B.2.bs, dockBike, bike-1");
    	expect ("2 10:30, BikeLock, B.2.bl, locked");
    	expect ("2 10:30, OKLight, B.2.ok, flashed");
    	
    	//Brian hires a bike the next day
    	input ("6 07:30, KeyReader, B.2.kr, insertKey, A.ki-1"); 
        expect("6 07:30, BikeLock, B.2.bl, unlocked");
        expect("6 07:30, OKLight,   B.2.ok, flashed");
    	
        //Brian returns bike next day
        input ("6 08:30, BikeSensor, B.2.bs, dockBike, bike-1");
    	expect ("6 08:30, BikeLock, B.2.bl, locked");
    	expect ("6 08:30, OKLight, B.2.ok, flashed");
    	
    	//Brian hires a bike same day
    	input ("6 09:30, KeyReader, B.2.kr, insertKey, A.ki-1"); 
        expect("6 09:30, BikeLock, B.2.bl, unlocked");
        expect("6 09:30, OKLight,   B.2.ok, flashed");
    	
        //Brian returns bike sameday
        input ("6 10:30, BikeSensor, B.2.bs, dockBike, bike-1");
    	expect ("6 10:30, BikeLock, B.2.bl, locked");
    	expect ("6 10:30, OKLight, B.2.ok, flashed");
    	
    	//Brian needs to know about his trip
    	//Brian has amnesia
    	input ("6 10:44, DSTouchScreen, B.ts, viewActivity");
    	expect("6 10:44, DSTouchScreen, B.ts, viewPrompt, Insert a key");
    	input ("6 10:45, KeyReader, B.kr, keyInsertion, A.ki-1");
    	expect ("6 10:45, DSTouchScreen, B.ts, viewUserActivity, ordered-tuples, 4,"
    			+ "HireTime,HireDS,ReturnDS,Duration (min),"
    			+ "6 07:30,B,B,60,"
    			+ "6 09:30,B,B,60");
    	
    }
    

    
    /*
     * 
     * SUPPORT CODE FOR RUNNING TESTS
     * 
     * NOTHING HERE SHOULD NEED TOUCHING
     * 
     * 
     */
     
    /**
     * Utility method for specifying an input event to drive in.
     * 
     * For use in test methods in this class.
     * 
     * @param inputEventString
     */
    private void input(String inputEventString) {
        distributor.enqueue(new Event(inputEventString));
    }
    
    /**
     * Utility method for specifying an expected output event.
     * 
     * For use in test methods in this class.
     * 
     * Relies on test object field expectedOutputEvents for passing
     * argument output event to checking method. 
     * 
     * @param outputEventString
     */
    private void expect(String outputEventString) {
        expectedOutputEvents.add(new Event(outputEventString));
    }
    
    
    /**
     * Queue up input events at event distributor.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     * 
     * @param es input events
     */
    public void enqueueInputEvents(List<Event> es) {
        for (Event e : es) {
            distributor.enqueue(e);
        }
    }
    
    
    /**
     * Set expected output events.  These are compared with actual 
     * output events after a test is run.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     *
     * @param es expected output events
     */
    public void setExpectedOutputEvents(List<Event> es) {
        expectedOutputEvents = es;
    }
    
    
    /**
     * Initialise logging framework so all log records FINER and above
     * are reported.
     * 
     */
    @BeforeClass
    public static void setupLogger() {
         
        // Enable log record filtering at FINER level.
        logger = Logger.getLogger("bikescheme"); 
        logger.setLevel(Level.FINER);
        
        Logger rootLogger = Logger.getLogger("");
        Handler handler = rootLogger.getHandlers()[0];
        handler.setLevel(Level.FINER);
    }
    
    /**
     * Setup test environment and starting system configuration.
     * 
     * Starting system configuration consists of a Hub object and
     * no Docking Station objects.
     * 
     * Suitable for calling directly as well as from JUnit.
     */
    @Before
    public void setupTestEnvAndSystem() {
       
        // Initialise core event framework objects
        
        distributor = new EventDistributor();
        collector = new EventCollector(); 
        
        // Create a hub object with interface devices.
        
                Hub hub = new Hub();
                
        // Connect up hub interface devices to event framework
                
        hub.setDistributor(distributor);
        hub.setCollector(collector);
         
        // Initialise expected output
        
        expectedOutputEvents = new ArrayList<Event>();
    }
    
   
     /**
     * Run test and check results. 
     * 
     * Run this after input events have been loaded into event queue in 
     * event distributor and expected output events have been loaded into
     * expectedOutputEvents field of object this.
     * 
     * If called directly, not via JUnit runner, the AssertionError, thrown
     * when some assertion fails, should be caught.
     */ 
    @After
    public void runAndCheck() {
        List<Event> actualOutputEvents = runTestAndReturnResults();
        checkTestResults(expectedOutputEvents, actualOutputEvents);
    } 
    
    
    /**
     * Inject input events in distributor queue into system and return the
     * resulting output events.
     * 
     * This method can called directly as an alternative to runAndCheck
     * if results want to be seen, but not checked.
     * 
     * @return Output events from test run
     */
    public List<Event> runTestAndReturnResults() {

        distributor.sendEvents();
        List<Event> actualOutputEvents = collector.fetchEvents();
        return actualOutputEvents;
    }
    
    /**
     * Compare expected and actual output events.  
     * 
     * Uses Event.listEqual() to do the comparison.  This not the same as
     * the normal list equality. 
     * 
     * @see Event
     * 
     * @param expectedEvents
     * @param actualEvents
     */
    public void checkTestResults(
            List<Event> expectedEvents,  // Avoid field name expectedOutputEvents
            List<Event> actualEvents) {
            
        // Log output event sequences for easy comparison when different.

        
        StringBuilder sb = new StringBuilder();
        sb.append(LS);
        sb.append("Expected output events:");
        sb.append(LS);
        for (Event e : expectedEvents) {
            sb.append(e);
            sb.append(LS);
        }
        sb.append("Actual output events:");
        sb.append(LS);
        for (Event e : actualEvents) {
            sb.append(e);
            sb.append(LS);
        }
        System.out.println(sb.toString());
        logger.info(sb.toString());
        
        assertTrue("Expected and actual output events differ",
                Event.listEqual(expectedEvents, actualEvents));
               
    }
}
