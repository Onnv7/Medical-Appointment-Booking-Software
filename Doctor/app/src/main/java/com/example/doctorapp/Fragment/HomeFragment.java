package com.example.doctorapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.doctorapp.Adapter.ArticleAdapter;
import com.example.doctorapp.Api.ArticleApiService;
import com.example.doctorapp.Api.RetrofitClient;
import com.example.doctorapp.Model.Article;
import com.example.doctorapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView rcvArticles;
    private List<Article> articlesList;
    private ArticleAdapter articleAdapter;
    private ArticleApiService articleApiService;
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
        return view;
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
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                rcvArticles.setLayoutManager(layoutManager);
                articleAdapter = new ArticleAdapter(articlesList, getContext() );
                rcvArticles.setAdapter(articleAdapter);
                System.out.println(articlesList.size());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void init(View view){
        articleApiService = RetrofitClient.getRetrofit().create(ArticleApiService.class);
        rcvArticles = view.findViewById(R.id.rcv_article_home_fragment);
    }
}