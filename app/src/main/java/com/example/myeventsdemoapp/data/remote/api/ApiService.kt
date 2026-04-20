package com.example.myeventsdemoapp.data.remote.api

import com.example.myeventsdemoapp.data.local.entity.Event
import retrofit2.Response


import retrofit2.http.GET

interface ApiService {

    @GET("a14213de-9103-462e-9bc9-a1e880b64a6a")
    suspend fun getEvents(): Response<List<Event>>
}