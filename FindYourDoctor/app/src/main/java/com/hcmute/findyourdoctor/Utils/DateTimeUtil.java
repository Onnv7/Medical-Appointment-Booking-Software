package com.hcmute.findyourdoctor.Utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
    public static List<String> getDateForRecyclerView() {
        LocalDate currentDate = null;
        List<String> list = new ArrayList<String>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Lấy ngày hiện tại
            Calendar calendar = Calendar.getInstance();
//            Date today = calendar.getTime();

            // Lấy ngày của ngày mai
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = calendar.getTime();
            list.add(sdf.format(tomorrow));

            // Lấy ngày của 7 ngày sau
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            Date sevenDaysAfter = calendar.getTime();
            list.add(sdf.format(sevenDaysAfter));
        }
        return list;
    }
}
