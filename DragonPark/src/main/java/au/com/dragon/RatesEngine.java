package au.com.dragon;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.dragon.db.FlatRatePeriodRepository;
import au.com.dragon.db.ParkingEventRepository;
import au.com.dragon.db.ParkingRateRepository;
import au.com.dragon.db.StandardRatePeriodRepository;
import au.com.dragon.rates.FlatRatePeriod;
import au.com.dragon.rates.ParkingRate;
import au.com.dragon.rates.Rate;
import au.com.dragon.rates.RatePeriod;
import au.com.dragon.rates.StandardRatePeriod;

@Service
public class RatesEngine {
    DecimalFormat formatter = new DecimalFormat("$##0.00");
    
    @Autowired
    private final ParkingRateRepository rateRepository;

    @Autowired
    private final StandardRatePeriodRepository stdRepository;

    @Autowired
    private final FlatRatePeriodRepository flatRepository;

    @Autowired
    private final ParkingEventRepository parkingRepository;
    
    private List<ParkingRate> parkingRates;
    
    public RatesEngine(
            ParkingRateRepository rateRepository,
            StandardRatePeriodRepository stdRepository,
            FlatRatePeriodRepository flatRepository,
            ParkingEventRepository parkingRepository
            ) {
        // FIXME - This should all be in a Configuration class
        this.rateRepository = rateRepository;
        this.stdRepository = stdRepository;
        this.flatRepository = flatRepository;
        this.parkingRepository = parkingRepository;
        populateParkingRates();
    }
    
    void populateParkingRates() {
        List<ParkingRate> parkingRates = rateRepository.findAll();
        for (ParkingRate rate : parkingRates) {
            if ("FlatRate".equals(rate.getType())) {
                List<FlatRatePeriod> ratePeriods = flatRepository.findByRateName(rate.getRateName());
                // List<FlatRatePeriod> is not a sub-type of List<RatePeriod>
                List<RatePeriod> periods = new ArrayList<>();
                periods.addAll(ratePeriods);
                rate.setRatePeriods(periods);
            } else {
                List<StandardRatePeriod> ratePeriods = stdRepository.findByRateName(rate.getRateName());
                List<RatePeriod> periods = new ArrayList<>();
                periods.addAll(ratePeriods);
                rate.setRatePeriods(periods);
            }
        }
        setParkingRates(parkingRates);
    }

    public List<ParkingRate> getParkingRates() {
        return parkingRates;
    }

    public void setParkingRates(List<ParkingRate> parkingRates) {
        this.parkingRates = parkingRates;
    }

    public Rate parkDragon(String entryDate, String exitDate) {
        LocalDateTime entryTime = LocalDateTime.parse(entryDate);
        LocalDateTime exitTime = LocalDateTime.parse(exitDate);
        for (ParkingRate parking : getParkingRates()) {
            RatePeriod period = parking.match(entryTime, exitTime);
            if (period != null) {
                String rateName = parking.getRateName();
                Double rateAmount = parking.getRate();
                if (period.isMultiDay()) {
                    long days = DAYS.between(entryTime.toLocalDate(), exitTime.toLocalDate()) + 1;
                    rateAmount = rateAmount * days;
                }
               return new Rate(rateName, formatRate(rateAmount));
            }
        }
        return null;
    }
    
    private String formatRate(Double price) {
        return formatter.format(price);
    }
}
