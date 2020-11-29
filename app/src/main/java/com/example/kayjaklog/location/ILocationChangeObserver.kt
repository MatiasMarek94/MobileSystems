package com.example.kayjaklog.location

interface ILocationChangeObserver {
    fun onLocationChange(event: LocationSensorEvent)
}