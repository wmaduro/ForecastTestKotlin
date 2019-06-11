package com.wlmtest.forecasttestjava.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.wlmtest.forecasttestjava.base.events.InternetDisconnectedEvent
import com.wlmtest.forecasttestjava.base.events.ProgressDialogLoadingDataEvent
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.FiveDaysForecastPojo
import com.wlmtest.forecasttestjava.repository.ApiDataParser
import com.wlmtest.forecasttestjava.repository.ForecastTestJavaRepository
import com.wlmtest.forecasttestjava.view.main.MainOrchestrationHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import javax.inject.Inject
import java.util.ArrayList


class MainViewModel @Inject
constructor(private val forecastTestJavaRepository: ForecastTestJavaRepository) : ViewModel() {


    /**
     * API DATA
     */
    private val currentWeatherDataMutableLiveData = MutableLiveData<CurrentWeatherData>()
    private val fiveDaysForecastDataMutableLiveData = MutableLiveData<FiveDaysForecastData>()

    /**
     * CITY FORECAST
     */
    var cityForecastPojoSelectedMutableLiveData = MutableLiveData<CityForecastPojo>()
    var cityForecastSearchListMutableLiveData = MutableLiveData<List<CityForecastPojo>>()

    /**
     * CITY FORECAST FIVE DAYS
     */
    var fiveDaysForecastPojoMutableLiveData = MutableLiveData<FiveDaysForecastPojo>()

    /**
     * VISUAL ORCHESTRATION
     */
    var fragmentOrchestrationHelperMutableLiveData = MutableLiveData<String>()
    var appInitialized = MutableLiveData<Boolean>()
    var showInternetUnavailableMessage = MutableLiveData<Boolean>()

    var progressDialogLoadingData= MutableLiveData<Boolean>();

    init {
        EventBus.getDefault().register(this)
        setObservers()
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }


    /**
     * Eventbus message handler responsible for receive the emitted event from internet disconnection interceptor.
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: InternetDisconnectedEvent) {
        showInternetUnavailableMessage.value = true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ProgressDialogLoadingDataEvent) {
        progressDialogLoadingData.value = event.loading
    }

    /**
     * Set all observers for VM
     */
    private fun setObservers() {


        /**
         * Responsible for observing the data returned form the API for current weather search.
         */
        this.currentWeatherDataMutableLiveData.observeForever { currentWeatherData ->
            cityForecastSearchListMutableLiveData.value = ArrayList()

            if (currentWeatherData != null && currentWeatherData.list != null && currentWeatherData.list!!.size >= 0) {

                val cityForecastPojoList =
                    ApiDataParser.parseCurrentWeatherDataToCityForecastPojoList(currentWeatherData)


                if (cityForecastPojoList.size == 1) {
                    cityForecastPojoSelectedMutableLiveData.value = cityForecastPojoList[0]
                    fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.SHOW_FORECAST_FRAGMENT)

                } else {

                    cityForecastSearchListMutableLiveData.value = cityForecastPojoList
                    fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.SHOW_SEARCH_RESULT_FRAGMENT)

                }

            } else {
                fragmentOrchestrationHelperMutableLiveData.setValue(MainOrchestrationHelper.SHOW_SEARCH_RESULT_FRAGMENT)
            }

            //
            appInitialized.value = true
        }

        /**
         *
         * Responsible for observing when some Forecast Pojo is selected. This selection will trigger the request of FiveDaysForecastData.
         *
         */
        cityForecastPojoSelectedMutableLiveData.observeForever { cityForecastPojo ->
            forecastTestJavaRepository
                .findFiveDaysForecastData(cityForecastPojo.cityId!!, fiveDaysForecastDataMutableLiveData)
        }

        /**
         * Responsible for observing the API Five days forecast data and trigger the parse to a POJO.
         *
         */
        fiveDaysForecastDataMutableLiveData.observeForever { fiveDaysForecastData ->
            fiveDaysForecastPojoMutableLiveData.value =
                ApiDataParser.parseFiveDaysForecastDataToFiveDaysForecastPojo(fiveDaysForecastData)
        }
    }


    /**
     * Find the weather date from API by name.
     *
     * Responsible for connect the view's request to the repository.
     *
     * @param cityName
     */
    fun findCurrentWeatherDataByCityName(cityName: String) {

        forecastTestJavaRepository
            .findCurrentWeatherData(cityName, currentWeatherDataMutableLiveData)

    }

    /**
     * Find the weather date from API by location.
     *
     * Responsible for connect the view's request to the repository.
     *
     * @param location
     */
    fun findCurrentWeatherDataByGPSCoordinates(location: Location) {

        forecastTestJavaRepository
            .findCurrentWeatherData(location, currentWeatherDataMutableLiveData)

    }


    /**
     * Set the City Forecast by POJO
     *
     * @param cityForecastPojo
     */
    fun setCityForecastPojoSelected(cityForecastPojo: CityForecastPojo) {
        //clearn  5days data
        fiveDaysForecastPojoMutableLiveData.value = null

        cityForecastPojoSelectedMutableLiveData.value = cityForecastPojo
        fragmentOrchestrationHelperMutableLiveData.value = MainOrchestrationHelper.SHOW_FORECAST_FRAGMENT
    }


}
