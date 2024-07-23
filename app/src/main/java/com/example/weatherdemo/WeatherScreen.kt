package com.example.weatherdemo

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherdemo.ui.component.CircularProgressBar
import com.example.weatherdemo.ui.component.WeatherMainScreen
import com.example.weatherdemo.ui.weather.WeatherUiStates
import com.example.weatherdemo.ui.weather.WeatherViewModel
import com.example.weatherdemo.utils.extention.isInternetAvailable
import kotlinx.coroutines.CoroutineScope


@Composable
fun WeatherScreen(
    context : Context,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    viewModel: WeatherViewModel = hiltViewModel(),
    isDarkTheme: MutableState<Boolean>
) {

    var state : WeatherUiStates by remember {mutableStateOf(WeatherUiStates.Default)}



    val apiKey = "update API key"
    val latitude = 12.954037
    val longitude = 77.680389


     if(isInternetAvailable(context)) {
         viewModel.fetchWeather(latitude, longitude, apiKey)
     }

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect {
            when(it) {
                is WeatherUiStates.Success -> {
                    Log.d("NaveenTest" , "Response1 ::" +it.response)
                    it.response
                    state = WeatherUiStates.Success(it.response)

                }
                is WeatherUiStates.Error -> {
                    Log.d("NaveenTest" , "Error ::" +it.error)
                    state = WeatherUiStates.Error(it.error)
                }
                is WeatherUiStates.Loading -> {
                    state = WeatherUiStates.Loading()

                    Log.d("NaveenTest" , "Loading ::" )

                }
                is WeatherUiStates.Default -> {
                    state = WeatherUiStates.Default
                    Log.d("NaveenTest" , "Default ::" )
                }
            }
        }
    }


    Scaffold(
        topBar =
        {TopBarApp(title = "WeatherDemo", isDarkTheme)},
        content = { paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
//                Column() {
//                    TopBarApp(title = "dasfds")
                    WeatherScreenContent(uiState = state)
//                }

            }
        },
    )




}

@Composable
fun WeatherScreenContent(
    uiState: WeatherUiStates,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is WeatherUiStates.Error -> {
            
            
        }
        is WeatherUiStates.Success -> {
//            ErrorCard(
//                modifier = Modifier.fillMaxSize(),
//                errorTitle = "Error Title",
//                errorDescription = "Error Description",
//                errorButtonText = "OK",
//                onClick = {  },
//                cardModifier = Modifier
//                    .fillMaxWidth()
//                    .height(LocalConfiguration.current.screenHeightDp.dp / 4 + 48.dp)
//                    .padding(horizontal = 64.dp)
//            )
            WeatherMainScreen(
            modifier = Modifier.wrapContentHeight(),
                uiState.response
            )




           // Greeting(name = "Naveen Response" + uiState.response)
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
            }
        }
    }
}
