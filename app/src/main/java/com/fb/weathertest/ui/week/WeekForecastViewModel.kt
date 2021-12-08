package com.fb.weathertest.ui.week

import androidx.lifecycle.viewModelScope
import com.fb.weathertest.data.model.location.Cord
import com.fb.weathertest.data.remote.WeatherRepository
import com.fb.weathertest.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeekForecastViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository
) : BaseViewModel() {

    val forecast = weatherRepo.forecast.stateIn(viewModelScope, SharingStarted.Lazily, null)
    fun getWeatherForecast(cord: Cord, forceRefresh: Boolean = false) {
        viewModelScope.launch(coroutineContext) {
            weatherRepo.getAllWeatherData(cord, forceRefresh)
        }
    }
}
