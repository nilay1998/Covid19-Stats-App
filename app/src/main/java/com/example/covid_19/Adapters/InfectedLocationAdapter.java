package com.example.covid_19.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.Service.Models.TravelHistoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfectedLocationAdapter extends RecyclerView.Adapter<InfectedLocationAdapter.ViewHolder> {

    ArrayList<TravelHistoryModel> mArrayList=new ArrayList<>();
    public InfectedLocationAdapter(ArrayList<TravelHistoryModel> arrayList){mArrayList=arrayList;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item,parent,false);
        InfectedLocationAdapter.ViewHolder holder=new InfectedLocationAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(mArrayList.get(position).getAddress());
        holder.latlong.setText(mArrayList.get(position).getLatlong());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.address_tv)
        TextView address;
        @BindView(R.id.latlong_tv)
        TextView latlong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
