package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientApiService {

    String BASE_URL = "patient";
    @GET(BASE_URL  + "/existed")
    Call<JsonObject> isExistedPatient(@Query("email") String email);
}
