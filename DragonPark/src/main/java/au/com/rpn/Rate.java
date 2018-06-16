package au.com.rpn;

import java.time.Instant;

public class Rate {
    String name;
    int price;
    String type;
    RateCondition entryCondition;
    RateCondition exitCondition;
    
    public Rate(String name, int price, String type, RateCondition entryCondition, RateCondition exitCondition) {
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

    public boolean match(Instant entryTime, Instant exitTime) {
        if (entryCondition.match(entryTime) && exitCondition.match(exitTime)) {
            return true;
        }
        return false;
    }
}
