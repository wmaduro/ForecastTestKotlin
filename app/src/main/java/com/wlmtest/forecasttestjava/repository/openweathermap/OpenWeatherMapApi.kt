package com.wlmtest.forecasttestjava.repository.openweathermap

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.wlmtest.forecasttestjava.base.ForecastTestJavaApplication
import com.wlmtest.forecasttestjava.base.NetworkConnectionInterceptor
import com.wlmtest.forecasttestjava.base.events.InternetDisconnectedEvent
import okhttp3.OkHttpClient
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
    //                        .client(this.provideOkHttpClient())
    val openWeatherMapInterface: OpenWeatherMapInterface
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory
                    .create()
            )
            .build()
            .create(OpenWeatherMapInterface::class.java)

    /**
     * Check if internet is available. It is used to prevent requests when internet is down.
     *
     * @return
     */
    private val isInternetAvailable: Boolean
        get() {
            if (ForecastTestJavaApplication.context == null) {
                return true
            }

            val connectivityManager = ForecastTestJavaApplication.context!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            return activeNetworkInfo != null && activeNetworkInfo.isConnected

        }

    /**
     * Set OkHttpclient and the interceptor for internet unavailable.
     *
     * @return
     */
    private fun provideOkHttpClient(): OkHttpClient {
        val okhttpClientBuilder = OkHttpClient.Builder()
        okhttpClientBuilder.connectTimeout(5, TimeUnit.SECONDS)
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)

        //        okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
        //            @Override
        //            public boolean isInternetAvailable() {
        //
        //                boolean isInternetAvailable = OpenWeatherMapApi.this.isInternetAvailable();
        //                if (!isInternetAvailable) {
        //                    EventBus.getDefault().post(new InternetDisconnectedEvent());
        //                }
        //                return isInternetAvailable;
        //            }
        //
        //            @Override
        //            public void onInternetUnavailable() {
        //            }
        //
        //            @Override
        //            public void onCacheUnavailable() {
        //            }
        //        });
        //
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
