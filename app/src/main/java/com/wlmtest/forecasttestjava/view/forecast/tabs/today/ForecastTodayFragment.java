package com.wlmtest.forecasttestjava.view.forecast.tabs.today;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.FragmentForecastTodayBinding;
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo;
import com.wlmtest.forecasttestjava.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastTodayFragment extends Fragment {

    private FragmentForecastTodayBinding fragmentForecastTodayBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentForecastTodayBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_forecast_today,
                        container,
                        false);

        return (View) fragmentForecastTodayBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CityForecastPojo cityForecastPojo = ((MainActivity) getActivity()).getMainViewModel().getCityForecastPojoSelectedMutableLiveData().getValue();

        fragmentForecastTodayBinding
                .setCityForecastPojoSelected(cityForecastPojo);

        try {

            String iconRef = cityForecastPojo.getIcon();
            if (iconRef != null && !iconRef.isEmpty()) {
                ImageView ivForecastToday = fragmentForecastTodayBinding.ivForecastToday;

                Picasso.get()
                        .load("http://openweathermap.org/img/w/" + iconRef + ".png")
                        .into(ivForecastToday);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
