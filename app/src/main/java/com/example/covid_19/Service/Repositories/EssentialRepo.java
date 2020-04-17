package com.example.covid_19.Service.Repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.covid_19.Service.Models.EssentialModel;
import com.example.covid_19.Service.Retrofit.NetworkClient;
import com.example.covid_19.Service.Retrofit.RequestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class EssentialRepo {
    private static EssentialRepo instance;


    public static EssentialRepo getInstance()
    {
        if(instance==null)
            instance=new EssentialRepo();
        return instance;
    }

    public MutableLiveData<EssentialModel> getmEssentialList()
    {
        MutableLiveData<EssentialModel> data=new MutableLiveData<>();

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        RequestService requestService = retrofit.create(RequestService.class);
        Call<EssentialModel> call = requestService.requestEssentials();
        call.enqueue(new Callback<EssentialModel>() {
            @Override
            public void onResponse(Call<EssentialModel> call, Response<EssentialModel> response) {
                Log.e(TAG, "onResponse: Success");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<EssentialModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });

        return data;
    }
}
