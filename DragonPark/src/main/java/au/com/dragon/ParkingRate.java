package au.com.dragon;

import java.time.LocalDateTime;

public class ParkingRate {
    String name;
    int price;
    String type;
    RateCondition entryCondition;
    RateCondition exitCondition;
    
    public ParkingRate(String name, int price, String type, RateCondition entryCondition, RateCondition exitCondition) {
        super();
        this.name = name;
        this.price = price;
        this.type = type;
        this.entryCondition = entryCondition;
        this.exitCondition = exitCondition;
    }
    
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public RateCondition getEntryCondition() {
        return entryCondition;
    }

    public RateCondition getExitCondition() {
        return exitCondition;
    }

    public boolean match(LocalDateTime entryTime, LocalDateTime exitTime) {
        if (entryCondition.match(entryTime) && exitCondition.match(exitTime)) {
            return true;
        }
        return false;
    }
}
