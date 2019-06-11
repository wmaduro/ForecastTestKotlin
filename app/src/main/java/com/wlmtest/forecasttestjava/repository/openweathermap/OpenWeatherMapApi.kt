package com.wlmtest.forecasttestjava.repository.openweathermap

import android.util.Log
import com.wlmtest.forecasttestjava.base.events.InternetDisconnectedEvent
import com.wlmtest.forecasttestjava.base.events.ProgressDialogLoadingDataEvent
import com.wlmtest.forecasttestjava.utils.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class OpenWeatherMapApi private constructor() {


    private val BASE_URL = "http://api.openweathermap.org"

    /**
     * Get the API's service
     * @return
     */
    val openWeatherMapInterface: OpenWeatherMapInterface
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(this.provideOkHttpClient())
            .addConverterFactory(
                MoshiConverterFactory
                    .create()
            )
            .build()
            .create(OpenWeatherMapInterface::class.java)


    /**
     * Set OkHttpclient and the interceptor for internet unavailable.
     *
     * @return
     */
    private fun provideOkHttpClient(): OkHttpClient {

        val okhttpClientBuilder =
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                    NetworkAvailableInterceptor()
                )

        return okhttpClientBuilder.build()
    }

    companion object {

        private var instance: OpenWeatherMapApi = OpenWeatherMapApi()

        fun getInstance(): OpenWeatherMapApi {
            if (instance == null) {
                synchronized(OpenWeatherMapApi::class.java) {
                    if (instance == null)
                        instance = OpenWeatherMapApi()
                }
            }
            return instance
        }
    }
}

class NetworkAvailableInterceptor : Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        if (!Utils.isNetworkAvailable()) {
            //open dialog network unavailable
            EventBus.getDefault().post(InternetDisconnectedEvent())
        } else {
            // open progress dialog
            EventBus.getDefault().post(ProgressDialogLoadingDataEvent(true))
        }

        var response : Response = chain.proceed(request);

        /**
         * after getting the response, send the event to close the progress dialog
         */
        EventBus.getDefault().post(ProgressDialogLoadingDataEvent(false))

        return response
    }
}