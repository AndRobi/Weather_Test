package com.fb.weathertest.ui.splash

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fb.weathertest.R
import com.fb.weathertest.databinding.FragmentSplashBinding
import com.fb.weathertest.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.System.exit
@Suppress("UnusedPrivateMember")
private const val TAG = "SplashFragment"

@AndroidEntryPoint
class SplashFragment : Fragment() {
    companion object {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

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
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.location_permission)
                .setMessage(R.string.access_location_message)
                .setNegativeButton(R.string.exit) { id, dialog ->
                    finishAffinity(activity as MainActivity)
                }
                .setPositiveButton(R.string.retry) { id, dialog ->
                    requirePermission()
                }
                .show()
        }
    }

    private fun requirePermission() {
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
