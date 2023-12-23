package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.view.children
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


class RouteInfoContentBottomSheet(private val routeData: RouteData?) : ContentExtraBottomSheetBase() {
    override var peekHeight: Int = 500
    override var maxHeight: Int = 1800
    override var halfExpandedRatio: Float = 0.5f

    private lateinit var binding: BottomSheetRouteInfoBinding

    constructor() : this(null) { // TODO
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

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

        buildRouteContent()

        binding.closeBtn.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.startButton.setOnClickListener {
            startRoute()
        }

        return binding.root
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        // TODO
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        if (slideOffset < 0) return
        binding.bottomSheetCard.scaleX = (0.5f + (slideOffset * 0.5f))
        binding.bottomSheetCard.scaleY = (0.5f + (slideOffset / 2))
        binding.bottomSheetCard.radius = (1 - slideOffset) * (binding.bottomSheetCard.height / 2)

        binding.startButton.translationY = (bottomSheet.height - peekHeight) * slideOffset
    }

    override fun close() {
        super.close()
        MapManager.instance.objectManager.zoomShown()
    }

    private fun startRoute() {
//        MapManager.instance.routeInspector.start(routeData!!)
//        if (MapManager.)
    }

    private fun buildRouteContent() {
        binding.bottomSheetRouteName.text = routeData?.name.toString()

        // TODO собрать инфу как надо
        //        binding.bottomSheetRouteName.setOnClickListener {
//            val uri: Uri = Uri.parse("geo:40.7128,-74.0060")
//            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(mapIntent)
//        }
//
//        binding.bottomSheetRouteLongDesc.text = route.longDesc.value
//
//        binding.mainContentOfRoute.setOnClickListener {
//            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
//        }
//
//        setImages()
    }

    private fun setImages() {
//        if (route.imagesArrayId == -1) {
//            binding.bottomSheetCard.visibility = View.GONE
//            return
//        }
//
//        val images = ImageArray(route.imagesArrayId)
//        val adapter = ImageAdapter(
//            images
//        ).apply {
//            onItemClick = { position ->
//
//                val intent = Intent(GlobalTools.instance.activity, FullscreenImageViewerActivity::class.java)
//                intent.putExtra("current_position", position)
//                intent.putExtra("current_array", GlobalTools.instance.getStringArray(route.imagesArrayId))
//                GlobalTools.instance.activity.startActivity(intent)
//            }
//            isZoomableOnItem = false
//
//            scaleTypeOnItem = ImageView.ScaleType.CENTER_CROP
//        }
//
//        binding.bottomSheetRouteImage.adapter = adapter
//        binding.bottomSheetRouteImage.children.find { it is RecyclerView }?.let {
//            (it as RecyclerView).isNestedScrollingEnabled = false
//        }
//
//        binding.bottomSheetRouteImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                val text = "${position + 1} из ${images.size}"
//                binding.numberOfPhoto.text = text
//            }
//        })
    }
}