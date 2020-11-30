package com.example.kayjaklog.location

object LocationWrapperSingleton {
    private var instance: LocationWrapper = LocationWrapper()

    fun getInstance(): LocationWrapper {
        return instance;
    }
}