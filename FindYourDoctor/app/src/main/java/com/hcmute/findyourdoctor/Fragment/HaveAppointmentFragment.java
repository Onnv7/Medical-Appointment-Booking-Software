package com.hcmute.findyourdoctor.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Model.Booking;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class HaveAppointmentFragment extends Fragment {
    RecyclerView rcvApointmentList;
    List<Booking> mSearchBookings;
    EditText edtSearch;
    AppointmentHistoryAdapter appointmentAdapter;
    private SharedPreferences sharedPreferences;
    String uid;
    List<Booking> mAppointment;
    JsonArray dataList;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String searchText = edtSearch.getText().toString().trim();
            if(searchText.trim().equals(""))
                renderRecyclerView();
            else
                searchAppointment(searchText);
        }

    };

    public HaveAppointmentFragment(JsonArray list) {
        this.dataList  = list;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_have_appointment, container, false);
        init(view);
        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false);

        rcvApointmentList.setLayoutManager(layoutFeatureAppointment);

        edtSearch.addTextChangedListener(textWatcher);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderRecyclerView();
    }

    private void init(View view) {
        sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", null);
        rcvApointmentList = view.findViewById(R.id.rcv_appointment_list);
        edtSearch = view.findViewById(R.id.edt_search_appointment_list);

    }

    public void renderRecyclerView() {
        mAppointment = new ArrayList<>();
        JsonArray jsonArray = dataList;
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject appointment = jsonArray.get(i).getAsJsonObject();
            Booking element = gson.fromJson(appointment, Booking.class);
            mAppointment.add(element);
        }
        appointmentAdapter = new AppointmentHistoryAdapter(mAppointment, getContext());
        rcvApointmentList.setAdapter(appointmentAdapter);
    }
    private void searchAppointment(String searchContent) {
        mSearchBookings =  new ArrayList<>();
        for (Booking booking : mAppointment) {
            if ((booking.getDoctor().getName().toLowerCase() + " " + booking.getStatus() + " " + booking.getTime()).contains(searchContent.toLowerCase())) {
                mSearchBookings.add(booking);
            }
        }
        System.out.println(mSearchBookings.size() + " ---");
        appointmentAdapter = new AppointmentHistoryAdapter(mSearchBookings, getContext());
        rcvApointmentList.setAdapter(appointmentAdapter);
    }

}