package com.example.weatherdemo


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.ui.component.TwoButtonAlert
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import com.example.weatherdemo.utils.extention.isDayTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var dialogShowCount: Boolean = false

    var location by mutableStateOf(CurrentLocation(false, false, 0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            var isDarkTheme = remember { mutableStateOf(false) }
            LocationMainContent()
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

    @Composable
    fun LocationMainContent() {
        var permissionDenied by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                 getLastLocation { loc ->
                    location = CurrentLocation(true, true, loc.latitude, loc.longitude)
                }
            } else {
                permissionDenied = true
            }
        }

        val permissionState = ContextCompat.checkSelfPermission(
            this@MainActivity,
            ACCESS_FINE_LOCATION
        )

        // Request permission if not already granted
        if (permissionState != PackageManager.PERMISSION_GRANTED) {
            LaunchedEffect(Unit) {
                permissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
        } else {
            // Permission is granted; get the location
            getLastLocation { loc ->
                location = CurrentLocation(true, true, loc.latitude, loc.longitude)
            }
        }

        Column {
            Button(onClick = {
                if (permissionState == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation { loc ->
                        location = CurrentLocation(true, true, loc.latitude, loc.longitude)
                    }
                } else {
                    permissionLauncher.launch(ACCESS_FINE_LOCATION)
                }
            }) {
                Text("Get Location")
            }

            if (permissionDenied && !dialogShowCount) {
                dialogShowCount = true
                LocationPermissionDialog(
                    onOkayClick = {
                        permissionLauncher.launch(ACCESS_FINE_LOCATION)
                        permissionDenied = false
                    },
                    onCancelClick = {
                        permissionDenied = false
                    }
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(callback: (android.location.Location) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: android.location.Location? ->
                location?.let {
                    callback(it)
                } ?: run {
                    Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @Composable
    fun LocationPermissionDialog(onOkayClick: () -> Unit, onCancelClick: () -> Unit) {
        TwoButtonAlert(onOkayClick = { onOkayClick() }, onCancelClick = {
            onCancelClick()
        })
    }


}



