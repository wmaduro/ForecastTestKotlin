package com.wlmtest.forecasttestjava.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wlmtest.forecasttestjava.repository.ForecastTestKotlinRepository

class MainViewModelFactory(private val forecastTestKotlinRepository: ForecastTestKotlinRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(forecastTestKotlinRepository) as T
    }
}
