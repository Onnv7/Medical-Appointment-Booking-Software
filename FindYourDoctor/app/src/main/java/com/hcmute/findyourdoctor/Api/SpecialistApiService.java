package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpecialistApiService {
    String BASE_URL = "specialist";

    @GET(BASE_URL + "/list")
    Call<JsonObject> getAllSpecialists();
}
