package com.example.kayjaklog.application

import android.content.Context
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateDatabase
import com.example.kayjaklog.data.CoordinateRepository
import com.example.kayjaklog.distancecalculator.DistanceCalculator
import com.example.kayjaklog.distancecalculator.DistanceCalculatorSingleton
import com.example.kayjaklog.distancecalculator.DistanceThresholdExceedEvent
import com.example.kayjaklog.distancecalculator.IDistanceCalculatorObserver
import com.example.kayjaklog.location.ILocationChangeObserver
import com.example.kayjaklog.location.LocationChangeWrapper
import com.example.kayjaklog.location.LocationChangeWrapperSingleton
import com.example.kayjaklog.location.LocationSensorEvent
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImprovedTrip(context: Context?) : IDistanceCalculatorObserver, ILocationChangeObserver {
    private val locationChangeWrapper: LocationChangeWrapper = LocationChangeWrapperSingleton.getInstance()
    private val distanceCalculator: DistanceCalculator = DistanceCalculatorSingleton.getInstance()
    private val repository: CoordinateRepository
    private val backendWebservice: BackendWebservice = BackendWebserviceSingleton.getInstance()

    var tripStatus: TripStatus = TripStatus.Created
    val tripId: Int = 1
    var backendTripId = -1
    var finalCoordinates: ArrayList<Coordinate>? = null

    init {
        val coordinateDao = CoordinateDatabase.getDatabase(context!!).coordinateDao()
        repository = CoordinateRepository(coordinateDao)
    }

    fun start() {
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()
        distanceCalculator.addObserver(this)
        tripStatus = TripStatus.Started
    }

    fun stop() {
        println("Stopping ImprovedTrip")
        distanceCalculator.removeObserver(this)
        locationChangeWrapper.removeObserver(this)
        tripStatus = TripStatus.Stopped
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            finalCoordinates = ArrayList(repository.getAllWithTripId(tripId))
            backendWebservice.createTrip(tripCreateCallBack)
            tripStatus = TripStatus.TripSubmitted
        }
    }

    override fun onThresholdExceeded(event: DistanceThresholdExceedEvent) {
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()
    }

    override fun onLocationChange(event: LocationSensorEvent) {
        locationChangeWrapper.removeObserver(this)
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
            println("Improved trip done!")
        }
    }
}