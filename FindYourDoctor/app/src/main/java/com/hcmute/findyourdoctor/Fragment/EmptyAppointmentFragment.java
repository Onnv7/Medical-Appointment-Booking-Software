package com.hcmute.findyourdoctor.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hcmute.findyourdoctor.Activity.DoctorListActitvity;
import com.hcmute.findyourdoctor.R;


public class EmptyAppointmentFragment extends Fragment {
    Button btnCreateBooking;

    public EmptyAppointmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_empty_appointment, container, false);
        init(view);
        return view;
    }
    private void init(View view) {
        btnCreateBooking = view.findViewById(R.id.btn_make_appointment_empty_appointment_fragment);
        btnCreateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoctorListActitvity.class);
                startActivity(intent);
            }
        });
    }
}