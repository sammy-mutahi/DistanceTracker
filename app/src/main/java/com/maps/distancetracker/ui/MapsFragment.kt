package com.maps.distancetracker.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.maps.distancetracker.R
import com.maps.distancetracker.databinding.FragmentMapsBinding
import com.maps.distancetracker.service.TrackerService
import com.maps.distancetracker.utils.Constants.ACTION_SERVICE_START
import com.maps.distancetracker.utils.Constants.ACTION_SERVICE_STOP
import com.maps.distancetracker.utils.Constants.PERMISSION_BACKGROUND_REQUEST_CODE
import com.maps.distancetracker.utils.Constants.PERMISSION_LOCATION_REQUEST_CODE
import com.maps.distancetracker.utils.MapStyle
import com.maps.distancetracker.utils.Permissions.hasBackgroundPermission
import com.maps.distancetracker.utils.Permissions.hasLocationPermission
import com.maps.distancetracker.utils.Permissions.requestBackgroundPermission
import com.maps.distancetracker.utils.Permissions.requestLocationPermission
import com.maps.distancetracker.utils.ViewExt.disable
import com.maps.distancetracker.utils.ViewExt.enable
import com.maps.distancetracker.utils.ViewExt.hide
import com.maps.distancetracker.utils.ViewExt.show
import com.maps.distancetracker.utils.moveCameraWithAnim
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks, OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy {
        FragmentMapsBinding.inflate(layoutInflater)
    }
    private val viewModel: MapsViewModel by viewModels()

    private val moveCameraPosition by lazy { MapUtils() }

    private lateinit var map: GoogleMap

    private var locationList: MutableList<LatLng> = mutableListOf()

    private val mapStyle: MapStyle by lazy {
        MapStyle()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //check for location permissions
        if (!hasLocationPermission(requireContext())) {
            requestLocationPermission(this)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        initListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentLocation.observe(viewLifecycleOwner) { location ->
            if (::map.isInitialized) {
                map.moveCameraWithAnim(LatLng(location.latitude, location.longitude))
                setUpMarkers()
            }
        }
    }

    private fun setUpMarkers() {
        //markers will go here

    }

    private fun initListeners() {
        binding.apply {
            startButton.setOnClickListener {
                startService()
                startButton.hide()
            }
            stopButton.setOnClickListener {
                stopService()
                stopButton.hide()
                startButton.show()
            }
        }
    }

    private fun stopService() {
        sendActionCommandToService(ACTION_SERVICE_STOP)
        binding.startButton.disable()
    }

    private fun startService() {
        if (hasBackgroundPermission(requireContext())) {
            startCountDown()
            binding.apply {
                startButton.hide()
                stopButton.show()
            }
        } else {
            requestBackgroundPermission(this)
        }
    }

    private fun startCountDown() {
        binding.apply {
            counterTextview.show()
            stopButton.disable()
        }

        val timer: CountDownTimer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val currentSecond = millisUntilFinished / 1000
                if (currentSecond.toString() == "0") {
                    binding.apply {
                        counterTextview.text = "GO"
                        counterTextview.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.black
                            )
                        )
                    }
                } else {
                    binding.apply {
                        counterTextview.text = "$currentSecond"
                        counterTextview.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.red
                            )
                        )
                    }
                }
            }

            override fun onFinish() {
                sendActionCommandToService(ACTION_SERVICE_START)
                binding.counterTextview.hide()
            }


        }
        timer.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_LOCATION_REQUEST_CODE -> {
                EasyPermissions.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults,
                    this
                )
            }
            PERMISSION_BACKGROUND_REQUEST_CODE -> {
                EasyPermissions.onRequestPermissionsResult(
                    requestCode,
                    permissions,
                    grantResults,
                    this
                )
            }
        }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        when (requestCode) {
            PERMISSION_LOCATION_REQUEST_CODE -> {
                //do nothing for now
            }
            PERMISSION_BACKGROUND_REQUEST_CODE -> {
                startService()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.apply {
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isScrollGesturesEnabled = false
        }
        mapStyle.setMapStyle(map, requireContext())
        viewModel.fetchLocationUpdates()
        observeTrackerService()
    }

    private fun observeTrackerService() {
        TrackerService.locations.observe(viewLifecycleOwner) {
            if (it != null) {
                locationList = it
                drawPolyline()
                followPolyline()
                if (locationList.size > 1) {
                    binding.stopButton.enable()
                }
            }
        }
    }

    private fun drawPolyline() {
        val polyline = map.addPolyline(
            PolylineOptions().apply {
                width(10f)
                color(Color.BLUE)
                jointType(JointType.ROUND)
                startCap(ButtCap())
                endCap(ButtCap())
                addAll(locationList)
            }
        )
    }

    private fun followPolyline() {
        if (locationList.isNotEmpty()) {
            map.animateCamera(
                (
                        CameraUpdateFactory.newCameraPosition(
                            moveCameraPosition.moveCameraPosition(
                                locationList.last()
                            )
                        )
                        ), 1000, null
            )
        }
    }

    private fun sendActionCommandToService(action: String) {
        Intent(requireContext(), TrackerService::class.java).apply {
            this.action = action
            binding.root.context.startService(this)
        }
    }

}