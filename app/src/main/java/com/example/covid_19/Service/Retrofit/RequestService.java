package com.example.covid_19.Service.Retrofit;

import com.example.covid_19.Service.Models.EssentialModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {
    @GET("/resources/resources.json")
    Call<EssentialModel> requestEssentials();
}
