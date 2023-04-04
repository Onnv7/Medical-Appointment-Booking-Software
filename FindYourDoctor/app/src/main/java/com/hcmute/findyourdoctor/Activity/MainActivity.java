package com.hcmute.findyourdoctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
//import com.hcmute.findyourdoctor.Domain.SpecialistDomain;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.ApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Fragment.AppointmentEmptyFragment;
import com.hcmute.findyourdoctor.Fragment.AppointmentFragment;
import com.hcmute.findyourdoctor.Fragment.HistoryFragment;
import com.hcmute.findyourdoctor.Fragment.HomeFragment;
import com.hcmute.findyourdoctor.Fragment.NotificationsFragment;
import com.hcmute.findyourdoctor.Fragment.ProfileFragment;
import com.hcmute.findyourdoctor.Adapter.ViewPagerAdapter;
import com.hcmute.findyourdoctor.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);
        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        Log.d("nva1", "onResponse: 00000000000000");
        apiService.test("642698a194ab5aa296209d00").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("nva1", "onResponse: 11111111111111");
                Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()) {
                    JsonObject res = response.body();
                    System.out.println(res.get("message").toString());
//                    Log.d("nva1", "onResponse: " + res.get("patientId").toString());
                }
                else {
                    Toast.makeText(MainActivity.this, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        setupChangeFragment();
    }


    private void setupChangeFragment() {
        fragmentArrayList.add(new HomeFragment());
//        fragmentArrayList.add(new AppointmentEmptyFragment());
        fragmentArrayList.add(new AppointmentFragment());
        fragmentArrayList.add(new NotificationsFragment());
        fragmentArrayList.add(new HistoryFragment());
        fragmentArrayList.add(new ProfileFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragmentArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        bottomNavigationView.setSelectedItemId(R.id.action_home);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        bottomNavigationView.setSelectedItemId(R.id.action_calendar);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        bottomNavigationView.setSelectedItemId(R.id.action_notifications);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        bottomNavigationView.setSelectedItemId(R.id.action_history);
                        break;
                    case 4:
                        viewPager.setCurrentItem(4);
                        bottomNavigationView.setSelectedItemId(R.id.action_profile);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_calendar:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_notifications:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_history:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.action_profile:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });
    }

}