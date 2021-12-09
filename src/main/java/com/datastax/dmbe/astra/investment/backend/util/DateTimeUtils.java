package com.datastax.dmbe.astra.investment.backend.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

// https://www.buzzphp.com/posts/get-timebased-uuids-upto-100s-of-nanoseconds
public class DateTimeUtils {

    private static final long EPOCH_DIFFERENCE = 122192928000000000L;
    private static final ZoneId GREENWICH_MEAN_TIME = ZoneId.of("GMT");    

    // ZonedDateTime from UUID
    public static ZonedDateTime timeBasedUuidToDate(UUID uuid) {
        if (uuid.version() != 1) {
            throw new IllegalArgumentException("Provided UUID was not time-based.");
        }
        // the UUID timestamp is in 100 nanosecond units.
        // convert that to nanoseconds
        long nanoseconds = (uuid.timestamp() - EPOCH_DIFFERENCE) * 100;
        long milliseconds = nanoseconds / 1000000000;
        long nanoAdjustment = nanoseconds % 1000000000;
        Instant instant = Instant.ofEpochSecond(milliseconds, nanoAdjustment);
        return ZonedDateTime.ofInstant(instant, GREENWICH_MEAN_TIME);
    }

    public static UUID dateToTimeBasedUuid(ZonedDateTime date) {
        return Uuids.endOf(date.toEpochSecond());    
    }
    
    public static ZonedDateTime toZonedDateTime(DateTimeFormatter formatter, String value) {
        return ZonedDateTime.parse(value, formatter);
    }    

    // String from UUID
    public static String formattedDateFromTimeBasedUuid(DateTimeFormatter formatter, UUID uuid) {
        ZonedDateTime date = timeBasedUuidToDate(uuid);
        return formatter.format(date);
    } 
        
    
}
