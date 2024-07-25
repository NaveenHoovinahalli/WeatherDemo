package com.example.weatherdemo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherdemo.R

@Composable
fun TopBarApp(title: String, isDarkTheme: MutableState<Boolean>) {

    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {


            var buttonTheme by remember {
                mutableStateOf(isDarkTheme.value)
            }

// RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {
                isDarkTheme.value = !isDarkTheme.value
                buttonTheme = !buttonTheme
            }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    if (buttonTheme) {
                        Image(
                            painter = painterResource(id = R.drawable.dark_mode),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.day_sunny),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }

            }
        },
    )
}