package au.com.dragon.db;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.dragon.parking.ParkingEvent;
import au.com.dragon.rates.FlatRatePeriod;
import au.com.dragon.rates.StandardRatePeriod;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    Logger log = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    StandardRatePeriodRepository stdRepository;

    @Autowired
    FlatRatePeriodRepository flatRepository;

    @Autowired
    ParkingEventRepository parkingRepository;

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
    public void testParkingRepository() {
       parkingRepository.save(new ParkingEvent(1L, "2018-05-07T08:25:43", "2018-05-07T09:25:43", "$50.00", "StandardRate"));
       List<ParkingEvent> parkingEvents = parkingRepository.findAll();
        assertFalse("ParkingEventRepository is empty", parkingEvents.isEmpty());
        for (ParkingEvent event : parkingEvents) {
            log.info("Parking Events: {}", event);
        }
        
    }
    
}
