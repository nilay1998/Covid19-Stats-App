package com.example.covid_19;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19.Service.Models.EssentialModel;
import com.example.covid_19.Service.Models.ResourcesModel;
import com.example.covid_19.ViewModels.EssentialActivityViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class EssentialAvtivity extends AppCompatActivity {

    private static final String TAG = "EssentialAvtivity";
    private EssentialActivityViewModel mEssentialActivityViewModel;

    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_avtivity);
        ButterKnife.bind(this);

        mEssentialActivityViewModel= new ViewModelProvider(this).get(EssentialActivityViewModel.class);
        mEssentialActivityViewModel.init();
        mEssentialActivityViewModel.getEssentials().observe(this, new Observer<EssentialModel>() {
            @Override
            public void onChanged(EssentialModel essentialModel) {
                mEssentialActivityViewModel.setmEssentialList();
                addItemsOnStateSpinner();
            }
        });
    }

    public void addItemsOnStateSpinner()
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mEssentialActivityViewModel.getUniqueState());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(dataAdapter);
    }

    public void addItemsOnCategorySpinner(String state)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mEssentialActivityViewModel.getStateCategories(state));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
    }

    @OnItemSelected(R.id.state_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {
        addItemsOnCategorySpinner(spinner.getItemAtPosition(position).toString());
    }
}
