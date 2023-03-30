package com.hcmute.findyourdoctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hcmute.findyourdoctor.Adapter.reviewAdapter;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.model.review;

import java.util.ArrayList;
import java.util.List;

public class DoctorDetail extends AppCompatActivity {
    RecyclerView listReview;
    List<review> mReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        listReview = (RecyclerView) findViewById(R.id.listReview);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);

        listReview.setLayoutManager(linearLayout);

        mReview = new ArrayList<>();

        addReview();
    }

    private void addReview(){
        review review1 = new review("Patient1", "ok luôn");
        review review2 = new review("Patient2", "ok");
        review review3 = new review("Patient2", "ok luôn");
        review review4 = new review("Patient2", "ok");
        mReview.add(review1);
        mReview.add(review2);
        mReview.add(review3);
        mReview.add(review4);

        reviewAdapter userAdapter = new reviewAdapter(mReview);
        listReview.setAdapter(userAdapter);

    }
}