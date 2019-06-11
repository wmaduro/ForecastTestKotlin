package com.wlmtest.forecasttestjava.view.forecast.tabs.fivedays

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.databinding.SmallForecastItemBinding
import com.wlmtest.forecasttestjava.model.pojo.forecastfivedays.CityForecastFiveDaysPojo


class SmallForecastItemAdapter(private val cityForecastFiveDaysPojoList: List<CityForecastFiveDaysPojo>) :
    RecyclerView.Adapter<SmallForecastItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val smallForecastItemBinding = DataBindingUtil.inflate<SmallForecastItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.small_forecast_item, parent, false
        )

        return ViewHolder(smallForecastItemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cityForecastFiveDaysPojo = cityForecastFiveDaysPojoList
            .toTypedArray()[position]


        holder.smallForecastItemBinding.cityForecastFiveDaysPojo = cityForecastFiveDaysPojo

        try {

            val iconRef = cityForecastFiveDaysPojo.icon
            if (iconRef != null && !iconRef.isEmpty()) {
                val ivIcon = holder.smallForecastItemBinding.ivIcon
                Picasso.get()
                    .load("http://openweathermap.org/img/w/$iconRef.png")
                    .into(ivIcon)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {

        return cityForecastFiveDaysPojoList.size
    }

    class ViewHolder(var smallForecastItemBinding: SmallForecastItemBinding) :
        RecyclerView.ViewHolder(smallForecastItemBinding.root)
}
