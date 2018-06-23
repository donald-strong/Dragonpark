package au.com.dragon.rates;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import au.com.dragon.db.DayOfWeekConverter;
import au.com.dragon.db.LocalTimeConverter;

@Entity
@Table(name = "flat_rate_period")
public class FlatRatePeriod implements RatePeriod {
    public static final LocalTimeConverter timeConverter = new LocalTimeConverter();
    
    @Id
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
    
    public FlatRatePeriod(String description, String entryDay, String entryStart, String entryEnd,
            String exitDay, String exitStart, String exitEnd) {
        this(description, DayOfWeek.valueOf(entryDay), parseLocalTime(entryStart), parseLocalTime(entryEnd),
                DayOfWeek.valueOf(exitDay), parseLocalTime(exitStart), parseLocalTime(exitEnd));
    }
    
    private static LocalTime parseLocalTime(String input) {
        return timeConverter.convertToEntityAttribute(input);
    }

    public FlatRatePeriod(String description, DayOfWeek entryDay, LocalTime entryStart, LocalTime entryEnd,
            DayOfWeek exitDay, LocalTime exitStart, LocalTime exitEnd) {
        this.description = description;
        this.entryDayOfWeek = entryDay;
        this.entryStart = entryStart;
        this.entryEnd = entryEnd;
        this.exitDayOfWeek = exitDay;
        this.exitStart = exitStart;
        this.exitEnd = exitEnd;
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
        StringBuilder buf = new StringBuilder();
        buf.append('"').append(description).append('"');
        buf.append(", entryDay=").append(entryDayOfWeek);
        buf.append(", entryTime=").append(timeConverter.convertToDatabaseColumn(entryStart));
        buf.append("-").append(timeConverter.convertToDatabaseColumn(entryEnd));
        buf.append(", exitDay=").append(exitDayOfWeek);
        buf.append(", exitTime=").append(timeConverter.convertToDatabaseColumn(exitStart));
        buf.append("-").append(timeConverter.convertToDatabaseColumn(exitEnd));
        return buf.toString();
    }
}
