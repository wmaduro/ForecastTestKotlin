package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class List {

    @Json(name = "id")
    var id: Int? = null
    @Json(name = "name")
    var name: String? = null
    @Json(name = "coord")
    var coord: Coord? = null
    @Json(name = "main")
    var main: Main? = null
    @Json(name = "dt")
    var dt: Int? = null
    @Json(name = "wind")
    var wind: Wind? = null
    @Json(name = "sys")
    var sys: Sys? = null
    @Json(name = "rain")
    var rain: Any? = null
    @Json(name = "snow")
    var snow: Any? = null
    @Json(name = "clouds")
    var clouds: Clouds? = null
    @Json(name = "weather")
    var weather: java.util.List<Weather>? = null

}
