package au.com.rpn;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DragonController {
    private final RatesEngine engine;
    
    public DragonController(RatesEngine engine) {
        this.engine = engine;
    }

    @RequestMapping("/rates")
    public List<ParkingRate> getRates() {
        return engine.getRates();
    }

    @RequestMapping("/park")
    public Rate greeting(@RequestParam(value="enter") String entryDate, @RequestParam(value="exit") String exitDate) {
        return engine.parkDragon(entryDate, exitDate);
    }
}