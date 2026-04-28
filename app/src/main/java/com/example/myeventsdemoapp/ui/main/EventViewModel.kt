package com.example.myeventsdemoapp.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.data.repository.EventRepository
import com.example.myeventsdemoapp.utils.distanceKm
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

val events: StateFlow<List<Event>> =
    _location
        .filterNotNull()
        .flatMapLatest { (userLat, userLng) ->

            repo.getEvents().map { list ->
                list.map {
                    val distance = distanceKm(userLat, userLng, it.lat, it.lng)
                    it.copy(distance = distance)
                }
            }

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun toggle(event: Event) {
        viewModelScope.launch {
            repo.toggleBookmark(event)
        }
    }

}