package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorApiService {
    String BASE_URL = "doctor";
    @GET(BASE_URL + "/top-doctor/{top}")
    Call<JsonObject> getTopDoctor(@Path("top") int top);

    @GET(BASE_URL + "/some-doctor/{some}")
    Call<JsonObject> getSomeDoctor(@Path("some") int some);

    @GET(BASE_URL + "/info/{doctorId}")
    Call<JsonObject> getInfoDoctorById(@Path("doctorId") String doctorId);
}
