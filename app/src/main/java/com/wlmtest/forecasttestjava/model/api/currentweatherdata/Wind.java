package com.wlmtest.forecasttestjava.model.api.currentweatherdata;

import com.squareup.moshi.Json;

public class Wind {

    @Json(name = "speed")
    private Double speed;
    @Json(name = "deg")
    private Double deg;
    @Json(name = "gust")
    private Double gust;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Double  getGust() {
        return gust;
    }

    public void setGust(Double  gust) {
        this.gust = gust;
    }

}
