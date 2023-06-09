package com.example.doctorapp.Fragment;
import com.example.doctorapp.Api.ScheduleApiService;
import com.example.doctorapp.Activity.EditTimeScheduleActivity;
import com.example.doctorapp.Utils.Constant;
import com.example.doctorapp.Utils.DateTimeFormat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonElement;

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

import com.example.doctorapp.Adapter.TimeSlotAdapter;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Domain.TimeSlotDomain;
import com.example.doctorapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    List<TimeSlotDomain> afternoonSlotList, eveningSlotList, morningSlotList;
    TimeSlotAdapter afternoonAdapter, eveningAdapter, morningAdapter;
    private Boolean isOpenCalendar = true;
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
        setOnDateChange();
        setOnEditButtonClick();
        setOnCloseOpenCalendar();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        renderGridView();
    }

    private void setOnEditButtonClick() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditTimeScheduleActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
    private void setOnCloseOpenCalendar() {
        tvDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpenCalendar) {
                    cldDate.setVisibility(View.GONE);
                    isOpenCalendar = false;
                }
                else {
                    cldDate.setVisibility(View.VISIBLE);
                    isOpenCalendar = true;
                }
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

//                    view.dateTextAppearance(getResources().getColor(R.color.white));
//                    view.setUnfocusedMonthDateColor(getResources().getColor(R.color.white));
//                } else {
//                    // Set màu cho item date không được chọn
//                    view.setDateColor(getResources().getColor(R.color.black));
//                    view.setUnfocusedMonthDateColor(getResources().getColor(R.color.gray));
//                }
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

                    List<TimeSlotDomain> morningList = new ArrayList<>();
                    List<TimeSlotDomain> afternoonList = new ArrayList<>();
                    List<TimeSlotDomain> eveningList = new ArrayList<>();


                    for (JsonElement e: morning) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        if(item.getSelected())
                            morningList.add(item);
                    }
                    setDataForSlotGridView(morningList, gvMorning, "morning");


                    for (JsonElement e: afternoon) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        if(item.getSelected())
                            afternoonList.add(item);
                    }
                    setDataForSlotGridView(afternoonList, gvAfternoon, "afternoon");


                    for (JsonElement e: evening) {
                        TimeSlotDomain item = gson.fromJson(e, TimeSlotDomain.class);
                        if(item.getSelected())
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
    private void setDataForSlotGridView(List<TimeSlotDomain> timeArray, GridView gridView, String type) {

        TimeSlotAdapter adapter = new TimeSlotAdapter(getContext(), R.layout.row_time_detail, timeArray, type);
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