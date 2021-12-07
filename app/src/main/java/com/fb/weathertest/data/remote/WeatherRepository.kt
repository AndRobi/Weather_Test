package com.fb.weathertest.data.remote

import com.fb.weathertest.data.db.ForecastDatabase
import com.fb.weathertest.data.model.location.Cord
import com.fb.weathertest.data.remote.api.OpenWeatherApi
import com.fb.weathertest.util.HOUR_TO_MILL

class WeatherRepository(
    private val openWeatherApi: OpenWeatherApi,
    private val db: ForecastDatabase
) {

    val forecast = db.forecastDao().getForecast()

    suspend fun getAllWeatherData(cord: Cord, forceRefresh: Boolean = false) {
        val lastRefreshTime = db.forecastDao().getTimeStamp()
        if (forceRefresh || lastRefreshTime == null || (lastRefreshTime + HOUR_TO_MILL > System.currentTimeMillis())) {
            db.forecastDao().deleteForecast()
            db.forecastDao()
                .add(openWeatherApi.getAllWeather(cord.lat.toString(), cord.lon.toString()))
        }
    }
}
