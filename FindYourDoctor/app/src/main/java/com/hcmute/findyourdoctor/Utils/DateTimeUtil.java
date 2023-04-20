package com.hcmute.findyourdoctor.Utils;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public static String formatDate(String inputDate) {
        String formattedDate = "";

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = null;
                date = inputDateFormat.parse(inputDate);
                // Kiểm tra xem ngày đầu vào có trùng với ngày hiện tại không
                Calendar inputCalendar = Calendar.getInstance();
                inputCalendar.setTime(date);
                Calendar todayCalendar = Calendar.getInstance();
                boolean isToday = inputCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                        inputCalendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) &&
                        inputCalendar.get(Calendar.DAY_OF_MONTH) == todayCalendar.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEE, d MMMM", Locale.getDefault());

                if (isToday) {
                    // Nếu là ngày hôm nay, đặt thành "Today"
                    formattedDate = "Today, " + outputDateFormat.format(date);
                } else {
                    // Ngược lại, định dạng theo mẫu "EEE, d MMMM"
                    formattedDate = outputDateFormat.format(date);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }
}
