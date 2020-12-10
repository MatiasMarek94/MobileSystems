package com.example.kayjaklog.application

import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateDatabase
import com.example.kayjaklog.data.CoordinateRepository
import com.example.kayjaklog.data.IOnWaterServiceCallBack
import com.example.kayjaklog.location.*
import com.example.kayjaklog.webservice.onwaterapi.OnWaterResponse
import android.content.Context as Context


class Trip(context: Context?): ILocationChangeObserver {

    private var onWaterCounter: Int = 0
    private var onLandCounter: Int = 0
    private var startTripIndicator: Int = 3
    private var endTripIndicator: Int = 5
    private var tripStarted = false
    private var locationCounter: Int = 0
    private var askLimit = 0
    private var shouldAskForLocation: Boolean = true
    private var context: Context?
    private var staticTrip: StaticTrip? = null;
    private var improvedTrip: ImprovedTrip? = null
    private val locationChangeWrapper: LocationChangeWrapper = LocationChangeWrapperSingleton.getInstance()
    private val repository: CoordinateRepository



    init {
        val coordinateDao = CoordinateDatabase.getDatabase(context!!).coordinateDao()
        repository = CoordinateRepository(coordinateDao)
        this.context = context

    }


    override fun onLocationChange(event: LocationSensorEvent) {
        locationCounter++
        if (locationCounter >= askLimit) {
            locationCounter = 0
            shouldAskForLocation = true
        }

        if(shouldAskForLocation) {
            locationChangeWrapper.requestNewLocation()
            shouldAskForLocation = false
            var coordinate = Coordinate(0, event.timestamp, event.lat, event.lng, 0)
            repository.getOnWaterStatus(coordinate, onWaterCallback )
        }
    }


    fun startTrips() {
        println("Trip() -> startTrips")
        staticTrip = StaticTrip(this.context)
        staticTrip!!.start()
        improvedTrip = ImprovedTrip(this.context)
        improvedTrip!!.start()
    }

    fun stopTrips(){
        if (this.staticTrip != null) {
            this.staticTrip?.stop()
            this.staticTrip = null
        }
        if (this.improvedTrip != null){
            this.improvedTrip?.stop()
            this.improvedTrip = null
        }
        else
        {
            return
        }
    }

    fun start() {
        println("Trip() -> start()")
        locationChangeWrapper.addObserver(this)
    }
        private val onWaterCallback = object : IOnWaterServiceCallBack {
        override fun onWaterCallBack(Response: OnWaterResponse){

                    if (Response.onWater) {
                        onWaterCounter++
                        if (onWaterCounter >= startTripIndicator && !tripStarted){
                            tripStarted = true
                            askLimit + 5
                            startTrips()
                        }
                    }
                    else if (!Response.onWater && !tripStarted){
                        onWaterCounter == 0
                    }
                    else if (tripStarted && !Response.onWater){
                        onLandCounter++
                        if (onLandCounter >= endTripIndicator ) {
                            stopTrips()
                        }
                    }
                    else if (tripStarted && Response.onWater) {
                        onLandCounter = 0
                    }
        }
    }


}