package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleApiService {
    String BASE_URL = "article";

    @GET(BASE_URL + "/get-all")
    Call<JsonObject> getAllArticles();
}
