package com.wlmtest.forecasttestjava.repository;

import android.location.Location;
import androidx.lifecycle.MutableLiveData;
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData;
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData;
import com.wlmtest.forecasttestjava.repository.openweathermap.OpenWeatherMapApi;
import com.wlmtest.forecasttestjava.repository.openweathermap.OpenWeatherMapInterface;
import com.wlmtest.forecasttestjava.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ForecastTestJavaRepository {

    private OpenWeatherMapInterface openWeatherMapInterface;

    @Inject
    public ForecastTestJavaRepository() {
        openWeatherMapInterface
                = OpenWeatherMapApi.getInstance().getOpenWeatherMapInterface();
    }


    /**
     * Find FiveDaysForecastData from API.
     *
     * @param cityId
     * @param fiveDaysForecastDataMutableLiveData
     */
    public void findFiveDaysForecastData(String cityId, final MutableLiveData<FiveDaysForecastData> fiveDaysForecastDataMutableLiveData) {

        Call<FiveDaysForecastData> fiveDaysForecastDataCall =
                openWeatherMapInterface.findFiveDaysForecastDataByCityId(cityId,
                        Utils.UNIT_CELCIUS_DB, null);

        fiveDaysForecastDataCall.enqueue(new Callback<FiveDaysForecastData>() {
            @Override
            public void onResponse(Call<FiveDaysForecastData> call, Response<FiveDaysForecastData> response) {

                final FiveDaysForecastData fiveDaysForecastData = response.body();

                if (response.isSuccessful() && fiveDaysForecastData != null) {
                    fiveDaysForecastDataMutableLiveData.setValue(fiveDaysForecastData);
                } else {
                    fiveDaysForecastDataMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<FiveDaysForecastData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Find CurrentWeatherData from API.
     *
     * @param searchParam
     * @param currentWeatherDataMutableLiveData
     */
    public void findCurrentWeatherData(Object searchParam, final MutableLiveData<CurrentWeatherData> currentWeatherDataMutableLiveData) {

        Call<CurrentWeatherData> currentWeatherDataCall = null;

        if (searchParam instanceof String) {

            currentWeatherDataCall = openWeatherMapInterface.findCurrentWeatherDataByCityName((String) searchParam, Utils.UNIT_CELCIUS_DB);

        } else {

            double latitude = ((Location) searchParam).getLatitude();
            double longitue = ((Location) searchParam).getLongitude();
            currentWeatherDataCall = openWeatherMapInterface.findCurrentWeatherDataByGPS(latitude, longitue, Utils.UNIT_CELCIUS_DB, "1");
        }

        currentWeatherDataCall.enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {

                final CurrentWeatherData currentWeatherData = response.body();

                if (response.isSuccessful() && currentWeatherData != null) {
                    currentWeatherDataMutableLiveData.setValue(currentWeatherData);
                } else {
                    currentWeatherDataMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}