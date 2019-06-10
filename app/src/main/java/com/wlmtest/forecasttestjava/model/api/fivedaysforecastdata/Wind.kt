package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class Wind {

    @Json(name = "speed")
    var speed: Double? = null
    @Json(name = "deg")
    var deg: Double? = null

}
