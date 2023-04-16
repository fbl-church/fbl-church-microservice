/**
 * Copyright of Awana App. All rights reserved.
 */
/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.common.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Common class for formatting local date and local date time objects for the
 * system.
 * 
 * @author Sam Butler
 * @since January 3, 2023
 */
public class LocalDateFormatter {

    /**
     * Formats a given {@link Date} object.
     * 
     * @param dt     The date to format.
     * @param format How to format the given date.
     * @return {@link String} of the formatted date representation.
     */
    public static String formatDate(Date dt, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dt);
    }

    /**
     * Formats a given {@link LocalDateTime} object.
     * 
     * @param dt     The local date time to format.
     * @param format How to format the given date.
     * @return {@link String} of the formatted date representation.
     */
    public static String formatDate(LocalDateTime dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dt.format(formatter);
    }

    /**
     * Formats a given {@link LocalDate} object.
     * 
     * @param dt     The local date to format.
     * @param format How to format the given date.
     * @return {@link String} of the formatted date representation.
     */
    public static String formatDate(LocalDate dt, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dt.format(formatter);
    }

    /**
     * Convert a date to a {@link LocalDateTime} object.
     * 
     * @param d The date to be converted.
     * @return The new local date time object.
     */
    public static LocalDateTime convertDateToLocalDateTime(Date d) {
        return d.toInstant().atZone(TimeZoneUtil.SYSTEM_ZONE).toLocalDateTime();
    }
}
