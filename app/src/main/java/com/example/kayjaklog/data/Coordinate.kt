package com.example.kayjaklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coordinate_table")
data class Coordinate(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val time: Double,
    val latitude: Double,
    val longitude: Double,
    val tripId: Int
)