package com.wlmtest.forecasttestjava.repository.openweathermap

import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapInterface {

    @GET("data/2.5/find?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    fun findCurrentWeatherDataByCityName(@Query("q") cityName: String, @Query("units") unit: String): Call<CurrentWeatherData>

    @GET("data/2.5/find?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    fun findCurrentWeatherDataByGPS(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String,
        @Query("cnt") cn: String?
    ): Call<CurrentWeatherData>

    @GET("data/2.5/forecast?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    fun findFiveDaysForecastDataByCityId(
        @Query("id") id: String,
        @Query("units") unit: String,
        @Query("cnt") cn: String
    ): Call<FiveDaysForecastData>


}
