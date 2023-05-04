package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorApiService {
    String BASE_URL = "doctor";

    @GET(BASE_URL + "/profile/{doctorId}")
    Call<JsonObject> getDoctorProfile(@Path("doctorId") String doctorId);
}
