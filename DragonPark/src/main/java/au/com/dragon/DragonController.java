package au.com.dragon;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.dragon.rates.ParkingRate;
import au.com.dragon.rates.Rate;

@RestController
public class DragonController {
    private final RatesEngine engine;
    
    public DragonController(RatesEngine engine) {
        this.engine = engine;
    }

    @RequestMapping("/rates")
    public List<ParkingRate> getRates() {
        return engine.getParkingRates();
    }

    @RequestMapping("/park")
    public Rate greeting(@RequestParam(value="enter") String entryDate, @RequestParam(value="exit") String exitDate) {
        return engine.parkDragon(entryDate, exitDate);
    }
}