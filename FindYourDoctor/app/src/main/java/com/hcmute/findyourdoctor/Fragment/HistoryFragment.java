package com.hcmute.findyourdoctor.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Activity.DoctorListActitvity;
import com.hcmute.findyourdoctor.Activity.HistoryDetailsActivity;
//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Adapter.AppointmentHistoryAdapter;
import com.hcmute.findyourdoctor.Adapter.DoctorListAdapter;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Model.Booking;
import com.hcmute.findyourdoctor.Listener.OnHistoryAppointmentClickListener;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment implements OnHistoryAppointmentClickListener {

    SharedPreferences sharedPreferences;
    RecyclerView rcvApointmentHistory;
    AppointmentHistoryAdapter appointmentHistoryAdapter;
    EditText edtSearch;
    List<Booking> mAppointmentHistory;
    List<Booking> mSearchBookings;

    public HistoryFragment() {
        // Required empty public constructor
    }
    BookingApiService bookingApiService;
    String uid;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Không làm gì
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String searchText = edtSearch.getText().toString().trim();
            if(searchText.trim().equals(""))
                renderHistory();
            else
                searchHistory(searchText);
        }

    };

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();
        init(getView());

        renderHistory();
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int drawableStart = 0;
                int marginLeft = ((ViewGroup.MarginLayoutParams) edtSearch.getLayoutParams()).leftMargin;

                int drawablePadding = edtSearch.getCompoundDrawablePadding();
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(event.getRawX() <= (edtSearch.getCompoundDrawables()[drawableStart].getBounds().width() + edtSearch.getPaddingLeft() + marginLeft + drawablePadding)) {
                        searchHistory(edtSearch.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });
        edtSearch.addTextChangedListener(textWatcher);
    }


    @Override
    public void onClickHistoryAppointment(String bookingId) {
        Intent intent = new Intent(getContext(), HistoryDetailsActivity.class);
        intent.putExtra("BookingId", bookingId);
        startActivity(intent);
    }
    private void renderHistory() {
        bookingApiService.getHistoryList(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(res.get("success").getAsBoolean()) {
                    mAppointmentHistory = new ArrayList<>();
                    JsonArray results = res.getAsJsonArray("result");
                    int size = results.size();
                    Gson gson = new Gson();
                    for(int i=0; i<size; i++) {
                        JsonObject obj = results.get(i).getAsJsonObject();
                        Booking booking = gson.fromJson(obj, Booking.class);
                        mAppointmentHistory.add(booking);
                    }

                    appointmentHistoryAdapter = new AppointmentHistoryAdapter(mAppointmentHistory, getContext());
                    rcvApointmentHistory.setAdapter(appointmentHistoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    private void searchHistory(String searchContent) {
        mSearchBookings =  new ArrayList<>();
        if(mAppointmentHistory == null || mAppointmentHistory.size() == 0) {
            return;
        }
        for (Booking booking : mAppointmentHistory) {
            if ((booking.getDoctor().getName().toLowerCase() + " " + booking.getStatus() + " " + booking.getTime()).contains(searchContent.toLowerCase())) {
                mSearchBookings.add(booking);
            }
        }
        appointmentHistoryAdapter = new AppointmentHistoryAdapter(mSearchBookings, getContext());
        rcvApointmentHistory.setAdapter(appointmentHistoryAdapter);
    }
    private void init(View view) {
        sharedPreferences = requireActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        edtSearch = view.findViewById(R.id.edt_search_booking_history);

        rcvApointmentHistory =  getView().findViewById(R.id.rcv_appointment_history);

        RecyclerView.LayoutManager layoutFeatureAppointment = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);

        rcvApointmentHistory.setLayoutManager(layoutFeatureAppointment);
    }
}