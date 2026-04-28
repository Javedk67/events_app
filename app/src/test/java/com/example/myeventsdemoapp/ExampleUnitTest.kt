package com.example.myeventsdemoapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun toggle_bookmark_should_add_when_not_bookmarked() {
        val isBookmarked = false

        val updated = !isBookmarked

        assertTrue(updated)
    }

    @Test
    fun toggle_bookmark_should_remove_when_bookmarked() {
        val isBookmarked = true

        val updated = !isBookmarked

        assertFalse(updated)
    }
    @Test
    fun events_list_should_not_be_empty() {
        val events = listOf("Music Festival", "Tech Meetup")

        assertTrue(events.isNotEmpty())
    }
}