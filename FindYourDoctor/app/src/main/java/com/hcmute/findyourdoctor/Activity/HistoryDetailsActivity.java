package com.hcmute.findyourdoctor.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.RatingBar;
import android.widget.Toast;

import com.hcmute.findyourdoctor.R;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        RatingBar ratingBar = findViewById(R.id.rtb_doctor_select_time);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // xử lý sự kiện khi người dùng click vào một ngôi sao
                Toast.makeText(getApplicationContext(), "Đánh giá của bạn là: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

}