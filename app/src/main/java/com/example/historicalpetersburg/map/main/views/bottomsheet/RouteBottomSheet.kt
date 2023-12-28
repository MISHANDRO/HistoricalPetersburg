package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.activities.FullscreenImageViewerActivity
import com.example.historicalpetersburg.databinding.BottomSheetHistoricalObjectBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.routeinspector.BottomSheetRouteViewVisitor
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageAdapter
import com.example.historicalpetersburg.tools.image.ImageArray
import com.google.android.material.bottomsheet.BottomSheetBehavior


class RouteBottomSheet(val routeData: RouteData) : CustomDialog() {
    lateinit var binding: BottomSheetHistoricalObjectBinding

    var visitor: BottomSheetRouteViewVisitor? = null

    override var peekHeight = 540
    override var maxHeight = 1900
    override var halfExpandedRatio = 0.5f

//    constructor() : this(MapManager.instance.objectManager.listOfAll[0] as RouteData)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.bottom_sheet_historical_object, container, false)
        binding = BottomSheetHistoricalObjectBinding.inflate(inflater, container, false)

        val buttonLayoutParams = binding.mainButton.layoutParams as RelativeLayout.LayoutParams
        buttonLayoutParams.topMargin = peekHeight - binding.mainButton.layoutParams.height
        binding.mainButton.layoutParams = buttonLayoutParams

        binding.closeBtn.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.mainButton.setOnClickListener {
            visitor = BottomSheetRouteViewVisitor(this)
            visitor!!.start()
        }

        binding.bottomSheetRouteImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val text = requireContext().getString(
                    R.string.image_viewer_of, position + 1, binding.bottomSheetRouteImage.adapter?.itemCount)
                binding.numberOfPhoto.text = text
            }
        })

        build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomSheetCard.scaleX = 0.5f
        binding.bottomSheetCard.scaleY = 0.5f
        binding.numberOfPhoto.alpha = 0f

//        behavior.peekHeight =
        view.setOnTouchListener { _, _ ->
            true
        }
    }

     override fun onSlide(bottomSheet: View, slideOffset: Float) {
         if (slideOffset < 0) return
         binding.bottomSheetCard.scaleX = (0.5f + (slideOffset * 0.5f))
         binding.bottomSheetCard.scaleY = (0.5f + (slideOffset / 2))
         binding.bottomSheetCard.radius = (1 - slideOffset) * (binding.bottomSheetCard.height / 2)

         binding.mainButton.translationY = (bottomSheet.height - peekHeight) * slideOffset
         binding.numberOfPhoto.alpha = slideOffset
         binding.notMainName.alpha = 1 - slideOffset * 3
    }

    override fun onDestroy() {
        super.onDestroy()

        MapManager.instance.objectManager.let {
            it.selectedHistoricalObject = null
            it.updateShown()
            it.zoomShown()
        }
    }

    private fun build() {
        val images = ImageArray()

        val completePartIds = MapManager.instance.userManager.repository.getCompletePartsByRoute(routeData)
        var text = SpannableStringBuilder()
        text.append(routeData.shortDesc.value)
        text.appendLine()
        text.appendLine()
        text.appendLine()

        for (partRouteId in completePartIds) {
            val partRoute = MapManager.instance.objectManager.routeRepository.getPartById(partRouteId)!!
            partRoute.images?.let { images += it }

            val name = SpannableString(partRoute.name.value).apply {
                setSpan(Typeface.BOLD, 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(RelativeSizeSpan(1.4f), 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            text.append(name)
            text.append(": ")
            text.appendLine()
            text.append(partRoute.text.value)
            text.appendLine()
            text.appendLine()
        }
        setImages(images)

        binding.name.text = routeData.name.value
        binding.notMainName.text = routeData.name.value
        binding.mainText.text = text
    }

    private fun setImages(imageArray: ImageArray) {
        if (imageArray.size == 0) {
            binding.imageLayer.visibility = View.GONE
            return
        }

        val adapter = ImageAdapter(
            imageArray
        ).apply {
            onItemClick = { position ->

                val intent = Intent(GlobalTools.instance.activity, FullscreenImageViewerActivity::class.java)
                intent.putExtra("current_position", position)
                intent.putExtra("current_array", imageArray.imageVals)
                GlobalTools.instance.activity.startActivity(intent)
            }
            isZoomableOnItem = false

            scaleTypeOnItem = ImageView.ScaleType.CENTER_CROP
        }

        binding.bottomSheetRouteImage.adapter = adapter
    }
}