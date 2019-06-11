package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.FragmentForecast5DaysBinding
import com.wlmtest.forecasttestjava.view.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class Forecast5DaysFragment : Fragment() {

    private var fragmentForecast5DaysBinding: FragmentForecast5DaysBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentForecast5DaysBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forecast5_days,
            container,
            false
        )


        return fragmentForecast5DaysBinding!!.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        (activity as MainActivity).mainViewModel.fiveDaysForecastPojo.observe(this, Observer {
            val forecast5DaysFragmentRecicleViewAdapter =
                Forecast5DaysFragmentRecicleViewAdapter((activity as MainActivity).mainViewModel)

            fragmentForecast5DaysBinding!!.forecast5DaysFragmentRecicleViewAdapter =
                forecast5DaysFragmentRecicleViewAdapter
        })

    }


}
