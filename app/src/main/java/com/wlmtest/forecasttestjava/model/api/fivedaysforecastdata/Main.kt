package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class Main {

    @Json(name = "temp")
    var temp: Double? = null
    @Json(name = "temp_min")
    var tempMin: Double? = null
    @Json(name = "temp_max")
    var tempMax: Double? = null
    @Json(name = "pressure")
    var pressure: Double? = null
    @Json(name = "sea_level")
    var seaLevel: Double? = null
    @Json(name = "grnd_level")
    var grndLevel: Double? = null
    @Json(name = "humidity")
    var humidity: Int? = null
    @Json(name = "temp_kf")
    var tempKf: Double? = null

}
