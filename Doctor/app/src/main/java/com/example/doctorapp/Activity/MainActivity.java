package com.example.doctorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doctorapp.Adapter.ViewPagerAdapter;
import com.example.doctorapp.Fragment.HomeFragment;
import com.example.doctorapp.Fragment.ProfileFragment;
import com.example.doctorapp.Fragment.ScheduleFragment;
import com.example.doctorapp.Fragment.TimeFragment;
import com.example.doctorapp.Fragment.NewBookingFragment;
import com.example.doctorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

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
        checkFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void checkFragment(){
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
//        fragmentArrayList.add(new NotificationsFragment());
        fragmentArrayList.add(new TimeFragment());
        fragmentArrayList.add(new NewBookingFragment());
        fragmentArrayList.add(new ScheduleFragment());
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
                        bottomNavigationView.setSelectedItemId(R.id.action_new_booking);
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
                    case R.id.action_new_booking:
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