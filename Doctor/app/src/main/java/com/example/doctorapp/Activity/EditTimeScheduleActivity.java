package com.example.doctorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doctorapp.Adapter.EditTimeSlotAdapter;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Api.ScheduleApiService;
import com.example.doctorapp.Domain.TimeSlotDomain;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTimeScheduleActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String uid;
    private String date;
    private Button btnComfirm;
    private ImageView ivBack;
    private ScheduleApiService scheduleApiService;
    private GridView gvMorning, gvAfternoon, gvEvening;
    List<TimeSlotDomain> afternoonSlotList, eveningSlotList, morningSlotList;
    private EditTimeSlotAdapter afternoonAdapter, eveningAdapter, morningAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_schedule);
        init();
        renderUI();
        setButtonClick();
    }
    private JsonArray getPeriod() {
        JsonArray periodList = new JsonArray();
        for (TimeSlotDomain morning: morningSlotList) {
            if(morning.getSelected()) {
                periodList.add(morning.getId());
            }
        }
        for (TimeSlotDomain afternoon: afternoonSlotList) {
            if(afternoon.getSelected()) {
                periodList.add(afternoon.getId());
            }
        }
        for (TimeSlotDomain evening: eveningSlotList) {
            if(evening.getSelected()) {
                periodList.add(evening.getId());
            }
        }
        return periodList;

    }
    private void setButtonClick() {
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(morningSlotList.toString());
                JsonObject body = new JsonObject();
                JsonArray periods = getPeriod();
                body.addProperty("date", date);
                body.add("period", periods);
                scheduleApiService.updateTimeSlotSchedule(uid, body).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                            Toast.makeText(EditTimeScheduleActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(EditTimeScheduleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void renderUI() {
        scheduleApiService.getScheduleForDoctor(uid, date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();

                    JsonObject result = res.getAsJsonObject("result");
                    JsonArray morning = result.getAsJsonArray("morning");
                    JsonArray afternoon = result.getAsJsonArray("afternoon");
                    JsonArray evening = result.getAsJsonArray("evening");

                    List<TimeSlotDomain> morningList = new ArrayList<>();
                    List<TimeSlotDomain> afternoonList = new ArrayList<>();
                    List<TimeSlotDomain> eveningList = new ArrayList<>();


                    for (JsonElement e: morning) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        morningList.add(item);
                    }
                    setDataForSlotGridView(morningList, gvMorning, "morning");


                    for (JsonElement e: afternoon) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        afternoonList.add(item);
                    }
                    setDataForSlotGridView(afternoonList, gvAfternoon, "afternoon");


                    for (JsonElement e: evening) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        eveningList.add(item);
                    }
                    setDataForSlotGridView(eveningList, gvEvening, "evening");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void setDataForSlotGridView(List<TimeSlotDomain> timeArray, GridView gridView, String type) {

        EditTimeSlotAdapter adapter = new EditTimeSlotAdapter(EditTimeScheduleActivity.this, R.layout.row_time_detail, timeArray, type);
        adapter.notifyDataSetChanged();

        if (type.equals("morning")) {
            morningSlotList = timeArray;
            this.morningAdapter = adapter;
        } else if (type.equals("afternoon")) {
            afternoonSlotList = timeArray;
            this.afternoonAdapter = adapter;
        } else if (type.equals("evening")) {
            eveningSlotList = timeArray;
            this.eveningAdapter = adapter;
        }
        gridView.setAdapter(adapter);
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    private void init() {
        sharedPreferences = EditTimeScheduleActivity.this.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        System.out.println("Nhan" + date);
        scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);

        gvMorning = findViewById(R.id.gv_morning_edit_time_schedule);
        gvAfternoon = findViewById(R.id.gv_afternoon_edit_time_schedule);
        gvEvening = findViewById(R.id.gv_evening_edit_time_schedule);

        btnComfirm = findViewById(R.id.btn_confirm_edit_time_schedule);
        ivBack = findViewById(R.id.iv_back_edit_time_schedule);
    }
}