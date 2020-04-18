package com.example.covid_19.ViewModels;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid_19.Service.Models.InfectedLocationModel;
import com.example.covid_19.Service.Models.TravelHistoryModel;
import com.example.covid_19.Service.Repositories.InfectedLocationRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class LocationActivityViewModel extends ViewModel {
    MutableLiveData<InfectedLocationModel> mInfectedLocation;
    InfectedLocationRepo mRepo;
    ArrayList<TravelHistoryModel> mInfectedLocationList;

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

    public void setmInfectedLocationList()
    {
        mInfectedLocationList=new ArrayList<>(Arrays.asList(mInfectedLocation.getValue().getTravel_history()));
    }

    public ArrayList<TravelHistoryModel> getNearestLocation(Location location)
    {
        ArrayList<TravelHistoryModel> arrayList=new ArrayList<>();
        for(int i=0;i<mInfectedLocationList.size();i++)
        {
            TravelHistoryModel travelHistoryModel=mInfectedLocationList.get(i);
            if(travelHistoryModel.getLatlong()!=null && !travelHistoryModel.getLatlong().isEmpty())
            {
                String arr[]=travelHistoryModel.getLatlong().split(",");
                Double distance=getDifference(location.getLatitude(),location.getLongitude(),Double.parseDouble(arr[0].trim()),Double.parseDouble(arr[1].trim()));
                travelHistoryModel.setLatlong(String.valueOf(distance));
                arrayList.add(travelHistoryModel);
            }
        }
        HashSet<TravelHistoryModel> hashSet=new HashSet<>(arrayList);
        arrayList.clear();
        arrayList.addAll(hashSet);

        Collections.sort(arrayList, new Comparator<TravelHistoryModel>()
        {
            public int compare(TravelHistoryModel o1, TravelHistoryModel o2) {
                Double dis=Double.parseDouble(o1.getLatlong())-Double.parseDouble(o2.getLatlong());
                return dis.intValue();
            }
        });
        
        return arrayList;
    }

    private double getDifference(double currLat, double currLon,double infLat, double infLon)
    {
        int radius=6378000;
        double x1=radius*Math.cos(Math.toRadians(currLat))*Math.cos(Math.toRadians(currLon));
        double y1=radius*Math.cos(Math.toRadians(currLat))*Math.sin(Math.toRadians(currLon));
        double z1=radius*Math.sin(Math.toRadians(currLat));

        double x2=radius*Math.cos(Math.toRadians(infLat))*Math.cos(Math.toRadians(infLon));
        double y2=radius*Math.cos(Math.toRadians(infLat))*Math.sin(Math.toRadians(infLon));
        double z2=radius*Math.sin(Math.toRadians(infLat));

        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)+Math.pow(z1-z2,2));
    }
}
