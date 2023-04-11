package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;

import com.hcmute.findyourdoctor.Adapter.selecTimeDetailAdapter;
import com.hcmute.findyourdoctor.Adapter.selectTimeAdapter;
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Domain.BookingDomain;
import com.hcmute.findyourdoctor.Domain.DoctorDomain;
import com.hcmute.findyourdoctor.Domain.PatientDomain;
import com.hcmute.findyourdoctor.Domain.ScheduleDomain;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Model.BookingModel;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTime;
import com.hcmute.findyourdoctor.Model.selectTimeDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class doctorSelectTimeDetailActivity extends AppCompatActivity {

    Button btn_confirmBook;
    GridView gw_timSeven, gw_timFive;
    List<selectTimeDetail> handArrayListSeven, handArrayListFive;
    selecTimeDetailAdapter adapterSeven, adapterFive;
    RecyclerView rc_selectTime_detail;
    List<selectTime> mselectTimeListDetail;
    selectTimeAdapter selectTimeAdapterDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_select_time_detail);

        AnhxaSeven();
        AnhxaFive();

        mselectTimeListDetail = new ArrayList<>();

        rc_selectTime_detail = (RecyclerView) findViewById(R.id.rc_selectTime_detail);

        addReview();


        adapterSeven = new selecTimeDetailAdapter(doctorSelectTimeDetailActivity.this, R.layout.row_time_detail, handArrayListSeven);
        adapterFive = new selecTimeDetailAdapter(doctorSelectTimeDetailActivity.this, R.layout.row_time_detail, handArrayListFive);

        gw_timSeven.setAdapter(adapterSeven);
        gw_timFive.setAdapter(adapterFive);


        selectTimeAdapterDetail = new selectTimeAdapter(mselectTimeListDetail, doctorSelectTimeDetailActivity.this);

        rc_selectTime_detail.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        rc_selectTime_detail.setLayoutManager(layoutManager);

        rc_selectTime_detail.setAdapter(selectTimeAdapterDetail);

        selectTimeAdapterDetail.notifyDataSetChanged();

        CreateBook();
    }

    private void CreateBook() {
        btn_confirmBook = findViewById(R.id.btn_confirmBook);
        btn_confirmBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingDomain book = new BookingDomain("642e7c9fb011248ed77f766f","642e7c52b011248ed77f766d","gap","accepted","vui","hay",2,"642fcd0b2e7e02772c9e5a0d");
                ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
                Call<BookingModel> call = apiService.createBook(book);
                call.enqueue(new Callback<BookingModel>() {
                    @Override
                    public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                        String a =String.valueOf(response.body().getSuccess());
                        Log.e("vao",a);
                        if(response.body().getSuccess().equals(true)){
                            openThank(Gravity.CENTER);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingModel> call, Throwable t) {
                        Log.e("ok", t.getMessage());
                    }
                });
            }
        });
    }

    private void openThank(int center) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.appointment_successful);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = center;
        window.setAttributes(windowAttributes);

        dialog.show();
    }

    private void AnhxaSeven() {
        gw_timSeven = (GridView) findViewById(R.id.gw_timSeven);
        handArrayListSeven = new ArrayList<>();

        handArrayListSeven.add( new selectTimeDetail("1:00 PM"));
        handArrayListSeven.add( new selectTimeDetail("1:30 PM"));
        handArrayListSeven.add( new selectTimeDetail("2:00 PM"));
        handArrayListSeven.add( new selectTimeDetail("2:30 PM"));
        handArrayListSeven.add( new selectTimeDetail("3:00 PM"));
        handArrayListSeven.add( new selectTimeDetail("3:30 PM"));
        handArrayListSeven.add( new selectTimeDetail("4:00 PM"));

    }
    private void AnhxaFive() {
        gw_timFive = (GridView) findViewById(R.id.gw_timFive);
        handArrayListFive = new ArrayList<>();

        handArrayListFive.add( new selectTimeDetail("1:00 PM"));
        handArrayListFive.add( new selectTimeDetail("1:30 PM"));
        handArrayListFive.add( new selectTimeDetail("2:00 PM"));
        handArrayListFive.add( new selectTimeDetail("2:30 PM"));
        handArrayListFive.add( new selectTimeDetail("3:00 PM"));

    }

    private void addReview(){
        selectTime selectTime1 = new selectTime("Patient1", "ok luôn");
        selectTime selectTime2 = new selectTime("Patient2", "ok");
        selectTime selectTime3 = new selectTime("Patient2", "ok luôn");
        selectTime selectTime4 = new selectTime("Patient2", "ok");
        mselectTimeListDetail.add(selectTime1);
        mselectTimeListDetail.add(selectTime2);
        mselectTimeListDetail.add(selectTime3);
        mselectTimeListDetail.add(selectTime4);
    }

}