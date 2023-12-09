package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.activities.FullscreenImageViewerActivity
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.databinding.BottomSheetRouteInfoBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.Route
import com.example.historicalpetersburg.tools.ImageAdapter
import com.example.historicalpetersburg.tools.value.ImageVal
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RouteInfoContentBottomSheet(private val route: Route) : ContentExtraBottomSheetBase() {
    override var peekHeight: Int = 500
    override var maxHeight: Int = 1800
    override var halfExpandedRatio: Float = 0.5f

    private lateinit var binding: BottomSheetRouteInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.bottom_sheet_route_info, container, false)

        binding = BottomSheetRouteInfoBinding.inflate(inflater, container, false)

        binding.bottomSheetRouteName.text = route.name.value

        binding.bottomSheetRouteName.setOnClickListener {
            val uri: Uri = Uri.parse("geo:40.7128,-74.0060")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(mapIntent)

        }

        binding.bottomSheetRouteLongDesc.text = route.longDesc.value
//        binding.bottomSheetRouteImage.setImageResource(R.drawable.icon_settings)

        binding.mainContentOfRoute.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }

        val images = arrayOf(ImageVal(R.drawable.menu_bg_png), ImageVal(R.drawable.night),
            ImageVal(R.drawable.piggy), ImageVal("https://klike.net/uploads/posts/2023-03/1678681102_3-47.jpg"))
        val adapter = ImageAdapter(
            images // TODO
        ).apply {
            onItemClick = { position ->
                val intent = Intent(GlobalTools.instance.activity, FullscreenImageViewerActivity::class.java)
                intent.putExtra("current_position", position)
                intent.putExtra("current_array", images)
                GlobalTools.instance.activity.startActivity(intent)
            }

            scaleTypeOnItem = ImageView.ScaleType.CENTER_CROP
        }

        binding.bottomSheetRouteImage.adapter = adapter

        binding.bottomSheetRouteImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val text = "${position + 1} из ${images.size}"
                binding.numberOfPhoto.text = text
            }
        })

        return binding.root
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        // TODO
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        //        binding.bottomSheetRouteImage.translationY = slideOffset * 100
//        binding.mainContentOfRoute.translationY = slideOffset * 100
        if (slideOffset < 0) return
        binding.bottomSheetCard.scaleX = (0.5f + (slideOffset * 0.5f))
        binding.bottomSheetCard.scaleY = (0.5f + (slideOffset / 2))
        binding.bottomSheetCard.radius = (1 - slideOffset) * (binding.bottomSheetCard.height / 2)
    }

    override fun close() {
        MapManager.instance.objectManager.zoomShown()
    }
}