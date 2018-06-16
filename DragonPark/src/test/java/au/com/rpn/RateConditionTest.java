package au.com.rpn;

import java.time.LocalTime;

import org.junit.Test;

import junit.framework.TestCase;

public class RateConditionTest extends TestCase {
    public void testRateCondition() {
        RateCondition condition = new RateCondition();
        condition.setDescription("6am to 9am");
        condition.setAmount(1500);
        condition.setStartTime("06:00");
        condition.setEndTime("09:00");
        condition.setFlatRate(true);
        
        assertEquals(6, condition.getStartTime().getHour());
        assertEquals(0, condition.getStartTime().getMinute());
        assertEquals(9, condition.getEndTime().getHour());
        assertEquals(0, condition.getEndTime().getMinute());
        assertTrue("08:00", condition.match(LocalTime.parse("08:00")));
        assertTrue("09:00", condition.match(LocalTime.parse("09:00")));
        assertTrue("06:00", condition.match(LocalTime.parse("06:00")));
        assertFalse("05:59", condition.match(LocalTime.parse("05:59")));
        assertFalse("09:01", condition.match(LocalTime.parse("09:01")));
        assertFalse("00:00", condition.match(LocalTime.parse("00:00")));
        assertFalse("23:59", condition.match(LocalTime.parse("23:59")));
    }
    
    @Test
    public void testRates() {
        // String name, int price, String type, RateCondition entryCondition, RateCondition exitCondition
       // Rate earlyBird = new Rate("Early Bird", )
    }
}
