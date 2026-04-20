package com.example.myeventsdemoapp.utils

import android.content.Context
import com.example.myeventsdemoapp.data.local.entity.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {
    fun loadEvents(context: Context): List<Event> {
        val json = context.assets.open("events.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<Event>>() {}.type
        return Gson().fromJson(json, type)
    }
}