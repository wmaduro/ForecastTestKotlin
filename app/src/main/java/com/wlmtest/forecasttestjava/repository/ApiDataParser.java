package com.wlmtest.forecasttestjava.repository;

import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData;
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.Weather;
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.City;
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData;
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.CityForecastFiveDaysPojo;
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.FiveDaysForecastPojo;
import com.wlmtest.forecasttestjava.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class ApiDataParser {

    public static FiveDaysForecastPojo parseFiveDaysForecastDataToFiveDaysForecastPojo(
            FiveDaysForecastData fiveDaysForecastData) {

        if (fiveDaysForecastData == null || fiveDaysForecastData.getList() ==null){
            return null;
        }


        FiveDaysForecastPojo fiveDaysForecastPojo = new FiveDaysForecastPojo();

        java.util.List<com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.List> lists = fiveDaysForecastData.getList();
        City city = fiveDaysForecastData.getCity();

        for (com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.List listReference : lists) {
            CityForecastFiveDaysPojo cityForecastFiveDaysPojo = new CityForecastFiveDaysPojo();

            //city name
            cityForecastFiveDaysPojo.setCityName(city.getName());

            //day and month
            cityForecastFiveDaysPojo.setDayMonthReference(Utils.extractDateInfo(listReference.getDtTxt(), false));

            //hour
            cityForecastFiveDaysPojo.setHourReference(Utils.extractDateInfo(listReference.getDtTxt(), true));

            //Max Temp
            cityForecastFiveDaysPojo.setMaxTemp(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTempMax())));

            //Min Temp
            cityForecastFiveDaysPojo.setMinTemp(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTempMin())));


            //Temperature
            cityForecastFiveDaysPojo.setTemperature(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTemp())));

            //Wind Direction
            cityForecastFiveDaysPojo.setWindDirection(listReference.getWind().getDeg());

            //Wind Speed
            cityForecastFiveDaysPojo.setWindSpeed(Double.toString(listReference.getWind().getSpeed()));

            //Icon
            com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.Weather weather = listReference.getWeather().size() > 0 ? listReference.getWeather().get(0) : null;
            String icon = weather != null ? weather.getIcon() : "";
            cityForecastFiveDaysPojo.setIcon(icon);

            //Message
            String message = weather != null ? weather.getDescription() : "";
            cityForecastFiveDaysPojo.setMessage(Utils.capitalizeFirstWord(message));

            //latitude
            cityForecastFiveDaysPojo.setLat(Double.toString(city.getCoord().getLat()));

            //longitude
            cityForecastFiveDaysPojo.setLon(Double.toString(city.getCoord().getLon()));

            fiveDaysForecastPojo.addCityForecastFiveDaysPojo(cityForecastFiveDaysPojo);
        }

        return fiveDaysForecastPojo;
    }

    public static java.util.List<CityForecastPojo> parseCurrentWeatherDataToCityForecastPojoList(CurrentWeatherData currentWeatherData) {

        java.util.List<CityForecastPojo> cityForecastPojoList = new ArrayList<>();

        java.util.List<com.wlmtest.forecasttestjava.model.api.currentweatherdata.List> lists = currentWeatherData.getList();

        for (com.wlmtest.forecasttestjava.model.api.currentweatherdata.List listReference : lists) {
            CityForecastPojo cityForecastPojo = new CityForecastPojo();

            //city id
            cityForecastPojo.setCityId(String.valueOf(listReference.getId()));

            //city name
            cityForecastPojo.setCityName(listReference.getName());

            //country
            cityForecastPojo.setCountry(listReference.getSys().getCountry());

            //date
            String strDate = Utils.formatDate(new Date(), null);
            cityForecastPojo.setDateReference(strDate);

            //Max Temp
            cityForecastPojo.setMaxTemp(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTempMax())));

            //Min Temp
            cityForecastPojo.setMinTemp(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTempMin())));


            //Temperature
            cityForecastPojo.setTemperature(Utils.removeInfoAfterPoint(Double.toString(listReference.getMain().getTemp())));

            //Wind Direction
            cityForecastPojo.setWindDirection(listReference.getWind().getDeg());

            //Wind Speed
            cityForecastPojo.setWindSpeed(Double.toString(listReference.getWind().getSpeed()));

            //Icon
            Weather weather = listReference.getWeather().size() > 0 ? listReference.getWeather().get(0) : null;
            String icon = weather != null ? weather.getIcon() : "";
            cityForecastPojo.setIcon(icon);

            //Message
            String message = weather != null ? weather.getDescription() : "";
            cityForecastPojo.setMessage(Utils.capitalizeFirstWord(message));

            //latitude
            cityForecastPojo.setLat(Double.toString(listReference.getCoord().getLat()));

            //longitude
            cityForecastPojo.setLon(Double.toString(listReference.getCoord().getLon()));

            cityForecastPojoList.add(cityForecastPojo);
        }

        return cityForecastPojoList;

    }
}
