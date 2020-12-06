package com.example.kayjaklog.services

import com.example.kayjaklog.distancecalculator.DistanceCalculator

class CoordinateAlgorithm {

    private var instance: DistanceCalculator = DistanceCalculator(1.0);

    fun getInstance(): DistanceCalculator {
        return instance;
    }

}