package com.example.kayjaklog.distancecalculator

import android.os.Handler
import android.os.Looper
import com.example.kayjaklog.accelerometer.AccelerometerSensorEvent
import com.example.kayjaklog.accelerometer.AccelerometerSingleton
import com.example.kayjaklog.accelerometer.IAccelerometerObserver
import kotlin.math.pow
import kotlin.math.sqrt

class DistanceCalculator(private val threshold: Double): IAccelerometerObserver {

    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var lastSensorEvent: AccelerometerSensorEvent? = null;
    private var lastThresholdExceedEvent: DistanceThresholdExceedEvent? = null;
    private var currentDistance: Double = 0.0;

    init {
        AccelerometerSingleton.getInstance().addObserver(this)
    }

    private var observers: ArrayList<IDistanceCalculatorObserver> = ArrayList();

    private fun updateObservers(event: DistanceThresholdExceedEvent) {
        for(observer in observers) {
            val runnable = UpdateObserversRunnable(observer, event)
            mainHandler.post(runnable)
        }
    }

    fun addObserver(newObserver: IDistanceCalculatorObserver) {
        observers.add(newObserver);
    }

    fun removeObserver(observer: IDistanceCalculatorObserver) {
        observers.remove(observer);
    }

    override fun onSensorChange(event: AccelerometerSensorEvent) {
        currentDistance += accelerationDistance(event)
        if(currentDistance > threshold) {
            val newEvent = DistanceThresholdExceedEvent(lastSensorEvent!!.timestamp, currentDistance)
            updateObservers(newEvent)
            lastThresholdExceedEvent = newEvent
            currentDistance = 0.0
        }
    }

    private fun accelerationDistance(event: AccelerometerSensorEvent): Double {
        var distance = 0.0;
        if(lastSensorEvent != null) {
            if(lastSensorEvent!!.timestamp == event.timestamp) {
                val newEvent = AccelerometerSensorEvent(
                    lastSensorEvent!!.timestamp,
                    lastSensorEvent!!.x + event.x,
                    lastSensorEvent!!.y + event.y,
                    lastSensorEvent!!.z + event.z,
                    4
                )
                lastSensorEvent = newEvent
                return 0.0
            }
            //request values from TYPE_LINEAR_ACCELERATION
            val currAcc = doubleArrayOf(event.x, event.y, event.z)
            //passed time since last request
            val dt = event.timestamp - lastSensorEvent!!.timestamp
            //calculate amount of vector
            val amount = sqrt(
                currAcc[0].pow(2.0) + currAcc[1].pow(2.0) + currAcc[2].pow(2.0)
            )
            distance = amount * dt.pow(2.0);
            //println("Distance calculated: $distance, current distance: $currentDistance") // , current dt: $dt, last timestamp: ${lastSensorEvent!!.timestamp}, cur timestamp: ${event.timestamp}
        }

        lastSensorEvent = event;

        //calculate traveled distance
        return distance
    }

    // https://stackoverflow.com/questions/9123272/is-there-a-way-to-pass-parameters-to-a-runnable
    class UpdateObserversRunnable(private val observer: IDistanceCalculatorObserver, private val event: DistanceThresholdExceedEvent) : Runnable {
        override fun run() {
            observer.onThresholdExceeded(event);
        }
    }

}