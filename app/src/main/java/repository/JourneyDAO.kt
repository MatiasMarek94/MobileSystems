package repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IJourneyDAO {

    @Query("SELECT * FROM journey ORDER BY time_stamp ASC")
    fun getJourney(): List<Coordinate>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coordinate: Coordinate)

    @Query("DELETE FROM journey")
    suspend fun deleteAll()
}