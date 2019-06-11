package com.wlmtest.forecasttestjava.view.forecast


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.FragmentForecastBinding
import com.wlmtest.forecasttestjava.view.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class ForecastFragment : Fragment() {


    private var fragmentForecastBinding: FragmentForecastBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentForecastBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forecast,
            container,
            false
        )

        return fragmentForecastBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityForecastPojo = (activity as MainActivity).mainViewModel.cityForecastPojoSelected.value

        fragmentForecastBinding!!.cityForecastPojoSelected = cityForecastPojo

        val forecastTabAdapter = ForecastTabAdapter((activity as MainActivity), activity!!.supportFragmentManager)

        val viewPager = activity!!.findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = forecastTabAdapter

        val tabs = activity!!.findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }


}
