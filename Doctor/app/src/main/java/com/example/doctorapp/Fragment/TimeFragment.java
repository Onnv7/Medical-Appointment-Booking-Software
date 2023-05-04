package com.example.doctorapp.Fragment;
import com.example.doctorapp.Api.ScheduleApiService;
import com.example.doctorapp.EditTimeScheduleActivity;
import com.example.doctorapp.Utils.Constant;
import com.example.doctorapp.Utils.DateTimeFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.doctorapp.Adapter.ScheduleAdapter;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Domain.ScheduleDomain;
import com.example.doctorapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimeFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private FloatingActionButton fabEdit;
    private CalendarView cldDate;
    private TextView tvDateSelected;
    private ScheduleApiService scheduleApiService;
    private GridView gvMorning, gvAfternoon, gvEvening;
    List<ScheduleDomain> afternoonSlotList, eveningSlotList, morningSlotList;
    ScheduleAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    private String uid;
    private String date;
    public TimeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        init(view);
        renderGridView();
        setOnDateChange();
        setOnEditButtonClick();
        return view;
    }
    private void setOnEditButtonClick() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTimeScheduleActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setOnDateChange() {
        cldDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Tạo đối tượng Calendar và thiết lập ngày tháng được chọn
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = dateFormat.format(selectedDate.getTime());

                String dateText = null;
                try {
                    dateText = DateTimeFormat.formatDate(date, "E, d MMM");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                tvDateSelected.setText(dateText);
                renderGridView();

            }
        });
    }
    private void renderGridView() {
        scheduleApiService.getScheduleForDoctor(uid, date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    Gson gson = new Gson();

                    JsonObject result = res.getAsJsonObject("result");
                    JsonArray morning = result.getAsJsonArray("morning");
                    JsonArray afternoon = result.getAsJsonArray("afternoon");
                    JsonArray evening = result.getAsJsonArray("evening");

                    List<ScheduleDomain> morningList = new ArrayList<>();
                    List<ScheduleDomain> afternoonList = new ArrayList<>();
                    List<ScheduleDomain> eveningList = new ArrayList<>();


                    for (JsonElement e: morning) {
                        ScheduleDomain item = gson.fromJson(e, ScheduleDomain.class);
                        morningList.add(item);
                    }
                    setDataForSlotGridView(morningList, gvMorning, "morning");


                    for (JsonElement e: afternoon) {
                        ScheduleDomain item = gson.fromJson(e, ScheduleDomain.class);
                        afternoonList.add(item);
                    }
                    setDataForSlotGridView(afternoonList, gvAfternoon, "afternoon");


                    for (JsonElement e: evening) {
                        ScheduleDomain item = gson.fromJson(e, ScheduleDomain.class);
                        eveningList.add(item);
                    }
                    setDataForSlotGridView(eveningList, gvEvening, "evening");

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void setDataForSlotGridView(List<ScheduleDomain> timeArray, GridView gridView, String type) {

        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), R.layout.row_time_detail, timeArray, type);
        adapter.notifyDataSetChanged();

        if (type.equals("morning")) {
            morningSlotList = timeArray;
            morningAdapter = adapter;
        } else if (type.equals("afternoon")) {
            afternoonSlotList = timeArray;
            afternoonAdapter = adapter;
        } else if (type.equals("evening")) {
            eveningSlotList = timeArray;
            eveningAdapter = adapter;
        }
        gridView.setAdapter(adapter);
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
    private void init(View view) {
        scheduleApiService = RetrofitClient.getRetrofit().create(ScheduleApiService.class);
        sharedPreferences = getActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        cldDate = view.findViewById(R.id.cld_setting_time);
        cldDate.setMinDate(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(cldDate.getDate());
        System.out.println(date);
        gvMorning = view.findViewById(R.id.gv_morning_setting_time);
        gvAfternoon = view.findViewById(R.id.gv_afternoon_setting_time);
        gvEvening = view.findViewById(R.id.gv_evening_setting_time);
        tvDateSelected = view.findViewById(R.id.tv_selected_date_setting_time);
        fabEdit = view.findViewById(R.id.fab_edit_setting_time);
    }
}