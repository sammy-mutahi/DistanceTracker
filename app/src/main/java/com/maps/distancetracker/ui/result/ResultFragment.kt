package com.maps.distancetracker.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.maps.distancetracker.data.Result
import com.maps.distancetracker.databinding.FragmentResultBinding


class ResultFragment : BottomSheetDialogFragment() {

    private val binding: FragmentResultBinding by lazy {
        FragmentResultBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val extras = requireArguments()
            val result = extras.getParcelable<Result>("result")
            binding.apply {
                result?.let {
                    distanceValueTextView.text = it.distance
                    timeValueTextView.text = it.time
                }
            }
        }
    }
}