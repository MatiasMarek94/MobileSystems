package com.example.kayjaklog.webservice.onwaterapi

object OnWaterApiServiceSingleton {

    private var instance: OnWaterApiWebservice = OnWaterApiWebservice()

    fun getInstance(): OnWaterApiWebservice {
        return instance;
    }
}

