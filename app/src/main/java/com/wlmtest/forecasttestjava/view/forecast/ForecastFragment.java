package com.wlmtest.forecasttestjava.view.forecast;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.FragmentForecastBinding;
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo;
import com.wlmtest.forecasttestjava.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {


    private FragmentForecastBinding fragmentForecastBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

          fragmentForecastBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_forecast,
                        container,
                        false);

        return (View) fragmentForecastBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CityForecastPojo cityForecastPojo = ((MainActivity) getActivity()).getMainViewModel().getCityForecastPojoSelectedMutableLiveData().getValue();

        fragmentForecastBinding
                .setCityForecastPojoSelected(cityForecastPojo);

        ForecastTabAdapter forecastTabAdapter =
                new ForecastTabAdapter(getActivity(), getActivity().getSupportFragmentManager());

        ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
        viewPager.setAdapter(forecastTabAdapter);

        TabLayout tabs = getActivity().findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



    }


}
