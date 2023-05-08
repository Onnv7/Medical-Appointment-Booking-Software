package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface DoctorApiService {
    String BASE_URL = "doctor";

    @GET(BASE_URL + "/profile/{doctorId}")
    Call<JsonObject> getDoctorProfile(@Path("doctorId") String doctorId);

    @Multipart
    @PUT(BASE_URL + "/update-profile/{doctorId}")
    Call<JsonObject> updateProfile(@Path("doctorId") String doctorId,
                                   @PartMap HashMap<String, RequestBody> body,
                                   @Part MultipartBody.Part avatar);
}
