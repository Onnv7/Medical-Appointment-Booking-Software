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
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Domain.FeatureDoctorDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Model.doctorList;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView rcv_specialist;
    private RecyclerView rcv_popularDoctor;
    private RecyclerView rcv_featureDoctor;
    List<SpecialistDomain> mSpecialist;
    List<doctorList> mPopularList;
    List<FeatureDoctorDomain> mFeatureDoctor;

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
        Log.d("FRAG", "home");
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

        addSpecialist();
        addPopularDoctor();
        addFeatureDoctor();

        SpecialistAdapter specialistAdapter = new SpecialistAdapter(mSpecialist);
        rcv_specialist.setAdapter(specialistAdapter);

        PopularDoctorAdapter popularDoctorAdapter = new PopularDoctorAdapter(mPopularList);
        rcv_popularDoctor.setAdapter(popularDoctorAdapter);

        FeatureDoctorAdapter featureDoctorAdapter = new FeatureDoctorAdapter(mFeatureDoctor);
        rcv_featureDoctor.setAdapter(featureDoctorAdapter);


        return view;
    }

    public void addSpecialist() {
        SpecialistDomain sp1 = new SpecialistDomain("Patient1", "ok luôn");
        SpecialistDomain sp2 = new SpecialistDomain("Patient2", "ok");
        SpecialistDomain sp3 = new SpecialistDomain("Patient2", "ok luôn");
        SpecialistDomain sp4 = new SpecialistDomain("Patient2", "ok");
        mSpecialist.add(sp1);
        mSpecialist.add(sp2);
        mSpecialist.add(sp3);
        mSpecialist.add(sp4);

    }

    public void addPopularDoctor() {
        doctorList sp1 = new doctorList("Dr. Fillerup Grab", "Medicine Specialist");
        doctorList sp2 = new doctorList("Dr. Fillerup Grab", "Medicine Specialist");
        doctorList sp3 = new doctorList("Dr. Fillerup Grab", "Medicine Specialist");
        doctorList sp4 = new doctorList("Dr. Fillerup Grab", "Medicine Specialist");
        mPopularList.add(sp1);
        mPopularList.add(sp2);
        mPopularList.add(sp3);
        mPopularList.add(sp4);
    }

    public void addFeatureDoctor() {
        FeatureDoctorDomain sp1 = new FeatureDoctorDomain("Dr. Fillerup Grab", "Medicine Specialist", 1, 20);
        FeatureDoctorDomain sp2 = new FeatureDoctorDomain("Dr. Fillerup Grab", "Medicine Specialist", 1, 20);
        FeatureDoctorDomain sp3 = new FeatureDoctorDomain("Dr. Fillerup Grab", "Medicine Specialist", 1, 20);
        FeatureDoctorDomain sp4 = new FeatureDoctorDomain("Dr. Fillerup Grab", "Medicine Specialist", 1, 20);
        mFeatureDoctor.add(sp1);
        mFeatureDoctor.add(sp2);
        mFeatureDoctor.add(sp3);
        mFeatureDoctor.add(sp4);
    }

}