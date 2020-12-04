package com.example.kayjaklog.webservice;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class Webservice {

    private RequestQueue requestQueue = null;

    public Webservice(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

//    https://stackoverflow.com/questions/48424033/android-volley-post-request-with-json-object-in-body-and-getting-response-in-str/48424181
//    https://stackoverflow.com/questions/49342841/android-wait-for-volley-response-for-continue
    public void sendPost(String url, final String jsonString, final IWebserviceCallback callBack) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onWebserviceResponse(new WebserviceResponse(WebserviceResponseCode.SUCCESSFUL, response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onWebserviceResponse(new WebserviceResponse(WebserviceResponseCode.UNSUCCESSFUL, error.toString()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonString == null ? null : jsonString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonString, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);
    }

    public void sendGet(String url, final IWebserviceCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onWebserviceResponse(new WebserviceResponse(WebserviceResponseCode.SUCCESSFUL, response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onWebserviceResponse(new WebserviceResponse(WebserviceResponseCode.UNSUCCESSFUL, error.toString()));
            }
        });

        requestQueue.add(stringRequest);
    }
}
