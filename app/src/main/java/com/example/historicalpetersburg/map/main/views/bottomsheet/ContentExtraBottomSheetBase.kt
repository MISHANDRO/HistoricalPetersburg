package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.historicalpetersburg.map.MapManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

abstract class ContentExtraBottomSheetBase : Fragment() {
    abstract var peekHeight: Int
    abstract var maxHeight: Int
    abstract var halfExpandedRatio: Float

    val bottomSheet = MapManager.instance.mapFragment.extraBottomSheet

    abstract override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun onStateChanged(bottomSheet: View, newState: Int)
    abstract fun onSlide(bottomSheet: View, slideOffset: Float)

    abstract fun close()

    fun show() {
        bottomSheet.apply {
            content = this@ContentExtraBottomSheetBase
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}