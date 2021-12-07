package com.fb.weathertest.util

import androidx.room.TypeConverter
import com.fb.weathertest.data.model.onecall.Daily
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    @TypeConverter
    fun dailyListToJson(dailyList: List<Daily>): String {
        val type = Types.newParameterizedType(List::class.java, Daily::class.java)
        val adapter = Moshi.Builder().build().adapter<List<Daily>>(type)
        return adapter.toJson(dailyList)
    }

    @TypeConverter
    fun jsonToDailyList(string: String): List<Daily> {
        val type = Types.newParameterizedType(List::class.java, Daily::class.java)
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<Daily>>(type)
        return adapter.fromJson(string)!!
    }
}
