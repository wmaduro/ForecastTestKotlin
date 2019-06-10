package com.wlmtest.forecasttestjava.model.api.currentweatherdata;

import com.squareup.moshi.Json;

public class Weather {

    @Json(name = "id")
    private Integer id;
    @Json(name = "main")
    private String main;
    @Json(name = "description")
    private String description;
    @Json(name = "icon")
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
