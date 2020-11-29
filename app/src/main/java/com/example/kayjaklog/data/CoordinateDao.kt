package com.example.kayjaklog.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoordinateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCoordinate(coordinate: Coordinate)

    @Query( "SELECT * FROM coordinate_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Coordinate>>

    @Query( "SELECT * FROM coordinate_table ORDER BY time ASC")
    fun readAllDataByTime(): LiveData<List<Coordinate>>

}