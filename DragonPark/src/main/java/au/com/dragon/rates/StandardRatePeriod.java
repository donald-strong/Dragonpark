package au.com.dragon.rates;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "standard_rate_period")
public class StandardRatePeriod implements RatePeriod {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name = "rate_name")
    String rateName;

    @Column(name="description")
    private String     description;
    
    @Column(name = "start_hours")
    private int     startHours;
    
    @Column(name = "end_hours")
    private int     endHours;
    
    @Column(name = "multi_day")
    private boolean    multiDay;

    @Transient
    private Duration   startTime = null;

    @Transient
    private Duration   endTime = null;

    public StandardRatePeriod() {
    }
    
    public StandardRatePeriod(String rateName, String description, int startHours, int endHours, boolean multiDay) {
        setRateName(rateName);
        setDescription(description);
        setStartHours(startHours);
        setEndHours(endHours);
        setMultiDay(multiDay);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public void setStartTime(Duration startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Duration endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getStartHours() {
        return startHours;
    }

    public void setStartHours(int startHours) {
        this.startHours = startHours;
    }

    public int getEndHours() {
        return endHours;
    }

    public void setEndHours(int endHours) {
        this.endHours = endHours;
    }

    public boolean isMultiDay() {
        return multiDay;
    }

    public void setMultiDay(boolean multiDay) {
        this.multiDay = multiDay;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getStartTime() {
        if (startTime == null) {
            startTime = Duration.ofHours(startHours);
        }
        return startTime;
    }

    public Duration getEndTime() {
        if (endTime == null) {
            endTime = Duration.ofHours(endHours);
        }
        return endTime;
    }

    private boolean isInSameDay(LocalDateTime entryStamp, LocalDateTime exitStamp) {
        Duration parkingTime = Duration.between(entryStamp, exitStamp);
        if (parkingTime.isNegative()) {
            return false;
        } else if (multiDay) {
            return true;
        } else {
            return entryStamp.getDayOfWeek() == exitStamp.getDayOfWeek();
        }
    }
    
    private boolean isInTimeLimit(LocalDateTime entryStamp, LocalDateTime exitStamp) {
        Duration parkingTime = Duration.between(entryStamp, exitStamp);
        if (getStartTime().compareTo(parkingTime) < 0) {
            if (multiDay) {
                return true;
            } else {
                return getEndTime().compareTo(parkingTime) > 0;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public boolean match(LocalDateTime entryStamp, LocalDateTime exitStamp) {
        return isInSameDay(entryStamp, exitStamp)
                && isInTimeLimit(entryStamp, exitStamp);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("StandardRatePeriod(").append(id);
        buf.append(", ").append(rateName);
        buf.append(", \"").append(description).append('"');
        buf.append(", startHours=").append(startHours);
        buf.append(", endHours=").append(endHours);
        buf.append(", multiDay=").append(multiDay);
        buf.append(')');
        return buf.toString();
    }
}
