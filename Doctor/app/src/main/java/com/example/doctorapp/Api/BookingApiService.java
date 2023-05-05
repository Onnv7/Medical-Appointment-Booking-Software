package com.example.doctorapp.Api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookingApiService {
    String BASE_URL = "booking";

    @GET(BASE_URL + "/doctor/schedule/{doctorId}")
    Call<JsonObject> listAcceptedBooking(@Path("doctorId") String doctorId, @Query("date") String date);

    @GET(BASE_URL + "/doctor/details/{bookingId}")
    Call<JsonObject> getBookingDetails(@Path("bookingId") String bookingId);

    @PATCH(BASE_URL + "/update/{bookingId}")
    Call<JsonObject> updateBookingDetails(@Path("bookingId") String bookingId, @Body JsonObject body);

    @GET(BASE_URL + "/doctor/new-booking/{doctorId}")
    Call<JsonObject> getNewBookingList(@Path("doctorId") String doctorId, @Query("date") String date);
}
