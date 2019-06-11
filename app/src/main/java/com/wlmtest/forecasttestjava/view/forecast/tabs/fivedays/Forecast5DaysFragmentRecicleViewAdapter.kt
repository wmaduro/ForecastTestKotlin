package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.Forecast5daysItemBinding
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel


class Forecast5DaysFragmentRecicleViewAdapter(private val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<Forecast5DaysFragmentRecicleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val forecast5daysItemBinding = DataBindingUtil.inflate<Forecast5daysItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.forecast5days_item, parent, false
        )

        return ViewHolder(forecast5daysItemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val forecast5DaysMap = this.mainViewModel.fiveDaysForecastPojo
            .value!!
            .forecast5DaysMap

        val dateReference = forecast5DaysMap.keys
            .toTypedArray()[position]
        holder.forecast5daysItemBinding.dateReference = dateReference

        val cityForecastFiveDaysPojoList = forecast5DaysMap[dateReference]
        val smallForecastItemAdapter = SmallForecastItemAdapter(cityForecastFiveDaysPojoList!!)
        holder.forecast5daysItemBinding.smallForecastItemAdapter = smallForecastItemAdapter

    }

    override fun getItemCount(): Int {
        return if (this.mainViewModel.fiveDaysForecastPojo == null || this.mainViewModel.fiveDaysForecastPojo.value == null) {
            0
        } else this.mainViewModel.fiveDaysForecastPojo.value!!.forecast5DaysMap.size
    }

    class ViewHolder(var forecast5daysItemBinding: Forecast5daysItemBinding) :
        RecyclerView.ViewHolder(forecast5daysItemBinding.root)
}
