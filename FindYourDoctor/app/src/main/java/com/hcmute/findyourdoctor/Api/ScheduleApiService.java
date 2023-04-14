package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleApiService {
    String BASE_URL = "schedule";
    @POST(BASE_URL + "/available/date/{doctorId}")
    Call<JsonObject> getAvailableSchedule(@Path("doctorId") String doctorId, @Body HashMap<String, String> body);


    @GET(BASE_URL + "/available/slot/{doctorId}")
    Call<JsonObject> getAvailableSlotSchedule(@Path("doctorId") String doctorId, @Query("date") String date);

}
