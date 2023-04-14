package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Domain.PopularDoctorDomain;

import java.util.ArrayList;
import java.util.List;

public class DoctorListActitvity extends AppCompatActivity {

    RecyclerView listDoctors;
    List<PopularDoctorDomain> mdoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list_actitvity);

        listDoctors = (RecyclerView) findViewById(R.id.listDoctors);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        listDoctors.setLayoutManager(linearLayout);

        mdoctorList = new ArrayList<>();

        addReview();
    }

    public void addReview() {
//        PopularDoctorDomain popularDoctorDomain1 = new PopularDoctorDomain("Patient1", "ok luôn");
//        PopularDoctorDomain popularDoctorDomain2 = new PopularDoctorDomain("Patient2", "ok");
//        PopularDoctorDomain popularDoctorDomain3 = new PopularDoctorDomain("Patient2", "ok luôn");
//        PopularDoctorDomain popularDoctorDomain4 = new PopularDoctorDomain("Patient2", "ok");
//        mdoctorList.add(popularDoctorDomain1);
//        mdoctorList.add(popularDoctorDomain2);
//        mdoctorList.add(popularDoctorDomain3);
//        mdoctorList.add(popularDoctorDomain4);

        doctorListAdapter doctorListAdapter = new doctorListAdapter(mdoctorList);
        listDoctors.setAdapter(doctorListAdapter);
    }
}