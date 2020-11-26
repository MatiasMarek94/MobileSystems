package com.example.kayjaklog.distancecalculator

data class DistanceThresholdExceedEvent(
    val lastTimestamp: Double,
    val distance: Double
) {
}