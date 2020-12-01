package com.example.kayjaklog.webservice.backend

import com.beust.klaxon.Klaxon
import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.Webservice


class BackendWebservice {
    private var webservice: Webservice? = null

    private var backendUrl: String = ""

    fun setup(webservice: Webservice, url: String) {
        this.webservice = webservice
        this.backendUrl = url
    }

    fun createBulkCoordinates(coordinateList: ArrayList<String>, callback: IWebserviceCallback) {
        if(webservice == null) {
            return
        }

        var coordinates = ArrayList<CreateCoordinateModel>()
        coordinates.add(
            CreateCoordinateModel(
                123,
                32.1,
                23.2
            )
        )
        coordinates.add(
            CreateCoordinateModel(
                423,
                42.1,
                73.2
            )
        )

        println(Klaxon().toJsonString(coordinates))

        webservice!!.sendPost(backendUrl, Klaxon().toJsonString(coordinates), callback)


    }
}