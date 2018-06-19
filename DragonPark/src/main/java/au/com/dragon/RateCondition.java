package au.com.dragon;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RateCondition {
    String  description;
    LocalTime  startTime;
    LocalTime  endTime;
    
    public RateCondition() {
    }

    public RateCondition(String description, String startTime, String endTime) {
        this(description, LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public RateCondition(String description, LocalTime startTime, LocalTime endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public boolean match(LocalDateTime timestamp) {
        LocalTime time = LocalTime.from(timestamp);
        return match(time);
    }

    public boolean match(LocalTime time) {
        return !startTime.isAfter(time) && !endTime.isBefore(time);
    }
}
