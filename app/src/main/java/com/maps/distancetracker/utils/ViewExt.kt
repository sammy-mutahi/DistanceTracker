package com.maps.distancetracker.utils

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

object ViewExt {
    fun View.show() {
        visibility = VISIBLE
    }

    fun View.hide() {
        visibility = INVISIBLE
    }
}