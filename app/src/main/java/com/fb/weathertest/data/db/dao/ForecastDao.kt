package com.fb.weathertest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fb.weathertest.data.model.onecall.OneCallResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(oneCallResponse: OneCallResponse)

    @Query("SELECT * FROM OneCallResponse LIMIT 1")
    fun getForecast(): Flow<OneCallResponse>

    @Query("DELETE FROM OneCallResponse")
    suspend fun deleteForecast()

    @Query("SELECT timeStamp FROM OneCallResponse LIMIT 1")
    suspend fun getTimeStamp(): Long?
}
