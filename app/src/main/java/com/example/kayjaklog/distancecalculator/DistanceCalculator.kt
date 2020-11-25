package com.example.kayjaklog.distancecalculator

import android.hardware.SensorEvent
import com.example.kayjaklog.accelerometer.AccelerometerSingleton
import com.example.kayjaklog.accelerometer.IAccelerometerObserver

class DistanceCalculator: IAccelerometerObserver {

    init {
        AccelerometerSingleton.getInstance().addObserver(this)
    }

    private var observers: ArrayList<IDistanceCalculatorObserver> = ArrayList();

    private fun updateObservers() {
        for(observer in observers) {
            observer.onThresholdExceeded();
        }
    }

    fun addObserver(newObserver: IDistanceCalculatorObserver) {
        observers.add(newObserver);
    }

    fun removeObserver(observer: IDistanceCalculatorObserver) {
        observers.remove(observer);
    }

    override fun onSensorChange(event: SensorEvent) {
        TODO("Not yet implemented")
    }

}