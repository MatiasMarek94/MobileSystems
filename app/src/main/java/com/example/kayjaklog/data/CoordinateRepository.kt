package com.example.kayjaklog.data

import androidx.lifecycle.LiveData


class CoordinateRepository(private val coordinateDao: CoordinateDao) {

    val readAllData: LiveData<List<Coordinate>> = getAllDAta()

    //WebService
    suspend fun addCoordinate(coordinate: Coordinate) {
        coordinateDao.addCoordinate(coordinate)
    }

    suspend fun deleteStorage() {
        coordinateDao.deleteStorage()
    }

    suspend fun getCoordinateByTime() : List<Coordinate> {
        return coordinateDao.getCoordinateByTime()
    }

    fun getAllDAta(): LiveData<List<Coordinate>> {
        return coordinateDao.readAllData()
    }



}