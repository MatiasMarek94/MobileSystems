package com.example.kayjaklog.accelerometer

data class AccelerometerSensorEvent(
    val timestamp: Double,
    val x: Double,
    val y: Double,
    val z: Double
) {
}