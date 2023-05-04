package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleApiService {
    String BASE_URL = "schedule";

    @GET(BASE_URL + "/date/list/{doctorId}")
    Call<JsonObject> getScheduleForDoctor(@Path("doctorId") String doctorId, @Query("date") String date);
}
