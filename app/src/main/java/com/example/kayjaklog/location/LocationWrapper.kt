package com.example.kayjaklog.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class LocationWrapper {
    private val observers: ArrayList<ILocationObserver> = ArrayList()
    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var isListening: Boolean = false

    fun setup(locationClient: FusedLocationProviderClient) {
        fusedLocationClient = locationClient
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    fun startListening() {
        if(isListening) {
            return
        }
        if(fusedLocationClient != null) {
            fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            isListening = true
        }
    }

    fun stopListening() {
        if(!isListening) {
            return
        }
        if(observers.isEmpty()) {
            if(fusedLocationClient != null) {
                fusedLocationClient!!.removeLocationUpdates(locationCallback)
                isListening = false
            }
        }
    }

    private fun locationToEvent(location: Location?): LocationSensorEvent? {
        if(location != null) {
            return LocationSensorEvent(
                (location.time / 1000).toDouble(),
                location.latitude,
                location.longitude,
                location.accuracy
            )
        }
        return null
    }
    private fun updateObservers(event: LocationSensorEvent) {
        for(observer in observers) {
            var runnable = UpdateObserversRunnable(observer, event)
            mainHandler.post(runnable)
        }
    }

    fun addObserver(newObserver: ILocationObserver) {
        observers.add(newObserver);
    }

    fun removeObserver(observer: ILocationObserver) {
        observers.remove(observer);
        if(observers.isEmpty()) {
            stopListening()
        }
    }
    class UpdateObserversRunnable(private val observer: ILocationObserver, private val event: LocationSensorEvent) : Runnable {
        override fun run() {
            observer.onLocationUpdate(event);
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                locationToEvent(location)?.let { updateObservers(it) }
            }
        }
    }
}