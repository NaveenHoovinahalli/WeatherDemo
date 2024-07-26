package com.example.weatherdemo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.ui.component.LocationMainContent
import com.example.weatherdemo.ui.component.WeatherScreen
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import com.example.weatherdemo.utils.extention.isDayTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var dialogShowCount: Boolean = false

    private var location by mutableStateOf(CurrentLocation(false, false, 0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            var isDarkTheme = remember { mutableStateOf(false) }
            LocationMainContent(this, dialogShowCount, fusedLocationClient) { loc ->
                location = CurrentLocation(true, true, loc.latitude, loc.longitude)
            }
            LaunchedEffect(key1 = true) {
                isDarkTheme.value = !isDayTime()
            }

            WeatherDemoTheme(isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    WeatherScreen(
                        context = this,
                        isDarkTheme = isDarkTheme,
                        location = location
                    )
                }
            }
        }

    }


}



