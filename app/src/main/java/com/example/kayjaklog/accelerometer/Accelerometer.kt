package com.example.kayjaklog.accelerometer

import android.os.Handler
import android.os.Looper
import kotlin.collections.ArrayList

class Accelerometer {

    private var observers: ArrayList<IAccelerometerObserver> = ArrayList();
    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var currentMockDataIteration: Int = 0;

    // https://stackoverflow.com/questions/55570990/kotlin-call-a-function-every-second
    private val fetchIntervalMockData = object : Runnable {
        override fun run() {
            listenToTimer()
            mainHandler.postDelayed(this, 5)
        }
    }

    fun startTimer() {
        mainHandler.post(fetchIntervalMockData)
    }

    fun pauseTimer() {
        mainHandler.removeCallbacks(fetchIntervalMockData)
    }

    private fun listenToTimer() {
        var i = currentMockDataIteration
        var currentEvent = AccelerometerSensorEvent(
            AccelerometerMockData.timeStamp[i],
            AccelerometerMockData.xData[i],
            AccelerometerMockData.yData[i],
            AccelerometerMockData.zData[i]
        )
        updateObservers(currentEvent)
        i++;
    }

    private fun listenToSensorManager() {
        TODO()
    }

    private fun updateObservers(event: AccelerometerSensorEvent) {
        for(observer in observers) {
            var runnable = UpdateObserversRunnable(observer, event)
            mainHandler.post(runnable)
        }
    }

    fun addObserver(newObserver: IAccelerometerObserver) {
        observers.add(newObserver);
    }

    fun removeObserver(observer: IAccelerometerObserver) {
        observers.remove(observer);
    }

    // https://stackoverflow.com/questions/9123272/is-there-a-way-to-pass-parameters-to-a-runnable
    class UpdateObserversRunnable(private val observer: IAccelerometerObserver, private val event: AccelerometerSensorEvent) : Runnable {
        override fun run() {
            observer.onSensorChange(event);
        }
    }

}