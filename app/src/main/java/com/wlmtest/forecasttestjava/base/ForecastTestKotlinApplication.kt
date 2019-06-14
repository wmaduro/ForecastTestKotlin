package com.wlmtest.forecasttestjava.base

import android.app.Application

class ForecastTestKotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var context: ForecastTestKotlinApplication
    }


}
