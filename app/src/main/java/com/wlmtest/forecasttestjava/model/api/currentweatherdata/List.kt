package com.wlmtest.forecasttestjava.model.api.currentweatherdata

import com.squareup.moshi.Json

class List {


    var id: Int? = null
    var name: String? = null
    var coord: Coord? = null
    lateinit var main: Main
    var dt: Int? = null
    lateinit var wind: Wind
    var sys: Sys? = null
    var rain: Any? = null
    var snow: Any? = null
    var clouds: Clouds? = null
    var weather: java.util.List<Weather>? = null

}
