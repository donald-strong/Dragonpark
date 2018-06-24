package au.com.dragon;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.dragon.RatesEngine;
import au.com.dragon.db.FlatRatePeriodRepository;
import au.com.dragon.db.ParkingEventRepository;
import au.com.dragon.db.ParkingRateRepository;
import au.com.dragon.db.StandardRatePeriodRepository;
import au.com.dragon.rates.FlatRatePeriod;
import au.com.dragon.rates.ParkingRate;
import au.com.dragon.rates.Rate;
import au.com.dragon.rates.RatePeriod;
import au.com.dragon.rates.StandardRatePeriod;
import junit.framework.TestCase;

public class RatesEngineTest extends TestCase {

    @Mock
    ParkingRateRepository rateRepository = Mockito.mock(ParkingRateRepository.class);

    @Mock
    StandardRatePeriodRepository stdRepository = Mockito.mock(StandardRatePeriodRepository.class);

    @Mock
    FlatRatePeriodRepository flatRepository = Mockito.mock(FlatRatePeriodRepository.class);

    @Mock
    ParkingEventRepository parkingRepository = Mockito.mock(ParkingEventRepository.class);
    
    RatesEngine engine;
    
    @Before
    public void setUp() {
        ParkingRate earlyBird = new ParkingRate("EarlyBird", "$15.00", "FlatRate", "Enter 06:00-09:00", "Exit 15:30-23:30", null);
        ParkingRate nightRate = new ParkingRate("NightRate", "$18.00", "FlatRate", "Enter 18:00-24:00", "Exit 00:00-06:00+1", null);
        ParkingRate weekendRate = new ParkingRate("WeekendRate", "$13.00", "FlatRate", "Enter 00:00 Saturday", "Exit 24:00 Sunday", null);
        ParkingRate oneHourRate = new ParkingRate("OneHour", "$5.00", "StdRate", "", "0-1 hours", null);
        ParkingRate twoHourRate = new ParkingRate("TwoHour", "$10.00", "StdRate", "", "1-2 hours", null);
        ParkingRate threeHourRate = new ParkingRate("ThreeHour", "$15.00", "StdRate", "", "2-3 hours", null);
        ParkingRate moreHourRate = new ParkingRate("MoreHours", "$20.00", "StdRate", "", "3+ hours", null);
        ParkingRate dailyRate = new ParkingRate("DailyRate", "$20.00", "StdRate", "", "Daily rate", null);
        List<ParkingRate> parkingRates = new ArrayList<>();
        parkingRates.add(earlyBird);
        parkingRates.add(nightRate);
        parkingRates.add(weekendRate);
        parkingRates.add(oneHourRate);
        parkingRates.add(twoHourRate);
        parkingRates.add(threeHourRate);
        parkingRates.add(moreHourRate);
        parkingRates.add(dailyRate);
        Mockito.when(rateRepository.findAll()).thenReturn(parkingRates);
        
        List<FlatRatePeriod> flatPeriods = new ArrayList<>();
        FlatRatePeriod mondayEarlyBird = new FlatRatePeriod("EarlyBird", "Monday Early Bird", "MONDAY", "06:00", "09:00", "MONDAY", "15:30", "23:30");
        FlatRatePeriod tuesdayNight = new FlatRatePeriod("NightRate", "Tuesday Night Rate", "TUESDAY", "18:00", "24:00", "WEDNESDAY", "00:00", "06:00");
        FlatRatePeriod saturdayWeekendRate = new FlatRatePeriod("WeekendRate", "Saturday Weekend Rate", "SATURDAY", "00:00", "24:00", "SATURDAY", "00:00", "24:00");
        flatPeriods.add(mondayEarlyBird);
        flatPeriods.add(tuesdayNight);
        flatPeriods.add(saturdayWeekendRate);
        Mockito.when(flatRepository.findAll()).thenReturn(flatPeriods);
        Mockito.when(flatRepository.findByRateName("EarlyBird")).thenReturn(Collections.singletonList(mondayEarlyBird));
        Mockito.when(flatRepository.findByRateName("NightRate")).thenReturn(Collections.singletonList(tuesdayNight));
        Mockito.when(flatRepository.findByRateName("WeekendRate")).thenReturn(Collections.singletonList(saturdayWeekendRate));

        List<StandardRatePeriod> stdPeriods = new ArrayList<>();
        StandardRatePeriod oneHour = new StandardRatePeriod("OneHour", "0-1 hours", 0, 1, false);
        StandardRatePeriod twoHour = new StandardRatePeriod("TwoHour", "1-2 hours", 1, 2, false);
        StandardRatePeriod threeHour = new StandardRatePeriod("ThreeHour", "2-3 hours", 2, 3, false);
        StandardRatePeriod moreHours = new StandardRatePeriod("MoreHours", "3+ hours", 3, 24, false);
        StandardRatePeriod moreDays = new StandardRatePeriod("DailyRate", "Daily rate", 0, 24, true);
        stdPeriods.add(oneHour);
        stdPeriods.add(twoHour);
        stdPeriods.add(threeHour);
        stdPeriods.add(moreHours);
        stdPeriods.add(moreDays);
        Mockito.when(stdRepository.findAll()).thenReturn(stdPeriods);
        Mockito.when(stdRepository.findByRateName("OneHour")).thenReturn(Collections.singletonList(oneHour));
        Mockito.when(stdRepository.findByRateName("TwoHour")).thenReturn(Collections.singletonList(twoHour));
        Mockito.when(stdRepository.findByRateName("ThreeHour")).thenReturn(Collections.singletonList(threeHour));
        Mockito.when(stdRepository.findByRateName("MoreHours")).thenReturn(Collections.singletonList(moreHours));
        Mockito.when(stdRepository.findByRateName("DailyRate")).thenReturn(Collections.singletonList(moreDays));

        engine = new RatesEngine(rateRepository, stdRepository, flatRepository, parkingRepository);
    }
    
    @Test
    public void testFlatParkingRates() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        
        // EarlyBird
        ParkingRate rate = parkingRates.get(0);
        assertEquals("EarlyBird", rate.getRateName());
        List<RatePeriod> ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("Monday Early Bird", ratePeriods.get(0).getDescription());
        
        // NightRate
        rate = parkingRates.get(1);
        assertEquals("NightRate", rate.getRateName());
        ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("Tuesday Night Rate", ratePeriods.get(0).getDescription());
        
        // WeekendRate
        rate = parkingRates.get(2);
        assertEquals("WeekendRate", rate.getRateName());
        ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("Saturday Weekend Rate", ratePeriods.get(0).getDescription());
    }
    
    @Test
    public void testStandardParkingRates() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        
        // OneHour
        ParkingRate rate = parkingRates.get(3);
        assertEquals("OneHour", rate.getRateName());
        List<RatePeriod> ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("0-1 hours", ratePeriods.get(0).getDescription());

        // TwoHour
        rate = parkingRates.get(4);
        assertEquals("TwoHour", rate.getRateName());
        ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("1-2 hours", ratePeriods.get(0).getDescription());

        // ThreeHour
        rate = parkingRates.get(5);
        assertEquals("ThreeHour", rate.getRateName());
        ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("2-3 hours", ratePeriods.get(0).getDescription());

        // MoreHour
        rate = parkingRates.get(6);
        assertEquals("MoreHours", rate.getRateName());
        ratePeriods =rate.getRatePeriods();
        assertEquals(1, ratePeriods.size());
        assertEquals("3+ hours", ratePeriods.get(0).getDescription());
    }

    @Test
    public void testEarlyBirdParkingRates() {
        Rate rate = engine.parkDragon("2017-05-01T08:25:43", "2017-05-01T18:24:32");
        assertNotNull("No rate found", rate);
        assertEquals("EarlyBird", rate.getRateName());
        assertEquals("$15.00", rate.getRate());
    }

    @Test
    public void testOvernightParkingRates() {
        Rate rate = engine.parkDragon("2018-05-01T20:00:00", "2018-05-02T04:00:00");
        assertNotNull("No rate found", rate);
        assertEquals("NightRate", rate.getRateName());
        assertEquals("$18.00", rate.getRate());
    }

    @Test
    public void testWeekendParkingRates() {
        Rate rate = engine.parkDragon("2018-05-05T08:25:43", "2018-05-05T18:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("WeekendRate", rate.getRateName());
        assertEquals("$13.00", rate.getRate());
    }

    @Test
    public void testOneHourParkingRate() {
        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-01T10:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("OneHour", rate.getRateName());
        assertEquals("$5.00", rate.getRate());
    }

    @Test
    public void testTwoHourParkingRate() {
        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-01T11:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("TwoHour", rate.getRateName());
        assertEquals("$10.00", rate.getRate());
    }

    @Test
    public void testThreeHourParkingRate() {
        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-01T12:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("ThreeHour", rate.getRateName());
        assertEquals("$15.00", rate.getRate());
    }

    @Test
    public void testFourHourParkingRate() {
        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-01T13:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("MoreHours", rate.getRateName());
        assertEquals("$20.00", rate.getRate());
    }

    @Test
    public void testTwelveHourParkingRate() {
        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-01T21:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("MoreHours", rate.getRateName());
        assertEquals("$20.00", rate.getRate());
    }

    @Test
    public void testTwoDayParkingRate() {
        LocalDateTime entryTime = LocalDateTime.parse("2018-05-01T09:25:43");
        LocalDateTime exitTime = LocalDateTime.parse("2018-05-02T21:05:26");
        long days = DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate());
        assertEquals(1, days);

        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-02T21:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("DailyRate", rate.getRateName());
        assertEquals("$40.00", rate.getRate());
    }

    @Test
    public void testSevenDayParkingRate() {
        LocalDateTime entryTime = LocalDateTime.parse("2018-05-01T09:25:43");
        LocalDateTime exitTime = LocalDateTime.parse("2018-05-07T21:05:26");
        long days = DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate());
        assertEquals(6, days);

        Rate rate = engine.parkDragon("2018-05-01T09:25:43", "2018-05-07T21:05:26");
        assertNotNull("No rate found", rate);
        assertEquals("DailyRate", rate.getRateName());
        assertEquals("$140.00", rate.getRate());
    }
}
