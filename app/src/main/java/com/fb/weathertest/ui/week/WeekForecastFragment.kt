package com.fb.weathertest.ui.week

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fb.weathertest.adapter.WeatherForecastAdapter
import com.fb.weathertest.databinding.FragmentWeekForcastBinding
import com.fb.weathertest.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "WeekForecastFragment"
@AndroidEntryPoint
class WeekForecastFragment : Fragment() {

    val viewModel: WeekForecastViewModel by viewModels()
    val weatherAdapter = WeatherForecastAdapter()

    lateinit var binding: FragmentWeekForcastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekForcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.weatherRecyle.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)
        }
        (activity as MainActivity).locationLiveData.observe(viewLifecycleOwner, {
            viewModel.getWeatherForecast(it)
        })
        lifecycleScope.launch {
            viewModel.forecast.collect {
                it?.let {
                    weatherAdapter.submitList(it.daily)
                    for (day in it.daily) {
                        Log.d(TAG, "temperature : ${day.temp.day}}")
                    }
                }
            }
        }
        // val action = WeekForecastFragmentDirections.actionWeekForecastFragmentToDetailsFragment("asd")
        // findNavController().navigate(action)
    }
}
