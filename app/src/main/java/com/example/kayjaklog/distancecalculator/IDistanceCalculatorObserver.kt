package com.example.kayjaklog.distancecalculator

interface IDistanceCalculatorObserver {
    fun onThresholdExceeded(event: DistanceThresholdExceedEvent);
}