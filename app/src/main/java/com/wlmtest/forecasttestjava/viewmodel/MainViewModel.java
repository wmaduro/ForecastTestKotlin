package com.wlmtest.forecasttestjava.viewmodel;

import android.location.Location;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.wlmtest.forecasttestjava.base.events.InternetDisconnectedEvent;
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData;
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData;
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.FiveDaysForecastPojo;
import com.wlmtest.forecasttestjava.repository.ApiDataParser;
import com.wlmtest.forecasttestjava.repository.ForecastTestJavaRepository;
import com.wlmtest.forecasttestjava.view.main.MainOrchestrationHelper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import java.util.ArrayList;


public class MainViewModel extends ViewModel {

    @Inject
    public MainViewModel(@NonNull ForecastTestJavaRepository forecastTestJavaRepository) {
        super();
        this.forecastTestJavaRepository = forecastTestJavaRepository;
        EventBus.getDefault().register(this);
        setObservers();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }

    private ForecastTestJavaRepository forecastTestJavaRepository;


    /**
     * API DATA
     */
    private MutableLiveData<CurrentWeatherData> currentWeatherDataMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<FiveDaysForecastData> fiveDaysForecastDataMutableLiveData = new MutableLiveData<>();

    /**
     * CITY FORECAST
     */
    public MutableLiveData<CityForecastPojo> cityForecastPojoSelectedMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<java.util.List<CityForecastPojo>> cityForecastSearchListMutableLiveData = new MutableLiveData<>();

    /**
     * CITY FORECAST FIVE DAYS
     */
    public MutableLiveData<FiveDaysForecastPojo> fiveDaysForecastPojoMutableLiveData = new MutableLiveData<>();

    /**
     * VISUAL ORCHESTRATION
     */
    public MutableLiveData<String> fragmentOrchestrationHelperMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> appInitialized = new MutableLiveData<>();
    public MutableLiveData<Boolean> showInternetUnavailableMessage = new MutableLiveData<>();


    /**
     * Eventbus message handler responsible for receive the emitted event from internet disconnection interceptor.
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(InternetDisconnectedEvent event) {
        showInternetUnavailableMessage.setValue(true);
    }

    ;


    /**
     * Set all observers for VM
     */
    private void setObservers() {


        /**
         * Responsible for observing the data returned form the API for current weather search.
         */
        this.currentWeatherDataMutableLiveData.observeForever(new Observer<CurrentWeatherData>() {

            @Override
            public void onChanged(CurrentWeatherData currentWeatherData) {

                cityForecastSearchListMutableLiveData.setValue(new ArrayList<CityForecastPojo>());

                if (currentWeatherData != null && currentWeatherData.getList() != null && currentWeatherData.getList().size() >= 0) {

                    java.util.List<CityForecastPojo> cityForecastPojoList = ApiDataParser.parseCurrentWeatherDataToCityForecastPojoList(currentWeatherData);


                    if (cityForecastPojoList.size() == 1) {
                        cityForecastPojoSelectedMutableLiveData.setValue(cityForecastPojoList.get(0));
                        fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.INSTANCE.getSHOW_FORECAST_FRAGMENT());

                    } else {

                        cityForecastSearchListMutableLiveData.setValue(cityForecastPojoList);
                        fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.INSTANCE.getSHOW_SEARCH_RESULT_FRAGMENT());

                    }

                } else {
                    fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.INSTANCE.getSHOW_SEARCH_RESULT_FRAGMENT());
                }

                //
                appInitialized.setValue(true);
            }
        });

        /**
         *
         * Responsible for observing when some Forecast Pojo is selected. This selection will trigger the request of FiveDaysForecastData.
         *
         */
        cityForecastPojoSelectedMutableLiveData.observeForever(new Observer<CityForecastPojo>() {
            @Override
            public void onChanged(CityForecastPojo cityForecastPojo) {

                forecastTestJavaRepository
                        .findFiveDaysForecastData(cityForecastPojo.getCityId(), fiveDaysForecastDataMutableLiveData);

            }
        });

        /**
         * Responsible for observing the API Five days forecast data and trigger the parse to a POJO.
         *
         */
        fiveDaysForecastDataMutableLiveData.observeForever(new Observer<FiveDaysForecastData>() {
            @Override
            public void onChanged(FiveDaysForecastData fiveDaysForecastData) {

                fiveDaysForecastPojoMutableLiveData.setValue(ApiDataParser.parseFiveDaysForecastDataToFiveDaysForecastPojo(fiveDaysForecastData));

            }
        });
    }


    /**
     * Find the weather date from API by name.
     *
     * Responsible for connect the view's request to the repository.
     *
     * @param cityName
     */
    public void findCurrentWeatherDataByCityName(String cityName) {

        forecastTestJavaRepository
                .findCurrentWeatherData(cityName, currentWeatherDataMutableLiveData);

    }

    /**
     * Find the weather date from API by location.
     *
     * Responsible for connect the view's request to the repository.
     *
     * @param location
     */
    public void findCurrentWeatherDataByGPSCoordinates(Location location) {

        forecastTestJavaRepository
                .findCurrentWeatherData(location, currentWeatherDataMutableLiveData);

    }


    /**
     * Set the City Forecast by POJO
     *
     * @param cityForecastPojo
     */
    public void setCityForecastPojoSelected(CityForecastPojo cityForecastPojo) {
        cityForecastPojoSelectedMutableLiveData.setValue(cityForecastPojo);
        fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.INSTANCE.getSHOW_FORECAST_FRAGMENT());
    }


}
