package com.ie.graphql.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * Converts a date string in the format dd-MM-yyyy" to a Date object.
     *
     * @param dateString the date string to convert
     * @return the Date object
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
}
