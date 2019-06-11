package com.wlmtest.forecasttestjava.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.CityForecastItemBinding
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel


class CityForecastRecicleViewAdapter(private val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<CityForecastRecicleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cityForecastItemBinding = DataBindingUtil.inflate<CityForecastItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.city_forecast_item, parent, false
        )

        cityForecastItemBinding.mainViewModel = this.mainViewModel

        return ViewHolder(cityForecastItemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cityForecastItemBinding.cityForecastPojo =
            this.mainViewModel.cityForecastSearchList.value!![position]

        try {
            val iconRef = this.mainViewModel.cityForecastSearchList.value!![position].icon

            val ivForecastItem = holder.cityForecastItemBinding.ivForecastItem
            if (iconRef != null && !iconRef.isEmpty()) {
                Picasso.get()
                    .load("http://openweathermap.org/img/w/$iconRef.png")
                    .into(ivForecastItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return if (this.mainViewModel.cityForecastSearchList == null || this.mainViewModel.cityForecastSearchList.value == null) {
            0
        } else this.mainViewModel.cityForecastSearchList.value!!.size
    }

    class ViewHolder(var cityForecastItemBinding: CityForecastItemBinding) :
        RecyclerView.ViewHolder(cityForecastItemBinding.root)
}
