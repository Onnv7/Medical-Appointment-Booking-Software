package com.hcmute.findyourdoctor.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmute.findyourdoctor.Activity.MainActivity;
//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {


    RecyclerView rcv_appointment_history;
    List<AppointmentDomain> mAppointmentHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        rcv_appointment_history =  view.findViewById(R.id.rcv_appointment_history);

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_appointment_history.setLayoutManager(layoutFeatureAppointment);

        mAppointmentHistory = new ArrayList<>();

        addAppointment();

        return view;

    }

    public void addAppointment() {
        AppointmentDomain apmHistory1 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "", "123", "26 January 2023, 7:00 - 7:30 AM");
        AppointmentDomain apmHistory2 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "", "123", "26 January 2023, 7:00 - 7:30 AM");
        AppointmentDomain apmHistory3 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "", "123", "26 January 2023, 7:00 - 7:30 AM");
        AppointmentDomain apmHistory4 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "", "123", "26 January 2023, 7:00 - 7:30 AM");
        AppointmentDomain apmHistory5 = new AppointmentDomain("Dr.Mahmud Nik Hasan", "", "123", "26 January 2023, 7:00 - 7:30 AM");
        mAppointmentHistory.add(apmHistory1);
        mAppointmentHistory.add(apmHistory2);
        mAppointmentHistory.add(apmHistory3);
        mAppointmentHistory.add(apmHistory4);
        mAppointmentHistory.add(apmHistory5);

        AppointmentHistoryAdapter appointmentHistoryAdapter = new AppointmentHistoryAdapter(mAppointmentHistory);
        rcv_appointment_history.setAdapter(appointmentHistoryAdapter);
    }

}