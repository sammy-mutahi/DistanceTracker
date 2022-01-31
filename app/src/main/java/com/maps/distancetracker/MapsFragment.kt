package com.maps.distancetracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.maps.distancetracker.databinding.FragmentMapsBinding
import com.maps.distancetracker.utils.CameraAndViewPort
import com.maps.distancetracker.utils.Constants.PERMISSION_BACKGROUND_REQUEST_CODE
import com.maps.distancetracker.utils.Constants.PERMISSION_LOCATION_REQUEST_CODE
import com.maps.distancetracker.utils.MapStyle
import com.maps.distancetracker.utils.Permissions.hasBackgroundPermission
import com.maps.distancetracker.utils.Permissions.hasLocationPermission
import com.maps.distancetracker.utils.Permissions.requestBackgroundPermission
import com.maps.distancetracker.utils.Permissions.requestLocationPermission
import com.maps.distancetracker.utils.ViewExt.disable
import com.maps.distancetracker.utils.ViewExt.hide
import com.maps.distancetracker.utils.ViewExt.show
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks, OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy {
        FragmentMapsBinding.inflate(layoutInflater)
    }

    private val cameraAndViewPort by lazy { CameraAndViewPort() }

    private lateinit var map: GoogleMap

    private val nairobi = LatLng(-1.286389, 36.817223)

    private val mapStyle: MapStyle by lazy {
        MapStyle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    private fun initListeners() {
        binding.startButton.setOnClickListener {
            onStartButtonClicked()
        }
    }

    private fun onStartButtonClicked() {
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
                onStartButtonClicked()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.addMarker(
            MarkerOptions().position(nairobi).title("Marker in Nairobi")
        )
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraAndViewPort.nairobi))
        map.uiSettings.apply {
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isCompassEnabled = false
            isScrollGesturesEnabled = false
        }
        mapStyle.setMapStyle(map, requireContext())
    }

}