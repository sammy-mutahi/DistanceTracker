package com.maps.distancetracker.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maps.distancetracker.utils.LocationUpdateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val locationUpdateUtils: LocationUpdateUtils
) : ViewModel() {

    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

    fun fetchLocationUpdates() = viewModelScope.launch {
        try {
            locationUpdateUtils.fetchLocationUpdates().collect { lastLocation ->
                _currentLocation.postValue(lastLocation)
            }
        } catch (e: Exception) {
            //update view state
        }
    }
}