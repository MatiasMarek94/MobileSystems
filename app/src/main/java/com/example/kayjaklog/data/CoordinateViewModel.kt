package com.example.kayjaklog.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.kayjaklog.webservice.onwaterapi.OnWaterResponse
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class CoordinateViewModel(application: Application): AndroidViewModel(application) {

    private val repository: CoordinateRepository
    val readAllData: LiveData<List<Coordinate>>

    val onWater: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


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

    fun getOnWaterStatus(coordinate: Coordinate): Boolean {
        viewModelScope.launch {
            repository.getOnWaterStatus(coordinate, onWaterCallBack)
        }
        return true;
    }

    private val onWaterCallBack = object : IOnWaterServiceCallBack {
        override fun onWaterCallBack(onWaterResponse: OnWaterResponse) {
            onWater.postValue(onWaterResponse.onWater)
        }
    }

}