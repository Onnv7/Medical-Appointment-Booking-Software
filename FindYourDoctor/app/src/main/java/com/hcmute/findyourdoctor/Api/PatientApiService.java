package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientApiService {
    String BASE_URL = "patient";
    @GET(BASE_URL + "/profile/{patientId}")
    Call<JsonObject> getProfileById(@Path("patientId") String patientId);

    @Multipart
    @PUT(BASE_URL + "/update-profile/{patientId}")
    Call<JsonObject> updateProfile(@Path("patientId") String patientId,
                                   @PartMap HashMap<String, RequestBody> body,
                                   @Part MultipartBody.Part avatar);
    @GET(BASE_URL  + "/existed")
    Call<JsonObject> isExistedPatient(@Query("email") String email);
}
