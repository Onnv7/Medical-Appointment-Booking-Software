package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.SeeallSpecialtyAdapter;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.SpecialistApiService;
import com.hcmute.findyourdoctor.Model.Specialist;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSpecialistActivity extends AppCompatActivity {
    List<Specialist> specialistList = new ArrayList<Specialist>();
    RecyclerView rcvSpecialistList;
    ImageView back_specialty;
    SpecialistApiService specialistApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_list);
        initViews();
        renderSpecialtyList();
        back_specialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void renderSpecialtyList(){
        specialistApiService.getAllSpecialists().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                Gson gson = new Gson();
                if(res.get("success").getAsBoolean()) {
                    JsonArray specialists = res.getAsJsonArray("result");
                    int size = specialists.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject specialist = specialists.get(i).getAsJsonObject();
                        Specialist specialistDomain = gson.fromJson(specialist, Specialist.class);
                        specialistList.add(specialistDomain);
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ListSpecialistActivity.this, 2, RecyclerView.VERTICAL, false);

                    rcvSpecialistList.setLayoutManager(layoutManager);
                    SeeallSpecialtyAdapter specialistAdapter = new SeeallSpecialtyAdapter(specialistList, ListSpecialistActivity.this);
                    rcvSpecialistList.setAdapter(specialistAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void initViews() {
        specialistApiService = RetrofitClient.getRetrofit().create(SpecialistApiService.class);
        rcvSpecialistList = findViewById(R.id.rcv_list_specialty);
        back_specialty = findViewById(R.id.back_specialty);

    }
}