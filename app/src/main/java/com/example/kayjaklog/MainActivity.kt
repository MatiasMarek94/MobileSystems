package com.example.kayjaklog

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kayjaklog.location.*
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(), SensorEventListener, ILocationChangeObserver, ILocationObserver {

    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)


        var locationWrapper = LocationWrapperSingleton.getInstance()
        locationWrapper.setup(LocationServices.getFusedLocationProviderClient(this))

        var locationChangeWrapper = LocationChangeWrapperSingleton.getInstance()
        locationChangeWrapper.setup(LocationServices.getFusedLocationProviderClient(this))
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()

        //locationWrapper.addObserver(this)
        //locationWrapper.startListening()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onLocationChange(event: LocationSensorEvent) {
        println("Last location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onLocationUpdate(event: LocationSensorEvent) {
        println("Listening location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // event.sensor.getType() because multiple types.
        //Test
        //TEst
    }


}