package com.fb.weathertest.data.remote

import com.fb.weathertest.data.model.onecall.OneCallResponse
import com.fb.weathertest.data.remote.api.OpenWeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class WeatherRepository(private val openWeatherApi: OpenWeatherApi) {

    private val _forecast = MutableStateFlow<OneCallResponse?>(null)
    val forecast: StateFlow<OneCallResponse?> = _forecast

    suspend fun getAllWeatherData(forceRefresh: Boolean = false): Boolean {
        return try {
            _forecast.emit(openWeatherApi.getAllWeather())
            false
        } catch (ex: Exception) {
            true
        }
    }
}
