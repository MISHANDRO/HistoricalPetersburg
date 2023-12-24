package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.activities.FullscreenImageViewerActivity
import com.example.historicalpetersburg.databinding.BottomSheetRouteInfoBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageAdapter
import com.example.historicalpetersburg.tools.image.ImageArray
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RouteInfoContentBottomSheet(private val routeData: RouteData?) : CustomDialog() {
    private lateinit var binding: BottomSheetRouteInfoBinding

    override var peekHeight = 500
    override var maxHeight = 1800
    override var halfExpandedRatio = 0.5f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.bottom_sheet_route_info, container, false)
        binding = BottomSheetRouteInfoBinding.inflate(inflater, container, false)

        val buttonLayoutParams = binding.startButton.layoutParams as RelativeLayout.LayoutParams
        buttonLayoutParams.topMargin = peekHeight - binding.startButton.layoutParams.height
        binding.startButton.layoutParams = buttonLayoutParams
        onSlide(binding.root, 0.0f)

        binding.closeBtn.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.startButton.setOnClickListener {
//            startRoute()
            binding.content.alpha = 0f
        }

        return binding.root
    }

     override fun onSlide(bottomSheet: View, slideOffset: Float) {
        if (slideOffset < 0) return
        binding.bottomSheetCard.scaleX = (0.5f + (slideOffset * 0.5f))
        binding.bottomSheetCard.scaleY = (0.5f + (slideOffset / 2))
        binding.bottomSheetCard.radius = (1 - slideOffset) * (binding.bottomSheetCard.height / 2)

        binding.startButton.translationY = (bottomSheet.height - peekHeight) * slideOffset
    }

    override fun onDestroy() {
        super.onDestroy()

        MapManager.instance.objectManager.zoomShown()
    }
}