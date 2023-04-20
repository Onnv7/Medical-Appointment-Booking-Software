package com.hcmute.findyourdoctor.Fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.HistoryDetailsActivity;
import com.hcmute.findyourdoctor.Activity.MainActivity;
//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment implements OnHistoryAppointmentClickListener {

    SharedPreferences sharedPreferences;
    RecyclerView rcv_appointment_history;
    List<AppointmentDomain> mAppointmentHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }
    BookingApiService bookingApiService;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getView().getContext().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);

        rcv_appointment_history =  getView().findViewById(R.id.rcv_appointment_history);

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);

        rcv_appointment_history.setLayoutManager(layoutFeatureAppointment);

        bookingApiService.getHistoryList(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    mAppointmentHistory = new ArrayList<>();
                    JsonArray results = res.getAsJsonArray("result");
                    int size = results.size();
                    for(int i=0; i<size; i++) {
                        JsonObject obj = results.get(i).getAsJsonObject();
                        JsonObject doctor = obj.getAsJsonObject("doctor");
                        String id = obj.get("_id").getAsString();
                        String name = doctor.get("name").getAsString();
                        String image = doctor.get("avatarUrl").getAsString();
                        String status = obj.get("status").getAsString();
                        String time = obj.get("time").getAsString();
                        AppointmentDomain element = new AppointmentDomain(id, name, status, image, time);
                        mAppointmentHistory.add(element);
                    }

                    AppointmentHistoryAdapter appointmentHistoryAdapter = new AppointmentHistoryAdapter(mAppointmentHistory, HistoryFragment.this);
                    rcv_appointment_history.setAdapter(appointmentHistoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClickHistoryAppointment(String bookingId) {
        Intent intent = new Intent(getContext(), HistoryDetailsActivity.class);
        intent.putExtra("BookingId", bookingId);
        startActivity(intent);
    }
}