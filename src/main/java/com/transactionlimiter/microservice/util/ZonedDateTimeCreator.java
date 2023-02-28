package com.transactionlimiter.microservice.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

@Component
public class ZonedDateTimeCreator {
    private Calendar calendar = Calendar.getInstance();
    private Date toZonedTime(LocalDateTime localDateTime) {  // Convert New York time to the current time zone
        ZonedDateTime zonedNewYorkTime = localDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedTime = zonedNewYorkTime.withZoneSameInstant(ZoneId.systemDefault());

        return Date.from(zonedTime.toInstant());
    }
    public Date createOpeningTime() {   // Return the session opening time in new york in the current time zone
        LocalDateTime openingTime = LocalDateTime.of
                (calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 9, 31, 0);
        return toZonedTime(openingTime);
    }
    public Date createClosingTime() {  // Return the session closing time in new york in the current time zone
        LocalDateTime closingTime = LocalDateTime.of
                (calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 16, 01, 0);

        return toZonedTime(closingTime);
    }
}
