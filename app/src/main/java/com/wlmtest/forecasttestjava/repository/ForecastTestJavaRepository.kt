package com.wlmtest.forecasttestjava.repository

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData
import com.wlmtest.forecasttestjava.repository.openweathermap.OpenWeatherMapServiceFactory
import com.wlmtest.forecasttestjava.repository.openweathermap.OpenWeatherMapService
import com.wlmtest.forecasttestjava.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import javax.inject.Inject
import javax.inject.Singleton


class ForecastTestJavaRepository
constructor() {

    private val openWeatherMapService: OpenWeatherMapService

    init {
        openWeatherMapService = OpenWeatherMapServiceFactory.getInstance().openWeatherMapService
    }


    /**
     * Find FiveDaysForecastData from API.
     *
     * @param cityId
     * @param fiveDaysForecastDataMutableLiveData
     */
    fun findFiveDaysForecastData(
        cityId: String,
        fiveDaysForecastDataMutableLiveData: MutableLiveData<FiveDaysForecastData>
    ) {

        val fiveDaysForecastDataCall = openWeatherMapService.findFiveDaysForecastDataByCityId(
            cityId,
            Utils.UNIT_CELCIUS_DB, null
        )

        fiveDaysForecastDataCall.enqueue(object : Callback<FiveDaysForecastData> {
            override fun onResponse(call: Call<FiveDaysForecastData>, response: Response<FiveDaysForecastData>) {

                val fiveDaysForecastData = response.body()

                if (response.isSuccessful && fiveDaysForecastData != null) {
                    fiveDaysForecastDataMutableLiveData.setValue(fiveDaysForecastData)
                } else {
                    fiveDaysForecastDataMutableLiveData.setValue(null)
                }


            }

            override fun onFailure(call: Call<FiveDaysForecastData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    /**
     * Find CurrentWeatherData from API.
     *
     * @param searchParam
     * @param currentWeatherDataMutableLiveData
     */
    fun findCurrentWeatherData(
        searchParam: Any,
        currentWeatherDataMutableLiveData: MutableLiveData<CurrentWeatherData>
    ) {

        var currentWeatherDataCall: Call<CurrentWeatherData>? = null

        if (searchParam is String) {

            currentWeatherDataCall =
                openWeatherMapService.findCurrentWeatherDataByCityName(searchParam, Utils.UNIT_CELCIUS_DB)

        } else {

            val latitude = (searchParam as Location).latitude
            val longitue = searchParam.longitude
            currentWeatherDataCall =
                openWeatherMapService.findCurrentWeatherDataByGPS(latitude, longitue, Utils.UNIT_CELCIUS_DB, null)
        }

        currentWeatherDataCall.enqueue(object : Callback<CurrentWeatherData> {
            override fun onResponse(call: Call<CurrentWeatherData>, response: Response<CurrentWeatherData>) {

                val currentWeatherData = response.body()

                if (response.isSuccessful && currentWeatherData != null) {
                    currentWeatherDataMutableLiveData.setValue(currentWeatherData)
                } else {
                    currentWeatherDataMutableLiveData.setValue(null)
                }
            }

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}