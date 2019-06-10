package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class FiveDaysForecastData {

    @Json(name = "cod")
    var cod: String? = null
    @Json(name = "message")
    var message: Double? = null
    @Json(name = "cnt")
    var cnt: Int? = null
    @Json(name = "list")
    var list: java.util.List<List>? = null
    @Json(name = "city")
    var city: City? = null

}
