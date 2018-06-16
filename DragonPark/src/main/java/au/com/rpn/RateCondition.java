package au.com.rpn;

import java.time.Instant;
import java.time.LocalTime;

public class RateCondition {
    String  description;
    LocalTime  startTime;
    LocalTime  endTime;
    int     amount;
    boolean flatRate;
    
    public RateCondition() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        setStartTime(LocalTime.parse(startTime));
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        setEndTime(LocalTime.parse(endTime));
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isFlatRate() {
        return flatRate;
    }

    public void setFlatRate(boolean flatRate) {
        this.flatRate = flatRate;
    }

    public boolean match(Instant timestamp) {
        LocalTime time = LocalTime.from(timestamp);
        return match(time);
    }

    public boolean match(LocalTime time) {
        return !startTime.isAfter(time) && !endTime.isBefore(time);
    }
}
