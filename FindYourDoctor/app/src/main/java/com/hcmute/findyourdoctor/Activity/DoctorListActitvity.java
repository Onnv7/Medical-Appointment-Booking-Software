package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.model.doctorList;

import java.util.ArrayList;
import java.util.List;

public class DoctorListActitvity extends AppCompatActivity {

    RecyclerView listDoctors;
    List<doctorList> mdoctorList;

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
        doctorList doctorList1 = new doctorList("Patient1", "ok luôn");
        doctorList doctorList2 = new doctorList("Patient2", "ok");
        doctorList doctorList3 = new doctorList("Patient2", "ok luôn");
        doctorList doctorList4 = new doctorList("Patient2", "ok");
        mdoctorList.add(doctorList1);
        mdoctorList.add(doctorList2);
        mdoctorList.add(doctorList3);
        mdoctorList.add(doctorList4);

        doctorListAdapter doctorListAdapter = new doctorListAdapter(mdoctorList);
        listDoctors.setAdapter(doctorListAdapter);
    }
}