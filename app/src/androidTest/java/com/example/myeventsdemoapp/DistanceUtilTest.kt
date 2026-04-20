package com.example.myeventsdemoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myeventsdemoapp.utils.distanceKm
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DistanceUtilTest {
    @Test
    fun distance_should_be_positive() {
        val result = distanceKm(10.0, 20.0, 11.0, 21.0)
        assertTrue(result > 0)
    }
}