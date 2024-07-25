package com.example.weatherdemo

import android.content.Context
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
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.ui.component.*
import com.example.weatherdemo.viewmodel.WeatherViewModel
import com.example.weatherdemo.utils.constants.AppConstants.API_KEY
import com.example.weatherdemo.utils.constants.AppConstants.APP_TOP_BAR_TITLE
import com.example.weatherdemo.utils.extention.isInternetAvailable
import com.example.weatherdemo.viewmodel.WeatherUiStates


@Composable
fun WeatherScreen(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
    isDarkTheme: MutableState<Boolean>,
    location: CurrentLocation
) {

    var state: WeatherUiStates by remember { mutableStateOf(WeatherUiStates.Launch) }
    var isLocationUpdated by remember {
        mutableStateOf(false)
    }

    isLocationUpdated = location.isValueAvailable

    if (isLocationUpdated) {
        if (isInternetAvailable(context) && location.isValueAvailable) {
            viewModel.fetchWeather(location.lat, location.lon, API_KEY)
        } else {
            viewModel.fetchWeatherFromDb()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect {
            when (it) {
                is WeatherUiStates.Success -> {
                    state = WeatherUiStates.Success(it.response)
                    viewModel.insertWeatherToDb(it.response)
                }
                is WeatherUiStates.SuccessFromDB -> {
                    state = WeatherUiStates.SuccessFromDB(it.response)
                }
                is WeatherUiStates.Error -> {
                    state = WeatherUiStates.Error(it.error)
                }
                is WeatherUiStates.Loading -> {
                    state = WeatherUiStates.Loading()
                }
                is WeatherUiStates.Default -> {
                    state = WeatherUiStates.Default
                }
                is WeatherUiStates.Launch -> {
                    state = WeatherUiStates.Launch
                } else ->{
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
                WeatherScreenContent(uiState = state, isDarkTheme = isDarkTheme)

            }
        },
    )


}

@Composable
fun SkyBackground(isDarkTheme: MutableState<Boolean>) {
    var topColor: Color
    var bottomColor: Color
    if (!isDarkTheme.value) {
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
        is WeatherUiStates.SuccessFromDB -> {
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
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 5))
            }
        }
        is WeatherUiStates.Launch -> {

        }
    }
}
