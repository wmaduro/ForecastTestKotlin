package com.wlmtest.forecasttestkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wlmtest.forecasttestkotlin.base.ActivityBase

class MainActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showInternetUnavailableMessage();
    }
}
