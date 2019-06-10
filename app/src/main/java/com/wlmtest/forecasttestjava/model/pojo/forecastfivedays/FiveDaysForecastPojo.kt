package com.wlmtest.forecasttestjava.model.pojo.forecastfivedays

import java.util.ArrayList
import java.util.LinkedHashMap

class FiveDaysForecastPojo {

    val forecast5DaysMap = LinkedHashMap<String, ArrayList<CityForecastFiveDaysPojo>>()

    fun addCityForecastFiveDaysPojo(cityForecastFiveDaysPojo: CityForecastFiveDaysPojo) {

        //date date
        if (cityForecastFiveDaysPojo.dayMonthReference == null ||
            cityForecastFiveDaysPojo.dayMonthReference!!.isEmpty()) {
            return
        }

        val dateReference:String = cityForecastFiveDaysPojo.dayMonthReference

        if (!forecast5DaysMap.containsKey(dateReference)) {
            forecast5DaysMap.put(dateReference,  ArrayList<CityForecastFiveDaysPojo>())
        }

        var cityForecastFiveDaysPojoList = forecast5DaysMap[dateReference]

        cityForecastFiveDaysPojoList!!.add(cityForecastFiveDaysPojo)
    }


}
