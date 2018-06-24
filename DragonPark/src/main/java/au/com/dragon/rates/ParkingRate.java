package au.com.dragon.rates;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.com.dragon.db.RateStringConverter;

@Entity
@Table(name = "parking_rate")
public class ParkingRate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Column(name = "rate_name")
    String rateName;

    @Column(name = "rate")
    @Convert(converter = RateStringConverter.class)
    Double rate;

    @Column(name = "type")
    String type;
    
    @Column(name = "entry_condition")
    String entryCondition;
    
    @Column(name = "exit_condition")
    String exitCondition;
    
    @Transient
    List<RatePeriod> ratePeriods;
    
    public ParkingRate() {
    }

    public ParkingRate(String rateName, String rate, String type, String entryCondition, String exitCondition, List<RatePeriod> ratePeriods) {
        RateStringConverter converter = new RateStringConverter();
        setRateName(rateName);
        setRate(converter.convertToEntityAttribute(rate));
        setType(type);
        setEntryCondition(entryCondition);
        setExitCondition(exitCondition);
        setRatePeriods(ratePeriods);
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

    public void setRateName(String name) {
        this.rateName = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntryCondition() {
        return entryCondition;
    }

    public void setEntryCondition(String entryCondition) {
        this.entryCondition = entryCondition;
    }

    public String getExitCondition() {
        return exitCondition;
    }

    public void setExitCondition(String exitCondition) {
        this.exitCondition = exitCondition;
    }

    public List<RatePeriod> getRatePeriods() {
        return ratePeriods;
    }

    public void setRatePeriods(List<RatePeriod> ratePeriods) {
        this.ratePeriods = ratePeriods;
    }

    public RatePeriod match(LocalDateTime entryTime, LocalDateTime exitTime) {
        for (RatePeriod period: ratePeriods) {
            if (period.match(entryTime, exitTime)) {
                return period;
            }
        }
        return null;
    }
    
    public String toString() {
        RateStringConverter converter = new RateStringConverter();
        StringBuilder buf = new StringBuilder();
        buf.append("ParkingRate(");
        buf.append(id);
        buf.append(", \"").append(rateName).append('"');
        buf.append(", rate=").append(converter.convertToDatabaseColumn(rate));
        buf.append(", type=").append(type);
        buf.append(", entryCondition=\"").append(entryCondition).append("\"");
        buf.append(", exitCondition=\"").append(exitCondition).append("\"");
        buf.append(", #periods=").append(ratePeriods==null ? 0 : ratePeriods.size());
        buf.append(')');
        return buf.toString();
    }
}
