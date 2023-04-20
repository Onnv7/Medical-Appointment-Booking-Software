package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.hcmute.findyourdoctor.Utils.Constant;
import com.hcmute.findyourdoctor.Utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSelectTimeDetailActivity extends AppCompatActivity implements OnAvailableDateClickListener, OnSelectedTimeSlot {
    String date, time;
    TextView tvAfternoonSlotNumber, tvEveningSlotNumber, tvMorningSlotNumber, tvSelectedDate, tvDoctorName, tvSpecialist, tvPatientQuantity;
    Button btnConfirm;
    EditText edtReminder;
    ImageView ivDoctorAvatar;
    RatingBar ratingBar;
    GridView gvAfternoon, gvEvening, gvMorning;
    List<selectTimeDetail> afternoonSlotList, eveningSlotList, morningSlotList;
    SelectTimeDetailAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    RecyclerView rcvSelectTimeDetail;
    List<selectTime> mselectTimeListDetail;
    SelectTimeAdapter selectTimeAdapterDetail;
    RetrofitClient retrofitClient;
    Doctor doctor;
    AdapterObserver adapterObserver = new AdapterObserver();
    SharedPreferences sharedPreferences;
    String uid;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences  = DoctorSelectTimeDetailActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);

        uid = sharedPreferences.getString("id", "");
        setContentView(R.layout.activity_doctor_select_time_detail);
        init();
        Intent intent = getIntent();

        doctor = (Doctor) intent.getSerializableExtra("doctor");
        setDoctorInfo();
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

        tvSelectedDate =findViewById(R.id.tv_selected_date);
        btnConfirm = findViewById(R.id.btn_confirmBook);

        edtReminder = findViewById(R.id.edt_reminder_for_doctor);
        tvDoctorName = findViewById(R.id.tv_doctor_name_select_time);
        tvSpecialist = findViewById(R.id.tv_specialist_select_time);

        ratingBar = findViewById(R.id.rtb_doctor_select_time);
        tvPatientQuantity = findViewById(R.id.tv_patient_quantity_select_time);

        ivDoctorAvatar = findViewById(R.id.iv_avatar_select_time);
        
    }
    private void CreateBook() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = "";
                if(adapterObserver.getType().equals("morning")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + morningSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                    time = morningSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                }
                if(adapterObserver.getType().equals("afternoon")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + afternoonSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                    time = afternoonSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                }
                if(adapterObserver.getType().equals("evening")) {
                    Toast.makeText(DoctorSelectTimeDetailActivity.this, "value" + eveningSlotList.get(adapterObserver.getIndex()).getTime(), Toast.LENGTH_SHORT).show();
                    time = eveningSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                }
                String message = edtReminder.getText().toString();
                BookingDomain book = new BookingDomain(uid,doctor.getId(), message,"waiting", time);
                ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
                Call<BookingModel> call = apiService.createBooking(book);
                call.enqueue(new Callback<BookingModel>() {
                    @Override
                    public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
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
            tvSelectedDate.setText(DateTimeUtil.formatDate(date, "EEE, d MMMM"));
            selectedDate = DateTimeUtil.formatDate(date, "EEE, dd MMMM yyyy");
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
    private void setDoctorInfo() {
        tvSpecialist.setText(doctor.getSpecialist());
        tvDoctorName.setText(doctor.getName());
        ratingBar.setRating(doctor.getRating());
        tvPatientQuantity.setText("(" +doctor.getPatientQuantity() + " patients)");
        Glide.with(this)
                .load(doctor.getAvatarUrl())
                .into(ivDoctorAvatar);
    }
}