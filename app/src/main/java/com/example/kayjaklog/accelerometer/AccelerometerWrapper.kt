package com.example.kayjaklog.accelerometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import kotlin.collections.ArrayList

class AccelerometerWrapper: SensorEventListener {

    private var observers: ArrayList<IAccelerometerObserver> = ArrayList();
    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var currentMockDataIteration: Int = 0;
    private var currentMillisDelay: Long = 5;
    private var currentAccuracy: Int = 0;
    private var sensorManager: SensorManager? = null;
    private val sensorType: Int = Sensor.TYPE_LINEAR_ACCELERATION

    // https://stackoverflow.com/questions/55570990/kotlin-call-a-function-every-second
    private val fetchIntervalMockData = object : Runnable {
        override fun run() {
            listenToTimer()
            mainHandler.postDelayed(this, currentMillisDelay)
        }
    }

    fun startTimer(currentMillisDelay: Long = 5) {
        this.currentMillisDelay = currentMillisDelay
        mainHandler.post(fetchIntervalMockData)
    }

    fun pauseTimer() {
        mainHandler.removeCallbacks(fetchIntervalMockData)
    }

    private fun listenToTimer() {
        var i = currentMockDataIteration
        var currentEvent = AccelerometerSensorEvent(
            AccelerometerMockData.timestamp[i],
            AccelerometerMockData.xData[i],
            AccelerometerMockData.yData[i],
            AccelerometerMockData.zData[i],
            currentAccuracy
        )
        updateObservers(currentEvent)
        currentMockDataIteration++;

        if(currentMockDataIteration >= AccelerometerMockData.timestamp.size) {
            pauseTimer()
        }
    }

    fun startListeningToSensorManager(sensorManager: SensorManager) {
        this.sensorManager = sensorManager
        if (this.sensorManager != null) {
            this.sensorManager!!.registerListener(
                this,
                this.sensorManager!!.getDefaultSensor(sensorType),
                SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    fun stopListeningToSensorManager() {
        if (this.sensorManager != null) {
            this.sensorManager!!.unregisterListener(
                this,
                this.sensorManager!!.getDefaultSensor(sensorType))
        }
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

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        currentAccuracy = accuracy
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null) {
            val newEvent = AccelerometerSensorEvent(
                (System.currentTimeMillis() / 1000).toDouble(),
                event.values[0].toDouble(),
                event.values[1].toDouble(),
                event.values[2].toDouble(),
                currentAccuracy)
            updateObservers(newEvent)
        }

    }

}