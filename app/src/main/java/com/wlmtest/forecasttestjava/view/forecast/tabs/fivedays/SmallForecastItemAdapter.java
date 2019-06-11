package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.SmallForecastItemBinding;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.CityForecastFiveDaysPojo;

import java.util.List;


public class SmallForecastItemAdapter
        extends RecyclerView.Adapter<SmallForecastItemAdapter.ViewHolder> {

    private List<CityForecastFiveDaysPojo> cityForecastFiveDaysPojoList;

    public SmallForecastItemAdapter(List<CityForecastFiveDaysPojo> cityForecastFiveDaysPojoList) {
        this.cityForecastFiveDaysPojoList = cityForecastFiveDaysPojoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SmallForecastItemBinding smallForecastItemBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.small_forecast_item, parent, false);

        return new ViewHolder(smallForecastItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CityForecastFiveDaysPojo cityForecastFiveDaysPojo =
                (CityForecastFiveDaysPojo) cityForecastFiveDaysPojoList
                        .toArray()[position];


        holder.smallForecastItemBinding
                .setCityForecastFiveDaysPojo(cityForecastFiveDaysPojo);

        try {

            String iconRef = cityForecastFiveDaysPojo.getIcon();
            if (iconRef != null && !iconRef.isEmpty()) {
                ImageView ivIcon = holder.smallForecastItemBinding.ivIcon;
                Picasso.get()
                        .load("http://openweathermap.org/img/w/" + iconRef + ".png")
                        .into(ivIcon);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return cityForecastFiveDaysPojoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public SmallForecastItemBinding smallForecastItemBinding;

        public ViewHolder(SmallForecastItemBinding smallForecastItemBinding) {

            super(smallForecastItemBinding.getRoot());
            this.smallForecastItemBinding = smallForecastItemBinding;

        }
    }
}
