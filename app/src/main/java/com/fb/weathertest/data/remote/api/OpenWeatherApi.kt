package com.fb.weathertest.data.remote.api

import com.fb.weathertest.BuildConfig
import com.fb.weathertest.data.model.onecall.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val DEFAULT_LAT = "47.45709761536959"
const val DEFAULT_LON = "19.143297539877146"

interface OpenWeatherApi {
    @GET("onecall")
    suspend fun getAllWeather(
        @Query("lat")latitude: String = DEFAULT_LAT,
        @Query("lon")longitude: String = DEFAULT_LON,
        @Query("appid")apiKey: String = BuildConfig.WEATHER_API_KEY
    ): OneCallResponse
}
