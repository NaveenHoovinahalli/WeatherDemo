package com.example.weatherdemo.utils.extention

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherdemo.model.CurrentLocation
import com.example.weatherdemo.utils.constants.AppConstants.REQUEST_LOCATION_PERMISSION
import com.google.android.gms.location.FusedLocationProviderClient


fun Activity.requestLocationPermissions(fusedLocationClient :FusedLocationProviderClient, callBack: (CurrentLocation) -> Unit) {
    when {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            // You can proceed with location operations
            this.getLastLocation(fusedLocationClient, callBack)

        }
        else -> {
            // You need to request permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }
}

fun Context.getLastLocation(fusedLocationClient : FusedLocationProviderClient, callBack: (CurrentLocation) -> Unit){
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    callBack (CurrentLocation(true,true,location.latitude, location.longitude ))
                    // Handle location found
                    // Here you can update UI or save location details
                    // For example, update a text or trigger another action
                } else {
                    // Location is null, handle the case where the location is not available

                }
            }
            .addOnFailureListener { exception ->

                // Handle failure to get location
                // This could be due to permissions or other errors

            }
    } else {
    }
}





