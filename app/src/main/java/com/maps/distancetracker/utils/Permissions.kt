package com.maps.distancetracker.utils

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import com.maps.distancetracker.utils.Constants.PERMISSION_BACKGROUND_REQUEST_CODE
import com.maps.distancetracker.utils.Constants.PERMISSION_LOCATION_REQUEST_CODE
import com.vmadalin.easypermissions.EasyPermissions

object Permissions {
    fun hasLocationPermission(context: Context): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun requestLocationPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "This application cannot work without Location Permission",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION

        )
    }

    fun hasBackgroundPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        return true
    }

    fun requestBackgroundPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "Backround permission is essential for this app without it we cannot provide our service.",
            PERMISSION_BACKGROUND_REQUEST_CODE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION

        )
    }
}