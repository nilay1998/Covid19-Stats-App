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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EssentialActivityViewModel extends ViewModel {
    MutableLiveData<EssentialModel> mEssentials;
    EssentialRepo mRepo;
    HashMap<String,HashMap <String , ArrayList<ResourcesModel>>> hashMap=new HashMap<>();

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
        ArrayList<ResourcesModel> mEssentialList=new ArrayList<>(Arrays.asList(mEssentials.getValue().getResources()));
        for(int i=0;i<mEssentialList.size();i++)
        {
            String state=mEssentialList.get(i).getState();
            String category=mEssentialList.get(i).getCategory();
            ResourcesModel resourcesModel=mEssentialList.get(i);
            if(hashMap.containsKey(state))
            {
                if(hashMap.get(state).containsKey(category))
                {
                    hashMap.get(state).get(category).add(resourcesModel);
                }
                else
                {
                    ArrayList<ResourcesModel> temp=new ArrayList<>();
                    temp.add(resourcesModel);
                    hashMap.get(state).put(category,temp);
                }
            }
            else
            {
                ArrayList<ResourcesModel> temp=new ArrayList<>();
                temp.add(resourcesModel);
                hashMap.put(state,new HashMap(){{put(category,temp);}});
            }
        }
    }

    public List<String> getUniqueState()
    {
        List<String> list=new ArrayList<>();
        list.addAll(hashMap.keySet());
        Collections.sort(list);
        return list;
    }

    public List<String> getStateCategories(String location)
    {
        List<String> list=new ArrayList<>();
        list.addAll(hashMap.get(location).keySet());
        Collections.sort(list);
        return list;
    }

    public ArrayList<ResourcesModel> getEssentialsFiltered(String state, String category)
    {
        return hashMap.get(state).get(category);
    }
}
