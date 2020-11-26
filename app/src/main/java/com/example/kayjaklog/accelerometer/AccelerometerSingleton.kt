package com.example.kayjaklog.accelerometer

object AccelerometerSingleton {
    private var instance: AccelerometerWrapper = AccelerometerWrapper()

    fun getInstance(): AccelerometerWrapper {
        return instance;
    }
}