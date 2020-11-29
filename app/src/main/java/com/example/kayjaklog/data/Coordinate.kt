package com.example.kayjaklog.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coordinate_table")
data class Coordinate(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val time: Long,
    val latitude: Double,
    val longitude: Double
)