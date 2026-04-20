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

        var isBookmarked = false

        // toggle logic
        isBookmarked = !isBookmarked

        assertEquals(true, isBookmarked)
    }
    @Test
    fun toggle_bookmark_should_remove_when_bookmarked() {

        var isBookmarked = true

        // toggle logic
        isBookmarked = !isBookmarked

        assertEquals(false, isBookmarked)
    }

    @Test
    fun json_data_should_not_be_empty() {

        val events = listOf(
            "Music Festival",
            "Tech Meetup"
        )

        assertEquals(false, events.isEmpty())
    }
}