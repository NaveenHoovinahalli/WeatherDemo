package com.example.weatherdemo.ui.component

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherdemo.MainActivity
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION
import com.example.weatherdemo.utils.extention.getLastLocation
import com.google.android.gms.location.FusedLocationProviderClient

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


@Composable
fun LocationMainContent(
    context: MainActivity,
    dialogShowCount1: Boolean,
    fusedLocationClient: FusedLocationProviderClient,
    callback: (Location) -> Unit
) {
    var permissionDenied by remember { mutableStateOf(false) }

    var dialogShowCount by remember { mutableStateOf(false) }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation(fusedLocationClient, callback)
        } else {
            permissionDenied = true
        }
    }

    val permissionState = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Request permission if not already granted
    if (permissionState != PackageManager.PERMISSION_GRANTED) {
        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    } else {
        // Permission is granted; get the location
        getLastLocation(fusedLocationClient, callback)
    }

    Column {
        Button(onClick = {
            if (permissionState == PERMISSION_GRANTED) {
                getLastLocation(fusedLocationClient , callback)
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }) {
            Text("Get Location")
        }

        if (permissionDenied && !dialogShowCount) {
            LocationPermissionDialog(
                onOkayClick = {
                    dialogShowCount = !dialogShowCount
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    permissionDenied = false
                },
                onCancelClick = {
                    dialogShowCount = !dialogShowCount
                    permissionDenied = false
                }
            )
        }
    }
}