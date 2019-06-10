package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class CurrentWeatherData {

    @Json(name = "message")
    var message: String? = null
    @Json(name = "cod")
    var cod: String? = null
    @Json(name = "count")
    var count: Int? = null
    @Json(name = "list")
    var list: java.util.List<com.wlmtest.forecasttestjava.model.api.currentweatherdata.List>? = null

}
