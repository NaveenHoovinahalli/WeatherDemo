package com.example.weatherdemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import com.example.weatherdemo.ui.weather.WeatherUiState
import com.example.weatherdemo.ui.weather.WeatherUiStates
import com.example.weatherdemo.ui.weather.WeatherViewModel
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION
import com.example.weatherdemo.utils.extention.getLastLocation
import com.example.weatherdemo.utils.extention.requestLocationPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
   // private val viewModel: WeatherViewModel by viewModels()


    private val viewModel: WeatherViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    WeatherScreen()
                }
            }
        }

        val apiKey = "a9d7877ecf28b142eab78b39d9e14c03"
        val latitude = 12.954037
        val longitude = 77.680389


        viewModel.fetchWeather(latitude, longitude, apiKey)

         lifecycle.coroutineScope.launchWhenCreated {
             viewModel.uiState.collect {uiState ->
                 Log.d("NaveenTest",":Response ;>:"+uiState.response)

             }
         }


        this.requestLocationPermissions()


    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with location operations
                 this.getLastLocation(fusedLocationClient = fusedLocationClient) {

                 }
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                Log.d("NaveenTest", " Dont have the permission")

            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherDemoTheme {
        Greeting("Android")
    }
}

