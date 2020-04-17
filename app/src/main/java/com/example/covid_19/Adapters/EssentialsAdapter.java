package com.example.covid_19.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.Service.Models.ResourcesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EssentialsAdapter extends RecyclerView.Adapter<EssentialsAdapter.ViewHolder>
{
    ArrayList<ResourcesModel> mResources=new ArrayList<>();
    public EssentialsAdapter(ArrayList<ResourcesModel> resources) {
        mResources=resources;
    }

    @NonNull
    @Override
    public EssentialsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.essential_list_item,parent,false);
        EssentialsAdapter.ViewHolder holder=new EssentialsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EssentialsAdapter.ViewHolder holder, int position) {
        holder.city.setText(mResources.get(position).getCity());
        holder.organisation.setText(mResources.get(position).getNameoftheorganisation());
        holder.description.setText(mResources.get(position).getDescriptionandorserviceprovided());
        holder.number.setText(mResources.get(position).getPhonenumber());
    }

    @Override
    public int getItemCount() {
        return mResources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.city_textView)
        TextView city;
        @BindView(R.id.description_textView)
        TextView description;
        @BindView(R.id.org_textView)
        TextView organisation;
        @BindView(R.id.number_textView)
        TextView number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}