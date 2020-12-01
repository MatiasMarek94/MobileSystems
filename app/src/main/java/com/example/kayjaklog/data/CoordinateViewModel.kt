package com.example.kayjaklog.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kayjaklog.location.LocationChangeWrapperSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoordinateViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Coordinate>>

    private val repository: CoordinateRepository


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

    fun readAllData() : LiveData<List<Coordinate>> {
        var liveData: LiveData<List<Coordinate>>? = null
        viewModelScope.launch(Dispatchers.IO) {
             liveData = repository.readAllData
        }
        return liveData!!
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStorage()
        }
    }

    fun getCoordinateByTime() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoordinateByTime()
        }
    }


}