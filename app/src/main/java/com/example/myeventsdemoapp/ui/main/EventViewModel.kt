package com.example.myeventsdemoapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myeventsdemoapp.data.local.entity.EventEntity
import com.example.myeventsdemoapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repo: EventRepository
) : ViewModel() {

    val events = MutableLiveData<List<EventEntity>>()

    fun loadEvents() {
        viewModelScope.launch {
            events.value = repo.getEvents()
        }
    }
}