package com.hcmute.findyourdoctor.Api;

import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Cái này để test, muốn viết api thì phân tách nó ra interface khác theo đối tượng và gọi
public interface ApiService {
    @GET("schedule/list/{id}")
    Call<JsonObject> test(@Path("id") String id);
}
