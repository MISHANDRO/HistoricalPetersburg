package com.example.historicalpetersburg.map.views.bottomsheet

import android.view.View
import android.widget.LinearLayout
import com.example.historicalpetersburg.GlobalTools
import com.example.historicalpetersburg.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ExtraBottomSheet(view: LinearLayout) : IBottomSheet {

    var content: ContentExtraBottomSheetBase? = null
        set(value) {
            field = value
            value?.let {
                GlobalTools.instance.fragmentManager.beginTransaction()
                    .replace(R.id.bottom_sheet_other_content_container, value)
                    .commit()

                peekHeight = it.peekHeight
                maxHeight = it.maxHeight
                halfExpandedRatio = it.halfExpandedRatio
            }
        }

    override val behavior = BottomSheetBehavior.from(view)

    init {
        behavior.isHideable = true
        state = BottomSheetBehavior.STATE_HIDDEN

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                content?.onStateChanged(bottomSheet, newState)

                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    content?.close()
                    removeAll()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                content?.onSlide(bottomSheet, slideOffset)
            }

        })
    }

    fun removeAll() {
        content?.let {
            GlobalTools.instance.fragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
        content = null
    }
}