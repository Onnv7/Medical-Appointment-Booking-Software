package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapp.Adapter.AdapterObserver;
import com.example.doctorapp.Adapter.SelectTimeDetailAdapter;
import com.example.doctorapp.Domain.SelectTimeDetailDomain;
import com.example.doctorapp.Listener.OnAvailableDateClickListener;
import com.example.doctorapp.Listener.OnSelectedTimeSlot;
import com.example.doctorapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorSelectTimeActivity extends AppCompatActivity implements OnAvailableDateClickListener, OnSelectedTimeSlot {
    TextView tv_doctor_selecttime;
    GridView gv_morning_select_time, gv_afternoon_select_time, gv_evening_select_time;
    AdapterObserver adapterObserver = new AdapterObserver();
    List<SelectTimeDetailDomain> afternoonSlotList, eveningSlotList, morningSlotList;
    SelectTimeDetailAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    TextView tvAfternoonSlotNumber, tvEveningSlotNumber, tvMorningSlotNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_select_time);

        initView();

        tv_doctor_selecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDay();
            }
        });

        onClick("123");
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
                tv_doctor_selecttime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePicker.show();
    }

    public void initView() {
        tv_doctor_selecttime = (TextView) findViewById(R.id.tv_doctor_selecttime);
        gv_morning_select_time = (GridView) findViewById(R.id.gv_morning_select_time);
        gv_afternoon_select_time = (GridView) findViewById(R.id.gv_afternoon_select_time);
        gv_evening_select_time = (GridView) findViewById(R.id.gv_evening_select_time);

        tvMorningSlotNumber = findViewById(R.id.tv_morning_slot_num_select_time);
        tvEveningSlotNumber = findViewById(R.id.tv_evening_slot_num_select_time);
        tvAfternoonSlotNumber = findViewById(R.id.tv_affternoon_slot_num_select_time);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = dateFormat.format(calendar.getTime());
        tv_doctor_selecttime.setText(currentTime);
    }


    private void setDataForSlotGridView(List timeArray, GridView gridView, String type, String timeType) {
        List<SelectTimeDetailDomain> dataList = new ArrayList<>();
        for (int i = 0; i < timeArray.size(); i++) {
            dataList.add(new SelectTimeDetailDomain(timeArray.get(i) + " " + timeType));
        }

        SelectTimeDetailAdapter adapter = new SelectTimeDetailAdapter(DoctorSelectTimeActivity.this, R.layout.row_time_detail, dataList, type, DoctorSelectTimeActivity.this, adapterObserver);
        adapter.notifyDataSetChanged();

        if (type.equals("morning")) {
            morningSlotList = dataList;
            morningAdapter = adapter;
        } else if (type.equals("afternoon")) {
            afternoonSlotList = dataList;
            afternoonAdapter = adapter;
        } else if (type.equals("evening")) {
            eveningSlotList = dataList;
            eveningAdapter = adapter;
        }
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    @Override
    public void onClick(String date) {

        List<String> morning = new ArrayList<String>();
        morning.add("7:00 - 7:15");
        morning.add("7:15 - 7:30");
        morning.add("7:30 - 7:45");
        morning.add("7:45 - 8:00");
        morning.add("8:15 - 9:00");


        List<String> afternoon = new ArrayList<String>();

        afternoon.add("1:00 - 1:30 ");
        afternoon.add("1:45 - 2:10 ");
        afternoon.add("2:15 - 2:45 ");
        afternoon.add("3:10 - 4:40 ");


        List<String> evening = new ArrayList<String>();

        evening.add("12:00 - 12:15 ");
        evening.add("12:15 - 12:30 ");
        evening.add("12:30 - 12:45 ");
        evening.add("12:45 - 1:00 ");




        afternoonAdapter = new SelectTimeDetailAdapter();

        if (morning.size() > 0) {
            gv_morning_select_time.setVisibility(View.VISIBLE);
            tvMorningSlotNumber.setVisibility(View.VISIBLE);
            tvMorningSlotNumber.setText("Morning " + morning.size() + " slots");
            setDataForSlotGridView(morning, gv_morning_select_time, "morning", "AM");
        } else {
            gv_morning_select_time.setVisibility(View.GONE);
            tvMorningSlotNumber.setVisibility(View.GONE);
        }

        if (afternoon.size() > 0) {
            gv_afternoon_select_time.setVisibility(View.VISIBLE);
            tvAfternoonSlotNumber.setVisibility(View.VISIBLE);
            tvAfternoonSlotNumber.setText("Afternoon " + afternoon.size() + " slots");
            setDataForSlotGridView(afternoon, gv_afternoon_select_time, "afternoon", "PM");
        } else {
            gv_afternoon_select_time.setVisibility(View.GONE);
            tvAfternoonSlotNumber.setVisibility(View.GONE);
        }

        if (evening.size() > 0) {
            gv_evening_select_time.setVisibility(View.VISIBLE);
            tvEveningSlotNumber.setVisibility(View.VISIBLE);
            tvEveningSlotNumber.setText("Evening " + evening.size() + " slots");
            setDataForSlotGridView(evening, gv_evening_select_time, "evening", "PM");
        } else {
            gv_evening_select_time.setVisibility(View.GONE);
            tvEveningSlotNumber.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSelectedTimeSlot(SelectTimeDetailAdapter adapter) {

    }
}