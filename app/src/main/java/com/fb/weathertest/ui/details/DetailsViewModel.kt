package com.fb.weathertest.ui.details

import androidx.lifecycle.viewModelScope
import com.fb.weathertest.data.model.onecall.Daily
import com.fb.weathertest.data.model.onecall.OneCallResponse
import com.fb.weathertest.data.remote.WeatherRepository
import com.fb.weathertest.ui.common.BaseViewModel
import com.fb.weathertest.util.HOUR_TO_MILL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository
) : BaseViewModel() {
    var dayMS: Long = 0
    val forecast = weatherRepo.forecast.transform<OneCallResponse?, Daily?> {
        emit(
            it?.daily?.firstOrNull {
                val dif = (dayMS - it.dt).absoluteValue * 1000
                val resu = HOUR_TO_MILL > dif
                return@firstOrNull resu
            }
        )
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
