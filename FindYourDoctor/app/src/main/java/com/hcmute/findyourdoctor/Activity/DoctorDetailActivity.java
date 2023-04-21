package com.hcmute.findyourdoctor.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.reviewAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {
    RecyclerView listReview;
    List<review> mReview;
    TextView tvName, tvSpecialist, tvPrice, tvClinicName, tvClinicAddress, tvIntroduce;
    Button btnBooking;
    ImageView ivAvatar;
    DoctorApiService doctorApiService;
    Doctor doctor;

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

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
//        String id = "643684e44171b72eadb118ef";

        doctorApiService.getInfoDoctorById(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();

                if(res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    doctor = gson.fromJson(res.getAsJsonObject("result"), Doctor.class);
                    doctor.setId(id);
                    tvName.setText(doctor.getName());
                    tvSpecialist.setText(doctor.getSpecialist());
                    tvPrice.setText("$ " + doctor.getPrice());
                    tvClinicAddress.setText(doctor.getClinicAddress());
                    tvClinicName.setText(doctor.getClinicName());
                    tvIntroduce.setText(doctor.getIntroduce());
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
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetailActivity.this, DoctorSelectTimeDetailActivity.class);
                intent.putExtra("doctor", doctor);

                startActivity(intent);
            }
        });
    }
    private void init() {
        tvName = findViewById(R.id.tv_name_doctor_details);
        tvSpecialist = findViewById(R.id.tv_specialist_doctor_details);
        tvPrice = findViewById(R.id.tv_price_doctor_details);
        tvClinicName = findViewById(R.id.tv_clinic_name_doctor_details);
        tvClinicAddress = findViewById(R.id.tv_clinic_address_doctor_details);
        ivAvatar = findViewById(R.id.iv_avatar_doctor_details);
        listReview = (RecyclerView) findViewById(R.id.listReview);
        tvIntroduce = findViewById(R.id.tv_introduce_doctor_details);
        btnBooking = findViewById(R.id.btn_booking_doctor_details);

    }
    private void addReview(){
        review review1 = new review("Patient1", "ok luôn");
        review review2 = new review("Patient2", "ok");
        review review3 = new review("Patient2", "ok luôn");
        review review4 = new review("Patient2", "ok");
//        mReview.add(review1);
//        mReview.add(review2);
//        mReview.add(review3);
//        mReview.add(review4);

        reviewAdapter userAdapter = new reviewAdapter(mReview);
        listReview.setAdapter(userAdapter);

    }
}