package com.example.kayjaklog.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoordinateDao {

    @Query("SELECT * FROM coordinate ORDER BY time_stamp ASC")
    fun getCoordinates(): LiveData<List<Coordinate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun insert(coordinate: Coordinate)

    @Query("DELETE FROM coordinate" )
    suspend fun deleteAll()


}