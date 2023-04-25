package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Domain.PopularDoctorDomain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActitvity extends AppCompatActivity {
    private  String search;

    RecyclerView listDoctors;
    DoctorApiService doctorApiService;
    List<Doctor> mdoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list_actitvity);
        initView();

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        listDoctors.setLayoutManager(linearLayout);
        renderDoctorList();

//        addReview();
    }
    private void initView() {
        mdoctorList = new ArrayList<>();
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);

        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        listDoctors = (RecyclerView) findViewById(R.id.listDoctors);

    }
    private void renderDoctorList() {
        doctorApiService.searchDoctor(search).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonArray result = res.get("result").getAsJsonArray();
                    int size = result.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = result.get(i).getAsJsonObject();
                        Doctor newDoctor = gson.fromJson(doctor, Doctor.class);
                        mdoctorList.add(newDoctor);
                    }
                    doctorListAdapter doctorListAdapter = new doctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}