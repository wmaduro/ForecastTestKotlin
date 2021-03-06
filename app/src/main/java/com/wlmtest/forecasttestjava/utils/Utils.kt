package com.wlmtest.forecasttestjava.utils

import android.content.Context
import android.net.ConnectivityManager
import com.wlmtest.forecasttestjava.base.ForecastTestKotlinApplication
import java.text.SimpleDateFormat
import java.util.Date

object Utils {
    @JvmField
    val UNIT_CELCIUS_DB = "metric"
    @JvmField
    val UNIT_FARENHITE_DB = "imperial"

    /**
     * Format miliseconds date.
     *
     * @param miliseconds
     * @param format
     * @return
     */
    @JvmStatic
    fun formatDateMiliseconds(miliseconds: Long, format: String?): String {
        var format = format
        if (format == null) {
            format = "dd/MM HH:mm:ss"
        }

        return SimpleDateFormat(format).format(Date(miliseconds))
    }

    /**
     * Set and format a default date
     *
     * @param date
     * @param format
     * @return
     */
    @JvmStatic
    fun formatDate(date: Date?, format: String?): String {
        return formatDateMiliseconds((date ?: Date()).time, format)
    }

    /**
     * Remove all information after a first point in a given string.
     *
     * @param string
     * @return
     */
    @JvmStatic
    fun removeInfoAfterPoint(value: Double?): String {
        if (value == null) {
            return ""
        }
        var string: String = value.toString();
        val posPoint = string.indexOf(".")
        if (posPoint > 0) {
            string = string.substring(0, posPoint)
        }

        return string
    }

    @JvmStatic
    fun capitalizeFirstWord(message: String): String {
        return message.substring(0, 1).toUpperCase() + message.substring(1)
    }


    /**
     * Extract date and hour information from date formated as YYYY/MM/DD  HH:MM:SS
     *
     * @param dateReference
     * @param hour
     * @return
     */

    @JvmStatic
    fun extractDateInfo(dateReference: String, hour: Boolean): String {

        var dayMonth: String

        if (hour) {
            dayMonth = dateReference.substring(11, 16)
        } else {
            dayMonth = dateReference.substring(8, 10) + "/" + dateReference.substring(5, 7)
        }

        return dayMonth
    }

    /**
     * Check if internet is available. It is used to prevent requests when internet is down.
     *
     * @return
     */
    fun isNetworkAvailable(): Boolean {

        if (ForecastTestKotlinApplication.context == null) {
            return true
        }

        val connectivityManager = ForecastTestKotlinApplication.context!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected

    }
}


