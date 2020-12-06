package com.example.kayjaklog.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiServiceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiWebservice
import com.example.kayjaklog.webservice.onwaterapi.OnWaterResponse
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class CoordinateViewModel(application: Application): AndroidViewModel(application) {

    private val repository: CoordinateRepository
    public val readAllData: LiveData<List<Coordinate>>



    init {
        val coordinateDao = CoordinateDatabase.getDatabase(application).coordinateDao()
        repository =  CoordinateRepository(coordinateDao)
        readAllData = repository.readAllData
    }

    fun addCoordinate(coordinate: Coordinate){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoordinate(coordinate)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStorage()
        }
    }


    suspend fun getLatestCoordinate() : Coordinate {
        return suspendCoroutine {
            viewModelScope.launch {
                repository.getLatestCoordinate()
            }
        }
    }

    fun getOnWaterStatus(coordinate: Coordinate): Boolean {
        viewModelScope.launch {
            repository.getOnWaterStatus(coordinate, onWaterCallBack)
        }
        return true;
    }

    private val onWaterCallBack = object : IOnWaterServiceCallBack {
        override fun onWaterCallBack(onWaterResponse: OnWaterResponse): Boolean {
            return onWaterResponse.onWater
        }
    }


}