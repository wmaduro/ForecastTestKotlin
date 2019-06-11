package com.wlmtest.forecasttestjava.repository.openweathermap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.wlmtest.forecasttestjava.base.ForecastTestJavaApplication;
import com.wlmtest.forecasttestjava.base.NetworkConnectionInterceptor;
import com.wlmtest.forecasttestjava.base.events.InternetDisconnectedEvent;
import okhttp3.OkHttpClient;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.util.concurrent.TimeUnit;


public class OpenWeatherMapApi {


    private String BASE_URL = "http://api.openweathermap.org";


    private static OpenWeatherMapApi instance;


    public static OpenWeatherMapApi getInstance() {
        if (instance == null) {
            synchronized (OpenWeatherMapApi.class) {
                if (instance == null)
                    instance = new OpenWeatherMapApi();
            }
        }
        return instance;
    }

    private OpenWeatherMapApi() {
    }

    /**
     * Get the API's service
     * @return
     */
    public OpenWeatherMapInterface getOpenWeatherMapInterface() {

        OpenWeatherMapInterface openWeatherMapInterface =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
//                        .client(this.provideOkHttpClient())
                        .addConverterFactory(MoshiConverterFactory
                                .create())
                        .build()
                        .create(OpenWeatherMapInterface.class);

        return openWeatherMapInterface;
    }

    /**
     * Check if internet is available. It is used to prevent requests when internet is down.
     *
     * @return
     */
    private boolean isInternetAvailable() {
        if (ForecastTestJavaApplication.getContext()== null){
            return true;
        }

        ConnectivityManager connectivityManager =
                (ConnectivityManager) ForecastTestJavaApplication.getContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    /**
     * Set OkHttpclient and the interceptor for internet unavailable.
     *
     * @return
     */
    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

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
        return okhttpClientBuilder.build();
    }


}
