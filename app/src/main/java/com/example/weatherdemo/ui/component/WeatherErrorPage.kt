package com.example.weatherdemo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.R

@Composable
fun WeatherErrorPage(
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(id = R.drawable.no_wifi),
            contentDescription = null,
            modifier = Modifier.size(65.dp)
        )
        Text(
            text = "Weather Unavailable",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "The weather app isn't connected to the internet or permission is not available. To view weather, enable permission and check the internet connection and try again",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
    }
}