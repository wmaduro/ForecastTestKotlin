package com.wlmtest.forecasttestjava.model.api.fivedaysforecastdata

import com.squareup.moshi.Json

class City {

    @Json(name = "id")
    var id: Int? = null
    @Json(name = "name")
    var name: String? = null
    @Json(name = "coord")
    var coord: Coord? = null
    @Json(name = "country")
    var country: String? = null
    @Json(name = "timezone")
    var timezone: Int? = null

}
