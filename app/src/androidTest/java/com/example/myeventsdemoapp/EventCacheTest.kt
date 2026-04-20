package com.example.myeventsdemoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myeventsdemoapp.utils.EventCache
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventCacheTest {
    @Test
    fun cache_should_expire_after_ttl() {
        EventCache.set(emptyList())
        Thread.sleep(6000)
        assertFalse(EventCache.isValid())
    }
}