package com.fb.weathertest.data.model.onecall

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Minutely(
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "precipitation")
    val precipitation: Double
)
