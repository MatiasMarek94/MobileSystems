package com.example.kayjaklog

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.kayjaklog.db.AppDatabase
import com.example.kayjaklog.db.Coordinate
import java.time.LocalDateTime
import java.lang.*;

class MainActivity : AppCompatActivity(), SensorEventListener {


    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_start = findViewById(R.id.start_button) as Button
        btn_start.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Start -> sensors", Toast.LENGTH_SHORT).show()
            print("click")
        }
    }

    suspend fun testRunFunction() {
        Thread.sleep(1000)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build().coordinateDao().insertSimple(System.currentTimeMillis())

        println("testRunFunction");
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // event.sensor.getType() because multiple types.
    }



}