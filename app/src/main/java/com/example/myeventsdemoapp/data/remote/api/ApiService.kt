package com.example.myeventsdemoapp.data.remote.api

import com.example.myeventsdemoapp.data.remote.dto.EventDto
import retrofit2.http.GET

interface ApiService {
    @GET("v1/events")
    suspend fun getEvents(): List<EventDto>
}