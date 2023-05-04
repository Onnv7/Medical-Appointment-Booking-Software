package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleApiService {
    String BASE_URL = "schedule";

    @GET(BASE_URL + "/date/list/{doctorId}")
    Call<JsonObject> getScheduleForDoctor(@Path("doctorId") String doctorId, @Query("date") String date);

    @PATCH(BASE_URL + "/update/{doctorId}")
    Call<JsonObject> updateTimeSlotSchedule(@Path("doctorId") String doctorId, @Body JsonObject body);
}
