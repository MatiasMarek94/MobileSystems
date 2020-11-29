package com.example.kayjaklog.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Coordinate::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coordinateDao(): CoordinateDao
}