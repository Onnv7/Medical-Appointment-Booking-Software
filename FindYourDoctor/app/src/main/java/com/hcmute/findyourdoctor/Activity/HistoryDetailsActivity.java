package com.hcmute.findyourdoctor.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ReviewApiService;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailsActivity extends AppCompatActivity {
    private ImageView ivDoctorAvatar, ivBack;
    private TextView tvTime, tvStatus, tvMessage, tvReview, tvDoctorAdvice, tvDoctorName, tvCreatedAt;
    private RatingBar rtbRatingDoctor;
    private LinearLayout loDoctorAdvice, loReview;
    private EditText edtReview;
    private Button btnSubmit;
    private String bookingId;
    private BookingApiService bookingApiService;
    private ReviewApiService reviewApiService;
    private String uid;
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        initViews();
        Intent intent = getIntent();
        bookingId = intent.getStringExtra("booking_id");
        renderViewFromData(bookingId);
        setOnButtonClick();
    }
    private void setOnButtonClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HashMap<String, Object> body = new HashMap<>();
                JsonObject body = new JsonObject();
                if(rtbRatingDoctor.getRating() == 0)
                {
                    Toast.makeText(HistoryDetailsActivity.this, "Please choose your satisfaction level by choosing the number of stars", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtReview.getText().toString().trim().equals(""))
                {
                    Toast.makeText(HistoryDetailsActivity.this, "Please write your review about this appointment", Toast.LENGTH_SHORT).show();
                    edtReview.requestFocus();
                    return;
                }
                float star = rtbRatingDoctor.getRating();
                String review = edtReview.getText().toString();
                body.addProperty("patient", uid);
                body.addProperty("doctor", doctorId);
                body.addProperty("booking", bookingId);
                body.addProperty("description", review);
                body.addProperty("star", star);
                reviewApiService.createReview(body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(HistoryDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            rtbRatingDoctor.setIsIndicator(true);
                            rtbRatingDoctor.setRating(star);
                            tvReview.setVisibility(View.VISIBLE);
                            tvReview.setText(review);

                            edtReview.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(HistoryDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }
    private void initViews() {
        SharedPreferences sharedPreferences = HistoryDetailsActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");

        ivDoctorAvatar = findViewById(R.id.iv_doctor_avatar_history_details);
        tvTime = findViewById(R.id.tv_appoinment_time_history_details);
        tvStatus = findViewById(R.id.tv_status_history_details);
        tvMessage = findViewById(R.id.tv_reminder_history_details);
        tvReview = findViewById(R.id.tv_review_history_details);
        tvDoctorAdvice = findViewById(R.id.tv_doctor_advice_history_details);
        tvDoctorName = findViewById(R.id.tv_hello_name_profile_fragment);
        tvCreatedAt = findViewById(R.id.tv_created_at_history_details);

        rtbRatingDoctor = findViewById(R.id.rtb_doctor_history_details);
        edtReview = findViewById(R.id.edt_review_history_details);

        btnSubmit = findViewById(R.id.btn_submit_history_details);

        loDoctorAdvice = findViewById(R.id.lo_doctor_advice_history_details);
        loReview = findViewById(R.id.lo_review_history_details);

        reviewApiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        ivBack = findViewById(R.id.iv_back_appoitment_details);


    }
    private void renderViewFromData(String bookingId) {
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingApiService.getHistoryAppointmentDetails(bookingId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonObject result = res.getAsJsonObject("result");
                    JsonObject doctor = result.getAsJsonObject("doctor");
                    JsonElement reviewElement = result.get("review");
                    JsonObject review;
                    if (reviewElement != null && !reviewElement.isJsonNull() && reviewElement.isJsonObject()) {
                        review = reviewElement.getAsJsonObject();
                    } else {
                        review = null;
                    }
                    doctorId = doctor.get("_id").getAsString();
                    String status = result.get("status").getAsString();
                    tvTime.setText(result.get("time").getAsString());
                    tvStatus.setText(status);
                    tvStatus.setAllCaps(true);
                    tvMessage.setText(result.get("message").getAsString());
                    tvCreatedAt.setText(result.get("createdAt").getAsString());
                    tvDoctorName.setText(doctor.get("name").getAsString());
                    Glide.with(HistoryDetailsActivity.this)
                            .load(doctor.get("avatarUrl").getAsString())
                            .into(ivDoctorAvatar);

                    if(status.equals("succeeded")) {
                        tvStatus.setTextColor(ContextCompat.getColor(HistoryDetailsActivity.this, R.color.status_succeeded));
                        tvDoctorAdvice.setText(result.get("advice").getAsString());
                        if(review != null) {
                            float star = review.get("star").getAsFloat();
                            btnSubmit.setVisibility(View.GONE);
                            edtReview.setVisibility(View.GONE);
                            rtbRatingDoctor.setRating(star);
                            tvReview.setText(review.get("description").getAsString());
                        }
                        else {
                            rtbRatingDoctor.setIsIndicator(false);
                            tvReview.setVisibility(View.GONE);

                        }
                    } else {
                        if(status.equals("waiting")) {
                            tvStatus.setTextColor(ContextCompat.getColor(HistoryDetailsActivity.this, R.color.status_waiting));
                        } else if (status.equals("accepted")) {
                            tvStatus.setTextColor(ContextCompat.getColor(HistoryDetailsActivity.this, R.color.status_accepted));
                        } else if (status.equals("denied")) {
                            tvStatus.setTextColor(ContextCompat.getColor(HistoryDetailsActivity.this, R.color.status_denied));
                        }
                        loDoctorAdvice.setVisibility(View.GONE);
                        loReview.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.GONE);
                        edtReview.setVisibility(View.GONE);
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

}