package com.example.doctorapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorapp.Adapter.NewBookingAdapter;
import com.example.doctorapp.Api.BookingApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBookingFragment extends Fragment {
    private RecyclerView rcvWaitingBookingList;
    private NewBookingAdapter newBookingAdapter;
    private List<Booking> bookingList;
    private SharedPreferences sharedPreferences;
    private String uid;
    private BookingApiService bookingApiService;

    public NewBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_booking, container, false);
        init(view);
//        renderRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderRecyclerView();
    }

    private void renderRecyclerView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        bookingApiService.getNewBookingList(uid, currentDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                bookingList = new ArrayList<>();
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    JsonArray result = res.getAsJsonArray("result");
                    Gson gson = new Gson();
                    for (JsonElement e: result) {
                        Booking booking = gson.fromJson(e, Booking.class);
                        bookingList.add(booking);
                    }
                    newBookingAdapter = new NewBookingAdapter(bookingList, getContext());
                    rcvWaitingBookingList.setAdapter(newBookingAdapter);
                    RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                    rcvWaitingBookingList.setLayoutManager(linearLayout);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void init(View view) {
        sharedPreferences = view.getContext().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        rcvWaitingBookingList = view.findViewById(R.id.rcv_waiting_booking_new_booking);
    }
}