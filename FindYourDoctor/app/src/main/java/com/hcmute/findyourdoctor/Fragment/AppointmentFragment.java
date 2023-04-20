package com.hcmute.findyourdoctor.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.LoginActivity;
import com.hcmute.findyourdoctor.Adapter.AppointmentAdapter;
import com.hcmute.findyourdoctor.Adapter.FeatureDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.PopularDoctorAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.doctorListAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.PatientApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Domain.AppointmentDomain;
import com.hcmute.findyourdoctor.Model.review;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentFragment extends Fragment {


    RecyclerView listAppointment;
    List<AppointmentDomain> mAppointment;
    private SharedPreferences sharedPreferences;
    String id;


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

        sharedPreferences = requireActivity().getSharedPreferences(LoginActivity.SHARE, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", null);
        BookingApiService bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingApiService.getBookingListId(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                addAppointment(jsonObject);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



        return view;
    }


    public void addAppointment(JsonObject jsonObject) {
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
        JsonArray jsonArray = jsonObject.getAsJsonArray("result");

        for(int i = 0 ;i<jsonArray.size();i++){
            JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject().get("doctor").getAsJsonObject();
//            String idDoctor = jsonObject1.get("_id").toString();
            String name = jsonObject1.get("name").toString().replace("\"","");
            String status = jsonArray.get(i).getAsJsonObject().get("status").toString().replace("\"","");
            String avatarUrl = jsonObject1.get("avatarUrl").toString().replace("\"","");
            String time = jsonArray.get(i).getAsJsonObject().get("time").toString().replace("\"","");
            mAppointment.add(new AppointmentDomain(name,status,avatarUrl,time));
//            String status = jsonObject1.get("status").toString();
//            String image = jsonObject1.get("avatarUrl").toString();
//            String time = jsonObject1.get("time").toString();
//            mAppointment.add(new AppointmentDomain(name,status,image,time));
        }
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(mAppointment);
        listAppointment.setAdapter(appointmentAdapter);
    }
}