package com.example.weatherdemo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.R
import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.extention.meterPerSecondToKilometerPerHour
import com.example.weatherdemo.utils.extention.toCelsius
import com.example.weatherdemo.utils.extention.toUtcDateTimeString
import com.example.weatherdemo.utils.extention.toUtcTimeString

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorTitle: String,
    errorDescription: String,
    errorButtonText: String,
    onClick: () -> Unit,
    cardModifier: Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Card(modifier = cardModifier) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colors.error),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            modifier = Modifier.size(64.dp),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Text(text = errorTitle, style = MaterialTheme.typography.h3)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center ,
                            text = errorDescription,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary
                        )
                        Button(
                            onClick = { onClick },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = errorButtonText.uppercase(),
                                style = MaterialTheme.typography.button
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun WeatherMainScreen(
    modifier: Modifier,
    response: WeatherResponse,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val uiState = null

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = response.name,
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = response.dt.toUtcDateTimeString(),
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )

//        AsyncImage(
//            modifier = Modifier.size(64.dp),
//            model = stringResource(
//                R.string.icon_image_url,
//                uiState.weather?.condition?.icon.orEmpty(),
//            ),
//            contentScale = ContentScale.FillBounds,
//            contentDescription = null,
//            error = painterResource(id = R.drawable.ic_placeholder),
//            placeholder = painterResource(id = R.drawable.ic_placeholder),
//        )
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
                "Feels like"
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(id = R.drawable.ic_sunrise), contentDescription = null,modifier = Modifier.size(45.dp))
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = response.sys.sunrise.toUtcTimeString(),
//                text = uiState.weather?.forecasts?.get(0)?.sunrise?.lowercase(Locale.US).orEmpty(),
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.ic_sunset), contentDescription = null, modifier = Modifier.size(45.dp))
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = response.sys.sunset.toUtcTimeString(),
//                text = uiState.weather?.forecasts?.get(0)?.sunset?.lowercase(Locale.US).orEmpty(),
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
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
                weatherLabel = stringResource(R.string.wind_speed_label),
                weatherValue = meterPerSecondToKilometerPerHour(response.wind.speed).toString(),
                weatherUnit = stringResource(R.string.wind_speed_unit),
                iconId = R.drawable.ic_wind,
            )

            WeatherComponent(
                modifier = Modifier.weight(1f),
                weatherLabel = stringResource(R.string.uv_index_label),
                weatherValue = "Naveen",
                weatherUnit = stringResource(R.string.uv_unit),
                iconId = R.drawable.ic_uv,
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
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            Text(
                text =  weatherValue,
                style = MaterialTheme.typography.body1,
            )

            Text(
                text =  weatherUnit,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

