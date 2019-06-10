package com.wlmtest.forecasttestjava.model.api.currentweatherdata;

import com.squareup.moshi.Json;

public class CurrentWeatherData {

    @Json(name = "message")
    private String message;
    @Json(name = "cod")
    private String cod;
    @Json(name = "count")
    private Integer count;
    @Json(name = "list")
    private java.util.List<com.wlmtest.forecasttestjava.model.api.currentweatherdata.List> list = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public java.util.List<com.wlmtest.forecasttestjava.model.api.currentweatherdata.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.wlmtest.forecasttestjava.model.api.currentweatherdata.List> list) {
        this.list = list;
    }

}
