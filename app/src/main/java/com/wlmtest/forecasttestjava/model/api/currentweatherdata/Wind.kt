package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class Wind {

    @Json(name = "speed")
    var speed: Double? = null
    @Json(name = "deg")
    var deg: Double? = null
    @Json(name = "gust")
    var gust: Double? = null

}
