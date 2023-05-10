package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface AuthApiService {
    String BASE_URL = "auth";

    @POST(BASE_URL + "/doctor/login")
    Call<JsonObject> doctorLogin(@Body JsonObject body);

    @PATCH(BASE_URL + "/doctor/change-password")
    Call<JsonObject> changePassword(@Body JsonObject body);

    @POST(BASE_URL + "/send-confirmation-code")
    Call<JsonObject> sendCodeToEmail(@Body JsonObject body);

}
