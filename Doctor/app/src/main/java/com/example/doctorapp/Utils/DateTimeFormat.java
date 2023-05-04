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
}
