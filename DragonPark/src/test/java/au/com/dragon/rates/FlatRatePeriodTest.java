package au.com.dragon.rates;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import junit.framework.TestCase;

import org.junit.Test;

import au.com.dragon.rates.FlatRatePeriod;

public class FlatRatePeriodTest extends TestCase {
    private static final String MIDNIGHT = "23:59:59.999999999";
    @Test
    public void testMondayEarlyBird() {
        FlatRatePeriod monday = new FlatRatePeriod("EarlyBird", "Monday Early Bird", "MONDAY", "06:00", "09:00", "MONDAY", "15:30", "23:30");
        assertEquals(DayOfWeek.MONDAY, monday.getEntryDayOfWeek());
        assertEquals("06:00", monday.getEntryStart().toString());
        assertEquals("09:00", monday.getEntryEnd().toString());
        assertEquals(DayOfWeek.MONDAY, monday.getExitDayOfWeek());
        assertEquals("15:30", monday.getExitStart().toString());
        assertEquals("23:30", monday.getExitEnd().toString());
        
        assertTrue("Early bird 08:25-18:05", monday.match(
                LocalDateTime.parse("2018-05-07T08:25:43"), 
                LocalDateTime.parse("2018-05-07T18:05:26")));
        assertTrue("Early bird 06:00-23:30", monday.match(
                LocalDateTime.parse("2018-05-07T06:00:00"), 
                LocalDateTime.parse("2018-05-07T23:30:00")));
    }

    @Test
    public void testTuesdayNightRate() {
        FlatRatePeriod tuesdayNight = new FlatRatePeriod("NightRate", "Tuesday Night Rate", "TUESDAY", "18:00", "24:00", "WEDNESDAY", "00:00", "06:00");
        assertEquals(DayOfWeek.TUESDAY, tuesdayNight.getEntryDayOfWeek());
        assertEquals("18:00", tuesdayNight.getEntryStart().toString());
        assertEquals(LocalTime.MAX, tuesdayNight.getEntryEnd());
        assertEquals(MIDNIGHT, tuesdayNight.getEntryEnd().toString());
        assertEquals(DayOfWeek.WEDNESDAY, tuesdayNight.getExitDayOfWeek());
        assertEquals("00:00", tuesdayNight.getExitStart().toString());
        assertEquals("06:00", tuesdayNight.getExitEnd().toString());
        
        assertTrue("Night Rate Tue 20:00-Wed 04:00+1", tuesdayNight.match(
                LocalDateTime.parse("2018-05-01T20:00:00"), 
                LocalDateTime.parse("2018-05-02T04:00:00")));
        assertTrue("Night Rate Tue 18:00-Wed 06:00+1", tuesdayNight.match(
                LocalDateTime.parse("2018-05-01T18:00:00"), 
                LocalDateTime.parse("2018-05-02T06:00:00")));
        assertFalse("Night Rate Wed 18:00-Thu 06:00+1", tuesdayNight.match(
                LocalDateTime.parse("2018-05-02T18:00:00"), 
                LocalDateTime.parse("2018-05-03T06:00:00")));
        assertFalse("Night Rate Tue 18:00-Wed 06:00+8", tuesdayNight.match(
                LocalDateTime.parse("2018-05-01T18:00:00"), 
                LocalDateTime.parse("2018-05-09T06:00:00")));
        assertFalse("Night Rate Wed 06:00-Tue18:00-1", tuesdayNight.match(
                LocalDateTime.parse("2018-05-02T06:00:00"),
                LocalDateTime.parse("2018-05-01T18:00:00")));
    }

    @Test
    public void testSaturdayWeekendRate() {
        FlatRatePeriod saturday = new FlatRatePeriod("WeekendRate", "Weekend Rate", "SATURDAY", "00:00", "24:00", "SATURDAY", "00:00", "24:00");
        assertEquals(DayOfWeek.SATURDAY, saturday.getEntryDayOfWeek());
        assertEquals("00:00", saturday.getEntryStart().toString());
        assertEquals(MIDNIGHT, saturday.getEntryEnd().toString());
        assertEquals(DayOfWeek.SATURDAY, saturday.getExitDayOfWeek());
        assertEquals("00:00", saturday.getExitStart().toString());
        assertEquals(MIDNIGHT, saturday.getExitEnd().toString());
        
        assertTrue("Weekend Rate Saturday 08:25-18:05", saturday.match(
                LocalDateTime.parse("2018-05-05T08:25:43"), 
                LocalDateTime.parse("2018-05-05T18:05:26")));
        assertTrue("Weekend Rate 00:00-23:59:59", saturday.match(
                LocalDateTime.parse("2018-05-05T00:00:00"), 
                LocalDateTime.parse("2018-05-05T23:59:59")));
        assertFalse("Weekend Rate 00:00-00:00+1", saturday.match(
                LocalDateTime.parse("2018-05-05T00:00:00"), 
                LocalDateTime.parse("2018-05-06T00:00:00")));
    }

    @Test
    public void testSatSundayWeekendRate() {
        FlatRatePeriod saturday = new FlatRatePeriod("WeekendRate", "Saturday Rate", "SATURDAY", "00:00", "24:00", "SUNDAY", "00:00", "24:00");
        assertEquals(DayOfWeek.SATURDAY, saturday.getEntryDayOfWeek());
        assertEquals("00:00", saturday.getEntryStart().toString());
        assertEquals(MIDNIGHT, saturday.getEntryEnd().toString());
        assertEquals(DayOfWeek.SUNDAY, saturday.getExitDayOfWeek());
        assertEquals("00:00", saturday.getExitStart().toString());
        assertEquals(MIDNIGHT, saturday.getExitEnd().toString());
        
        assertTrue("Weekend Rate 08:25-18:05+1", saturday.match(
                LocalDateTime.parse("2018-05-05T08:25:43"), 
                LocalDateTime.parse("2018-05-06T18:05:26")));
        assertTrue("Weekend Rate 00:00-24:00+1", saturday.match(
                LocalDateTime.parse("2018-05-05T00:00:00"), 
                LocalDateTime.parse("2018-05-06T23:59:59")));
        assertTrue("Weekend Rate 24:00:00-00:00+1", saturday.match(
                LocalDateTime.parse("2018-05-05T23:59:59"), 
                LocalDateTime.parse("2018-05-06T00:00:00")));
        assertFalse("Weekend Rate 00:00-00:00+2", saturday.match(
                LocalDateTime.parse("2018-05-05T00:00:00"), 
                LocalDateTime.parse("2018-05-07T00:00:00")));
        assertFalse("Weekend Rate 08:25-18:05+8", saturday.match(
                LocalDateTime.parse("2018-05-05T08:25:43"), 
                LocalDateTime.parse("2018-05-13T18:05:26")));
    }
}
