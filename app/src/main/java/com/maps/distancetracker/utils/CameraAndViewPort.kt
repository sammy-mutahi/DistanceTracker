package com.maps.distancetracker.utils

import android.location.Location
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class CameraAndViewPort {

    fun moveCamera(location: Location): CameraPosition {
        return CameraPosition.Builder()
            .target(LatLng(location.latitude, location.latitude))
            .zoom(17f)
            .bearing(100f)
            .tilt(45f)
            .build()
    }
}