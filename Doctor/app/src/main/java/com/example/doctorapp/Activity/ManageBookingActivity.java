package com.example.doctorapp.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctorapp.Adapter.ListBookingAdapter;
import com.example.doctorapp.Adapter.TimeManageBookingAdapter;
import com.example.doctorapp.Domain.DayDomain;
import com.example.doctorapp.Domain.InforPatientDomain;
import com.example.doctorapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageBookingActivity extends AppCompatActivity {
    TextView tv_selected_time_manage_booking;
    RecyclerView rv_day_booking_manage, lw_manage_booking;
    List<DayDomain> mListDay = new ArrayList<>();
    List<InforPatientDomain> mListInforPatient = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);

        initView();

        tv_selected_time_manage_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDay();
            }
        });


        addDate();

        rv_day_booking_manage = (RecyclerView) findViewById(R.id.rv_day_booking_manage);
        lw_manage_booking = (RecyclerView) findViewById(R.id.lw_manage_booking);

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        rv_day_booking_manage.setLayoutManager(linearLayout);

        addInforPatient();

        RecyclerView.LayoutManager linearLayout2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        lw_manage_booking.setLayoutManager(linearLayout2);

    }



    private void selectDay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                calendar.set(i, i1, i2);
                tv_selected_time_manage_booking.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePicker.show();
    }

    private void initView() {
        tv_selected_time_manage_booking = (TextView) findViewById(R.id.tv_selected_time_manage_booking);
        rv_day_booking_manage = (RecyclerView) findViewById(R.id.rv_day_booking_manage);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = dateFormat.format(calendar.getTime());
        tv_selected_time_manage_booking.setText(currentTime);
    }

    public void addDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String[] dates = new String[7];
        dates[0] = sdf.format(calendar.getTime());

        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            dates[i] = sdf.format(calendar.getTime());
        }

        for (int i = 0; i < 7; i++) {
            String[] parts = dates[i].split(",");
            String dayOfWeek = parts[0];

            switch (dayOfWeek.toLowerCase()) {
                case "monday":
                    parts[0] = "M";
                    break;
                case "tuesday":
                    parts[0] = "T";
                    break;
                case "wednesday":
                    parts[0] = "W";
                    break;
                case "thursday":
                    parts[0] = "Th";
                    break;
                case "friday":
                    parts[0] = "F";
                    break;
                case "saturday":
                    parts[0] = "Sa";
                    break;
                case "sunday":
                    parts[0] = "Su";
                    break;
                default:
                    parts[0] = dayOfWeek.substring(0, 1); // Lấy ký tự đầu tiên của thứ
                    break;
            }

            String date = parts[1].trim();
            mListDay.add(new DayDomain(parts[0], date));
            Log.d(TAG, "addDate: " + parts[0] + ", " + date);

            TimeManageBookingAdapter manageAdapter1 = new TimeManageBookingAdapter(mListDay, ManageBookingActivity.this);

            rv_day_booking_manage.setAdapter(manageAdapter1);

            manageAdapter1.notifyDataSetChanged();
        }
    }


    public void addInforPatient(){
        mListInforPatient.add(new InforPatientDomain("8:00 - 9:00", "Nguyen Binh A", "12/2/2002"));
        mListInforPatient.add(new InforPatientDomain("11:00 - 13:00", "Nguyen Van A", "12/2/2002"));
        mListInforPatient.add(new InforPatientDomain("13:00 - 14:00", "Nguyen Binh A", "12/2/2002"));
        mListInforPatient.add(new InforPatientDomain("8:00 - 9:00", "Nguyen Binh A", "12/2/2002"));

        ListBookingAdapter bookingAdapter = new ListBookingAdapter(mListInforPatient, ManageBookingActivity.this);

        lw_manage_booking.setAdapter(bookingAdapter);

        bookingAdapter.notifyDataSetChanged();
    }

}