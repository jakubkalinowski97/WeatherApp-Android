package com.example.jakub.iweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jakub.iweather.Database.CityEntity;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
    private final LayoutInflater mInflater;
    private List<CityEntity> mCities; // Cached copy of words
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityItemView, countryItemView;


        public CityViewHolder(View itemView) {
            super(itemView);
            cityItemView = itemView.findViewById(R.id.textViewCity);
            countryItemView = itemView.findViewById(R.id.textViewCountry);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    CityListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        if (mCities != null) {
            final CityEntity current = mCities.get(position);
            holder.cityItemView.setText(current.getName());
            holder.countryItemView.setText(current.getCountry());
        } else {
            // Covers the case of data not being ready yet.
            holder.cityItemView.setText("No cities.");
        }
    }

    void setCities(List<CityEntity> cities){
        mCities = cities;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCities != null)
            return mCities.size();
        else return 0;
    }
}