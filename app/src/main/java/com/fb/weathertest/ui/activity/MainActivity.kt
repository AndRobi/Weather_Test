package com.fb.weathertest.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.fb.weathertest.R
import com.fb.weathertest.databinding.ActivityMainBinding
import com.fb.weathertest.util.location.LocationLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("UnusedPrivateMember")
private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var locationLiveData: LocationLiveData
    val navController: NavController by lazy {
        findNavController(R.id.fragment)
    }
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
