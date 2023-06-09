package com.hcmute.findyourdoctor.Fragment;

import static com.hcmute.findyourdoctor.Utils.Constant.SHARE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.DoctorDetailActivity;
import com.hcmute.findyourdoctor.Activity.DoctorListActitvity;
import com.hcmute.findyourdoctor.Activity.ListSpecialistActivity;
import com.hcmute.findyourdoctor.Activity.MainActivity;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Api.DoctorApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Api.SpecialistApiService;
import com.hcmute.findyourdoctor.Model.Specialist;
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
    private TextView tv_Seeall_Specialty, tvHiName, btnSeeAll01, btnSeeAll02;
    private EditText edtSearch;
    List<Specialist> mSpecialist;
    List<Doctor> mPopularList;
    List<Doctor> mFeatureDoctor;

    SpecialistApiService specialistApiService;
    DoctorApiService doctorApiService;
    SharedPreferences sharedPreferences;

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
        initView(view);

        renderPopularDoctor();
        renderSpecialists();
        renderSomeDoctor();
        setOnClickSeeAll();
        setOnEnterEditTextSearch();
        return view;
    }

    @Override
    public void onDoctorCardClick(Doctor doctor) {
        Intent intent = new Intent(HomeFragment.this.getContext(), DoctorDetailActivity.class);
        intent.putExtra("id", doctor.getId());
        startActivity(intent);
    }
    private void setOnEnterEditTextSearch() {
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    Intent intent = new Intent(HomeFragment.this.getContext(), DoctorListActitvity.class);
                    intent.putExtra("search", edtSearch.getText().toString());
                    startActivity(intent);
                    // TODO: continue
                }
                return false;
            }
        });
    }
    private void setOnClickSeeAll() {
        tv_Seeall_Specialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListSpecialistActivity.class);
                startActivity(intent);
            }
        });

        btnSeeAll01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoctorListActitvity.class);
                startActivity(intent);
            }
        });
        btnSeeAll02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoctorListActitvity.class);
                startActivity(intent);
            }
        });

    }
    private void renderPopularDoctor() {
        doctorApiService.getTopDoctor(10).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonArray topDoctors = res.getAsJsonArray("result");
                    int size = topDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = topDoctors.get(i).getAsJsonObject();
                        Doctor element = gson.fromJson(doctor, Doctor.class);
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

    }
    private void renderSomeDoctor() {
        doctorApiService.getSomeDoctor(10).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonArray someDoctors = res.getAsJsonArray("result");
                    int size = someDoctors.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject doctor = someDoctors.get(i).getAsJsonObject();

                        Doctor element = gson.fromJson(doctor, Doctor.class);
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
    }
    private void renderSpecialists(){
        specialistApiService.getSomeSpecialists(5).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    JsonArray specialists = res.getAsJsonArray("result");
                    Gson gson = new Gson();
                    int size = specialists.size();
                    for (int i = 0; i < size; i++) {
                        JsonObject specialist = specialists.get(i).getAsJsonObject();
                        Specialist specialistDomain = gson.fromJson(specialist, Specialist.class);
//                        SpecialistDomain obj = new SpecialistDomain(specialist.get("name").getAsString(), specialist.get("imageUrl").getAsString());
                        mSpecialist.add(specialistDomain);
                    }
                    SpecialistAdapter specialistAdapter = new SpecialistAdapter(mSpecialist);
                    rcv_specialist.setAdapter(specialistAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void initView(View view) {
        sharedPreferences = HomeFragment.this.getContext().getSharedPreferences(SHARE, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);

        rcv_specialist =  view.findViewById(R.id.rcv_specialist);
        rcv_popularDoctor =  view.findViewById(R.id.rcv_popularDoctor);
        rcv_featureDoctor =  view.findViewById(R.id.rcv_featureDoctor);
        tv_Seeall_Specialty = view.findViewById(R.id.tv_Seeall_Specialty);
        edtSearch = view.findViewById(R.id.edt_search_home_fragment);
        tvHiName = view.findViewById(R.id.tv_name_home_fragment);
        tvHiName.setText("Hi " + name + "!");

        btnSeeAll01 = view.findViewById(R.id.tv_see_all_01);
        btnSeeAll02 = view.findViewById(R.id.tv_see_all_02);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerPopular = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutFeaturePopular = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        rcv_specialist.setLayoutManager(layoutManager);
        rcv_popularDoctor.setLayoutManager(layoutManagerPopular);
        rcv_featureDoctor.setLayoutManager(layoutFeaturePopular);


        mSpecialist = new ArrayList<>();
        mPopularList = new ArrayList<>();
        mFeatureDoctor= new ArrayList<>();
    }
}