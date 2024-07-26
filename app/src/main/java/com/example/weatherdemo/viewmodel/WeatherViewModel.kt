package com.example.weatherdemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.data.db.WeatherDatabase
import com.example.weatherdemo.data.repository.WeatherRepository
import com.example.weatherdemo.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.*

/**
 * ViewModel for getting response and to handle other logic
 * */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherDao: WeatherDao
) :
    ViewModel() {


    @Inject
    lateinit var database: WeatherDatabase

    private val _uiState: MutableStateFlow<WeatherUiStates> =
        MutableStateFlow(WeatherUiStates.Launch)
    val uiState: StateFlow<WeatherUiStates> = _uiState.asStateFlow()


    /**
    * Get the weather response from the API with Lat and Long
    * */
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

    /**
     * Get the weather response from the DB
     * */
    fun fetchWeatherFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherDao.readAllDate().collect {
                    _uiState.value = WeatherUiStates.SuccessFromDB(response = it[0])
                }


            } catch (e: Exception) {
                // Handle error
                _uiState.value = WeatherUiStates.Default

            }
        }
    }

    /**
     * Insert the weather date to the DB
     * TODO : User proper DB sequence
     * */
    fun insertWeatherToDb(response: WeatherResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                response.let {
                    database.clearAllTables()
                    weatherDao.insertWeather(response)
                }

            } catch (e: Exception) {
            }
        }
    }
}
