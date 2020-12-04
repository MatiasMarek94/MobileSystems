package com.example.kayjaklog.data

import androidx.lifecycle.LiveData
import com.example.kayjaklog.webservice.Webservice
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiServiceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiWebservice


class CoordinateRepository(private val coordinateDao: CoordinateDao) {

    val readAllData: LiveData<List<Coordinate>> = getAllDAta()
    val webservice: BackendWebservice = BackendWebserviceSingleton.getInstance()
    val onWaterService: OnWaterApiWebservice = OnWaterApiServiceSingleton.getInstance()

    suspend fun addCoordinate(coordinate: Coordinate) {
        coordinateDao.addCoordinate(coordinate)
    }

     fun deleteStorage() {
        coordinateDao.deleteStorage()
    }

     fun getCoordinateByTime() : List<Coordinate> {
        return coordinateDao.getCoordinateByTime()
    }

    fun getAllDAta(): LiveData<List<Coordinate>> {
        return coordinateDao.readAllData()
    }

    fun getAllWithTripId(tripId: Int): List<Coordinate> {
        return coordinateDao.getAllWithTripId(tripId)
    }
}