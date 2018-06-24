package au.com.dragon.rates;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import au.com.dragon.db.DayOfWeekConverter;
import au.com.dragon.db.LocalTimeConverter;

@Entity
@Table(name = "flat_rate_period")
public class FlatRatePeriod implements RatePeriod {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name = "rate_name")
    String rateName;

    @Column(name="description")
    private String     description;
    
    @Column(name = "entry_day")
    @Convert(converter = DayOfWeekConverter.class)
    private DayOfWeek  entryDayOfWeek;
    
    @Column(name = "entry_start")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime  entryStart;
    
    @Column(name = "entry_end")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime  entryEnd;
    
    @Column(name = "exit_day")
    @Convert(converter = DayOfWeekConverter.class)
    private DayOfWeek  exitDayOfWeek;
    
    @Column(name = "exit_start")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime  exitStart;
    
    @Column(name = "exit_end")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime  exitEnd;
    
    public FlatRatePeriod() {
    }
    
    public FlatRatePeriod(String rateName, String description, String entryDay, String entryStart, String entryEnd,
            String exitDay, String exitStart, String exitEnd) {
        LocalTimeConverter timeConverter = new LocalTimeConverter();
        setRateName(rateName);
        setDescription(description);
        setEntryDayOfWeek(DayOfWeek.valueOf(entryDay));
        setEntryStart(timeConverter.convertToEntityAttribute(entryStart));
        setEntryEnd(timeConverter.convertToEntityAttribute(entryEnd));
        setExitDayOfWeek(DayOfWeek.valueOf(exitDay));
        setExitStart(timeConverter.convertToEntityAttribute(exitStart));
        setExitEnd(timeConverter.convertToEntityAttribute(exitEnd));
    }
    
    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntryDayOfWeek(DayOfWeek entryDayOfWeek) {
        this.entryDayOfWeek = entryDayOfWeek;
    }

    public void setEntryStart(LocalTime entryStart) {
        this.entryStart = entryStart;
    }

    public void setEntryEnd(LocalTime entryEnd) {
        this.entryEnd = entryEnd;
    }

    public void setExitDayOfWeek(DayOfWeek exitDayOfWeek) {
        this.exitDayOfWeek = exitDayOfWeek;
    }

    public void setExitStart(LocalTime exitStart) {
        this.exitStart = exitStart;
    }

    public void setExitEnd(LocalTime exitEnd) {
        this.exitEnd = exitEnd;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public DayOfWeek getEntryDayOfWeek() {
        return entryDayOfWeek;
    }

    public LocalTime getEntryStart() {
        return entryStart;
    }

    public LocalTime getEntryEnd() {
        return entryEnd;
    }

    public DayOfWeek getExitDayOfWeek() {
        return exitDayOfWeek;
    }

    public LocalTime getExitStart() {
        return exitStart;
    }

    public LocalTime getExitEnd() {
        return exitEnd;
    }
    
    @Override
    public boolean isMultiDay() {
        return false;
    }
    
    private boolean isInSameWeek(LocalDateTime entryStamp, LocalDateTime exitStamp) {
        Duration parkingTime = Duration.between(entryStamp, exitStamp);
        long parkingDays = parkingTime.toDays();
        return !parkingTime.isNegative() && parkingDays < 5;
    }
    
    private boolean isInEntryPeriod(LocalDateTime entryStamp) {
        LocalTime entryTime = LocalTime.from(entryStamp);
        DayOfWeek entryDay = DayOfWeek.from(entryStamp);
        return entryDayOfWeek.equals(entryDay) 
                && !entryStart.isAfter(entryTime) && !entryEnd.isBefore(entryTime);
    }
    
    private boolean isInExitPeriod(LocalDateTime exitStamp) {
        LocalTime exitTime = LocalTime.from(exitStamp);
        DayOfWeek exitDay = DayOfWeek.from(exitStamp);
        return exitDayOfWeek.equals(exitDay)
                && !exitStart.isAfter(exitTime) && !exitEnd.isBefore(exitTime);
    }
    
    @Override
    public boolean match(LocalDateTime entryStamp, LocalDateTime exitStamp) {
        return isInSameWeek(entryStamp, exitStamp)
            && isInEntryPeriod(entryStamp)
            && isInExitPeriod(exitStamp);
    }


    @Override
    public String toString() {
        LocalTimeConverter timeConverter = new LocalTimeConverter();
        StringBuilder buf = new StringBuilder();
        buf.append("FlatRatePeriod(");
        buf.append('"').append(description).append('"');
        buf.append(", entryDay=").append(entryDayOfWeek);
        buf.append(", entryTime=").append(timeConverter.convertToDatabaseColumn(entryStart));
        buf.append("-").append(timeConverter.convertToDatabaseColumn(entryEnd));
        buf.append(", exitDay=").append(exitDayOfWeek);
        buf.append(", exitTime=").append(timeConverter.convertToDatabaseColumn(exitStart));
        buf.append("-").append(timeConverter.convertToDatabaseColumn(exitEnd));
        buf.append(')');
        return buf.toString();
    }
}
