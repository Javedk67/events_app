package com.example.myeventsdemoapp.utils

import android.location.Location
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.local.entity.EventEntity
import java.text.SimpleDateFormat
import java.util.*

fun distanceKm( lat1: Double?,
                lon1: Double?,
                lat2: Double?,
                lon2: Double?): Float {


    if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) return 0f

    val result = FloatArray(1)
    Location.distanceBetween(lat1, lon1, lat2, lon2, result)
    return result[0] / 1000f
}


fun Event.toEntity(existing: EventEntity?): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        location = location,
        lat = lat,
        lng = lng,
        time = time,
        imageUrl = imageUrl,
        isBookmarked = existing?.isBookmarked ?: false,
        lastUpdated = System.currentTimeMillis()
    )
}

fun EventEntity.toDomain(): Event {
    return Event(
        id = id,
        title = title,
        location = location,
        lat = lat,
        lng = lng,
        time = time,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked
    )
}
fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}