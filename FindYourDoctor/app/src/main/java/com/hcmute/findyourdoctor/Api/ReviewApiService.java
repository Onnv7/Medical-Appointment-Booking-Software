package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewApiService {
    String BASE_URL = "review";
    @POST(BASE_URL + "/create")
    Call<JsonObject> createReview(@Body JsonObject review);

    @GET(BASE_URL + "/list/{doctorId}")
    Call<JsonObject> getReviewByDoctor(@Path("doctorId") String doctorId);

    @PATCH(BASE_URL + "/update-liker/{reviewId}")
    Call<JsonObject> updateLiker(@Path("reviewId") String reviewId, @Body JsonObject body);
}
