package com.example.covid_19.Service.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.covid_19.Service.Models.InfectedLocationModel;
import com.example.covid_19.Service.Retrofit.NetworkClient;
import com.example.covid_19.Service.Retrofit.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfectedLocationRepo {

    private static InfectedLocationRepo instance;

    public static InfectedLocationRepo getInstance()
    {
        if(instance==null)
            instance=new InfectedLocationRepo();
        return instance;
    }

    public MutableLiveData<InfectedLocationModel> getInfectedLocation()
    {
        MutableLiveData<InfectedLocationModel> data=new MutableLiveData<>();
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        RequestService requestService = retrofit.create(RequestService.class);
        Call<InfectedLocationModel> call=requestService.requestLocation();
        call.enqueue(new Callback<InfectedLocationModel>() {
            @Override
            public void onResponse(Call<InfectedLocationModel> call, Response<InfectedLocationModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<InfectedLocationModel> call, Throwable t) {

            }
        });
        return data;
    }
}
