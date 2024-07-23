package com.example.weatherdemo

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.example.weatherdemo.ui.theme.WeatherDemoTheme
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION
import com.example.weatherdemo.utils.extention.getLastLocation
import com.example.weatherdemo.utils.extention.requestLocationPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationClient: FusedLocationProviderClient
   // private val viewModel: WeatherViewModel by viewModels()
     var firstTime = true

    //private val viewModel: WeatherViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            var isDarkTheme = remember { mutableStateOf(false) }
            var isDark = isSystemInDarkTheme()
            SideEffect {
                Log.d("NaveenTest","Restart")
                // Example of some side effect
                if(firstTime) {
                    isDarkTheme.value = isDark
                    firstTime = false
                }
            }

            WeatherDemoTheme(isDarkTheme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

//                    TopBarApp("Title")
                   // Greeting("Android")

                    WeatherScreen(context = this,scope = lifecycle.coroutineScope, isDarkTheme =  isDarkTheme)
                }
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
    WeatherDemoTheme(false) {
        Greeting("Android")
    }
}



@Composable
fun TopBarApp(title: String, isDarkTheme: MutableState<Boolean>) {

    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {


            var buttonTheme by remember {
                mutableStateOf(isDarkTheme.value)
            }

// RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {
                isDarkTheme.value = !isDarkTheme.value
                buttonTheme = ! buttonTheme
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    if(buttonTheme){
                        Icon(Icons.Filled.Search, contentDescription = null)
                        Text(text = "Dark")
                    }else{
                        Icon(Icons.Default.Search, contentDescription = null)
                        Text(text = "Light")
                    }

                }

            }
        },
    )
}

