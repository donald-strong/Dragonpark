package au.com.dragon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.dragon.db.FlatRatePeriodRepository;
import au.com.dragon.db.ParkingEventRepository;
import au.com.dragon.db.ParkingRateRepository;
import au.com.dragon.db.StandardRatePeriodRepository;
import au.com.dragon.parking.ParkingEvent;
import au.com.dragon.rates.FlatRatePeriod;
import au.com.dragon.rates.ParkingRate;
import au.com.dragon.rates.StandardRatePeriod;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    Logger log = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    ParkingRateRepository rateRepository;

    @Autowired
    StandardRatePeriodRepository stdRepository;

    @Autowired
    FlatRatePeriodRepository flatRepository;

    @Autowired
    ParkingEventRepository parkingRepository;

    @Autowired
    RatesEngine engine;

    @Test
    public void loadSpringContext() {
        // If this fails then the Spring or JPA configuration is wrong
    }
    
    @Test
    public void testStdRepository() {
        List<StandardRatePeriod> stdRates = stdRepository.findAll();
        assertFalse("StandardRatePeriodRepository is empty", stdRates.isEmpty());
        for (StandardRatePeriod period : stdRates) {
            log.info("Standard Rate: {}", period);
        }
    }
    
    @Test
    public void testFlatRepository() {
        List<FlatRatePeriod> flatRates = flatRepository.findAll();
        assertFalse("FlatRatePeriodRepository is empty", flatRates.isEmpty());
        for (FlatRatePeriod period : flatRates) {
            log.info("Standard Rate: {}", period);
        }
    }
    
    @Test
    public void testEarlyBirdRepository() {
        List<FlatRatePeriod> flatRates = flatRepository.findByRateName("EarlyBird");
        assertFalse("findByRateName(\"EarlyBird\") is empty", flatRates.isEmpty());
        for (FlatRatePeriod period : flatRates) {
            log.info("Standard Rate: {}", period);
            assertEquals("EarlyBird", period.getRateName());
        }
    }
    
    @Test
    public void testRateRepository() {
       List<ParkingRate> parkingRates = rateRepository.findAll();
        assertFalse("ParkingRateRepository is empty", parkingRates.isEmpty());
        for (ParkingRate rate : parkingRates) {
            log.info("Parking Rate: {}", rate);
        }    
    }
    
    @Test
    public void testParkingRepository() {
       parkingRepository.save(new ParkingEvent(1L, "2018-05-07T08:25:43", "2018-05-07T09:25:43", "$50.00", "StandardRate"));
       List<ParkingEvent> parkingEvents = parkingRepository.findAll();
        assertFalse("ParkingEventRepository is empty", parkingEvents.isEmpty());
        for (ParkingEvent event : parkingEvents) {
            log.info("Parking Events: {}", event);
        }
    }

    @Test
    public void testRatesEngineEarlyBird() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        assertEquals("EarlyBird", engine.getParkingRates().get(0).getRateName());
        assertEquals(5, engine.getParkingRates().get(0).getRatePeriods().size());
    }

    @Test
    public void testRatesEngineNightRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        assertEquals("NightRate", engine.getParkingRates().get(1).getRateName());
        assertEquals(5, engine.getParkingRates().get(1).getRatePeriods().size());
    }

    @Test
    public void testRatesEngineWeekendRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        assertEquals("WeekendRate", engine.getParkingRates().get(2).getRateName());
        assertEquals(3, engine.getParkingRates().get(2).getRatePeriods().size());
    }

    @Test
    public void testRatesEngineOneHourRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        ParkingRate rate = engine.getParkingRates().get(3);
        assertEquals("OneHour", rate.getRateName());
        assertEquals(1, rate.getRatePeriods().size());
        assertEquals("0-1 Hours", rate.getRatePeriods().get(0).getDescription());
    }

    @Test
    public void testRatesEngineTwoHourRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        ParkingRate rate = engine.getParkingRates().get(4);
        assertEquals("TwoHour", rate.getRateName());
        assertEquals(1, rate.getRatePeriods().size());
        assertEquals("1-2 Hours", rate.getRatePeriods().get(0).getDescription());
    }

    @Test
    public void testRatesEngineThreeHourRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        ParkingRate rate = engine.getParkingRates().get(5);
        assertEquals("ThreeHour", rate.getRateName());
        assertEquals(1, rate.getRatePeriods().size());
        assertEquals("2-3 Hours", rate.getRatePeriods().get(0).getDescription());
        assertFalse("Multi day should be false", rate.getRatePeriods().get(0).isMultiDay());
    }
    
    @Test
    public void testRatesEngineMoreHourRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        ParkingRate rate = engine.getParkingRates().get(6);
        assertEquals("MoreHours", rate.getRateName());
        assertEquals(1, rate.getRatePeriods().size());
        assertEquals("3+ Hours", rate.getRatePeriods().get(0).getDescription());
        assertFalse("Multi day should be false", rate.getRatePeriods().get(0).isMultiDay());
    }
    
    @Test
    public void testRatesEngineDailyRate() {
        List<ParkingRate> parkingRates = engine.getParkingRates();
        assertEquals(8, engine.getParkingRates().size());
        ParkingRate rate = engine.getParkingRates().get(7);
        assertEquals("DailyRate", rate.getRateName());
        assertEquals(1, rate.getRatePeriods().size());
        assertEquals("Daily Rate", rate.getRatePeriods().get(0).getDescription());
        assertTrue("Multi day should be true", rate.getRatePeriods().get(0).isMultiDay());
    }
}
