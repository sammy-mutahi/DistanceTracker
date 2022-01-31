package com.maps.distancetracker.utils

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class CameraAndViewPort {

    val nairobi: CameraPosition = CameraPosition.Builder()
        .target(LatLng(-1.286389, 36.817223))
        .zoom(17f)
        .bearing(100f)
        .tilt(45f)
        .build()
}