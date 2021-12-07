package com.fb.weathertest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fb.weathertest.data.model.onecall.Daily
import com.fb.weathertest.databinding.WeatherItemBinding
import com.fb.weathertest.util.WEATHER_IMG_LINK
import com.fb.weathertest.util.nameOfDay

class WeatherForecastAdapter() : ListAdapter<Daily, WeatherForecastAdapter.WeatherViewHolder>(WeatherForecastDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WeatherItemBinding.inflate(layoutInflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val day = getItem(position)
        val url = WEATHER_IMG_LINK + day.weather[0].icon + ".png"
        holder.bind(day.dt.nameOfDay(), url)
    }

    inner class WeatherViewHolder(private val binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: String, url: String) {
            binding.dayName.text = day
            Glide.with(binding.root).load(url).into(binding.dayWeatherIcon)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    class WeatherForecastDiffCallBack : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(
            oldItem: Daily,
            newItem: Daily
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Daily,
            newItem: Daily
        ): Boolean {
            return oldItem == newItem
        }
    }
}
