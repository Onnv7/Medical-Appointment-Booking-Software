package com.example.doctorapp.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormat {
    // date: yyyy-MM-dd
    public static String formatDate(String dateInput, String pattern) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputDateFormat.parse(dateInput);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(pattern);
        return outputDateFormat.format(date);
    }

    public static String formatDateMongodb(String dateInput, String pattern) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(pattern);
        Date inputDate = inputDateFormat.parse(dateInput);
        return outputDateFormat.format(inputDate);
    }
}
