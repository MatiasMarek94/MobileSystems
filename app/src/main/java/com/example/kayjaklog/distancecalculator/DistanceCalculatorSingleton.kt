package com.example.kayjaklog.distancecalculator

object DistanceCalculatorSingleton {
    private var instance: DistanceCalculator = DistanceCalculator();

    fun getInstance(): DistanceCalculator {
        return instance;
    }
}