package com.wlmtest.forecasttestjava

import android.os.Bundle
import com.wlmtest.forecasttestjava.base.ActivityBase

class MainActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showInternetUnavailableMessage();
    }
}
