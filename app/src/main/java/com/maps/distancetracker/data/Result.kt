package com.maps.distancetracker.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Result(
    val distance: String,
    val time: String
) : Parcelable