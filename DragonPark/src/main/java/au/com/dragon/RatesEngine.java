package au.com.dragon;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import au.com.dragon.rates.ParkingRate;
import au.com.dragon.rates.Rate;

public class RatesEngine {
    DecimalFormat formatter = new DecimalFormat("$##0.00");
    private final List<ParkingRate> parkingRates;
    
    public RatesEngine(List<ParkingRate> parkingRates) {
        this.parkingRates = parkingRates;
    }

    public List<ParkingRate> getRates() {
        return parkingRates;
    }
    
    public Rate parkDragon(String entryDate, String exitDate) {
        LocalDateTime entryTime = LocalDateTime.parse(entryDate);
        LocalDateTime exitTime = LocalDateTime.parse(exitDate);
        for (ParkingRate parking : getRates()) {
            if (parking.match(entryTime, exitTime)) {
                String rateName = parking.getName();
                String amount = formatRate(parking.getPrice());
                return new Rate(rateName, amount);
            }
        }
        return null;
    }
    
    private String formatRate(int price) {
        return formatter.format(price / 100);
    }
}
