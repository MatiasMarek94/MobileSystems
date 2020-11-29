package com.example.kayjaklog.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoordinateViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Coordinate>>
    private val repository: CoordinateRepository

    init {
        val coordinateDao = CoordinateDatabase.getDatabase(application).coordinateDao()
        repository =  CoordinateRepository(coordinateDao)
        readAllData = repository.readAllData
    }

    fun addUser(coordinate: Coordinate){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(coordinate)
        }
    }

}