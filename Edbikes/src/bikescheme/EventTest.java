package bikescheme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class EventTest {

    @Test
    public void test1() {
        assertEquals(
                new Event("1 00:00,C,i,m, a, b, c, d"),
                new Event("1 00:00,C,i,m, a, b, c, d") );
    }
    @Test
    public void test2() {
        assertNotEquals(
                new Event("1 00:00,C,i,m, a, b, c, d"),
                new Event("1 00:00,C,i,m, a, b, c, e") );
    }
    
    /**
     * To test the event class.
     * Conventions:
     * a is an Event we create.
     * b is another event, created by design to be the same as a.
     * c is an event we make different from a.
     * 
     */
    @SuppressWarnings("deprecation")
 @Test
    public void testEventEquals(){
 	   //logger.info("Starting test: testEventClass");
 	   Event a;
 	   Event b;
 	   Event c;
 	   Date dateA = new Date(2015,12,2,7,30);
 	   Date dateB = new Date(2015,12,2,7,30);
 	   Date dateC = new Date(2016,12,2,7,30);
 	   List<String> argsA = new ArrayList<String>();
 	   List<String> argsB = new ArrayList<String>();
 	   List<String> argsC = new ArrayList<String>();
 	   
 	   //same things
 	   a = new Event(dateA, "class","instance","messagename",argsA);
 	   b = new Event(dateB, "class","instance","messagename",argsB);
 	   assertTrue(a.equals(b));
 	   
 	   // two different dates
 	   c = new Event (dateC, "class", "instance", "messagename", argsC);
 	   assertFalse(a.equals(c));
 	   
 	   // different string arguments
 	   c = new Event (dateA,"Class","instance","messagename",argsA);
 	   assertFalse(a.equals(c));
 	   c = new Event (dateA,"class","Instance","messagename",argsA);
 	   assertFalse(a.equals(c));
 	   c = new Event (dateA,"class","instance","Messagename",argsA);
 	   assertFalse(a.equals(c));

 	   // different arguments. 
 	   argsC.add("unordered-tuples");
 	   argsC.add("2");
 	   c = new Event (dateA, "class","instance","messagename",argsC);
 	   assertFalse(a.equals(c));
 	   
 	   // adding same argument to different list
 	   argsA.add("unordered-tuples");
 	   argsA.add("2");
 	   assertTrue(a.equals(c));
 	   
 	   // testing ordered tuples
 	   String[] x = {"asdf","sfdg"};
 	   String[] y = {"gmmk","wert"};
 	   argsA.addAll(Arrays.asList(x));
 	   argsA.addAll(Arrays.asList(y));
 	   argsC.addAll(Arrays.asList(x));
 	   argsC.addAll(Arrays.asList(y));
 	   assertTrue(a.equals(c));
 	   
 	   //ordered tuples 
 	   int i = argsA.indexOf("unordered-tuples");
 	   argsA.add(i, "ordered-tuples");
 	   argsA.remove("unordered-tuples");
 	   i = argsC.indexOf("unordered-tuples");
 	   argsC.add(i, "ordered-tuples");
 	   argsC.remove("unordered-tuples");
 	   assertTrue(a.equals(c));
 	   
 	   argsA.addAll(Arrays.asList(x));
 	   argsB.addAll(Arrays.asList(y));
 	   assertFalse(a.equals(c));
    }
    
    @SuppressWarnings("deprecation")
 @Test public void testEventListEqual(){
 	   //logger.info("Starting test: testEventListEqual");
 	   ArrayList<Event> listA = new ArrayList<Event>();
 	   ArrayList<Event> listB = new ArrayList<Event>();
 	   
 	   Date dateA = new Date(2015,12,2,7,30);
 	   Date dateB = new Date(2015,12,2,7,30);
 	   List<String> argsA = new ArrayList<String>();
 	   List<String> argsB = new ArrayList<String>();
 	   Event a = new Event(dateA, "class","instance","messagename",argsA);
 	   Event b = new Event(dateB, "class", "instance", "messagename", argsB);
 	   //Event b = new Event(dateB, "class","pop","messagename",argsB);
 	   
 	   //check truth
 	   listA.add(a);
 	   listA.add(b);
 	   listB.add(a);
 	   listB.add(b);
 	   assertTrue(Event.listEqual(listA,listB));
 	   
 	   //check order
 	   //order unimportant!
 	   listB.remove(a);
 	   listB.add(a);
 	   assertTrue(Event.listEqual(listA, listB));
 	   
 	   // check additions
 	   listB.remove(b);
 	   listB.add(b);
 	   listB.add(a);
 	   assertFalse(Event.listEqual(listA,listB));
 	   
    }
     
}
