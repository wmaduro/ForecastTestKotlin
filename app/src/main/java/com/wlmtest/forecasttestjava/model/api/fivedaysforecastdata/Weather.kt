package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class Weather {

    @Json(name = "id")
    var id: Int? = null
    @Json(name = "main")
    var main: String? = null
    @Json(name = "description")
    var description: String? = null
    @Json(name = "icon")
    var icon: String? = null

}
