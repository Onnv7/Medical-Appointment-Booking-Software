package com.hcmute.findyourdoctor.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.findyourdoctor.Adapter.AppointmentAdapter;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Model.review;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;


public class AppointmentFragment extends Fragment {

    RecyclerView listAppointment;
    List<AppointmentDomain> mAppointment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        listAppointment =  view.findViewById(R.id.listAppointment);

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        listAppointment.setLayoutManager(layoutFeatureAppointment);

        mAppointment = new ArrayList<>();


//        addAppointment();


        return view;
    }


    public void addAppointment() {
//        AppointmentDomain apm1 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "Status: waiting", "123", "26 January 2023, 7:00 - 7:30 AM");
//        AppointmentDomain apm2 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "Status: waiting", "123", "26 January 2023, 7:00 - 7:30 AM");
//        AppointmentDomain apm3 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "Status: waiting", "123", "26 January 2023, 7:00 - 7:30 AM");
//        AppointmentDomain apm4 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "Status: waiting", "123", "26 January 2023, 7:00 - 7:30 AM");
//        AppointmentDomain apm5 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "Status: waiting", "123", "26 January 2023, 7:00 - 7:30 AM");
//        mAppointment.add(apm1);
//        mAppointment.add(apm2);
//        mAppointment.add(apm3);
//        mAppointment.add(apm4);
//        mAppointment.add(apm5);
//
//        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(mAppointment);
//        listAppointment.setAdapter(appointmentAdapter);
    }
}