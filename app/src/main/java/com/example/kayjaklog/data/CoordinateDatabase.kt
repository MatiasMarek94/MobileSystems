package com.example.kayjaklog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Coordinate::class], version = 2, exportSchema = false)
abstract class CoordinateDatabase: RoomDatabase() {

    abstract fun coordinateDao(): CoordinateDao

    companion object {
        @Volatile
        private var INSTANCE: CoordinateDatabase? = null

        fun getDatabase(context: Context): CoordinateDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoordinateDatabase::class.java,
                    "coordinate_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
