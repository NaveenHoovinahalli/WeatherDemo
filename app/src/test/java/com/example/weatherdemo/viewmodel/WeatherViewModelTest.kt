package com.example.weatherdemo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.data.db.WeatherDatabase
import com.example.weatherdemo.data.repository.WeatherRepository
import com.example.weatherdemo.di.DatabaseModule
import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.Result
import com.example.weatherdemo.utils.repository.createFakeWeatherResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import javax.inject.Inject


@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    private val repository: WeatherRepository = mock(WeatherRepository::class.java)
    private val weatherDao: WeatherDao = mock(WeatherDao::class.java)
    private val database: WeatherDatabase = mock(WeatherDatabase::class.java)



    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository, weatherDao)
    }

    @Test
    fun `fetchWeather should emit Success state when repository returns Success`() = runTest {
        val weatherResponse = createFakeWeatherResponse() // Initialize with mock data
        `when`(repository.getWeather(10.0, 20.0, "api_key")).thenReturn(flowOf(Result.Success(weatherResponse)))

        viewModel.fetchWeather(10.0, 20.0, "api_key")

        val job = launch {
            viewModel.uiState.toList().let { states ->
                assertTrue(states.any { it is WeatherUiStates.Loading })
                assertTrue(states.any { it is WeatherUiStates.Success })
                assertEquals((states.last() as WeatherUiStates.Success).response, weatherResponse)
            }
        }

        job.cancel()
    }

    @Test
    fun `fetchWeather should emit Error state when repository returns Error`() = runTest {
        val errorMessage = "Error fetching weather"
        `when`(repository.getWeather(10.0, 20.0, "api_key")).thenReturn(flowOf(Result.Error(errorMessage)))

        viewModel.fetchWeather(10.0, 20.0, "api_key")

        val job = launch {
            viewModel.uiState.toList().let { states ->
                assertTrue(states.any { it is WeatherUiStates.Loading })
                assertTrue(states.any { it is WeatherUiStates.Error })
                assertEquals((states.last() as WeatherUiStates.Error).error, errorMessage)
            }
        }

        job.cancel()
    }

    @Test
    fun `fetchWeatherFromDb should emit SuccessFromDB state when data is present`() = runTest {
        val weatherResponse = listOf(createFakeWeatherResponse()) // Initialize with mock data
        `when`(weatherDao.readAllDate()).thenReturn(flowOf(weatherResponse))

        viewModel.fetchWeatherFromDb()

        val job = launch {
            viewModel.uiState.toList().let { states ->
                assertTrue(states.any { it is WeatherUiStates.SuccessFromDB })
                assertEquals((states.last() as WeatherUiStates.SuccessFromDB).response, weatherResponse[0])
            }
        }

        job.cancel()
    }

    @Test
    fun `fetchWeatherFromDb should emit Default state on exception`() = runTest {
        `when`(weatherDao.readAllDate()).thenReturn(emptyFlow())

        viewModel.fetchWeatherFromDb()

        val job = launch {
            viewModel.uiState.toList().let { states ->
                assertTrue(states.any { it is WeatherUiStates.Default })
            }
        }

        job.cancel()
    }

}
