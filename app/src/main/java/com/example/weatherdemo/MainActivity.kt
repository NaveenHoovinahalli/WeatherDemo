package com.example.weatherdemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.coroutineScope
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.data.db.WeatherDatabase
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION
import com.example.weatherdemo.utils.extention.getLastLocation
import com.example.weatherdemo.utils.extention.isDayTime
import com.example.weatherdemo.utils.extention.requestLocationPermissions
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var weatherDao: WeatherDao
    var location by mutableStateOf(CurrentLocation(false, 0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // Initialize userDao
        val db = WeatherDatabase.getDatabase(applicationContext)
        weatherDao = db.weatherDao()
        // dbData()



        setContent {


            val systemUiController: SystemUiController = rememberSystemUiController()
//
//            systemUiController.isStatusBarVisible = false // Status bar
//
//            systemUiController.isSystemBarsVisible = false

//            val systemUiController = rememberSystemUiController()
//
//            // Example usage: Set status bar color
//            systemUiController.setStatusBarColor(color = R.color.purple_200)

//            DisposableEffect(key1 = true) {
//                systemUiController.isSystemBarsVisible = false
//                onDispose { systemUiController.isSystemBarsVisible = true }
//            }

            var isDarkTheme = remember { mutableStateOf(false) }
            var isDark = isSystemInDarkTheme()
            this.requestLocationPermissions(fusedLocationClient = fusedLocationClient) {
                location = CurrentLocation(true, it.lat, it.lon)
            }
            LaunchedEffect(key1 = true) {
               // isDarkTheme.value = isDark
                isDarkTheme.value = !isDayTime()
            }

            WeatherDemoTheme(isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    WeatherScreen(
                        context = this,
                        scope = lifecycle.coroutineScope,
                        isDarkTheme = isDarkTheme,
                        weatherDao = weatherDao,
                        location = location
                    )
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted, fetch location
                this.getLastLocation(fusedLocationClient = fusedLocationClient) {
                    location = CurrentLocation(true, it.lat, it.lon)
                }
            } else {
                // permission is denied
            }
        }
    }

}



