package com.wlmtest.forecasttestjava.repository.openweathermap;

import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData;
import com.wlmtest.forecasttestjava.model.fivedaysforecastdata.FiveDaysForecastData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapInterface {

    @GET("data/2.5/find?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    public Call<CurrentWeatherData> findCurrentWeatherDataByCityName(@Query("q") String cityName, @Query("units") String unit);

    @GET("data/2.5/find?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    public Call<CurrentWeatherData> findCurrentWeatherDataByGPS(@Query("lat") double latitude,
                                                                @Query("lon") double longitude,
                                                                @Query("units") String unit,
                                                                @Query("cnt") String cn);

    @GET("data/2.5/forecast?APPID=51b0889d8947bb4df12f2f1928ec6daa")
    public Call<FiveDaysForecastData> findFiveDaysForecastDataByCityId(@Query("id") String id,
                                                                       @Query("units") String unit,
                                                                       @Query("cnt") String cn);


}
