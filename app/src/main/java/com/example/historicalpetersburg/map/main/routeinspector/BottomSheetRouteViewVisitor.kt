package com.example.historicalpetersburg.map.main.routeinspector

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.animation.doOnEnd
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.location.LocationUpdateListener
import com.example.historicalpetersburg.map.main.views.bottomsheet.RouteBottomSheet
import com.example.historicalpetersburg.tools.GlobalTools

class BottomSheetRouteViewVisitor(private var bottomSheet: RouteBottomSheet) : IRouteInspectorVisitor {
    private val fadeOut = ValueAnimator.ofFloat(1f, 0f)
    private val fadeIn = ValueAnimator.ofFloat(0f, 1f)
    private val duration = 200L

    private val binding = bottomSheet.binding

    init {
        fadeOut.apply {
            duration = this@BottomSheetRouteViewVisitor.duration

            addUpdateListener {
                binding.content.alpha = it.animatedValue as Float
            }
        }
        fadeIn.apply {
            duration = this@BottomSheetRouteViewVisitor.duration

            addUpdateListener {
                binding.content.alpha = it.animatedValue as Float
            }
        }
    }

    override fun visit(state: InactiveState) {
//        TODO("Not yet implemented")
        GlobalTools.instance.toast("Thanks!!!")
        bottomSheet.visitor = null
        bottomSheet.close()
    }

    override fun visit(state: PathState) {
        startAnimation {
            binding.name.text = "Двигаем дальше"
            binding.mainText.text = state.partRoute.preview?.value
        }

        binding.mainButton.text = "Приступим"
    }

    override fun visit(state: PartState) {
        startAnimation {
            binding.name.text = state.partRoute.name.value
            binding.mainText.text = state.partRoute.text.value
        }

        binding.mainButton.text = GlobalTools.instance.getString(
            (if (state.partRoute.nextId == null) R.string.finish_route_btn else R.string.continue_route_btn)
        )
    }

    fun start() {
        val curPosition = MapManager.instance.locationManager.curPosition
        if (curPosition == null) {
            return
        }

        GlobalTools.instance.toast(MapManager.instance.map.getDistance(curPosition, bottomSheet.routeData.startPlacemark!!.coordinate))

        MapManager.instance.routeInspector.apply {
            visitor = this@BottomSheetRouteViewVisitor
            start(bottomSheet.routeData)
        }

        binding.mainButton.setOnClickListener {
            MapManager.instance.routeInspector.toNextPart()
        }
    }

    private fun startAnimation(actionToEnd: () -> Unit) {
        fadeOut.doOnEnd {
            actionToEnd.invoke()
            fadeIn.start()
        }

        fadeOut.start()
    }
}