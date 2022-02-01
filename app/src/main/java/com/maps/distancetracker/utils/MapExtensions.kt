package com.maps.distancetracker.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

const val MAP_CAMERA_ZOOM = 17F
const val MAP_CAMERA_ZOOM_INT = 12

fun GoogleMap.moveCameraWithAnim(latLng: LatLng) {
    animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_CAMERA_ZOOM))
}