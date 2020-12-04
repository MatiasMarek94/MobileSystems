package com.example.kayjaklog.webservice.backend

import com.beust.klaxon.Klaxon
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.Webservice
import kotlin.math.floor


class BackendWebservice {
    private var webservice: Webservice? = null

    private var backendUrl: String = ""

    fun setup(webservice: Webservice, url: String) {
        this.webservice = webservice
        this.backendUrl = url
    }

    fun createBulkCoordinates(coordinateList: ArrayList<Coordinate>, tripId: Int, callback: IWebserviceCallback) {
        if(webservice == null) {
            return
        }

        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder
            .append(backendUrl)
            .append("Trip")
            .append(tripId)
            .append("/Coordinate/bulk")

        println(Klaxon().toJsonString(coordinateList))

        webservice!!.sendPost(stringBuilder.toString(), Klaxon().toJsonString(coordinateList), callback)
    }

    fun createTrip(callback: IWebserviceCallback) {
        if(webservice == null) {
            return
        }

        val currentEpoch = floor((System.currentTimeMillis() / 1000).toDouble()).toLong()
        val createTripModel = CreateTripModel(currentEpoch)

        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder
            .append(backendUrl)
            .append("Trip")

        webservice!!.sendPost(stringBuilder.toString(), Klaxon().toJsonString(createTripModel), callback)
    }
}