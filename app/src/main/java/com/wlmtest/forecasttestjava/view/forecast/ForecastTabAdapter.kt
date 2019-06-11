package com.wlmtest.forecasttestjava.view.forecast

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays.Forecast5DaysFragment
import com.wlmtest.forecasttestjava.view.forecast.tabs.today.ForecastTodayFragment

class ForecastTabAdapter(private val mContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> return ForecastTodayFragment()
            1 -> return Forecast5DaysFragment()
            else -> return null
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }
}