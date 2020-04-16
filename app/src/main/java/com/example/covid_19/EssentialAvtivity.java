package com.example.covid_19;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19.Retrofit.Model;
import com.example.covid_19.Retrofit.NetworkClient;
import com.example.covid_19.Retrofit.RequestService;
import com.example.covid_19.Retrofit.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EssentialAvtivity extends AppCompatActivity {

    private static final String TAG = "EssentialAvtivity";
    ArrayList<Resources> mEssentialList;
    
    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_avtivity);
        ButterKnife.bind(this);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        RequestService requestService = retrofit.create(RequestService.class);
        Call<Model> call = requestService.requestEssentials();
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e(TAG, "onResponse: Success");
                mEssentialList = new ArrayList<>(Arrays.asList(response.body().getResources()));
                addItemsOnSpinner();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

    public void addItemsOnSpinner()
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(dataAdapter);
    }

    @OnItemSelected(R.id.state_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {

    }
}
