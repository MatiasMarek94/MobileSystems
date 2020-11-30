package com.example.kayjaklog.location

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient

class LocationChangeWrapper : ILocationObserver {
    private val locationChangeObservers: ArrayList<ILocationChangeObserver> = ArrayList()
    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationWrapper: LocationWrapper? = null
    private var lastLocationSensorEvent: LocationSensorEvent? = null

    fun setup(locationClient: FusedLocationProviderClient) {
        fusedLocationClient = locationClient
        locationWrapper = LocationWrapperSingleton.getInstance()
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocation() {
        if(locationWrapper != null) {
            locationWrapper!!.addObserver(this)
            locationWrapper!!.startListening()
        }
    }

    private fun updateObservers(event: LocationSensorEvent) {
        for(observer in locationChangeObservers) {
            var runnable = UpdateObserversRunnable(observer, event)
            mainHandler.post(runnable)
        }
    }

    fun addObserver(newObserver: ILocationChangeObserver) {
        locationChangeObservers.add(newObserver);
    }

    fun removeObserver(observer: ILocationChangeObserver) {
        locationChangeObservers.remove(observer);
    }
    class UpdateObserversRunnable(private val observer: ILocationChangeObserver, private val event: LocationSensorEvent) : Runnable {
        override fun run() {
            observer.onLocationChange(event);
        }
    }

    override fun onLocationUpdate(event: LocationSensorEvent) {
        if(lastLocationSensorEvent != null && (event.lat == lastLocationSensorEvent!!.lat && event.lng == lastLocationSensorEvent!!.lng)) {
            return
        }
        lastLocationSensorEvent = event
        if(locationWrapper != null) {
            locationWrapper!!.removeObserver(this)
        }
        updateObservers(event)

//        if(locationWrapper != null) {
//            locationWrapper!!.removeObserver(this)
//        }
    }
}