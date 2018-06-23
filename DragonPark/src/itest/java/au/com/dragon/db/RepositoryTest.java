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

    @Test
    public void contextLoads() {
        List<StandardRatePeriod> stdRates = stdRepository.findAll();
        assertFalse("StandardRatePeriodRepository is empty", stdRates.isEmpty());
        for (StandardRatePeriod period : stdRates) {
            log.info("Standard Rate: {}", period);
        }

        List<FlatRatePeriod> flatRates = flatRepository.findAll();
        assertFalse("FlatRatePeriodRepository is empty", flatRates.isEmpty());
        for (FlatRatePeriod period : flatRates) {
            log.info("Standard Rate: {}", period);
        }
    }
    
}
