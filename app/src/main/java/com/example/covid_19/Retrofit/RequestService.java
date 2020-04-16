package com.example.covid_19.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {
    @GET("/resources/resources.json")
    Call<Model> requestEssentials();
}
