package com.example.kayjaklog

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.kayjaklog.accelerometer.AccelerometerMockData
import com.example.kayjaklog.accelerometer.AccelerometerSensorEvent
import com.example.kayjaklog.accelerometer.AccelerometerSingleton
import com.example.kayjaklog.accelerometer.IAccelerometerObserver
import com.example.kayjaklog.distancecalculator.DistanceCalculatorSingleton
import com.example.kayjaklog.distancecalculator.DistanceThresholdExceedEvent
import com.example.kayjaklog.distancecalculator.IDistanceCalculatorObserver
import com.example.kayjaklog.location.*
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.Webservice
import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiWebservice
import com.example.kayjaklog.webservice.onwaterapi.ReadResultModel
import com.google.android.gms.location.LocationServices

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
      //  setupActionBarWithNavController(findNavController(R.id.my_nav))
        //locationWrapper.addObserver(this)
        //locationWrapper.startListening()


        val webservice = Webservice(Volley.newRequestQueue(this))
        val backendWebservice = BackendWebservice()
        backendWebservice.setup(webservice, "http://10.0.2.2:5000/api/")
//        backendWebservice.createBulkCoordinates(ArrayList(), webserviceCallback)
//        backendWebservice.createTrip(webserviceCallback)

        val onWaterApiWebservice = OnWaterApiWebservice()
        onWaterApiWebservice.setup(webservice, "JSRTbxbsyy_6NXsT8Hsz")
//        onWaterApiWebservice.isOnWater("23.92323,-66.3", onWaterApiWebserviceCallback)


    }

    override fun onLocationChange(event: LocationSensorEvent) {
        // println("Last location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onLocationUpdate(event: LocationSensorEvent) {
        // println("Listening location: ${event.timestamp}; ${event.lat}; ${event.lng}")
    }

    override fun onSensorChange(event: AccelerometerSensorEvent) {
        //println("Current sensor event (${event.timestamp}): ${event.x}; ${event.y}; ${event.z}. Accuracy: ${event.accuracy}")
    }

    override fun onThresholdExceeded(event: DistanceThresholdExceedEvent) {
        // println("Current distance threshold exceeded: ${event.lastTimestamp}; ${event.distance}")
    }

    private val webserviceCallback = object : IWebserviceCallback {
        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
            println("Response received: ${webserviceResponse.responseCode}: ${webserviceResponse.responseString}")
        }
    }

    private val onWaterApiWebserviceCallback = object : IWebserviceCallback {
        override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
            println("On water response received: ${webserviceResponse.responseCode}: ${webserviceResponse.responseString}")
            val result = Klaxon().parse<ReadResultModel>(webserviceResponse.responseString)
            if (result != null) {
                println("On water: ${result.water}")
            }
        }
    }


}