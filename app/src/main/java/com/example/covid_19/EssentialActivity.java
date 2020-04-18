package com.example.covid_19;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class EssentialActivity extends AppCompatActivity {

    private static final String TAG = "EssentialActivity";
    private EssentialActivityViewModel mEssentialActivityViewModel;

    @BindView(R.id.addFilter_tv)
    TextView addFilterTv;
    @BindView(R.id.filter_linearLayout)
    LinearLayout filterLinearLayout;
    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.essential_recyclerView)
    RecyclerView essentialRecyclerView;
//    @BindView(R.id.searchButton)
//    Button searchButton;

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

//    @OnItemSelected(R.id.category_spinner)
//    public void categorySpinnerItemSelected(Spinner spinner, int position) {
//        String state = stateSpinner.getSelectedItem().toString();
//        String category = spinner.getItemAtPosition(position).toString();
//
//    }

    private void inflateRecyclerView(String state, String category) {
        EssentialsAdapter adapter = new EssentialsAdapter(mEssentialActivityViewModel.getEssentialsFiltered(state, category));
        essentialRecyclerView.setAdapter(adapter);
        essentialRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @OnClick(R.id.searchButton)
    public void onViewClicked() {
        inflateRecyclerView(stateSpinner.getSelectedItem().toString(), categorySpinner.getSelectedItem().toString());
        filterLinearLayout.setVisibility(View.INVISIBLE);
        addFilterTv.setClickable(true);
    }

    @OnClick(R.id.addFilter_tv)
    public void onTextViewClicked() {
        filterLinearLayout.setVisibility(View.VISIBLE);
        addFilterTv.setClickable(false);
    }
}
