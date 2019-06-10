package com.wlmtest.forecasttestjava.model.pojo

class CityForecastPojo : ForecastGenericPojo() {
    var cityId: String? = null
    private var dateReference: String? = null

    val hourReference: String
        get() = dateReference!!.substring(6, 11)

    fun getDateReference(): String {
        return dateReference!!.substring(0, 5)
    }

    fun setDateReference(dateReference: String) {
        this.dateReference = dateReference
    }


}
