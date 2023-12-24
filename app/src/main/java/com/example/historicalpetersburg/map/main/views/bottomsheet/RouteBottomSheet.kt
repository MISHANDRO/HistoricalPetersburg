package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.BottomSheetRouteBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.routeinspector.BottomSheetRouteViewVisitor
import com.example.historicalpetersburg.tools.GlobalTools
import com.google.android.material.bottomsheet.BottomSheetBehavior


class RouteBottomSheet(val routeData: RouteData) : CustomDialog() {
    lateinit var binding: BottomSheetRouteBinding
    var animation: Animation? = null

    var visitor: BottomSheetRouteViewVisitor? = null

    override var peekHeight = 500
    override var maxHeight = 1800
    override var halfExpandedRatio = 0.5f


    constructor() : this(MapManager.instance.objectManager.listOfAll[0] as RouteData)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.bottom_sheet_route, container, false)
        binding = BottomSheetRouteBinding.inflate(inflater, container, false)

        val buttonLayoutParams = binding.mainButton.layoutParams as RelativeLayout.LayoutParams
        buttonLayoutParams.topMargin = peekHeight - binding.mainButton.layoutParams.height
        binding.mainButton.layoutParams = buttonLayoutParams
        onSlide(binding.root, 0.0f)

        binding.closeBtn.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.mainButton.setOnClickListener {
            visitor = BottomSheetRouteViewVisitor(this)
            visitor!!.start()
        }

        return binding.root
    }

     override fun onSlide(bottomSheet: View, slideOffset: Float) {
        if (slideOffset < 0) return
        binding.bottomSheetCard.scaleX = (0.5f + (slideOffset * 0.5f))
        binding.bottomSheetCard.scaleY = (0.5f + (slideOffset / 2))
        binding.bottomSheetCard.radius = (1 - slideOffset) * (binding.bottomSheetCard.height / 2)

        binding.mainButton.translationY = (bottomSheet.height - peekHeight) * slideOffset
    }

    override fun onDestroy() {
        super.onDestroy()

        MapManager.instance.objectManager.zoomShown()
    }
}