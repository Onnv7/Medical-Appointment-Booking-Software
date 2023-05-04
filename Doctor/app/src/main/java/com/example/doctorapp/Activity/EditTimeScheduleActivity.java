package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Api.ScheduleApiService;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;

public class EditTimeScheduleActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String uid;
    private String date;
    private ScheduleApiService scheduleApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_schedule);
        init();
    }
    private void init() {
        sharedPreferences = EditTimeScheduleActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);
    }
}