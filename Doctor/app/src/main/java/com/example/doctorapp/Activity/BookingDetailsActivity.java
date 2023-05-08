package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class BookingDetailsActivity extends AppCompatActivity {
    private String bookingId;
    private ImageView ivPatientAvt, ivBack;
    private BookingApiService bookingApiService;
    private TextView tvName, tvDatetime, tvStatus, tvMessage, tvAdvice, tvPhoneNumber;
    private TextView btnSuccess, btnDeny;
    private EditText edtAdvice;
    private ConstraintLayout layoutControlButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        init();
        renderUI();
        setOnButtonClick(btnSuccess, "succeeded");
        setOnButtonClick(btnDeny, "denied");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setOnButtonClick(TextView btn, String status) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject body = new JsonObject();
                body.addProperty("advice", edtAdvice.getText().toString());
                body.addProperty("status", status);
                bookingApiService.updateBookingDetails(bookingId, body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                            tvAdvice.setVisibility(View.VISIBLE);
                            tvAdvice.setText(edtAdvice.getText().toString());
                            edtAdvice.setVisibility(View.GONE);
                            layoutControlButton.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }
        });
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
                    if(status.equals("succeeded") || status.equals("denied")) {
                        if(status.equals("succeeded")) {
                            tvStatus.setAllCaps(true);
                            tvStatus.setTextColor(ContextCompat.getColor(BookingDetailsActivity.this, R.color.status_succeeded));
                        } else {
                            tvStatus.setAllCaps(true);
                            tvStatus.setTextColor(ContextCompat.getColor(BookingDetailsActivity.this, R.color.status_denied));
                        }
                        edtAdvice.setVisibility(View.GONE);
                        layoutControlButton.setVisibility(View.GONE);
                        tvAdvice.setText(booking.getAdvice());
                    }
                    else {
                        tvAdvice.setVisibility(View.GONE);
                    }
                    tvStatus.setText(booking.getStatus());
                    tvMessage.setText(booking.getMessage());

                    Glide.with(BookingDetailsActivity.this)
                            .load(booking.getPatient().getAvatarUrl())
                            .into(ivPatientAvt);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void init() {
        Intent intent = getIntent();
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingId = intent.getStringExtra("booking_id");
        ivPatientAvt = findViewById(R.id.iv_patient_avt_booking_details);
        tvName = findViewById(R.id.tv_name_booking_details);
        tvDatetime = findViewById(R.id.tv_datetime_booking_details);
        tvStatus = findViewById(R.id.tv_status_booking_details);
        tvMessage = findViewById(R.id.tv_patient_message_booking_details);
        edtAdvice = findViewById(R.id.edt_advice_booking_details);
        btnSuccess = findViewById(R.id.btn_success_booking_details);
        btnDeny = findViewById(R.id.btn_deny_schedule_details);
        tvAdvice = findViewById(R.id.tv_advice_booking_details);
        layoutControlButton = findViewById(R.id.layout_control_button_booking_details);
        tvPhoneNumber = findViewById(R.id.tv_patient_phone_booking_details);
        ivBack = findViewById(R.id.iv_back_booking_details);
    }
}