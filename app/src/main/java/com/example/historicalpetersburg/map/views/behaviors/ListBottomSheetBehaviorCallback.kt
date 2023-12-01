package com.example.historicalpetersburg.map.views.behaviors

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ListBottomSheetBehaviorCallback(
    private val pinContent: View,
    private val margin: Int,
    private var behavior: BottomSheetBehavior<LinearLayout>) : BottomSheetBehavior.BottomSheetCallback()
{
    private val params = pinContent.layoutParams as ViewGroup.MarginLayoutParams

    init {
        params.bottomMargin = behavior.peekHeight
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                // Нижнее окно свернуто
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                // Нижнее окно развернуто
            }
            BottomSheetBehavior.STATE_HIDDEN -> {
                // Нижнее окно скрыто
            }
            BottomSheetBehavior.STATE_DRAGGING -> {
                // Пользователь перетаскивает нижнее окно
            }
            BottomSheetBehavior.STATE_SETTLING -> {
                // Нижнее окно выполняет анимацию изменения состояния
                val cur = 1 - bottomSheet.top.toFloat() / behavior.maxHeight.toFloat();
                if (behavior.halfExpandedRatio - .15f < cur && cur < behavior.halfExpandedRatio + .15f) {
                    behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                }
            }
            // Другие состояния, если необходимо
            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                TODO
            }
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        params.bottomMargin = bottomSheet.height - bottomSheet.top + margin
        pinContent.requestLayout()
        println(bottomSheet.height - bottomSheet.top)
    }
}