package com.example.kayjaklog.webservice.onwaterapi

import com.beust.klaxon.Json

data class ReadResultModel(
    val query: String,
    @Json(name = "request_id")
    val requestId: String,
    val lat: Double,
    val lon: Double,
    val water: Boolean
) {
}