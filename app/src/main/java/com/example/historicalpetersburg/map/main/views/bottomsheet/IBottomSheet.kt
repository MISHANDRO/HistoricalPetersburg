package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

interface IBottomSheet {
    val behavior: BottomSheetBehavior<*>

    var peekHeight: Int
        get() = behavior.peekHeight
        set(value) { behavior.peekHeight = value }

    var maxHeight: Int
        get() = behavior.maxHeight
        set(value) { behavior.maxHeight = value }

    var state: Int
        get() = behavior.state
        set(value) { behavior.state = value }

    var halfExpandedRatio: Float
        get() = behavior.halfExpandedRatio
        set(value) { behavior.halfExpandedRatio = value }

}