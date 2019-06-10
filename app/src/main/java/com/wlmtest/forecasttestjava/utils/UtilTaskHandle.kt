package com.wlmtest.forecasttestjava.utils

import android.os.Handler

import java.util.Timer
import java.util.TimerTask

/**
 * Tools' class
 */
object UtilTaskHandle {

    interface TaskHandle {
        fun invalidate()
    }

    /**
     * Create a timeout thread.
     *
     * @param r
     * @param delay
     * @return
     */
    fun setTimeout(r: Runnable, delay: Long): TaskHandle {
        val h = Handler()
        h.postDelayed(r, delay)
        return object : TaskHandle {
            override fun invalidate() {
                h.removeCallbacks(r)
            }
        }
    }

    /**
     * Create a loop timeout thread.
     *
     * @param r
     * @param interval
     * @return
     */
    fun setInterval(r: Runnable, interval: Long): TaskHandle {
        val t = Timer()
        val h = Handler()
        t.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                h.post(r)
            }
        }, interval, interval)  // Unlike JavaScript, in Java the initial call is immediate, so we put interval instead.
        return object : TaskHandle {
            override fun invalidate() {
                t.cancel()
                t.purge()
            }
        }
    }
}