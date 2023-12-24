package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

abstract class CustomDialog : Fragment() {

    lateinit var behavior: BottomSheetBehavior<*>
    abstract var peekHeight: Int
    abstract var maxHeight: Int
    abstract var halfExpandedRatio: Float

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior = BottomSheetBehavior.from(view)
        behavior.apply {
            peekHeight = this@CustomDialog.peekHeight
            maxHeight = this@CustomDialog.maxHeight
            halfExpandedRatio = this@CustomDialog.halfExpandedRatio

            isHideable = true

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        fragmentManager!!.beginTransaction()
                            .remove(this@CustomDialog)
                            .commit()
                    }

                    this@CustomDialog.onStateChanged(bottomSheet, newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    this@CustomDialog.onSlide(bottomSheet, slideOffset)
                }

            })
        }

        view.setOnTouchListener { _, _ ->
            true
        }
    }

    fun show(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .add(R.id.fragment_map, this)
            .commit()
    }

    fun close() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun onStateChanged(bottomSheet: View, newState: Int) {}
    open fun onSlide(bottomSheet: View, slideOffset: Float) {}
}