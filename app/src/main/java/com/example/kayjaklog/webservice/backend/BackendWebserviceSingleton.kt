package com.example.kayjaklog.webservice.backend

object BackendWebserviceSingleton {

    private var instance: BackendWebservice = BackendWebservice()

    fun getInstance(): BackendWebservice {
        return instance;
    }
}

