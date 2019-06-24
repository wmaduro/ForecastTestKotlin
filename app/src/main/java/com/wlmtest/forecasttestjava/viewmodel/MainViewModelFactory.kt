package com.wlmtest.forecasttestjava.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wlmtest.forecasttestjava.repository.ForecastTestJavaRepository

class MainViewModelFactory(private val forecastTestJavaRepository: ForecastTestJavaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(forecastTestJavaRepository) as T
    }
}
