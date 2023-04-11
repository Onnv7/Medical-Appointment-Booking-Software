package com.hcmute.findyourdoctor.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.SpecialistApiService;
import com.hcmute.findyourdoctor.Domain.FeatureDoctorDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Domain.PopularDoctorDomain;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView rcv_specialist;
    private RecyclerView rcv_popularDoctor;
    private RecyclerView rcv_featureDoctor;
    List<SpecialistDomain> mSpecialist;
    List<PopularDoctorDomain> mPopularList;
    List<FeatureDoctorDomain> mFeatureDoctor;

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


//        Log.d("FRAG", "home");
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
                    Log.d("nva", "onResponse: " + topDoctors.toString());
                    int size = topDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = topDoctors.get(i).getAsJsonObject();
                        mPopularList.add(new PopularDoctorDomain(doctor.get("id").getAsString(), doctor.get("name").getAsString(), doctor.get("specialist").getAsString(),
                                doctor.get("rating").getAsFloat(), doctor.get("avatarUrl").getAsString()));
                    }

                    PopularDoctorAdapter popularDoctorAdapter = new PopularDoctorAdapter(mPopularList);
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
                    Log.d("nva", "onResponse: " + someDoctors.toString());
                    int size = someDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = someDoctors.get(i).getAsJsonObject();
                        mFeatureDoctor.add(new FeatureDoctorDomain(doctor.get("id").getAsString(), doctor.get("avatarUrl").getAsString(), doctor.get("name").getAsString(),
                                doctor.get("rating").getAsFloat(), doctor.get("price").getAsFloat()));
                    }
                    FeatureDoctorAdapter featureDoctorAdapter = new FeatureDoctorAdapter(mFeatureDoctor);
                    rcv_featureDoctor.setAdapter(featureDoctorAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        return view;
    }
}