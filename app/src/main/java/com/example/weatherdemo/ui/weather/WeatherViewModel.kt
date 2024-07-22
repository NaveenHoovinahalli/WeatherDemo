package com.example.weatherdemo.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdemo.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.weatherdemo.utils.Result


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {



    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()


    fun fetchWeather(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getWeather(latitude, longitude, apiKey).map { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.value = WeatherUiState(response = result.data)
                        }
                        is Result.Loading -> {

                        }
                        is Result.Error -> {

                        }
                    }
                }

                result.collect {
                }

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
