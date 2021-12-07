package com.fb.weathertest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fb.weathertest.data.db.dao.ForecastDao
import com.fb.weathertest.data.model.onecall.OneCallResponse
import com.fb.weathertest.util.Converters

@Database(entities = [OneCallResponse::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}
