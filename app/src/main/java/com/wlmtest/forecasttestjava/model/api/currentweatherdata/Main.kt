package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class Main {

    @Json(name = "temp")
    var temp: Double? = null
    @Json(name = "pressure")
    var pressure: Double? = null
    @Json(name = "humidity")
    var humidity: Int? = null
    @Json(name = "temp_min")
    var tempMin: Double? = null
    @Json(name = "temp_max")
    var tempMax: Double? = null

}
