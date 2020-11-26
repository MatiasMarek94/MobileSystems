package com.example.kayjaklog.accelerometer

interface IAccelerometerObserver {
    fun onSensorChange(event: AccelerometerSensorEvent);
}