package com.hcmute.findyourdoctor.Fragment;

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
import com.hcmute.findyourdoctor.Activity.DoctorDetailActivity;
import com.hcmute.findyourdoctor.Activity.HistoryDetailsActivity;
import com.hcmute.findyourdoctor.Activity.LoginActivity;
import com.hcmute.findyourdoctor.Adapter.AppointmentAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.PatientApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.Model.review;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment implements OnHistoryAppointmentClickListener {

    RecyclerView listAppointment;
    List<AppointmentDomain> mAppointment;
    private SharedPreferences sharedPreferences;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", null);
        listAppointment = getView().findViewById(R.id.listAppointment);

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(getView().getContext(),
                LinearLayoutManager.VERTICAL, false);

        listAppointment.setLayoutManager(layoutFeatureAppointment);

        mAppointment = new ArrayList<>();

        BookingApiService bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingApiService.getBookingListId(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                addAppointment(jsonObject);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void addAppointment(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("result");

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject appointment = jsonArray.get(i).getAsJsonObject();
            JsonObject doctor = appointment.get("doctor").getAsJsonObject();
            String bookingId = appointment.get("_id").getAsString();
            String doctorId = doctor.get("_id").getAsString();
            String name = doctor.get("name").getAsString();
            String status = appointment.get("status").getAsString();
            String avatarUrl = doctor.get("avatarUrl").getAsString();
            String time = appointment.get("time").getAsString();
            mAppointment.add(new AppointmentDomain(bookingId, doctorId, name, status, avatarUrl, time));
            // String status = jsonObject1.get("status").toString();
            // String image = jsonObject1.get("avatarUrl").toString();
            // String time = jsonObject1.get("time").toString();
            // mAppointment.add(new AppointmentDomain(name,status,image,time));
        }
        AppointmentHistoryAdapter appointmentAdapter = new AppointmentHistoryAdapter(mAppointment, AppointmentFragment.this);
        listAppointment.setAdapter(appointmentAdapter);
    }

    @Override
    public void onClickHistoryAppointment(String bookingId) {
    }
}