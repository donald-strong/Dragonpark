package au.com.dragon;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.dragon.ParkingRate;
import au.com.dragon.Rate;
import au.com.dragon.RateCondition;
import au.com.dragon.RatesEngine;
import junit.framework.TestCase;

public class RatesEngineTest extends TestCase {
    List<ParkingRate> parkingRates = new ArrayList<>();
    RatesEngine engine;
    
    @Before
    public void setUp() {
        RateCondition entryCondition = new RateCondition("6am to 9am", "06:00", "09:00");
        RateCondition exitCondition = new RateCondition("3:30pm to 11:30pm", "15:30", "23:30");
        ParkingRate earlyBird = new ParkingRate("Early Bird", 1500, "Flat Rate", entryCondition, exitCondition);
        parkingRates.add(earlyBird);
        
        engine = new RatesEngine(parkingRates);
    }
    
    @Test
    public void testEarlyBirdParkingRates() {
        assertEquals(1, engine.getRates().size());
        
        Rate earlyRate = engine.parkDragon("2017-05-01T08:25:43", "2017-05-01T18:24:32");
        assertEquals("Early Bird", earlyRate.getRateName());
        assertEquals("$15.00", earlyRate.getRate());
    }
}
