package au.com.dragon.db;

import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalTimeConverter  implements AttributeConverter<LocalTime, String>{
    /** "24:00" is an alias for a period going right up to midnight. */
    public static final String TWENTYFOUR = "24:00";

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        if (LocalTime.MAX.equals(localTime)) {
            return TWENTYFOUR;
        } else {
            return localTime.toString();
        }
    }

    @Override
    public LocalTime convertToEntityAttribute(String localTime) {
        if (TWENTYFOUR.equals(localTime)) {
            return LocalTime.MAX;
        } else {
            return LocalTime.parse(localTime);
        }
    }

}
