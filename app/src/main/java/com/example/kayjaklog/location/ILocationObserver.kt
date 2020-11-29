package com.example.kayjaklog.location

interface ILocationObserver {
    fun onLocationUpdate(event: LocationSensorEvent)
}