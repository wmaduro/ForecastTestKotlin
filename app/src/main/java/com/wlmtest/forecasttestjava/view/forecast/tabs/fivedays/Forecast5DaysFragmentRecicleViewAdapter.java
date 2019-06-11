package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.Forecast5daysItemBinding;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.CityForecastFiveDaysPojo;
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class Forecast5DaysFragmentRecicleViewAdapter
        extends RecyclerView.Adapter<Forecast5DaysFragmentRecicleViewAdapter.ViewHolder> {

    private MainViewModel mainViewModel;

    public Forecast5DaysFragmentRecicleViewAdapter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Forecast5daysItemBinding forecast5daysItemBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.forecast5days_item, parent, false);

        return new ViewHolder(forecast5daysItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LinkedHashMap<String, ArrayList<CityForecastFiveDaysPojo>> forecast5DaysMap =
                this.mainViewModel.getFiveDaysForecastPojoMutableLiveData()
                .getValue()
                .getForecast5DaysMap();

        String dateReference = forecast5DaysMap.keySet()
                .toArray()[position].toString();
        holder.forecast5daysItemBinding
                .setDateReference(dateReference);

        List<CityForecastFiveDaysPojo> cityForecastFiveDaysPojoList = forecast5DaysMap.get(dateReference);
        SmallForecastItemAdapter smallForecastItemAdapter =
                new SmallForecastItemAdapter(cityForecastFiveDaysPojoList);
        holder.forecast5daysItemBinding.setSmallForecastItemAdapter(smallForecastItemAdapter);

    }

    @Override
    public int getItemCount() {
        if (this.mainViewModel.getFiveDaysForecastPojoMutableLiveData() == null ||
                this.mainViewModel.getFiveDaysForecastPojoMutableLiveData().getValue() == null) {
            return 0;
        }
        return this.mainViewModel.getFiveDaysForecastPojoMutableLiveData().getValue().getForecast5DaysMap().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public Forecast5daysItemBinding forecast5daysItemBinding;

        public ViewHolder(Forecast5daysItemBinding forecast5daysItemBinding) {

            super(forecast5daysItemBinding.getRoot());
            this.forecast5daysItemBinding = forecast5daysItemBinding;

        }
    }
}
