package com.example.kayjaklog.application

import android.content.Context
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateDatabase
import com.example.kayjaklog.data.CoordinateRepository
import com.example.kayjaklog.location.ILocationObserver
import com.example.kayjaklog.location.LocationSensorEvent
import com.example.kayjaklog.location.LocationWrapper
import com.example.kayjaklog.location.LocationWrapperSingleton
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StaticTrip(context: Context?) : ILocationObserver {
    private val locationWrapper: LocationWrapper = LocationWrapperSingleton.getInstance()
    private val repository: CoordinateRepository
    private val backendWebservice: BackendWebservice = BackendWebserviceSingleton.getInstance()

    var tripStatus: TripStatus = TripStatus.Created
    val tripId: Int = 0
    var backendTripId = -1
    var finalCoordinates: ArrayList<Coordinate>? = null


    init {
        val coordinateDao = CoordinateDatabase.getDatabase(context!!).coordinateDao()
        repository = CoordinateRepository(coordinateDao)
    }

    fun start() {
        println("Static Trip start()")
        locationWrapper.addObserver(this)
        locationWrapper.startListening()
        tripStatus = TripStatus.Started
    }

    fun stop() {
        println("Stopping static trip!")
        locationWrapper.removeObserver(this)
        tripStatus = TripStatus.Stopped
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            finalCoordinates = ArrayList(repository.getAllWithTripId(tripId))
            backendWebservice.createTrip(tripCreateCallBack)
            tripStatus = TripStatus.TripSubmitted
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
        }
    }

    private val tripCreateCallBack = object : IWebserviceCallback {
        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
            backendTripId = webserviceResponse.responseString.toInt()
            backendWebservice.createBulkCoordinates(finalCoordinates!!, backendTripId, coordinatesCreateCallback)
            tripStatus = TripStatus.CoordinatesSubmitted
        }
    }

    private val coordinatesCreateCallback = object : IWebserviceCallback {
        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
            println("Server response: ${webserviceResponse.responseString}")
            tripStatus = TripStatus.Complete
            println("Static trip done!")
        }
    }
}