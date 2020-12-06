/*
//package com.example.kayjaklog.application
//
//import android.content.Context
//import com.example.kayjaklog.data.Coordinate
//import com.example.kayjaklog.data.CoordinateDatabase
//import com.example.kayjaklog.data.CoordinateRepository
//import com.example.kayjaklog.location.ILocationChangeObserver
//import com.example.kayjaklog.location.LocationSensorEvent
//import com.example.kayjaklog.location.LocationWrapper
//import com.example.kayjaklog.location.LocationWrapperSingleton
//import com.example.kayjaklog.webservice.IWebserviceCallback
//import com.example.kayjaklog.webservice.WebserviceResponse
//import com.example.kayjaklog.webservice.backend.BackendWebservice
//import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
//
//class Trip(context: Context?): ILocationChangeObserver {
//
//    private var onWaterCounter: Int = 0
//    private var tripStarted = false
//
//
//    private val locationWrapper: LocationWrapper = LocationWrapperSingleton.getInstance()
//    private val repository: CoordinateRepository = TODO()
//    private val backendWebservice: BackendWebservice = BackendWebserviceSingleton.getInstance()
//
//
//    init {
//        val coordinateDao = CoordinateDatabase.getDatabase(context!!).coordinateDao()
//        repository = CoordinateRepository(coordinateDao)
//    }
//
//
//    override fun onLocationChange(event: LocationSensorEvent) {
//        var coordinate: Coordinate = Coordinate(0, event.timestamp, event.lat, event.lng, 0)
//        repository.getOnWaterStatus(coordinate, onWaterCallback )
//    }
//
//
//
//
//    fun start() {
//        locationWrapper.addObserver(this)
//        locationWrapper.startListening()
//    }
//
//    fun stop(){
//
//    }
//
//    private val tripCreateCallBack = object : IWebserviceCallback {
//        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
//            backendTripId = webserviceResponse.responseString.toInt()
//            backendWebservice.createBulkCoordinates(finalCoordinates!!, backendTripId, coordinatesCreateCallback)
//            tripStatus = TripStatus.CoordinatesSubmitted
//        }
//    }
//
//    private val onWaterCallback = object : IWebserviceCallback {
//        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
//            webserviceResponse.responseString
//
//        }
//    }
//
//
//
}*/
