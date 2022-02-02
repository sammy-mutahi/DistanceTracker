package com.maps.distancetracker.utils

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView

class MapsBindingAdapter {
    companion object {
        @BindingAdapter("observeTracking")
        @JvmStatic
        fun observeTracker(view: View, started: Boolean?) {
            if (started == true && view is Button) {
                view.visibility = VISIBLE
            } else if (started == true && view is MaterialTextView) {
                view.visibility = INVISIBLE
            }
        }
    }
}