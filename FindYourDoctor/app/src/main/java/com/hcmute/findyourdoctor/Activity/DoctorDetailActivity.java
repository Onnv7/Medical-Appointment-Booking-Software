package com.hcmute.findyourdoctor.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.ReviewAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.ReviewApiService;
import com.hcmute.findyourdoctor.Fragment.HomeFragment;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {
    RecyclerView listReview;
    List<Review> mReview;
    TextView tvName, tvSpecialist, tvPrice, tvClinicName, tvClinicAddress, tvIntroduce, tvPatientQuantity, tvSucceededQuantity, tvPhoneNumber;
    RatingBar ratingBar;
    Button btnBooking;
    ImageView ivAvatar;
    DoctorApiService doctorApiService;
    ReviewApiService reviewApiService;
    Doctor doctor;
    String doctorId;
    ImageView imv_back_doctor_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        init();

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        listReview.setLayoutManager(linearLayout);

        mReview = new ArrayList<>();
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        addReview();

//        String id = "643684e44171b72eadb118ef";
        renderDoctorInfo();
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetailActivity.this, DoctorSelectTimeDetailActivity.class);
                intent.putExtra("doctor", doctor);
                startActivity(intent);
            }
        });

        imv_back_doctor_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("id");
        tvName = findViewById(R.id.tv_name_doctor_details);
        tvSpecialist = findViewById(R.id.tv_specialist_doctor_details);
        tvPrice = findViewById(R.id.tv_price_doctor_details);
        tvClinicName = findViewById(R.id.tv_clinic_name_doctor_details);
        tvClinicAddress = findViewById(R.id.tv_clinic_address_doctor_details);
        tvPhoneNumber = findViewById(R.id.tv_phone_number_doctor_details);
        ivAvatar = findViewById(R.id.iv_avatar_doctor_details);
        listReview = (RecyclerView) findViewById(R.id.rcv_patient_review_doctor_details);
        tvIntroduce = findViewById(R.id.tv_introduce_doctor_details);
        btnBooking = findViewById(R.id.btn_booking_doctor_details);
        imv_back_doctor_detail = findViewById(R.id.imv_back_doctor_detail);

        tvPatientQuantity = findViewById(R.id.tv_patient_quantity);
        tvSucceededQuantity = findViewById(R.id.tv_successed_quantity);
        ratingBar = findViewById(R.id.rtb_doctor_details);

        reviewApiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);
    }

    private void renderDoctorInfo() {

        doctorApiService.getInfoDoctorById(doctorId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();

                if (res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonObject result = res.getAsJsonObject("result");
                    doctor = gson.fromJson(result, Doctor.class);
                    tvName.setText(doctor.getName());
                    tvSpecialist.setText(doctor.getSpecialist().getName());
                    tvPrice.setText("$ " + doctor.getPrice());
                    tvClinicAddress.setText(doctor.getClinicAddress());
                    tvClinicName.setText(doctor.getClinicName());
                    tvIntroduce.setText(doctor.getIntroduce());
                    tvPatientQuantity.setText(doctor.getPatientQuantity() + "");
                    tvPhoneNumber.setText(doctor.getPhone());
                    tvSucceededQuantity.setText(result.get("successBookingQuantity").getAsString());
                    ratingBar.setStepSize(0.1f);
                    ratingBar.setRating(doctor.getRating());
                    Glide.with(DoctorDetailActivity.this)
                            .load(doctor.getAvatarUrl())
                            .into(ivAvatar);
                }
                Log.d(TAG, "onResponse: detail:" + doctor.getId());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void addReview() {
        reviewApiService.getReviewByDoctor(doctorId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray result = res.getAsJsonArray("result");
                    int size = result.size();
                    Gson gson = new Gson();
                    for (int i = 0; i < size; i++) {
                        JsonObject review = result.get(i).getAsJsonObject();
                        Review rv = gson.fromJson(review, Review.class);
                        mReview.add(rv);
                        ReviewAdapter userAdapter = new ReviewAdapter(mReview, DoctorDetailActivity.this);
                        listReview.setAdapter(userAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}