package com.example.kayjaklog.db
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coordinate")
data class Coordinate (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "time_stamp")
    val time: Long?,
    @ColumnInfo(name = "latitude")
    val latitude: Double?,
    @ColumnInfo(name = "longitude")
    val longitude: Double?
)



