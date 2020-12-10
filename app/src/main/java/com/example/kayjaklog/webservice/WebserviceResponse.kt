package com.example.kayjaklog.webservice

data class WebserviceResponse(
    val responseCode: WebserviceResponseCode,
    val responseString: String
) {
}