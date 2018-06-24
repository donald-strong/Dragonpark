package au.com.dragon.db;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RateStringConverter  implements AttributeConverter<Double, String>{
    DecimalFormat formatter = new DecimalFormat("$##0.00");

    @Override
    public String convertToDatabaseColumn(Double rate) {
        return formatter.format(rate);
    }

    @Override
    public Double convertToEntityAttribute(String rate) {
         try {
            return formatter.parse(rate).doubleValue();
        } catch (ParseException e) {
            return Double.NaN;
        }
    }

}
