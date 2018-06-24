package au.com.dragon.parking;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import au.com.dragon.db.LocalDateTimeConverter;
import au.com.dragon.db.RateStringConverter;

@Entity
@Table(name = "parking_event")
public class ParkingEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name = "entry_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime entryDate;

    @Column(name = "exit_date")
    @Convert(converter = LocalDateTimeConverter.class)
    LocalDateTime exitDate;
    
    @Column(name = "rate")
    @Convert(converter = RateStringConverter.class)
    Double rate;

    @Column(name = "rate_name")
    String rateName;
    
    public ParkingEvent() {
    }
    
    public ParkingEvent(Long id, String entryDate, String exitDate, String rate, String rateName) {
        setId(id);
        setEntryDate(LocalDateTime.parse(entryDate));
        setExitDate(LocalDateTime.parse(exitDate));
        RateStringConverter rateConverter = new RateStringConverter();
        setRate(rateConverter.convertToEntityAttribute(rate));
        setRateName(rateName);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }
    
    public String toString() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        RateStringConverter rateConverter = new RateStringConverter();
        StringBuilder buf = new StringBuilder();
        buf.append(id);
        buf.append(", entryDay=").append(timeConverter.convertToDatabaseColumn(entryDate));
        buf.append(", exitDay=").append(timeConverter.convertToDatabaseColumn(exitDate));
        buf.append(", rate=").append(rateConverter.convertToDatabaseColumn(rate));
        buf.append(", rateName=").append(rateName);
        return buf.toString();
    }
}
