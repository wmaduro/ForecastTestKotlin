package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class List {


    var dt: Int? = null

    lateinit var main: Main

    var weather: java.util.List<Weather>? = null

    var clouds: Clouds? = null

    var wind: Wind? = null

    var rain: Rain? = null

    var sys: Sys? = null

    lateinit var dt_txt: String

}
