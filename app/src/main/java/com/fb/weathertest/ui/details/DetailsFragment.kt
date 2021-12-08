package com.fb.weathertest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fb.weathertest.R
import com.fb.weathertest.databinding.FragmentDetailsBinding
import com.fb.weathertest.util.WEATHER_IMG_LINK
import com.fb.weathertest.util.timeOfDay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "DetailsFragment"

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dayMS = args.dt

        lifecycleScope.launch {
            viewModel.forecast.collect {
                it?.let {
                    with(binding) {
                        Glide.with(root)
                            .load(WEATHER_IMG_LINK + it.weather[0].icon + ".png")
                            .into(weatherIcon)
                        weatherDescription.text = it.weather[0].description
                        sunrise.text = getString(R.string.sunrise) + it.sunrise.timeOfDay()
                        sunset.text = getString(R.string.sunset) + it.sunset.timeOfDay()
                        moonrise.text = getString(R.string.moonrise) + it.moonrise.timeOfDay()
                        dailyMax.text = getString(R.string.daily_max) + it.temp.max.toString()
                        dailyMin.text = getString(R.string.daily_min) + it.temp.min.toString()
                        pressure.text = getString(R.string.pressure) + it.pressure.toString()
                        humidity.text = getString(R.string.humidity) + it.humidity.toString()
                    }
                }
            }
        }
    }
}
