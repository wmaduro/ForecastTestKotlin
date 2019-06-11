package com.wlmtest.forecasttestjava.view.forecast.tabs.today


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.FragmentForecastTodayBinding
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo
import com.wlmtest.forecasttestjava.view.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class ForecastTodayFragment : Fragment() {

    private var fragmentForecastTodayBinding: FragmentForecastTodayBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentForecastTodayBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forecast_today,
            container,
            false
        )

        return fragmentForecastTodayBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val cityForecastPojo = (activity as MainActivity).mainViewModel.cityForecastPojoSelectedMutableLiveData.value

        fragmentForecastTodayBinding!!.cityForecastPojoSelected = cityForecastPojo

        try {

            val iconRef = cityForecastPojo!!.icon
            if (iconRef != null && !iconRef.isEmpty()) {
                val ivForecastToday = fragmentForecastTodayBinding!!.ivForecastToday

                Picasso.get()
                    .load("http://openweathermap.org/img/w/$iconRef.png")
                    .into(ivForecastToday)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

}
