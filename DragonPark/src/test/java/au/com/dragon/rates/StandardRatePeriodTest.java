package au.com.dragon.rates;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.Test;

import au.com.dragon.rates.StandardRatePeriod;
import junit.framework.TestCase;

public class StandardRatePeriodTest extends TestCase {
    @Test
    public void testOneHourRate() {
        StandardRatePeriod rate = new StandardRatePeriod("0-1 hours", 0, 1, false);
        assertEquals("0-1 hours", rate.getDescription());
        assertEquals(Duration.ZERO, rate.getStartTime());
        assertEquals(Duration.ofHours(1), rate.getEndTime());
        
        assertTrue("1 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T10:05:26")));

        assertFalse("1 hour rate at midnight", rate.match(
                LocalDateTime.parse("2018-05-01T23:25:43"), 
                LocalDateTime.parse("2018-05-02T00:05:26")));
        assertFalse("2 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T11:05:26")));
    }

    @Test
    public void testTwoHourRate() {
        StandardRatePeriod rate = new StandardRatePeriod("1-2 hours", 1, 2, false);
        assertEquals("1-2 hours", rate.getDescription());
        assertEquals(Duration.ofHours(1), rate.getStartTime());
        assertEquals(Duration.ofHours(2), rate.getEndTime());
        
        assertTrue("2 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T11:05:26")));

        assertFalse("2 hour rate at midnight", rate.match(
                LocalDateTime.parse("2018-05-01T23:25:43"), 
                LocalDateTime.parse("2018-05-02T01:05:26")));
        assertFalse("1 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T10:05:26")));
        assertFalse("3 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T12:05:26")));
    }

    @Test
    public void testThreeHourRate() {
        StandardRatePeriod rate = new StandardRatePeriod("2-3 hours", 2, 3, false);
        assertEquals("2-3 hours", rate.getDescription());
        assertEquals(Duration.ofHours(2), rate.getStartTime());
        assertEquals(Duration.ofHours(3), rate.getEndTime());
        
        assertTrue("3 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T12:05:26")));
        
        assertFalse("3 hour rate at midnight", rate.match(
                LocalDateTime.parse("2018-05-01T23:25:43"), 
                LocalDateTime.parse("2018-05-02T02:05:26")));
        assertFalse("2 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T11:05:26")));
        assertFalse("4 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T13:05:26")));
    }

    @Test
    public void testWholeDayRate() {
        StandardRatePeriod rate = new StandardRatePeriod("3+ hours", 3, 24, false);
        assertEquals("3+ hours", rate.getDescription());
        assertEquals(Duration.ofHours(3), rate.getStartTime());
        assertEquals(Duration.ofHours(24), rate.getEndTime());
        
        assertTrue("3+ hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T13:05:26")));
        assertTrue("23 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T00:25:43"), 
                LocalDateTime.parse("2018-05-01T23:05:26")));

        assertFalse("3+ hour rate at midnight", rate.match(
                LocalDateTime.parse("2018-05-01T23:25:43"), 
                LocalDateTime.parse("2018-05-02T03:05:26")));
        assertFalse("3 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T12:05:26")));
    }


    @Test
    public void testMultiDayRate() {
        StandardRatePeriod rate = new StandardRatePeriod("MultiDay", 3, 24, true);
        assertEquals("MultiDay", rate.getDescription());
        assertEquals(Duration.ofHours(3), rate.getStartTime());
        assertEquals(Duration.ofHours(24), rate.getEndTime());
        
        assertTrue("3+ hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T13:05:26")));
        assertTrue("23 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T00:25:43"), 
                LocalDateTime.parse("2018-05-01T23:05:26")));
        assertTrue("3+ hour rate at midnight", rate.match(
                LocalDateTime.parse("2018-05-01T23:25:43"), 
                LocalDateTime.parse("2018-05-02T03:05:26")));
        assertTrue("1 week", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-08T12:05:26")));

        assertFalse("3 hour rate", rate.match(
                LocalDateTime.parse("2018-05-01T09:25:43"), 
                LocalDateTime.parse("2018-05-01T12:05:26")));
    }
}
