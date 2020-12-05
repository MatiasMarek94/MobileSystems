package com.example.kayjaklog.distancecalculator

object DistanceCalculatorSingleton {
    private var instance: DistanceCalculator = DistanceCalculator(20.0);

    fun getInstance(): DistanceCalculator {
        return instance;
    }
}