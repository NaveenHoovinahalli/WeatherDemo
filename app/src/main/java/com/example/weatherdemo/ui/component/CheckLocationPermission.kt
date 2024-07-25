package com.example.weatherdemo.ui.component

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION

@Composable
fun CheckLocationPermission() {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
 
    val permissionState = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
 
    if (permissionState != PERMISSION_GRANTED) {
        showDialog.value = true
    }
 
    if (showDialog.value) {
        TwoButtonAlert(
            onOkayClick = {
                // Request permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
                showDialog.value = false
            },
            onCancelClick = {
                showDialog.value = false
            }
        )
    }
}
