package com.wlmtest.forecasttestjava.model.api.currentweatherdata;

import com.squareup.moshi.Json;

public class Sys {

    @Json(name = "country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
