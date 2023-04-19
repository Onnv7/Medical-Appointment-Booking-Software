package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.SelectTimeDetailAdapter;
import com.hcmute.findyourdoctor.Adapter.SelectTimeAdapter;
import com.hcmute.findyourdoctor.AdapterObserver;
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ScheduleApiService;
import com.hcmute.findyourdoctor.Domain.BookingDomain;
import com.hcmute.findyourdoctor.Listener.OnAvailableDateClickListener;
import com.hcmute.findyourdoctor.Listener.OnSelectedTimeSlot;
import com.hcmute.findyourdoctor.Model.BookingModel;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTime;
import com.hcmute.findyourdoctor.Model.selectTimeDetail;
import com.hcmute.findyourdoctor.Utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSelectTimeDetailActivity extends AppCompatActivity implements OnAvailableDateClickListener, OnSelectedTimeSlot {
    String date, time;
    TextView tvAfternoonSlotNumber, tvEveningSlotNumber, tvMorningSlotNumber;
    Button btn_confirmBook;
    GridView gvAfternoon, gvEvening, gvMorning;
    List<selectTimeDetail> afternoonSlotList, eveningSlotList, morningSlotList;
    SelectTimeDetailAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    RecyclerView rcvSelectTimeDetail;
    List<selectTime> mselectTimeListDetail;
    SelectTimeAdapter selectTimeAdapterDetail;
    RetrofitClient retrofitClient;
    Doctor doctor;
    AdapterObserver adapterObserver = new AdapterObserver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_select_time_detail);
        init();
        Intent intent = getIntent();


        doctor = (Doctor) intent.getSerializableExtra("doctor");

        setRecyclerViewSelectDate(doctor.getId());

        CreateBook();
    }
    private void init() {
        mselectTimeListDetail = new ArrayList<>();
        rcvSelectTimeDetail = (RecyclerView) findViewById(R.id.rcv_date_time_select_time);

        gvEvening = (GridView) findViewById(R.id.gv_evening_select_time);
        gvAfternoon = (GridView) findViewById(R.id.gv_afternoon_select_time);
        gvMorning = findViewById(R.id.gv_morning_select_time);

        tvMorningSlotNumber = findViewById(R.id.tv_morning_slot_num_select_time);
        tvEveningSlotNumber = findViewById(R.id.tv_evening_slot_num_select_time);
        tvAfternoonSlotNumber = findViewById(R.id.tv_affternoon_slot_num_select_time);
        
    }
    private void CreateBook() {
        btn_confirmBook = findViewById(R.id.btn_confirmBook);
        btn_confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoctorSelectTimeDetailActivity.this, "KQ: " + adapterObserver.getType() +adapterObserver.getIndex(), Toast.LENGTH_SHORT).show();
                if(adapterObserver.getType().equals("morning")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + morningSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                }
                if(adapterObserver.getType().equals("afternoon")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + afternoonSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                }
                if(adapterObserver.getType().equals("evening")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + eveningSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                }
                BookingDomain book = new BookingDomain("642e7c9fb011248ed77f766f","642e7c52b011248ed77f766d","gap","accepted","vui","hay",2,"642fcd0b2e7e02772c9e5a0d");
                ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
                Call<BookingModel> call = apiService.createBook(book);
//                call.enqueue(new Callback<BookingModel>() {
//                    @Override
//                    public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
//                        if(response.body().getSuccess().equals(true)){
//                            openThank(Gravity.CENTER);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BookingModel> call, Throwable t) {
//                        Log.e("ok", t.getMessage());
//                    }
//                });
            }
        });
    }

    private void openThank(int center) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_successful);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        dialog.show();
    }


    private void setRecyclerViewSelectDate(String doctorId){
        List<selectTime> availableDate = new ArrayList<>();
        ScheduleApiService scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);
        List<String> dates = DateTimeUtil.getDateForRecyclerView();
        HashMap<String, String> body = new HashMap<>();
        body.put("start", dates.get(0));
        body.put("end", dates.get(1));

        scheduleApiService.getAvailableSchedule(doctorId, body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();

                if(res.get("success").getAsBoolean()) {
                    JsonArray availableSchedule = res.getAsJsonArray("result");
                    for (int i = 0; i < availableSchedule.size(); i++) {
                        JsonObject schedule = availableSchedule.get(i).getAsJsonObject();
                        selectTime date = new selectTime(schedule.get("date").getAsString(), schedule.get("available").getAsInt());
                        availableDate.add(date);
                    }
                    selectTimeAdapterDetail = new SelectTimeAdapter(availableDate, DoctorSelectTimeDetailActivity.this, DoctorSelectTimeDetailActivity.this);

                    rcvSelectTimeDetail.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DoctorSelectTimeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rcvSelectTimeDetail.setLayoutManager(layoutManager);
                    rcvSelectTimeDetail.setAdapter(selectTimeAdapterDetail);
                    selectTimeAdapterDetail.notifyDataSetChanged();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(String date) {
        ScheduleApiService scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);
        try {
            scheduleApiService.getAvailableSlotSchedule(doctor.getId(), date).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();
                    adapterObserver.setIndex(-1);
                    adapterObserver.setType("");
                    if(res.get("success").getAsBoolean()) {
                        JsonObject result = res.getAsJsonObject("result");
                        JsonArray morning = result.getAsJsonArray("morning");
                        JsonArray afternoon = result.getAsJsonArray("afternoon");
                        JsonArray evening = result.getAsJsonArray("evening");

                        afternoonAdapter = new SelectTimeDetailAdapter();

                        if(morning.size() > 0) {
                            gvMorning.setVisibility(View.VISIBLE);
                            tvMorningSlotNumber.setVisibility(View.VISIBLE);
                            tvMorningSlotNumber.setText("Morning " + morning.size() + " slots");
                            setDataForSlotGridView(morning, gvMorning, "morning");
                        }
                        else {
                            gvMorning.setVisibility(View.GONE);
                            tvMorningSlotNumber.setVisibility(View.GONE);
                        }

                        if(afternoon.size() > 0) {
                            gvAfternoon.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setText("Afternoon " + afternoon.size() + " slots");
                            setDataForSlotGridView(afternoon, gvAfternoon, "afternoon");
                        }
                        else {
                            gvAfternoon.setVisibility(View.GONE);
                            tvAfternoonSlotNumber.setVisibility(View.GONE);
                        }

                        if(evening.size() > 0) {
                            gvEvening.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setText("Evening " + evening.size() + " slots");
                            setDataForSlotGridView(evening, gvEvening, "evening");
                        }
                        else {
                            gvEvening.setVisibility(View.GONE);
                            tvEveningSlotNumber.setVisibility(View.GONE);
                        }
                    }
                    else {
                        gvMorning.setVisibility(View.GONE);
                        tvMorningSlotNumber.setVisibility(View.GONE);
                        gvEvening.setVisibility(View.GONE);
                        tvEveningSlotNumber.setVisibility(View.GONE);
                        gvAfternoon.setVisibility(View.GONE);
                        tvAfternoonSlotNumber.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    private void setDataForSlotGridView(JsonArray jsonArray, GridView gridView, String type){
        List<selectTimeDetail> dataList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            dataList.add( new selectTimeDetail(jsonArray.get(i).getAsString() + " PM"));
        }

        SelectTimeDetailAdapter adapter = new SelectTimeDetailAdapter(DoctorSelectTimeDetailActivity.this, R.layout.row_time_detail, dataList, type, DoctorSelectTimeDetailActivity.this, adapterObserver);
        adapter.notifyDataSetChanged();

        if(type.equals("morning")) {
            morningSlotList = dataList;
            morningAdapter = adapter;
        } else if (type.equals("afternoon")) {
            afternoonSlotList = dataList;
            afternoonAdapter = adapter;
        }else if (type.equals("evening")) {
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
    public void onSelectedTimeSlot(SelectTimeDetailAdapter adapter) {
    }


}