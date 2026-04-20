package com.example.myeventsdemoapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsdemoapp.data.local.entity.Event

import com.example.myeventsdemoapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repo: EventRepository,

) : ViewModel() {

    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)

    fun setLocation(lat: Double, lng: Double) {
        _location.value = lat to lng

    }
    val location1 = _location.value

    val lat1 = location1?.first
    val lng1 = location1?.second

//    val location = _location
//        .filterNotNull()
//        .flatMapLatest { (lat, lng) ->
//            repo.getLocation(lat1!!, lng1!!) // ✅ pass to repo
//        }

    val events: StateFlow<List<Event>> = repo.getEvents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val bookmarkedIds:StateFlow<List<String>> = repo.getBookmarkedIds().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun toggle(event: Event,isBookmarked: Boolean) {
        viewModelScope.launch {
            repo.toggleBookmark(event,isBookmarked)
            Log.d("//////","viewmodel"+event.id)
        }
    }

}