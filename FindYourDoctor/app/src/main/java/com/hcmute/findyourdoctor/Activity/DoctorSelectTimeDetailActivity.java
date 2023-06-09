package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.SelectTimeDetailAdapter;
import com.hcmute.findyourdoctor.Adapter.SelectTimeAdapter;
import com.hcmute.findyourdoctor.AdapterObserver;
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ScheduleApiService;
import com.hcmute.findyourdoctor.Domain.TimeSlotDomain;
import com.hcmute.findyourdoctor.Listener.OnAvailableDateClickListener;
import com.hcmute.findyourdoctor.Listener.OnSelectedTimeSlot;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Domain.SelectTimeDomain;
import com.hcmute.findyourdoctor.Domain.SelectTimeDetailDomain;
import com.hcmute.findyourdoctor.Utils.Constant;
import com.hcmute.findyourdoctor.Utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSelectTimeDetailActivity extends AppCompatActivity
        implements OnAvailableDateClickListener, OnSelectedTimeSlot {
    String date, time;
    TextView tvAfternoonSlotNumber, tvEveningSlotNumber, tvMorningSlotNumber, tvSelectedDate, tvDoctorName,
            tvSpecialist, tvPatientQuantity;
    Button btnConfirm;
    EditText edtReminder;
    ImageView ivDoctorAvatar, imv_back_select_time;
    RatingBar ratingBar;
    GridView gvAfternoon, gvEvening, gvMorning;
    List<TimeSlotDomain> afternoonSlotList, eveningSlotList, morningSlotList;
    SelectTimeDetailAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    RecyclerView rcvSelectTimeDetail;
    List<SelectTimeDomain> mselectTimeListDetail;
    SelectTimeAdapter selectTimeAdapterDetail;
    BookingApiService bookingApiService;
    Doctor doctor;
    AdapterObserver adapterObserver = new AdapterObserver();
    SharedPreferences sharedPreferences;
    String uid;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = DoctorSelectTimeDetailActivity.this.getSharedPreferences(Constant.SHARE,
                Context.MODE_PRIVATE);

        uid = sharedPreferences.getString("id", "");
        setContentView(R.layout.activity_doctor_select_time_detail);
        init();
        Intent intent = getIntent();

        doctor = (Doctor) intent.getSerializableExtra("doctor");
        setDoctorInfo();
        setRecyclerViewSelectDate(doctor.getId());

        setOnBookingClick();

        imv_back_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        tvSelectedDate = findViewById(R.id.tv_selected_date);
        btnConfirm = findViewById(R.id.btn_confirmBook);
        imv_back_select_time = findViewById(R.id.imv_back_select_time);

        edtReminder = findViewById(R.id.edt_reminder_for_doctor);
        tvDoctorName = findViewById(R.id.tv_doctor_name_select_time);
        tvSpecialist = findViewById(R.id.tv_specialist_select_time);

        ratingBar = findViewById(R.id.rtb_doctor_select_time);
        tvPatientQuantity = findViewById(R.id.tv_patient_quantity_select_time);

        ivDoctorAvatar = findViewById(R.id.iv_avatar_select_time);

    }

    private void setOnBookingClick() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(DoctorSelectTimeDetailActivity.this);
                alert.setTitle("Booking");
                alert.setIcon(R.mipmap.ic_launcher);
                alert.setMessage("Are you sure to book this appointment?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String time = "";
                        if (adapterObserver.getType().equals("morning")) {
                            time = morningSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                        }
                        if (adapterObserver.getType().equals("afternoon")) {
                            time = afternoonSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                        }
                        if (adapterObserver.getType().equals("evening")) {
                            time = eveningSlotList.get(adapterObserver.getIndex()).getTime() + " " + selectedDate;
                        }
                        String message = edtReminder.getText().toString();
                        if (time.equals("")) {
                            Toast.makeText(DoctorSelectTimeDetailActivity.this,
                                    "Please choose a time for your appointment", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);

                        JsonObject body = new JsonObject();
                        body.addProperty("patient", uid);
                        body.addProperty("doctor", doctor.getId());
                        body.addProperty("message", edtReminder.getText().toString());
                        body.addProperty("status", "waiting");
                        body.addProperty("time", time);
                        Call<JsonObject> call = bookingApiService.createBooking(body);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JsonObject res = response.body();
                                if (res.get("success").getAsBoolean()) {
                                    openThank(Gravity.CENTER);
                                } else if (res.get("message").getAsString().equals("Existed")) {
                                    Toast.makeText(DoctorSelectTimeDetailActivity.this,
                                            "You have already booked this appointment. Please choose another time slot",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(DoctorSelectTimeDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();

            }
        });
    }

    private void openThank(int center) {
        TextView tv_done;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_successful);
        Window window = dialog.getWindow();
        tv_done = dialog.findViewById(R.id.tv_done);
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        dialog.show();

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    private void setRecyclerViewSelectDate(String doctorId) {
        List<SelectTimeDomain> availableDate = new ArrayList<>();
        ScheduleApiService scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);
        List<String> dates = DateTimeUtil.getDateForRecyclerView();
        HashMap<String, String> body = new HashMap<>();
        body.put("start", dates.get(0));
        body.put("end", dates.get(1));

        scheduleApiService.getAvailableSchedule(doctorId, body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();

                if (res.get("success").getAsBoolean()) {
                    JsonArray availableSchedule = res.getAsJsonArray("result");
                    for (int i = 0; i < availableSchedule.size(); i++) {
                        JsonObject schedule = availableSchedule.get(i).getAsJsonObject();
                        SelectTimeDomain date = new SelectTimeDomain(schedule.get("date").getAsString(),
                                schedule.get("available").getAsInt());
                        availableDate.add(date);
                    }
                    selectTimeAdapterDetail = new SelectTimeAdapter(availableDate, DoctorSelectTimeDetailActivity.this,
                            DoctorSelectTimeDetailActivity.this);

                    rcvSelectTimeDetail.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                            DoctorSelectTimeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rcvSelectTimeDetail.setLayoutManager(layoutManager);
                    rcvSelectTimeDetail.setAdapter(selectTimeAdapterDetail);
                    selectTimeAdapterDetail.notifyDataSetChanged();
                } else {

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
                    if (res.get("success").getAsBoolean()) {
                        JsonObject result = res.getAsJsonObject("result");
                        JsonArray morning = result.getAsJsonArray("morning");
                        JsonArray afternoon = result.getAsJsonArray("afternoon");
                        JsonArray evening = result.getAsJsonArray("evening");

                        afternoonAdapter = new SelectTimeDetailAdapter();

                        if (morning.size() > 0) {
                            gvMorning.setVisibility(View.VISIBLE);
                            tvMorningSlotNumber.setVisibility(View.VISIBLE);
                            tvMorningSlotNumber.setText("Morning " + morning.size() + " slots");
                            setDataForSlotGridView(morning, gvMorning, "morning", "AM");
                        } else {
                            gvMorning.setVisibility(View.GONE);
                            tvMorningSlotNumber.setVisibility(View.GONE);
                        }

                        if (afternoon.size() > 0) {
                            gvAfternoon.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setVisibility(View.VISIBLE);
                            tvAfternoonSlotNumber.setText("Afternoon " + afternoon.size() + " slots");
                            setDataForSlotGridView(afternoon, gvAfternoon, "afternoon", "PM");
                        } else {
                            gvAfternoon.setVisibility(View.GONE);
                            tvAfternoonSlotNumber.setVisibility(View.GONE);
                        }

                        if (evening.size() > 0) {
                            gvEvening.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setVisibility(View.VISIBLE);
                            tvEveningSlotNumber.setText("Evening " + evening.size() + " slots");
                            setDataForSlotGridView(evening, gvEvening, "evening", "PM");
                        } else {
                            gvEvening.setVisibility(View.GONE);
                            tvEveningSlotNumber.setVisibility(View.GONE);
                        }
                    } else {
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
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void setDataForSlotGridView(JsonArray jsonArray, GridView gridView, String type, String timeType) {
        List<TimeSlotDomain> dataList = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            TimeSlotDomain timeSlotDomain = gson.fromJson(jsonArray.get(i), TimeSlotDomain.class);
            dataList.add(timeSlotDomain);
        }

        SelectTimeDetailAdapter adapter = new SelectTimeDetailAdapter(DoctorSelectTimeDetailActivity.this,
                R.layout.row_time_detail, dataList, type, DoctorSelectTimeDetailActivity.this, adapterObserver);
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
    public void onSelectedTimeSlot(SelectTimeDetailAdapter adapter) {
    }

    private void setDoctorInfo() {
        tvSpecialist.setText(doctor.getSpecialist().getName());
        tvDoctorName.setText(doctor.getName());
        ratingBar.setRating(doctor.getRating());
        tvPatientQuantity.setText("(" + doctor.getPatientQuantity() + " patients)");
        Glide.with(this)
                .load(doctor.getAvatarUrl())
                .into(ivDoctorAvatar);
    }
}