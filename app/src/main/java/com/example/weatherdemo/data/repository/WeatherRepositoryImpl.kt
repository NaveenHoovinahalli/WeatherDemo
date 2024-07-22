package com.example.weatherdemo.data.repository


import com.example.weatherdemo.data.network.WeatherApiService
import com.example.weatherdemo.model.WeatherResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.weatherdemo.utils.Result
import com.example.weatherdemo.utils.Result.*


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : WeatherRepository {

    override fun getWeather(lat: Double, lang: Double, key: String): Flow<Result<WeatherResponse>> = flow {
        emit(Loading)
        try {
            val result = weatherApi.getWeather(lat , lang, key)
            emit(Success(result))
        } catch (exception: HttpException) {
            emit(Error(exception.message.orEmpty()))
        } catch (exception: IOException) {
            emit(Error("Please check your network connection and try again!"))
        }
    }.flowOn(dispatcher)
}