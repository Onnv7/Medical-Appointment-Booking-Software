package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Api.BookingApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBookingDetailsActivity extends AppCompatActivity {
    private String bookingId;
    private BookingApiService bookingApiService;
    private TextView tvName, tvDatetime, tvStatus, tvMessage, tvAdvice, tvPhoneNumber;
    private TextView btnAccept, btnDeny;
    private ImageView ivPatientAvt, ivBack;
    private ConstraintLayout layoutControlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking_details);
        init();
        renderUI();
        setOnButtonClick(btnAccept, "accepted");
        setOnButtonClick(btnDeny, "denied");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderUI() {
        bookingApiService.getBookingDetails(bookingId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    JsonObject result = res.getAsJsonObject("result");
                    Gson gson = new Gson();
                    Booking booking = gson.fromJson(result, Booking.class);
                    tvName.setText(booking.getPatient().getName());
                    tvDatetime.setText(booking.getTime());
                    tvPhoneNumber.setText(booking.getPatient().getPhone());
                    String status = booking.getStatus();

                    tvStatus.setText(booking.getStatus());
                    tvMessage.setText(booking.getMessage());

                    Glide.with(NewBookingDetailsActivity.this)
                            .load(booking.getPatient().getAvatarUrl())
                            .into(ivPatientAvt);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void setOnButtonClick(TextView btn, String status) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject body = new JsonObject();
                body.addProperty("status", status);
                bookingApiService.updateBookingDetails(bookingId, body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
    }
    private void init() {
        Intent intent = getIntent();
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingId = intent.getStringExtra("booking_id");
        ivPatientAvt = findViewById(R.id.iv_patient_avt_new_booking_details);
        tvName = findViewById(R.id.tv_name_new_booking_details);
        tvDatetime = findViewById(R.id.tv_datetime_new_booking_details);
        tvStatus = findViewById(R.id.tv_status_new_booking_details);
        tvMessage = findViewById(R.id.tv_patient_message_new_booking_details);
        btnAccept = findViewById(R.id.btn_accept_new_booking_details);
        btnDeny = findViewById(R.id.btn_deny_new_booking_details);

        layoutControlButton = findViewById(R.id.layout_control_button_new_booking_details);
        tvPhoneNumber = findViewById(R.id.tv_patient_phone_new_booking_details);
        ivBack = findViewById(R.id.imv_back_new_booking_details);
    }
}