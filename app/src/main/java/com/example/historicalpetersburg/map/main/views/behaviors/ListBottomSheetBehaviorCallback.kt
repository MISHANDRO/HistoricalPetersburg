package com.example.historicalpetersburg.map.main.views.behaviors

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.tools.GlobalTools
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ListBottomSheetBehaviorCallback(
    private val pinContent: View,
    private val window: Window,
    private var behavior: BottomSheetBehavior<LinearLayout>) : BottomSheetBehavior.BottomSheetCallback()
{
    private val params = pinContent.layoutParams as ViewGroup.MarginLayoutParams
    private var prevIsHalfState = if (behavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED) 1 else 0

    init {
        params.bottomMargin = behavior.peekHeight
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                // Нижнее окно свернуто
                prevIsHalfState = 0
                MapManager.instance.map.zoomPadding.bottom =
                    (GlobalTools.instance.displayMetrics.heightPixels - bottomSheet.top + params.height + params.bottomMargin).toFloat()
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                // Нижнее окно развернуто
                prevIsHalfState = 0
            }
            BottomSheetBehavior.STATE_HIDDEN -> {
                // Нижнее окно скрыто
            }
            BottomSheetBehavior.STATE_DRAGGING -> {
                // Пользователь перетаскивает нижнее окно
            }
            BottomSheetBehavior.STATE_SETTLING -> {
                // Нижнее окно выполняет анимацию изменения состояния
                if (prevIsHalfState == 1 && (1 - bottomSheet.top.toFloat() / bottomSheet.height.toFloat()) < behavior.halfExpandedRatio) {
                    prevIsHalfState = -1
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                prevIsHalfState = if (prevIsHalfState == -1) 0 else 1
                MapManager.instance.map.zoomPadding.bottom =
                    (GlobalTools.instance.displayMetrics.heightPixels - bottomSheet.top + params.height + params.bottomMargin).toFloat()
            }
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        pinContent.translationY = -slideOffset * (bottomSheet.height - behavior.peekHeight - behavior.expandedOffset)
        window.statusBarColor = Color.argb(((0.65 * slideOffset + 0.35) * 255).toInt(), 30, 30, 30)
    }
}