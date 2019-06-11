package com.wlmtest.forecasttestjava.view.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.FragmentSearchResultBinding
import com.wlmtest.forecasttestjava.model.pojo.CityForecastPojo
import com.wlmtest.forecasttestjava.view.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class SearchResultFragment : Fragment() {

    private var fragmentSearchResultBinding: FragmentSearchResultBinding? = null
    private var cityForecastRecicleViewAdapter: CityForecastRecicleViewAdapter? = null
    private var rlMultipleCitiesFound: ViewGroup? = null
    private var rlMainLayout: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchResultBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_result,
            container,
            false
        )

        return fragmentSearchResultBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rlMultipleCitiesFound = activity!!.findViewById(R.id.rlMultipleCitiesFound)
        rlMainLayout = activity!!.findViewById(R.id.rlMainLayout)
        //init the with for the first page
        rlMultipleCitiesFound!!.visibility = View.GONE
        rlMainLayout!!.visibility = View.VISIBLE


        cityForecastRecicleViewAdapter = CityForecastRecicleViewAdapter((activity as MainActivity).mainViewModel)

        fragmentSearchResultBinding!!.cityForecastRecicleViewAdapter = cityForecastRecicleViewAdapter

        (activity!!.findViewById<View>(R.id.tvGeneralInfo) as TextView).setText(R.string.greeting_message)


        setObservers()
    }

    /**
     * Set all observers.
     */
    private fun setObservers() {

        (activity as MainActivity)
            .mainViewModel.cityForecastSearchListMutableLiveData
            .observe(this,
                Observer { cityForecastPojoList ->
                    if (cityForecastPojoList.size > 0) {

                        rlMultipleCitiesFound!!.visibility = View.VISIBLE
                        rlMainLayout!!.visibility = View.GONE

                    } else {

                        rlMultipleCitiesFound!!.visibility = View.GONE
                        rlMainLayout!!.visibility = View.VISIBLE

                        (activity!!.findViewById<View>(R.id.tvGeneralInfo) as TextView).setText(R.string.city_not_found)
                    }
                })

    }

}
