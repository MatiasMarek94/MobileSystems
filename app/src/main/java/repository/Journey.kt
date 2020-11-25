package repository
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journey")
data class Journey (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "time_stamp") val time: Long?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?
)



