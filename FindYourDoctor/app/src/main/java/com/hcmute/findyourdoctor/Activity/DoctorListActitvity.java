package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Fragment.HomeFragment;
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
    EditText edtSearch;
    RecyclerView listDoctors;
    DoctorApiService doctorApiService;
    List<Doctor> mdoctorList;
    doctorListAdapter doctorListAdapter;
    String specialistId;
    List<Doctor> foundDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list_actitvity);
        initView();
        renderUI();
        setOnSearchClick();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        listDoctors.setLayoutManager(linearLayout);
    }
    private void initView() {
        mdoctorList = new ArrayList<>();
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);

        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        specialistId = intent.getStringExtra("specialistId");
        listDoctors = (RecyclerView) findViewById(R.id.listDoctors);
        edtSearch = findViewById(R.id.edt_search_doctor_list);

    }
    private void setOnSearchClick() {
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(edtSearch.getText().toString().trim().equals("")) {
                        renderUI();
                    }
                    else {
                        searchDoctor(edtSearch.getText().toString().trim());
                    }
                }
                return false;
            }
        });
    }
    private void renderUI() {
        mdoctorList = new ArrayList<Doctor>();
        if(specialistId != null) {
            renderDoctorsListBySpecialist();
        }
        else if(search != null) {
            renderSearchDoctorList();
        }
    }
    private void searchDoctor(String searchContent) {
        foundDoctors =  new ArrayList<>();
        for (Doctor doctor : mdoctorList) {
            if (doctor.getName().toLowerCase().contains(searchContent.toLowerCase())) {
                foundDoctors.add(doctor);
            }
        }
        doctorListAdapter = new doctorListAdapter(foundDoctors, DoctorListActitvity.this);
        listDoctors.setAdapter(doctorListAdapter);
    }
    private void renderDoctorsListBySpecialist() {
        doctorApiService.getDoctorsBySpecialist(specialistId).enqueue(new Callback<JsonObject>() {
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
                    doctorListAdapter = new doctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void renderSearchDoctorList() {
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
                    doctorListAdapter = new doctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}