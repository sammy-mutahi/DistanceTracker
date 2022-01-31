package com.maps.distancetracker.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.maps.distancetracker.R

class MapStyle {
    fun setMapStyle(map: GoogleMap, context: Context) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e("MapActivity", "Map style not set")
            }
        } catch (e: Exception) {
            e.message?.let { message -> Log.e("MapActivity", message) }
        }
    }
}