package com.example.kayjaklog.accelerometer

import android.hardware.SensorEvent

interface IAccelerometerObserver {
    fun onSensorChange(event: SensorEvent);
}