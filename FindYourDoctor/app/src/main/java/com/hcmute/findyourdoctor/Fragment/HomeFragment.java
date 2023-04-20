package com.hcmute.findyourdoctor.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.DoctorDetailActivity;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.SpecialistApiService;
import com.hcmute.findyourdoctor.Domain.FeatureDoctorDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Domain.PopularDoctorDomain;
import com.hcmute.findyourdoctor.Listener.OnDocterCardClickListener;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements OnDocterCardClickListener {

    private RecyclerView rcv_specialist;
    private RecyclerView rcv_popularDoctor;
    private RecyclerView rcv_featureDoctor;
    List<SpecialistDomain> mSpecialist;
    List<Doctor> mPopularList;
    List<Doctor> mFeatureDoctor;

    SpecialistApiService specialistApiService;
    DoctorApiService doctorApiService;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        specialistApiService = RetrofitClient.getRetrofit().create(SpecialistApiService.class);
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);


//        recyclerViewSpecialist();

        rcv_specialist =  view.findViewById(R.id.rcv_specialist);
        rcv_popularDoctor =  view.findViewById(R.id.rcv_popularDoctor);
        rcv_featureDoctor =  view.findViewById(R.id.rcv_featureDoctor);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerPopular = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutFeaturePopular = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        rcv_specialist.setLayoutManager(layoutManager);
        rcv_popularDoctor.setLayoutManager(layoutManagerPopular);
        rcv_featureDoctor.setLayoutManager(layoutFeaturePopular);


        mSpecialist = new ArrayList<>();
        mPopularList = new ArrayList<>();
        mFeatureDoctor= new ArrayList<>();

        specialistApiService.getAllSpecialists().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray specialists = res.getAsJsonArray("result");
                    int size = specialists.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject specialist = specialists.get(i).getAsJsonObject();
                        SpecialistDomain obj = new SpecialistDomain(specialist.get("name").getAsString(), specialist.get("imageUrl").getAsString());
                        mSpecialist.add(obj);
                    }
                    SpecialistAdapter specialistAdapter = new SpecialistAdapter(mSpecialist);
                    rcv_specialist.setAdapter(specialistAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        doctorApiService.getTopDoctor(10).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray topDoctors = res.getAsJsonArray("result");
                    int size = topDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = topDoctors.get(i).getAsJsonObject();
                        Doctor element = new Doctor();
                        element.setId(doctor.get("id").getAsString());
                        element.setName(doctor.get("name").getAsString());
                        element.setSpecialist(doctor.get("specialist").getAsString());
                        element.setAvatarUrl(doctor.get("avatarUrl").getAsString());
                        element.setRating(doctor.get("rating").getAsFloat());
                        mPopularList.add(element);
                    }

                    PopularDoctorAdapter popularDoctorAdapter = new PopularDoctorAdapter(mPopularList);
                    popularDoctorAdapter.setOnDocterCardClickListener(HomeFragment.this);
                    rcv_popularDoctor.setAdapter(popularDoctorAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        doctorApiService.getSomeDoctor(10).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray someDoctors = res.getAsJsonArray("result");
                    int size = someDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = someDoctors.get(i).getAsJsonObject();
                        Doctor element = new Doctor();
                        element.setId(doctor.get("id").getAsString());
                        element.setAvatarUrl(doctor.get("avatarUrl").getAsString());
                        element.setName(doctor.get("name").getAsString());
                        System.out.println(doctor.get("id").getAsString());
                        element.setRating(doctor.get("rating").getAsFloat());
                        element.setPrice(doctor.get("price").getAsFloat());
                        mFeatureDoctor.add(element);
                    }
                    FeatureDoctorAdapter featureDoctorAdapter = new FeatureDoctorAdapter(mFeatureDoctor);
                    featureDoctorAdapter.setOnDocterCardClickListener(HomeFragment.this);
                    rcv_featureDoctor.setAdapter(featureDoctorAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onDoctorCardClick(Doctor doctor) {
        Intent intent = new Intent(HomeFragment.this.getContext(), DoctorDetailActivity.class);
        intent.putExtra("id", doctor.getId());
        startActivity(intent);
    }
}