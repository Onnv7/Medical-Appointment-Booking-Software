package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorApiService {
    String BASE_URL = "doctor";
    @GET(BASE_URL + "/top-doctor/{top}")
    Call<JsonObject> getTopDoctor(@Path("top") int top);

    @GET(BASE_URL + "/some-doctor/{some}")
    Call<JsonObject> getSomeDoctor(@Path("some") int some);

    @GET(BASE_URL + "/info/{doctorId}")
    Call<JsonObject> getInfoDoctorById(@Path("doctorId") String doctorId);

    @GET(BASE_URL + "/search")
    Call<JsonObject> searchDoctor(@Query("content") String content);

    @GET(BASE_URL + "/specialist/{specialistId}")
    Call<JsonObject> getDoctorsBySpecialist(@Path("specialistId") String specialistId);
}
