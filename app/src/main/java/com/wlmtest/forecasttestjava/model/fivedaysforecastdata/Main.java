
package com.wlmtest.forecasttestjava.model.fivedaysforecastdata;

import com.squareup.moshi.Json;

public class Main {

    @Json(name = "temp")
    private Double temp;
    @Json(name = "temp_min")
    private Double tempMin;
    @Json(name = "temp_max")
    private Double tempMax;
    @Json(name = "pressure")
    private Double pressure;
    @Json(name = "sea_level")
    private Double seaLevel;
    @Json(name = "grnd_level")
    private Double grndLevel;
    @Json(name = "humidity")
    private Integer humidity;
    @Json(name = "temp_kf")
    private Double tempKf;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempKf() {
        return tempKf;
    }

    public void setTempKf(Double tempKf) {
        this.tempKf = tempKf;
    }

}
