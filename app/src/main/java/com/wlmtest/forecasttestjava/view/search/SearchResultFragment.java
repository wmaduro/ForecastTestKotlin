package com.wlmtest.forecasttestjava.view.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.databinding.FragmentSearchResultBinding;
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo;
import com.wlmtest.forecasttestjava.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private FragmentSearchResultBinding fragmentSearchResultBinding;
    private CityForecastRecicleViewAdapter cityForecastRecicleViewAdapter;
    private ViewGroup rlMultipleCitiesFound;
    private ViewGroup rlMainLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSearchResultBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_search_result,
                        container,
                        false);

        return (View) fragmentSearchResultBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlMultipleCitiesFound = getActivity().findViewById(R.id.rlMultipleCitiesFound);
        rlMainLayout = getActivity().findViewById(R.id.rlMainLayout);
        //init the with for the first page
        rlMultipleCitiesFound.setVisibility(View.GONE);
        rlMainLayout.setVisibility(View.VISIBLE);


        cityForecastRecicleViewAdapter =
                new CityForecastRecicleViewAdapter(((MainActivity) getActivity()).getMainViewModel());

        fragmentSearchResultBinding
                .setCityForecastRecicleViewAdapter(cityForecastRecicleViewAdapter);

        ((TextView) getActivity().findViewById(R.id.tvGeneralInfo)).setText(R.string.greeting_message);


        setObservers();
    }

    /**
     * Set all observers.
     */
    private void setObservers() {

        ((MainActivity) getActivity())
                .getMainViewModel().getCityForecastSearchListMutableLiveData()
                .observe(this,
                        new Observer<java.util.List<CityForecastPojo>>() {
                            @Override
                            public void onChanged(java.util.List<CityForecastPojo> cityForecastPojoList) {

                                if (cityForecastPojoList.size() > 0) {

                                    rlMultipleCitiesFound.setVisibility(View.VISIBLE);
                                    rlMainLayout.setVisibility(View.GONE);

                                } else {

                                    rlMultipleCitiesFound.setVisibility(View.GONE);
                                    rlMainLayout.setVisibility(View.VISIBLE);

                                    ((TextView) getActivity().findViewById(R.id.tvGeneralInfo)).setText(R.string.city_not_found);
                                }

                            }
                        });

    }

}
