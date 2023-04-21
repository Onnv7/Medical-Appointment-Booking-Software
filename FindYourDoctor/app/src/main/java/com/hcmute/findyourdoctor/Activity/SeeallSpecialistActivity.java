package com.hcmute.findyourdoctor.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.hcmute.findyourdoctor.Adapter.SeeallSpecialtyAdapter;
import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.hcmute.findyourdoctor.Fragment.HomeFragment;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;

public class SeeallSpecialistActivity extends AppCompatActivity {
    ArrayList<SpecialistDomain> specialist;
    RecyclerView gw_all_specilaty;
    ImageView back_specialty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeall_specialist);

        gw_all_specilaty = findViewById(R.id.gw_all_specilaty);
        back_specialty = findViewById(R.id.back_specialty);
        specialist = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            specialist = bundle.getParcelableArrayList("SPECIALIST_LIST");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SeeallSpecialistActivity.this, LinearLayoutManager.HORIZONTAL, false);

        gw_all_specilaty.setLayoutManager(layoutManager);


        SeeallSpecialtyAdapter seeallSpecialtyAdapter = new SeeallSpecialtyAdapter(specialist);
        gw_all_specilaty.setAdapter(seeallSpecialtyAdapter);


        back_specialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeallSpecialistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}