package au.com.dragon.db;

import java.time.DayOfWeek;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DayOfWeekConverter  implements AttributeConverter<DayOfWeek, String>{

    @Override
    public String convertToDatabaseColumn(DayOfWeek dayOfWeek) {
        return dayOfWeek.toString();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(String dayOfWeekStr) {
        return DayOfWeek.valueOf(dayOfWeekStr);
    }

}
