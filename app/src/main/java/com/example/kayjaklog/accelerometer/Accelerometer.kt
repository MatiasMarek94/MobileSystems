package com.example.kayjaklog.accelerometer

import android.hardware.SensorEvent
import android.os.Handler
import android.os.Looper
import java.util.*
import kotlin.collections.ArrayList

class Accelerometer {

    private var observers: ArrayList<IAccelerometerObserver> = ArrayList();
    private var mainHandler: Handler = Handler(Looper.getMainLooper())

    // https://stackoverflow.com/questions/55570990/kotlin-call-a-function-every-second
    private val updateTextTask = object : Runnable {
        override fun run() {
            listenToTimer()
            mainHandler.postDelayed(this, 5)
        }
    }

    fun startTimer() {

    }

    fun pauseTimer() {

    }

    private fun listenToTimer() {

    }

    private fun listenToSensorManager() {
        TODO()
    }

    private fun updateObservers(event: SensorEvent) {
        for(observer in observers) {
            observer.onSensorChange(event);
        }
    }

    fun addObserver(newObserver: IAccelerometerObserver) {
        observers.add(newObserver);
    }

    fun removeObserver(observer: IAccelerometerObserver) {
        observers.remove(observer);
    }

}