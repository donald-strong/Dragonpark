package au.com.rpn;

import java.time.LocalDateTime;
import java.time.LocalTime;

import junit.framework.TestCase;

import org.junit.Test;

public class RateConditionTest extends TestCase {
    public void testRateCondition() {
        RateCondition condition = new RateCondition("6am to 9am", "06:00", "09:00");
        
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
        RateCondition entryCondition = new RateCondition("6am to 9am", "06:00", "09:00");
        RateCondition exitCondition = new RateCondition("3:30pm to 11:30pm", "15:30", "23:30");
        ParkingRate earlyBird = new ParkingRate("Early Bird", 1500, "Flat Rate", entryCondition, exitCondition);
        LocalDateTime time5am = LocalDateTime.parse("2017-12-31T05:00:00");
        LocalDateTime time8am = LocalDateTime.parse("2017-12-31T08:00:00");
        LocalDateTime time10am = LocalDateTime.parse("2017-12-31T10:00:00");
        LocalDateTime time2pm = LocalDateTime.parse("2017-12-31T14:00:00");
        LocalDateTime time4pm = LocalDateTime.parse("2017-12-31T16:00:00");
        LocalDateTime time11pm = LocalDateTime.parse("2017-12-31T23:00:00");
        LocalDateTime time1145pm = LocalDateTime.parse("2017-12-31T23:45:00");

        assertEquals("2017-12-31T08:00", time8am.toString());
        assertFalse("Match 5am", entryCondition.match(time5am));
        assertTrue("Match 8am", entryCondition.match(time8am));
        assertFalse("Match 10am", entryCondition.match(time10am));
        assertFalse("Match 2pm", exitCondition.match(time2pm));
        assertTrue("Match 4pm", exitCondition.match(time4pm));
        assertTrue("Match 11pm", exitCondition.match(time11pm));
        assertFalse("Match 11:45pm", exitCondition.match(time1145pm));
        
        assertTrue("Match Early Bird", earlyBird.match(time8am, time4pm));
        assertTrue("Match Early Bird", earlyBird.match(time8am, time11pm));
        assertFalse("Match Early Bird", earlyBird.match(time5am, time4pm));
        assertFalse("Match Early Bird", earlyBird.match(time2pm, time11pm));
        assertFalse("Match Early Bird", earlyBird.match(time8am, time1145pm));
    }
}
