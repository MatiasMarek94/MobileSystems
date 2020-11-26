package com.example.kayjaklog.accelerometer

import android.content.res.AssetManager
import java.io.File

object AccelerometerMockData {
    val timestamp: ArrayList<Double> = ArrayList()
    val xData: ArrayList<Double> = ArrayList()
    val yData: ArrayList<Double> = ArrayList()
    val zData: ArrayList<Double> = ArrayList()

//    val timestampFileName: String = "timestamp_top_10000.txt"
//    val xDataFileName: String = "x_top_10000.txt"
//    val yDataFileName: String = "y_top_10000.txt"
//    val zDataFileName: String = "modified_z_top_10000.txt"
    val timestampFileName: String ="daniel_timestamp.txt"
    val xDataFileName: String = "daniel_x.txt"
    val yDataFileName: String = "daniel_y.txt"
    val zDataFileName: String = "daniel_z.txt"

    fun loadFiles(assetManger: AssetManager) {
        assetManger.open(timestampFileName).bufferedReader().forEachLine  {
            timestamp.add(it.toDouble())
        }
        assetManger.open(xDataFileName).bufferedReader().forEachLine {
            xData.add(it.toDouble())
        }
        assetManger.open(yDataFileName).bufferedReader().forEachLine {
            yData.add(it.toDouble())
        }
        assetManger.open(zDataFileName).bufferedReader().forEachLine {
            zData.add(it.toDouble())
        }

    }
}