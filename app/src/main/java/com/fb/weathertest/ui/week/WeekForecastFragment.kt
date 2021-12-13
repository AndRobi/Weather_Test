package com.fb.weathertest.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fb.weathertest.adapter.WeatherForecastAdapter
import com.fb.weathertest.databinding.FragmentWeekForcastBinding
import com.fb.weathertest.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@Suppress("UnusedPrivateMember")
private const val TAG = "WeekForecastFragment"

@AndroidEntryPoint
class WeekForecastFragment : Fragment() {

    private val viewModel: WeekForecastViewModel by viewModels()
    private val weatherAdapter = WeatherForecastAdapter()

    private val locationLiveData by lazy { (activity as MainActivity).locationLiveData }
    lateinit var binding: FragmentWeekForcastBinding

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
        weatherAdapter.setOnItemClicked {
            day ->
            locationLiveData.value?.let {
                viewModel.getWeatherForecast(it, true)
                goToDetails(day)
            }
        }
        binding.refreshLayout.apply {
            setOnRefreshListener {
                locationLiveData.value?.let {
                    viewModel.getWeatherForecast(it)
                }
                if (locationLiveData.value != null) {
                    this.isRefreshing = false
                }
            }
        }
        locationLiveData.observe(viewLifecycleOwner, {
            viewModel.getWeatherForecast(it)
        })
        lifecycleScope.launch {
            viewModel.forecast.collect {
                it?.let {
                    weatherAdapter.submitList(it.daily)
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }
    private fun goToDetails(day: Long) {
        val action = WeekForecastFragmentDirections.actionWeekForecastFragmentToDetailsFragment(day)
        findNavController().navigate(action)
    }
}
