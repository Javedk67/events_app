package com.example.myeventsdemoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myeventsdemoapp.data.local.dao.EventDao
import com.example.myeventsdemoapp.data.remote.api.ApiService

import com.example.myeventsdemoapp.data.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RepositoryTest {
    @get:Rule
    val mockitoRule = org.mockito.junit.MockitoJUnit.rule()

    private val api = org.mockito.Mockito.mock(ApiService::class.java)
    private val dao = org.mockito.Mockito.mock(EventDao::class.java)


}