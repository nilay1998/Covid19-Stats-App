package com.example.covid_19;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.Adapters.EssentialsAdapter;
import com.example.covid_19.Service.Models.EssentialModel;
import com.example.covid_19.ViewModels.EssentialActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class EssentialActivity extends AppCompatActivity {

    private static final String TAG = "EssentialActivity";
    private EssentialActivityViewModel mEssentialActivityViewModel;

    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.essential_recyclerView)
    RecyclerView essentialRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential);
        ButterKnife.bind(this);

        mEssentialActivityViewModel = new ViewModelProvider(this).get(EssentialActivityViewModel.class);
        mEssentialActivityViewModel.init();
        mEssentialActivityViewModel.getEssentials().observe(this, new Observer<EssentialModel>() {
            @Override
            public void onChanged(EssentialModel essentialModel) {
                mEssentialActivityViewModel.setmEssentialList();
                addItemsOnStateSpinner();
            }
        });
    }

    public void addItemsOnStateSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mEssentialActivityViewModel.getUniqueState());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(dataAdapter);
    }

    public void addItemsOnCategorySpinner(String state) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mEssentialActivityViewModel.getStateCategories(state));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
    }

    @OnItemSelected(R.id.state_spinner)
    public void stateSpinnerItemSelected(Spinner spinner, int position) {
        addItemsOnCategorySpinner(spinner.getItemAtPosition(position).toString());
    }

    @OnItemSelected(R.id.category_spinner)
    public void categorySpinnerItemSelected(Spinner spinner, int position) {
        String state = stateSpinner.getSelectedItem().toString();
        String category = spinner.getItemAtPosition(position).toString();
        inflateRecyclerView(state, category);
    }

    private void inflateRecyclerView(String state, String category) {
        EssentialsAdapter adapter=new EssentialsAdapter(mEssentialActivityViewModel.getEssentialsFiltered(state,category));
        essentialRecyclerView.setAdapter(adapter);
        essentialRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
