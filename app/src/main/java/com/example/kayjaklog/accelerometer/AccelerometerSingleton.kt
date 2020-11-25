package com.example.kayjaklog.accelerometer

object AccelerometerSingleton {
    private var instance: Accelerometer = Accelerometer()

    fun getInstance(): Accelerometer {
        return instance;
    }
}