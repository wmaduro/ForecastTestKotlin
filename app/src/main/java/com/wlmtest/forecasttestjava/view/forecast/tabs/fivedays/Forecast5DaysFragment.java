package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.FragmentForecast5DaysBinding;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.FiveDaysForecastPojo;
import com.wlmtest.forecasttestjava.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Forecast5DaysFragment extends Fragment {

    private FragmentForecast5DaysBinding fragmentForecast5DaysBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentForecast5DaysBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_forecast5_days,
                        container,
                        false);


        return (View) fragmentForecast5DaysBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ((MainActivity) getActivity()).getMainViewModel().getFiveDaysForecastPojoMutableLiveData().observe(this, new Observer<FiveDaysForecastPojo>() {
            @Override
            public void onChanged(FiveDaysForecastPojo fiveDaysForecastPojo) {


                Forecast5DaysFragmentRecicleViewAdapter forecast5DaysFragmentRecicleViewAdapter =
                        new Forecast5DaysFragmentRecicleViewAdapter(((MainActivity) getActivity()).getMainViewModel());

                fragmentForecast5DaysBinding.setForecast5DaysFragmentRecicleViewAdapter(forecast5DaysFragmentRecicleViewAdapter);


            }
        });

    }


}
