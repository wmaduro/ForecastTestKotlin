package com.wlmtest.forecasttestjava.base

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ForecastTestJavaApplication : Application(), Application.ActivityLifecycleCallbacks {

    var currentActivity: ActivityBase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    init {
        registerActivityLifecycleCallbacks(this)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        this.currentActivity = activity as ActivityBase

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    companion object {
        @JvmStatic
        var context: ForecastTestJavaApplication? = null
            private set
    }
}
