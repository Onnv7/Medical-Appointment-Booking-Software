package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;
import com.hcmute.findyourdoctor.Domain.BookingDomain;
import com.hcmute.findyourdoctor.Model.BookingModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// Cái này để test, muốn viết api thì phân tách nó ra interface khác theo đối tượng và gọi
public interface ApiService {
    @GET("schedule/list/{id}")
    Call<JsonObject> test(@Path("id") String id);

    @POST("booking/create")
    Call<BookingModel> createBook(@Body BookingDomain bookingDomain);
}
