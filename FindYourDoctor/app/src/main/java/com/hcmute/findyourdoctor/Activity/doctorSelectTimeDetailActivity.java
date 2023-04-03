package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridView;

import com.hcmute.findyourdoctor.Adapter.selecTimeDetailAdapter;
import com.hcmute.findyourdoctor.Adapter.selectTimeAdapter;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Model.selectTime;
import com.hcmute.findyourdoctor.Model.selectTimeDetail;

import java.util.ArrayList;
import java.util.List;

public class doctorSelectTimeDetailActivity extends AppCompatActivity {

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