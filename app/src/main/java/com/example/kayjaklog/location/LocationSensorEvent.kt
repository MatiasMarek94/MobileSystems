package com.example.kayjaklog.location

data class LocationSensorEvent(
    val timestamp: Double,
    val lat: Double,
    val lng: Double,
    val accuracy: Float
) {
}