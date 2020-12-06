package com.example.kayjaklog.data

import com.example.kayjaklog.webservice.WebserviceResponse
import com.example.kayjaklog.webservice.onwaterapi.OnWaterResponse

interface IOnWaterServiceCallBack {
        fun onWaterCallBack(Response: OnWaterResponse): Boolean
}