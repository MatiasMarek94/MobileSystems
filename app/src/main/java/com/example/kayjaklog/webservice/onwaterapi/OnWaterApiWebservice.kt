package com.example.kayjaklog.webservice.onwaterapi

import com.example.kayjaklog.webservice.IWebserviceCallback
import com.example.kayjaklog.webservice.Webservice

class OnWaterApiWebservice {
    private var webservice: Webservice? = null

    private var url: String = "https://api.onwater.io/api/v1/results/"
    private var accessToken: String = ""

    fun setup(webservice: Webservice, accessToken: String) {
        this.webservice = webservice
        this.accessToken = accessToken
    }

    fun isOnWater(coordinate: String, callback: IWebserviceCallback) {
        if(webservice == null) {
            return
        }

        val stringBuilder: StringBuilder = StringBuilder()
        stringBuilder.append(url).append(coordinate).append("?access_token=").append(accessToken)

        webservice!!.sendGet(stringBuilder.toString(), callback)
    }
}