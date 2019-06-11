package com.wlmtest.forecasttestjava.repository

import com.wlmtest.forecasttestjava.model.api.currentweatherdata.CurrentWeatherData
import com.wlmtest.forecasttestjava.model.api.currentweatherdata.Weather
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.City
import com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata.FiveDaysForecastData
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.CityForecastFiveDaysPojo
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.FiveDaysForecastPojo
import com.wlmtest.forecasttestjava.utils.Utils

import java.util.ArrayList
import java.util.Date

object ApiDataParser {
    @JvmStatic
    fun parseFiveDaysForecastDataToFiveDaysForecastPojo(
        fiveDaysForecastData: FiveDaysForecastData?
    ): FiveDaysForecastPojo? {

        if (fiveDaysForecastData == null || fiveDaysForecastData.list == null) {
            return null
        }


        val fiveDaysForecastPojo = FiveDaysForecastPojo()

        val lists = fiveDaysForecastData.list
        val city = fiveDaysForecastData.city

        for (listReference in lists!!) {
            val cityForecastFiveDaysPojo = CityForecastFiveDaysPojo()

            //city name
            cityForecastFiveDaysPojo.cityName = city!!.name

            //day and month
            cityForecastFiveDaysPojo.dayMonthReference = Utils.extractDateInfo(listReference.dtTxt, false)

            //hour
            cityForecastFiveDaysPojo.hourReference = Utils.extractDateInfo(listReference.dtTxt, true)

            //Max Temp
            cityForecastFiveDaysPojo.maxTemp =
                Utils.removeInfoAfterPoint(listReference.main.tempMax)

            //Min Temp
            cityForecastFiveDaysPojo.minTemp =
                Utils.removeInfoAfterPoint(listReference.main.tempMin)


            //Temperature
            cityForecastFiveDaysPojo.temperature =
                Utils.removeInfoAfterPoint(listReference.main.temp)

            //Wind Direction
            cityForecastFiveDaysPojo.setWindDirection(listReference.wind!!.deg!!)

            //Wind Speed
            cityForecastFiveDaysPojo.windSpeed = java.lang.Double.toString(listReference.wind!!.speed!!)

            //Icon
            val weather = if (listReference.weather!!.size > 0) listReference.weather!!.get(0) else null
            val icon = if (weather != null) weather.icon else ""
            cityForecastFiveDaysPojo.icon = icon

            //Message
            val message = if (weather != null) weather.description else ""
            cityForecastFiveDaysPojo.message = Utils.capitalizeFirstWord(message!!)

            //latitude
            cityForecastFiveDaysPojo.lat = java.lang.Double.toString(city.coord!!.lat!!)

            //longitude
            cityForecastFiveDaysPojo.lon = java.lang.Double.toString(city.coord!!.lon!!)

            fiveDaysForecastPojo.addCityForecastFiveDaysPojo(cityForecastFiveDaysPojo)
        }

        return fiveDaysForecastPojo
    }

    @JvmStatic
    fun parseCurrentWeatherDataToCityForecastPojoList(currentWeatherData: CurrentWeatherData): List<CityForecastPojo> {

        val cityForecastPojoList = ArrayList<CityForecastPojo>()

        val lists = currentWeatherData.list

        for (listReference in lists!!) {
            val cityForecastPojo = CityForecastPojo()

            //city id
            cityForecastPojo.cityId = listReference.id.toString()

            //city name
            cityForecastPojo.cityName = listReference.name

            //country
            cityForecastPojo.country = listReference.sys!!.country

            //date
            val strDate = Utils.formatDate(Date(), null)
            cityForecastPojo.setDateReference(strDate)

            //Max Temp
            cityForecastPojo.maxTemp =
                Utils.removeInfoAfterPoint(listReference.main.temp_max)

            //Min Temp
            cityForecastPojo.minTemp =
                Utils.removeInfoAfterPoint(listReference.main.temp_min)


            //Temperature
            cityForecastPojo.temperature =
                Utils.removeInfoAfterPoint(listReference.main.temp)

            //Wind Direction
            cityForecastPojo.setWindDirection(listReference.wind!!.deg!!)

            //Wind Speed
            cityForecastPojo.windSpeed = java.lang.Double.toString(listReference.wind!!.speed!!)

            //Icon
            val weather = if (listReference.weather!!.size > 0) listReference.weather!!.get(0) else null
            val icon = if (weather != null) weather.icon else ""
            cityForecastPojo.icon = icon

            //Message
            val message = if (weather != null) weather.description else ""
            cityForecastPojo.message = Utils.capitalizeFirstWord(message!!)

            //latitude
            cityForecastPojo.lat = java.lang.Double.toString(listReference.coord!!.lat!!)

            //longitude
            cityForecastPojo.lon = java.lang.Double.toString(listReference.coord!!.lon!!)

            cityForecastPojoList.add(cityForecastPojo)
        }

        return cityForecastPojoList

    }
}
