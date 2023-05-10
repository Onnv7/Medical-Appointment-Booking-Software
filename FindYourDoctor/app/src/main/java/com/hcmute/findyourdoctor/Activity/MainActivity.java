package com.hcmute.findyourdoctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
//import com.hcmute.findyourdoctor.Adapter.SpecialistAdapter;
//import com.hcmute.findyourdoctor.Model.SpecialistDomain;
import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Api.BookingApiService;
import com.hcmute.findyourdoctor.Api.RetrofitClient;
import com.hcmute.findyourdoctor.Fragment.AppointmentFragment;
import com.hcmute.findyourdoctor.Fragment.HistoryFragment;
import com.hcmute.findyourdoctor.Fragment.HomeFragment;
import com.hcmute.findyourdoctor.Fragment.ProfileFragment;
import com.hcmute.findyourdoctor.Adapter.ViewPagerAdapter;
import com.hcmute.findyourdoctor.R;
import com.hcmute.findyourdoctor.Utils.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    String id_user;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);

        sharedPreferences = getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("id", null);
        appoinmentTF(id_user);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void checkFragment(Boolean check){
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new AppointmentFragment());
        fragmentArrayList.add(new HistoryFragment());
        fragmentArrayList.add(new ProfileFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, fragmentArrayList);
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
                        bottomNavigationView.setSelectedItemId(R.id.action_history);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
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
//                    case R.id.action_notifications:
//                        viewPager.setCurrentItem(2);
//                        break;
                    case R.id.action_history:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_profile:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
    }
    private void appoinmentTF(String id_user) {
        BookingApiService bookingApiService = RetrofitClient.getRetrofit().create(BookingApiService.class);
        bookingApiService.getBookingListId(id_user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonArray("result").size() == 0){
                    checkFragment(false);
                }else{
                    checkFragment(true);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}