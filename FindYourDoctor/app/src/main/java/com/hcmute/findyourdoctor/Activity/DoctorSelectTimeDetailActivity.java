package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ScheduleApiService;
import com.hcmute.findyourdoctor.Domain.BookingDomain;
import com.hcmute.findyourdoctor.Listener.OnAvailableDateClickListener;
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

public class DoctorSelectTimeDetailActivity extends AppCompatActivity implements OnAvailableDateClickListener {
    String date, time;
    TextView tvAfternoonSlotNumber, tvEveningSlotNumber;
    Button btn_confirmBook;
    GridView gvAfternoon, gvEvening;
    List<selectTimeDetail> afternoonSlotList, eveningSlotList;
    SelectTimeDetailAdapter afternoonAdapter, eveningAdapter;
    RecyclerView rcvSelectTimeDetail;
    List<selectTime> mselectTimeListDetail;
    SelectTimeAdapter selectTimeAdapterDetail;
    RetrofitClient retrofitClient;
    Doctor doctor;
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
        tvEveningSlotNumber = findViewById(R.id.tv_evening_slot_num_select_time);
        tvAfternoonSlotNumber = findViewById(R.id.tv_affternoon_slot_num_select_time);
        
    }
    private void setGridView(){
        gvEvening.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DoctorSelectTimeDetailActivity.this, "lmmmm", Toast.LENGTH_SHORT).show();
//                gvEvening.getChildAt(i).setBackgroundResource(R.drawable.background_details_time_selected);
            }
        });
    }
    private void CreateBook() {
        btn_confirmBook = findViewById(R.id.btn_confirmBook);
        btn_confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingDomain book = new BookingDomain("642e7c9fb011248ed77f766f","642e7c52b011248ed77f766d","gap","accepted","vui","hay",2,"642fcd0b2e7e02772c9e5a0d");
                ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
                Call<BookingModel> call = apiService.createBook(book);
                call.enqueue(new Callback<BookingModel>() {
                    @Override
                    public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                        String a =String.valueOf(response.body().getSuccess());
                        if(response.body().getSuccess().equals(true)){
                            openThank(Gravity.CENTER);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingModel> call, Throwable t) {
                        Log.e("ok", t.getMessage());
                    }
                });
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
                    selectTimeAdapterDetail = new SelectTimeAdapter(availableDate, DoctorSelectTimeDetailActivity.this);

                    rcvSelectTimeDetail.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DoctorSelectTimeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rcvSelectTimeDetail.setLayoutManager(layoutManager);
                    rcvSelectTimeDetail.setAdapter(selectTimeAdapterDetail);

                    selectTimeAdapterDetail.notifyDataSetChanged();

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
                    if(res.get("success").getAsBoolean()) {
                        JsonObject result = res.getAsJsonObject("result");
                        JsonArray morning = result.getAsJsonArray("morning");
                        JsonArray afternoon = result.getAsJsonArray("afternoon");
                        JsonArray evening = result.getAsJsonArray("evening");


                        if(afternoon.size() > 0) {
                            gvAfternoon.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setText("Afternoon " + afternoon.size() + " slots");
                            setDataForSlotGridView(afternoon, afternoonSlotList, afternoonAdapter, gvAfternoon);

                        }
                        else {
                            gvAfternoon.setVisibility(View.GONE);
                            tvEveningSlotNumber.setVisibility(View.GONE);
                        }

                        if(evening.size() > 0) {
                            gvEvening.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setText("Evening " + evening.size() + " slots");
                            setDataForSlotGridView(evening, eveningSlotList, eveningAdapter, gvEvening);
                            System.out.println("eve");
                        }
                        else {
                            gvEvening.setVisibility(View.GONE);
                            tvEveningSlotNumber.setVisibility(View.GONE);
                        }

                    }
                    else {

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
    private void setDataForSlotGridView(JsonArray jsonArray, List<selectTimeDetail> dataList, SelectTimeDetailAdapter adapter, GridView gridView){
        dataList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            dataList.add( new selectTimeDetail(jsonArray.get(i).getAsString() + " PM"));
        }

        adapter = new SelectTimeDetailAdapter(DoctorSelectTimeDetailActivity.this, R.layout.row_time_detail, dataList);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DoctorSelectTimeDetailActivity.this, "beuh ânna", Toast.LENGTH_SHORT).show();
            }
        });
    }
}