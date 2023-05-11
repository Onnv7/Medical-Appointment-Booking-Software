package com.example.doctorapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Adapter.ArticleAdapter;
import com.example.doctorapp.Adapter.SliderItemAdapter;
import com.example.doctorapp.Api.ArticleApiService;
import com.example.doctorapp.Api.DoctorApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Activity.DoctorDetailActivity;
import com.example.doctorapp.Domain.SliderItemDomain;
import com.example.doctorapp.Model.Article;
import com.example.doctorapp.Model.Doctor;
import com.example.doctorapp.R;
import com.example.doctorapp.Utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView rcvArticles;
    private List<Article> articlesList;
    private ArticleAdapter articleAdapter;
    private ArticleApiService articleApiService;
    private DoctorApiService doctorApiService;
    private SharedPreferences sharedPreferences;
    private String uid;
    private TextView tvName, tvGreeting;
    private ImageView ivAvatar;
    private ViewPager2 vp2Slider;
    private CircleIndicator3 ci3Slider;
    private SliderItemAdapter sliderItemAdapter;
    private List<SliderItemDomain> sliderItemDomainList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(vp2Slider.getCurrentItem() == sliderItemDomainList.size() - 1) {
                vp2Slider.setCurrentItem(0);
            }
            else {
                vp2Slider.setCurrentItem(vp2Slider.getCurrentItem() + 1);
            }
        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        renderUI();
        setOnAvatarClick();

        return view;
    }
    private void setOnAvatarClick() {
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoctorDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    private void renderUI() {
        articleApiService.getAllArticles().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    articlesList = new ArrayList<>();
                    JsonArray result = res.getAsJsonArray("result");
                    Gson gson = new Gson();
                    for (JsonElement element : result) {
                        Article article = gson.fromJson(element, Article.class);
                        articlesList.add(article);
                    }
                }
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                rcvArticles.setLayoutManager(layoutManager);
                articleAdapter = new ArticleAdapter(articlesList, getContext() );
                rcvArticles.setAdapter(articleAdapter);
//                System.out.println(articlesList.size());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        doctorApiService.getDoctorProfile(uid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                if(response.isSuccessful() && res.get("success").getAsBoolean()) {
                    JsonObject result = res.getAsJsonObject("result");
                    Gson gson = new Gson();
                    Doctor doctor = gson.fromJson(result, Doctor.class);
                    tvName.setText(doctor.getName());
                    Glide.with(getContext())
                            .load(doctor.getAvatarUrl())
                            .into(ivAvatar);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHourOfDay();
        String greeting = "";
        if (hour >= 5 && hour < 12) {
            greeting = "Good morning";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }
        tvGreeting.setText(greeting);

        sliderItemDomainList = createSliderItemList();
        sliderItemAdapter = new SliderItemAdapter(sliderItemDomainList, getContext());
        vp2Slider.setAdapter(sliderItemAdapter);
        ci3Slider.setViewPager(vp2Slider);

        vp2Slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);

                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private void init(View view){
        sharedPreferences = HomeFragment.this.getActivity().getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("id", "");
        doctorApiService = RetrofitClient.getRetrofit().create(DoctorApiService.class);
        articleApiService = RetrofitClient.getRetrofit().create(ArticleApiService.class);
        rcvArticles = view.findViewById(R.id.rcv_article_home_fragment);
        tvName = view.findViewById(R.id.tv_name_home_fragment);
        tvGreeting = view.findViewById(R.id.tv_greeting_home_fragment);
        ivAvatar = view.findViewById(R.id.iv_doctor_avatar_home_fragment);
        vp2Slider = view.findViewById(R.id.vp2_slider_home_fragment);
        ci3Slider = view.findViewById(R.id.ci3_slider_home_fragment);
    }
    private List<SliderItemDomain> createSliderItemList() {
        List<SliderItemDomain> list = new ArrayList<SliderItemDomain>();
        list.add(new SliderItemDomain(R.drawable.slider01));
        list.add(new SliderItemDomain(R.drawable.slider04));
        list.add(new SliderItemDomain(R.drawable.slider05));
        return list;
    }
}