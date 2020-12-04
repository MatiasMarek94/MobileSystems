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

    @Query( "SELECT * FROM coordinate_table ORDER BY id ASC")
    fun getCoordinateByTime(): List<Coordinate>

    @Query("DELETE FROM coordinate_table")
    fun deleteStorage()

    @Query( "SELECT * FROM coordinate_table WHERE tripId = :tripId ORDER BY time ASC")
    fun getAllWithTripId(tripId: Int): List<Coordinate>
}