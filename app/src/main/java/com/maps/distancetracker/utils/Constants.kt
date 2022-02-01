package com.maps.distancetracker.utils

object Constants {
    const val PERMISSION_LOCATION_REQUEST_CODE = 1
    const val PERMISSION_BACKGROUND_REQUEST_CODE = 2

    //service
    const val ACTION_SERVICE_START = "com.maps.distancetracker.ACTION_SERVICE_START"
    const val ACTION_SERVICE_STOP = "com.maps.distancetracker.ACTION_SERVICE_STOP"

    //notification
    const val NOTIFICATION_CHANNEL_ID = "tracker_notification_id"
    const val NOTIFICATION_CHANNEL_NAME = "tracker_notification_name"
    const val NOTIFICATION_ID = 3
    const val PENDING_INTENT_REQUEST_CODE = 99

    const val LOCATION_UPDATE_INTERVAL = 4000L
    const val LOCATION_FASTEST_UPDATE_INTERVAL = 2000L
}