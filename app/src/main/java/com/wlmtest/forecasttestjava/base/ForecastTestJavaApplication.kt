package com.wlmtest.forecasttestjava.base

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ForecastTestJavaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @JvmStatic
        lateinit var context: ForecastTestJavaApplication
    }


}
