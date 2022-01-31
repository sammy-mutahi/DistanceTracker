package com.maps.distancetracker.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.maps.distancetracker.utils.Constants.ACTION_SERVICE_START
import com.maps.distancetracker.utils.Constants.ACTION_SERVICE_STOP
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackerService : LifecycleService() {

    companion object {
        val started = MutableLiveData<Boolean>()
    }

    private fun setInitialValues() {
        started.postValue(false)
    }

    override fun onCreate() {
        super.onCreate()
        setInitialValues()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_SERVICE_START -> {
                    started.postValue(true)
                }
                ACTION_SERVICE_STOP -> {
                    started.postValue(false)
                }
                else -> {
                    //do nothing
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}