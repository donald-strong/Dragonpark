package au.com.dragon.rates;

import java.time.LocalDateTime;

public interface RatePeriod {

    String getDescription();

    boolean match(LocalDateTime entryStamp, LocalDateTime exitStamp);

    boolean isMultiDay();
}