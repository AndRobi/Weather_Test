package com.fb.weathertest.ui.splash

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fb.weathertest.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SplashFragment"

@AndroidEntryPoint
class SplashFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        requirePermission()
    }

    private val permissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        val response = map.entries.first()
        val isGranted = response.value
        if (isGranted) { goToMain() } else {
            requirePermission()
        }
    }

    private fun requirePermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        permissionRequester.launch(permissions)
    }

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun goToMain() {
        val action = SplashFragmentDirections.actionSplashFragmentToWeekForecastFragment()
        findNavController().navigate(action)
    }
}
