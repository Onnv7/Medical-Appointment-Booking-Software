package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReviewApiService {
    String BASE_URL = "review";

    @GET(BASE_URL + "/list/{doctorId}")
    Call<JsonObject> getReviewByDoctor(@Path("doctorId") String doctorId);
}
