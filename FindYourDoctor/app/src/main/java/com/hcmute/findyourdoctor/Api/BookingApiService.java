package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApiService {
    String BASE_URL = "booking";
    @GET(BASE_URL + "/patient/list/{patientId}")
    Call<JsonObject> getBookingListId(@Path("patientId") String patientId);
}
