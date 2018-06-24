package au.com.dragon.db;

import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateTimeConverter  implements AttributeConverter<LocalDateTime, String>{
    @Override
    public String convertToDatabaseColumn(LocalDateTime localTime) {
        return localTime.toString();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String localTime) {
        return LocalDateTime.parse(localTime);
    }

}
