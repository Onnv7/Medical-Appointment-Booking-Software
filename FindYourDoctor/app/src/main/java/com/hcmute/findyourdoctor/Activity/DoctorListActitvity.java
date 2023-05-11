package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.DoctorListAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActitvity extends AppCompatActivity {
    private String search;
    EditText edtSearch;
    RecyclerView listDoctors;
    DoctorApiService doctorApiService;
    List<Doctor> mdoctorList;
    DoctorListAdapter doctorListAdapter;
    String specialistId;
    List<Doctor> foundDoctors;
    ImageView imv_back_doctorlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list_actitvity);
        initView();
        renderUI();
        setOnSearchEvent();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        listDoctors.setLayoutManager(linearLayout);

        imv_back_doctorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        mdoctorList = new ArrayList<>();
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        imv_back_doctorlist = (ImageView) findViewById(R.id.imv_back_doctorlist);
        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        specialistId = intent.getStringExtra("specialistId");
        listDoctors = (RecyclerView) findViewById(R.id.listDoctors);
        edtSearch = findViewById(R.id.edt_search_doctor_list);

    }
    @SuppressLint("ClickableViewAccessibility")
    private void setOnSearchEvent() {
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(edtSearch.getText().toString().trim().equals("")) {
                        getAllDoctors();
                    }
                    else {
                        searchDoctor(edtSearch.getText().toString().trim());
                    }
                }
                return false;
            }
        });

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int drawableStart = 0, drawableEnd = 2;
                int marginLeft = ((ViewGroup.MarginLayoutParams) edtSearch.getLayoutParams()).leftMargin;
                int marginRight = ((ViewGroup.MarginLayoutParams) edtSearch.getLayoutParams()).rightMargin;
                int drawablePadding = edtSearch.getCompoundDrawablePadding();
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() <= (edtSearch.getCompoundDrawables()[drawableStart].getBounds().width() + edtSearch.getPaddingLeft() + marginLeft + drawablePadding)) {
                        searchDoctor(edtSearch.getText().toString().trim());
                        return true;
                    } else if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[drawableEnd].getBounds().width() - edtSearch.getPaddingRight() - drawablePadding)) {
                        edtSearch.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void getAllDoctors() {
        mdoctorList = new ArrayList<Doctor>();
        doctorApiService.getAllDoctors().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res= response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray result = res.getAsJsonArray("result");
                    int size = result.size();
                    Gson gson = new Gson();
                    System.out.println("---" +size);
                    for (int i = 0; i < size; i++) {
                        JsonObject doctorJson = result.get(i).getAsJsonObject();
                        Doctor doctor = gson.fromJson(doctorJson, Doctor.class);
                        mdoctorList.add(doctor);
                    }
                    doctorListAdapter = new DoctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
        else {
            getAllDoctors();
        }
    }
    private void searchDoctor(String searchContent) {
        foundDoctors =  new ArrayList<>();
        for (Doctor doctor : mdoctorList) {
            if (doctor.getName().toLowerCase().contains(searchContent.toLowerCase())) {
                foundDoctors.add(doctor);
            }
        }
        doctorListAdapter = new DoctorListAdapter(foundDoctors, DoctorListActitvity.this);
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
                    doctorListAdapter = new DoctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void renderSearchDoctorList() {
        edtSearch.setText(search);
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
                    doctorListAdapter = new DoctorListAdapter(mdoctorList, DoctorListActitvity.this);
                    listDoctors.setAdapter(doctorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}