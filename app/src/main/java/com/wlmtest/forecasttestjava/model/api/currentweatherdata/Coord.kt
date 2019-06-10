package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class Coord {

    @Json(name = "lat")
    var lat: Double? = null
    @Json(name = "lon")
    var lon: Double? = null

}
