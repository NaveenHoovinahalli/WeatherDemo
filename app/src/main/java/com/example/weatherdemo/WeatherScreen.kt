package com.example.weatherdemo

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.ui.component.*
import com.example.weatherdemo.ui.weather.WeatherUiStates
import com.example.weatherdemo.ui.weather.WeatherViewModel
import com.example.weatherdemo.utils.constants.AppConstants.API_KEY
import com.example.weatherdemo.utils.constants.AppConstants.APP_TOP_BAR_TITLE
import com.example.weatherdemo.utils.extention.isInternetAvailable
import kotlinx.coroutines.CoroutineScope


@Composable
fun WeatherScreen(
    context: Context,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    viewModel: WeatherViewModel = hiltViewModel(),
    isDarkTheme: MutableState<Boolean>,
    weatherDao: WeatherDao,
    location: CurrentLocation
) {

    var state: WeatherUiStates by remember { mutableStateOf(WeatherUiStates.Launch) }

    if (isInternetAvailable(context) && location.isValueAvailable) {
        Log.d("NaveenTest", " Calling API::" + location.lat)
        viewModel.fetchWeather(location.lat, location.lon, API_KEY)

    } else if(false){
        //Fetch from the DB
    } else {
        state = WeatherUiStates.Default
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect {
            when (it) {
                is WeatherUiStates.Success -> {
                    state = WeatherUiStates.Success(it.response)
                    Log.d("NaveenTest", " Calling API Response::" + it.response)
                }
                is WeatherUiStates.Error -> {
                    state = WeatherUiStates.Error(it.error)
                }
                is WeatherUiStates.Loading -> {
                    state = WeatherUiStates.Loading()
                }
                is WeatherUiStates.Default -> {
                    state = WeatherUiStates.Default
                } is WeatherUiStates.Launch -> {
                    state = WeatherUiStates.Launch
                }
            }
        }
    }


    Scaffold(
        topBar =
        { TopBarApp(title = APP_TOP_BAR_TITLE, isDarkTheme) },
        content = { paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                SkyBackground(isDarkTheme)
                WeatherScreenContent(uiState = state, isDarkTheme =  isDarkTheme)

            }
        },
    )


}

@Composable
fun SkyBackground(isDarkTheme: MutableState<Boolean>) {
    var topColor : Color
    var bottomColor : Color
    if(!isDarkTheme.value) {
        topColor = Color(0xFF87CEEB) // Light blue
        bottomColor = Color(0xFF72A3CC) // Dark blue
    } else {
        topColor = Color(0xFF090808) // Light blue
        bottomColor = Color(0xFF041524) // Dark blue
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor),
                    startY = 0f,
                    endY = 800f // Adjust this value for the height of your gradient
                )
            )
    ) {
        // Content of your screen goes here
    }
}


@Composable
fun WeatherScreenContent(
    uiState: WeatherUiStates,
    modifier: Modifier = Modifier,
    isDarkTheme: MutableState<Boolean>
) {
    when (uiState) {
        is WeatherUiStates.Error -> {

        }
        is WeatherUiStates.Success -> {
            WeatherMainScreen(
                modifier = Modifier.wrapContentHeight(),
                uiState.response,
                isDarkTheme
            )
        }
        is WeatherUiStates.Default -> {
            WeatherErrorPage(modifier = Modifier.fillMaxSize())
        }
        is WeatherUiStates.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        } is WeatherUiStates.Launch -> {

        }
    }
}
