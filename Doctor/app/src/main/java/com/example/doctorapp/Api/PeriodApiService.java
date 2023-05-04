package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PeriodApiService {
    String BASE_URL = "period";
    @GET(BASE_URL + "/list")
    Call<JsonObject> getPeriodList();
}
