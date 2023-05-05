package com.example.doctorapp.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.doctorapp.Adapter.ListBookingAdapter;
import com.example.doctorapp.Adapter.DateAdapter;
import com.example.doctorapp.Api.BookingApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Domain.DateDomain;
import com.example.doctorapp.Listener.OnDateSelectedListener;
import com.example.doctorapp.Model.Booking;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment implements OnDateSelectedListener {
    SharedPreferences sharedPreferences;
    TextView tvSelectedTime;
    RecyclerView rcvDate, rcvBooking;
    List<DateDomain> mListDay = new ArrayList<>();
    List<Booking> mListInforPatient = new ArrayList<>();
    ListBookingAdapter listBookingAdapter;
    BookingApiService bookingApiService;
    String uid;
    public ScheduleFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initView(view);

        addDate();

        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rcvDate.setLayoutManager(linearLayout);


        RecyclerView.LayoutManager linearLayout2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rcvBooking.setLayoutManager(linearLayout2);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(getView());
    }

    private void initView(View view) {
        bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        rcvDate = (RecyclerView) view.findViewById(R.id.rcv_date_schedule);
        rcvBooking = (RecyclerView) view.findViewById(R.id.rcv_booking_schedule);
//        tvSelectedTime = (TextView) view.findViewById(R.id.tv_selected_time_schedule);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = dateFormat.format(calendar.getTime());
//        tvSelectedTime.setText(currentTime);
    }

    public void addDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String[] dates = new String[7];
        dates[0] = sdf.format(calendar.getTime());

        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            dates[i] = sdf.format(calendar.getTime());
        }

        for (int i = 0; i < 7; i++) {
            String[] parts = dates[i].split(",");
            String dayOfWeek = parts[0];

            switch (dayOfWeek.toLowerCase()) {
                case "monday":
                    parts[0] = "Mo";
                    break;
                case "tuesday":
                    parts[0] = "Tu";
                    break;
                case "wednesday":
                    parts[0] = "We";
                    break;
                case "thursday":
                    parts[0] = "Th";
                    break;
                case "friday":
                    parts[0] = "Fr";
                    break;
                case "saturday":
                    parts[0] = "Sa";
                    break;
                case "sunday":
                    parts[0] = "Su";
                    break;
                default:
                    parts[0] = dayOfWeek.substring(0, 1); // Lấy ký tự đầu tiên của thứ
                    break;
            }

            String date = parts[1].trim();
            mListDay.add(new DateDomain(parts[0], date));
        }

        DateAdapter manageAdapter1 = new DateAdapter(mListDay, getContext(), ScheduleFragment.this);

        rcvDate.setAdapter(manageAdapter1);

        manageAdapter1.notifyDataSetChanged();
    }

    @Override
    public void onDateSelected(String date) {
        mListInforPatient = new ArrayList<>();
        sharedPreferences = ScheduleFragment.this.getActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        bookingApiService.listAcceptedBooking(uid, date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();
                    JsonArray result = res.get("result").getAsJsonArray();
                    for (JsonElement e: result) {
                        Booking booking = gson.fromJson(e, Booking.class);
                        mListInforPatient.add(booking);
                    }
                    listBookingAdapter = new ListBookingAdapter(mListInforPatient, getContext());

                    rcvBooking.setAdapter(listBookingAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}