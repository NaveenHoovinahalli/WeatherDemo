package com.example.weatherdemo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.R
import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.extention.*

@Composable
fun WeatherMainScreen(
    modifier: Modifier,
    response: WeatherResponse,
    isDarkTheme: MutableState<Boolean>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {


        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = response.name,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = response.dt.toUtcDateTimeString(),
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(4.dp))


        Image(painter = painterResource(
            getWeatherCondition(response.weather[0].description, !isDarkTheme.value)
        ), contentDescription = null,modifier = Modifier.size(70.dp))


        Text(
            text = stringResource(
                R.string.temperature_value_in_celsius,
                response.main.temp.toCelsius()
            ),
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
//            text = uiState.weather?.condition?.text.orEmpty(),
            text = response.weather[0].description.capitalize(),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(
                R.string.feels_like_temperature_in_celsius,
                response.main.feels_like.toCelsius()
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.wind_speed_label),
                weatherValue = meterPerSecondToKilometerPerHour(response.wind.speed).toString(),
                weatherUnit = stringResource(R.string.wind_speed_unit),
                iconId = R.drawable.ic_wind,
            )

            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.humidity_label),
                weatherValue = response.main.humidity.toString(),
                weatherUnit = stringResource(R.string.humidity_unit),
                iconId = R.drawable.ic_humidity,
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.sun_rise),
                weatherValue = "",
                weatherUnit = response.sys.sunrise.toUtcTimeString() + " AM",
                iconId = R.drawable.sun_rise,
            )
            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.sun_set),
                weatherValue = "",
                weatherUnit = response.sys.sunset.toUtcTimeString() + " PM",
                iconId = R.drawable.sun_set,
            )
        }
    }
}

@Composable
fun WeatherComponent(modifier: Modifier, weatherLabel: String, weatherValue: String, weatherUnit: String, iconId: Int) {
    ElevatedCard(
        modifier = modifier
            .padding(end = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = weatherLabel,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = Color.Black

            )
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )

            Text(
                text =  weatherValue,
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )

            Text(
                text =  weatherUnit,
                style = MaterialTheme.typography.body2,
                color = Color.Black

            )
        }
    }
}

