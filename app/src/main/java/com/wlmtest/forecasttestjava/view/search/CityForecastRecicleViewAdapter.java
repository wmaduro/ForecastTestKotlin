package com.wlmtest.forecasttestjava.view.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.CityForecastItemBinding;
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel;


public class CityForecastRecicleViewAdapter extends RecyclerView.Adapter<CityForecastRecicleViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;

    public CityForecastRecicleViewAdapter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CityForecastItemBinding cityForecastItemBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.city_forecast_item, parent, false);

        cityForecastItemBinding.setMainViewModel(this.mainViewModel);

        return new ViewHolder(cityForecastItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.cityForecastItemBinding
                .setCityForecastPojo(
                        this.mainViewModel.getCityForecastSearchListMutableLiveData().getValue().get(position));

        try {
            String iconRef = this.mainViewModel.getCityForecastSearchListMutableLiveData().getValue().get(position).getIcon();

            ImageView ivForecastItem = holder.cityForecastItemBinding.ivForecastItem;
            if (iconRef != null && !iconRef.isEmpty()) {
                Picasso.get()
                        .load("http://openweathermap.org/img/w/" + iconRef + ".png")
                        .into(ivForecastItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (this.mainViewModel.getCityForecastSearchListMutableLiveData() == null ||
                this.mainViewModel.getCityForecastSearchListMutableLiveData().getValue() == null) {
            return 0;
        }
        return this.mainViewModel.getCityForecastSearchListMutableLiveData().getValue().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public CityForecastItemBinding cityForecastItemBinding;

        public ViewHolder(CityForecastItemBinding cityForecastItemBinding) {

            super(cityForecastItemBinding.getRoot());
            this.cityForecastItemBinding = cityForecastItemBinding;

        }
    }
}
