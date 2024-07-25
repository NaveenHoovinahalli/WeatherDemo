package com.example.weatherdemo.model

data class CurrentLocation(val isValueAvailable: Boolean,val isLocationUpdated: Boolean=false, val lat : Double, val lon : Double)