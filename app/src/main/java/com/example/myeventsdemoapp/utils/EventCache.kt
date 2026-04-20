package com.example.myeventsdemoapp.utils

import com.example.myeventsdemoapp.data.local.entity.Event

object EventCache {
    private var data: List<Event>? = null
    private var timestamp: Long = 0L

    private const val TTL = 5 * 60 * 1000 // 5 min

    fun isValid(): Boolean {
        return data != null && System.currentTimeMillis() - timestamp < TTL
    }

    fun get(): List<Event>? = data

    fun set(list: List<Event>) {
        data = list
        timestamp = System.currentTimeMillis()
    }

    fun clear() {
        data = null
        timestamp = 0L
    }
}