package com.hcmute.findyourdoctor.Api;


import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface AuthApiService {
    String BASE_URL = "auth";
    @POST(BASE_URL + "/patient/login")
    Call<JsonObject> loginPatient(@Body HashMap<String, String> body);

    @POST(BASE_URL + "/patient/register")
    Call<JsonObject> registerPatient(@Body HashMap<String, String> body);

    @POST(BASE_URL + "/send-confirmation-code")
    Call<JsonObject> sendCodeToEmail(@Body HashMap<String, String> body);

    @GET("schedule/list/{id}")
    Call<JsonObject> test(@Path("id") String id);
}
