package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class List {

    @Json(name = "dt")
    var dt: Int? = null
    @Json(name = "main")
    var main: Main? = null
    @Json(name = "weather")
    var weather: java.util.List<Weather>? = null
    @Json(name = "clouds")
    var clouds: Clouds? = null
    @Json(name = "wind")
    var wind: Wind? = null
    @Json(name = "rain")
    var rain: Rain? = null
    @Json(name = "sys")
    var sys: Sys? = null
    @Json(name = "dt_txt")
    lateinit var dtTxt: String

}
