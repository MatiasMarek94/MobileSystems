package com.example.kayjaklog.data

import androidx.lifecycle.LiveData


class CoordinateRepository(private val coordinateDao: CoordinateDao) {

    val readAllData: LiveData<List<Coordinate>> = coordinateDao.readAllData()

    suspend fun addUser(coordinate: Coordinate) {
        coordinateDao.addCoordinate(coordinate)
    }

}