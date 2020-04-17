package com.example.covid_19.ViewModels;

import android.content.res.Resources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid_19.Service.Models.ResourcesModel;
import com.example.covid_19.Service.Repositories.EssentialRepo;
import com.example.covid_19.Service.Models.EssentialModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EssentialActivityViewModel extends ViewModel {
    MutableLiveData<EssentialModel> mEssentials;
    EssentialRepo mRepo;
    ArrayList<ResourcesModel> mEssentialList;

    public void init()
    {
        if(mEssentials!=null)
            return;
        mRepo=EssentialRepo.getInstance();
        mEssentials=mRepo.getmEssentialList();
    }
    public LiveData<EssentialModel> getEssentials()
    {
        return mEssentials;
    }

    public void setmEssentialList() {
        mEssentialList=new ArrayList<>(Arrays.asList(mEssentials.getValue().getResources()));
    }

    public List<String> getUniqueState()
    {
        Set<String> set = new LinkedHashSet<>();
        for(int i=0;i<mEssentialList.size();i++)
        {
            if(mEssentialList.get(i).getState()!=null)
               set.add(mEssentialList.get(i).getState());
        }

        List<String> list=new ArrayList<>();
        list.addAll(set);
        Collections.sort(list);

        return list;
    }
}
