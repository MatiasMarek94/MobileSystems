package com.example.kayjaklog.application

import android.content.Context
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateDatabase
import com.example.kayjaklog.data.CoordinateRepository
import com.example.kayjaklog.location.ILocationObserver
import com.example.kayjaklog.location.LocationSensorEvent
import com.example.kayjaklog.location.LocationWrapper
import com.example.kayjaklog.location.LocationWrapperSingleton
import kotlinx.coroutines.*

class StaticTrip(context: Context?) : ILocationObserver {
    val tripId: Int = 0
    val locationWrapper: LocationWrapper = LocationWrapperSingleton.getInstance()
    private val repository: CoordinateRepository


    init {
        val coordinateDao = CoordinateDatabase.getDatabase(context!!).coordinateDao()
        repository = CoordinateRepository(coordinateDao)
    }

    fun start() {
        locationWrapper.addObserver(this)
        locationWrapper.startListening()
        println("Started static!")
    }

    fun stop() {
        locationWrapper.removeObserver(this)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val result = repository.getAllWithTripId(tripId)
            println("With trip id = 0: $result")
        }
    }

    override fun onLocationUpdate(event: LocationSensorEvent) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            repository.addCoordinate(Coordinate(
                0,
                event.timestamp,
                event.lat,
                event.lng,
                tripId
            ))
            println("Saved with trip id 0!")
        }
    }
}