package com.example.covid_19.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid_19.Service.Models.InfectedLocationModel;
import com.example.covid_19.Service.Repositories.InfectedLocationRepo;

public class LocationActivityViewModel extends ViewModel {
    MutableLiveData<InfectedLocationModel> mInfectedLocation;
    InfectedLocationRepo mRepo;

    public void init()
    {
        if(mInfectedLocation!=null)
            return;
        mRepo=InfectedLocationRepo.getInstance();
        mInfectedLocation=mRepo.getInfectedLocation();
    }

    public LiveData<InfectedLocationModel> getInfectedLocation()
    {
        return mInfectedLocation;
    }
}
