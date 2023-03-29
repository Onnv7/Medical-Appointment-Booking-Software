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
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView.Adapter specialistAdapter;
    private RecyclerView specialistRecyclerView;

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
        return view;
    }
    private void recyclerViewSpecialist() {
        View view = getView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        specialistRecyclerView = view.findViewById(R.id.rcv_specialist);
        specialistRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<SpecialistDomain> specialistDomains = new ArrayList<>();
//        specialistDomains.add(new SpecialistDomain("Pizza", "specialist_pic01"));
        specialistAdapter = new SpecialistAdapter(specialistDomains);
        specialistRecyclerView.setAdapter(specialistAdapter);
    }

}