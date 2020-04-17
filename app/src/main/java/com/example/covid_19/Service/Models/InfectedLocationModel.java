package com.example.covid_19.Service.Models;

public class InfectedLocationModel {
    private TravelHistoryModel[] travel_history;

    public TravelHistoryModel[] getTravel_history ()
    {
        return travel_history;
    }

    public void setTravel_history (TravelHistoryModel[] travel_history)
    {
        this.travel_history = travel_history;
    }
}
