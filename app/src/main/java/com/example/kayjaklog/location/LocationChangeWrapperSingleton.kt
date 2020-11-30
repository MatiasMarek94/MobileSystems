package com.example.kayjaklog.location

object LocationChangeWrapperSingleton {
    private var instance: LocationChangeWrapper = LocationChangeWrapper()

    fun getInstance(): LocationChangeWrapper {
        return instance;
    }
}