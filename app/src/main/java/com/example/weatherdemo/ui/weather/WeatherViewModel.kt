package com.example.weatherdemo.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdemo.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.*


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiStates> =
        MutableStateFlow(WeatherUiStates.Launch)
    val uiState: StateFlow<WeatherUiStates> = _uiState.asStateFlow()


    fun fetchWeather(latitude: Double, longitude: Double, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val result1 = repository.getWeather(latitude, longitude, apiKey)
                result1.collect { it ->
                    when (it) {
                        is Result.Success -> {
                            _uiState.value = WeatherUiStates.Success(response = it.data)
                        }
                        is Result.Loading -> {
                            _uiState.value = WeatherUiStates.Loading()
                        }
                        is Result.Error -> {
                            _uiState.value = WeatherUiStates.Error(it.errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
