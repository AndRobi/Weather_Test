package com.fb.weathertest.util

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resumeWithException

fun String.toDate(
    dateFormat: String = "YYYY:mm:DD",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}
@Suppress("MagicNumber")
fun Long.nameOfDay(): String {
    val formatter = SimpleDateFormat("EEEE")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    return formatter.format(calendar.time)
}
@Suppress("MagicNumber")
fun Long.timeOfDay(): String {
    val formatter = SimpleDateFormat("hh:mm")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    return formatter.format(calendar.time)
}
@SuppressLint("MissingPermission")
suspend fun FusedLocationProviderClient.awaitCurrentLocation(priority: Int): Location? {
    return suspendCancellableCoroutine {
        val cts = CancellationTokenSource()
        getCurrentLocation(priority, cts.token)
            .addOnSuccessListener { location ->
                it.resume(location, null)
            }.addOnFailureListener { e ->
                it.resumeWithException(e)
            }

        it.invokeOnCancellation {
            cts.cancel()
        }
    }
}
