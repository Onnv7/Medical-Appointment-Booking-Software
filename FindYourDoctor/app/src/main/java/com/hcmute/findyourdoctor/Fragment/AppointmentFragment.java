package com.hcmute.findyourdoctor.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Booking;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFragment extends Fragment implements OnHistoryAppointmentClickListener {

    RecyclerView rcvApointmentList;
    List<Booking> mAppointment;
    List<Booking> mSearchBookings;
    EditText edtSearch;
    AppointmentHistoryAdapter appointmentAdapter;
    private SharedPreferences sharedPreferences;
    String uid;
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
                renderAppointmentList();
            else
                searchAppointment(searchText);
        }

    };

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
        init(getView());

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(getView().getContext(),
                LinearLayoutManager.VERTICAL, false);

        rcvApointmentList.setLayoutManager(layoutFeatureAppointment);

        renderAppointmentList();
        edtSearch.addTextChangedListener(textWatcher);
    }
    private void renderAppointmentList() {
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
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject appointment = jsonArray.get(i).getAsJsonObject();
            Booking element = gson.fromJson(appointment, Booking.class);
            mAppointment.add(element);
        }
        appointmentAdapter = new AppointmentHistoryAdapter(mAppointment, AppointmentFragment.this);
        rcvApointmentList.setAdapter(appointmentAdapter);
    }

    @Override
    public void onClickHistoryAppointment(String bookingId) {
    }
    private void searchAppointment(String searchContent) {
        mSearchBookings =  new ArrayList<>();
        for (Booking booking : mAppointment) {
            if ((booking.getDoctor().getName().toLowerCase() + " " + booking.getStatus() + " " + booking.getTime()).contains(searchContent.toLowerCase())) {
                mSearchBookings.add(booking);
            }
        }
        System.out.println(mSearchBookings.size() + " ---");
        appointmentAdapter = new AppointmentHistoryAdapter(mSearchBookings, AppointmentFragment.this);
        rcvApointmentList.setAdapter(appointmentAdapter);
    }
    private void init(View view) {
        sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", null);
        rcvApointmentList = getView().findViewById(R.id.rcv_appointment_list);
        edtSearch = view.findViewById(R.id.edt_search_appointment_list);
    }
}