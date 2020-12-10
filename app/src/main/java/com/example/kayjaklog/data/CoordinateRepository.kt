package com.example.kayjaklog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonDoc
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.backend.BackendWebservice
import com.example.kayjaklog.webservice.backend.BackendWebserviceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiServiceSingleton
import com.example.kayjaklog.webservice.onwaterapi.OnWaterApiWebservice
import com.example.kayjaklog.webservice.onwaterapi.OnWaterResponse
import com.example.kayjaklog.webservice.onwaterapi.ReadResultModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.StringReader
import java.sql.SQLOutput
import kotlin.coroutines.suspendCoroutine


class CoordinateRepository(private val coordinateDao: CoordinateDao) {

    val readAllData: LiveData<List<Coordinate>> = getAllData()

    val onWater: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    private val onWaterService: OnWaterApiWebservice = OnWaterApiServiceSingleton.getInstance()

    suspend fun addCoordinate(coordinate: Coordinate) {
        coordinateDao.addCoordinate(coordinate)
    }

     fun deleteStorage() {
        coordinateDao.deleteStorage()
    }

     fun getCoordinateByTime() : List<Coordinate> {
        return coordinateDao.getCoordinateByTime()
    }

    fun getAllData(): LiveData<List<Coordinate>> {
        return coordinateDao.readAllData()
    }

    fun getLatestCoordinate(): Coordinate {
        return coordinateDao.getLatestCoordinate()
    }


    fun getAllWithTripId(tripId: Int): List<Coordinate> {
        return coordinateDao.getAllWithTripId(tripId)
    }

    val webservice: OnWaterApiWebservice = OnWaterApiServiceSingleton.getInstance()

    fun getOnWaterStatus(latestCoordinate: Coordinate, onWaterAPICallback: IOnWaterServiceCallBack) {

        val whenGetOnWaterStatus = object : IWebserviceCallback {
            override fun onWebserviceResponse(webserviceResponse: WebserviceResponse) {
                val onWaterResponse = Klaxon().parse<ReadResultModel>(webserviceResponse.responseString)

                if (onWaterResponse != null) {
                    onWater.postValue(onWaterResponse.water!!)
                }

                val onWater = onWaterResponse?.water
                onWaterAPICallback.onWaterCallBack(OnWaterResponse(onWater!!))
            }
        }

        onWaterService.isOnWater(latestCoordinate, whenGetOnWaterStatus)

        }






}