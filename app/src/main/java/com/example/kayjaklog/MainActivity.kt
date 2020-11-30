package com.example.kayjaklog

import android.content.Context
import android.content.res.AssetManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kayjaklog.location.*
import com.google.android.gms.location.LocationServices
import com.example.kayjaklog.accelerometer.*
import com.example.kayjaklog.distancecalculator.DistanceCalculator
import com.example.kayjaklog.distancecalculator.DistanceCalculatorSingleton
import com.example.kayjaklog.distancecalculator.DistanceThresholdExceedEvent
import com.example.kayjaklog.distancecalculator.IDistanceCalculatorObserver
import java.nio.file.FileSystems

class MainActivity : AppCompatActivity(), IAccelerometerObserver, IDistanceCalculatorObserver, ILocationChangeObserver, ILocationObserver {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AccelerometerMockData.loadFiles(assets)
        val accelerometer = AccelerometerSingleton.getInstance()
        accelerometer.addObserver(this)
        val distanceCalculator = DistanceCalculatorSingleton.getInstance()
        distanceCalculator.addObserver(this)
//        accelerometer.startTimer()
        accelerometer.startListeningToSensorManager(getSystemService(Context.SENSOR_SERVICE) as SensorManager)


        var locationWrapper = LocationWrapperSingleton.getInstance()
        locationWrapper.setup(LocationServices.getFusedLocationProviderClient(this))

        var locationChangeWrapper = LocationChangeWrapperSingleton.getInstance()
        locationChangeWrapper.setup(LocationServices.getFusedLocationProviderClient(this))
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()

        //locationWrapper.addObserver(this)
        //locationWrapper.startListening()

    }

    override fun onLocationChange(event: LocationSensorEvent) {
        println("Last location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onLocationUpdate(event: LocationSensorEvent) {
        println("Listening location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onSensorChange(event: AccelerometerSensorEvent) {
        //println("Current sensor event (${event.timestamp}): ${event.x}; ${event.y}; ${event.z}. Accuracy: ${event.accuracy}")
    }

    override fun onThresholdExceeded(event: DistanceThresholdExceedEvent) {
        println("Current distance threshold exceeded: ${event.lastTimestamp}; ${event.distance}")
    }


}