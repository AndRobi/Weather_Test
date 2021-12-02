package com.fb.weathertest.ui.week

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fb.weathertest.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "WeekForecastFragment"
@AndroidEntryPoint
class WeekForecastFragment : Fragment() {

    val viewModel: WeekForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWeatherForecast()
        lifecycleScope.launch {
            viewModel.forecast.collect {
                it?.let {
                    Log.d(TAG, "onCreate: ${it.timeStamp }}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_week_forcast, container, false)
    }
}
