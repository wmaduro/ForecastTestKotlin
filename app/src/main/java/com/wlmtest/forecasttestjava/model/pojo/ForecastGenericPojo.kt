package com.wlmtest.forecasttestjava.model.pojo

abstract class ForecastGenericPojo {

    var cityName: String? = null
    var country: String? = null
    var temperature: String? = null
    var icon: String? = null
    var message: String? = null
    var lat: String? = null
    var lon: String? = null


    var maxTemp: String? = null
    var minTemp: String? = null

    private var windDirection: String? = null
    var windSpeed: String? = null

    val cityNameAndCountry: String
        get() = cityName!! + if (country!!.isEmpty()) "" else ", " + country!!

    /**
     * Convert wind direction information
     *
     * @param deg
     */
    fun setWindDirection(deg: Double) {

        if (deg >= 337.5 || deg < 22.5) {
            this.windDirection = ForecastGenericPojo.N
        } else if (deg >= 22.5 || deg < 67.5) {
            this.windDirection = ForecastGenericPojo.NE
        } else if (deg >= 67.5 || deg < 112.5) {
            this.windDirection = ForecastGenericPojo.E
        } else if (deg >= 112.5 || deg < 157.5) {
            this.windDirection = ForecastGenericPojo.SE
        } else if (deg >= 157.5 || deg < 202.5) {
            this.windDirection = ForecastGenericPojo.S
        } else if (deg >= 202.5 || deg < 247.5) {
            this.windDirection = ForecastGenericPojo.SW
        } else if (deg >= 247.5 || deg < 292.5) {
            this.windDirection = ForecastGenericPojo.W
        } else if (deg >= 292.5 || deg < 337.5) {
            this.windDirection = ForecastGenericPojo.NW
        } else {
            this.windDirection = ForecastGenericPojo.UNKNOWN
        }
    }

    fun getWindDirection(): String? {
        return windDirection
    }

    fun setWindDirection(windDirection: String) {
        this.windDirection = windDirection
    }

    companion object {

        var N = "N"
        var S = "N"
        var E = "E"
        var W = "W"
        var NW = "NW"
        var NE = "NE"
        var SW = "SW"
        var SE = "SE"
        var UNKNOWN = "?"
    }
}
