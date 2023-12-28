package com.example.historicalpetersburg.map.main.views.bottomsheet

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.activities.FullscreenImageViewerActivity
import com.example.historicalpetersburg.databinding.BottomSheetHistoricalObjectBinding
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageAdapter
import com.example.historicalpetersburg.tools.image.ImageArray
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yandex.mapkit.directions.Directions
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.geometry.Direction


class PlaceBottomSheet(val placeData: PlaceData) : CustomDialog() {
    lateinit var binding: BottomSheetHistoricalObjectBinding

    override var peekHeight = 540
    override var maxHeight = 1900
    override var halfExpandedRatio = 0.5f

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
            // TODO проверить и открыть инфу
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
        var isCompleted = MapManager.instance.userManager.repository.isPlaceCompleted(placeData)

        binding.name.text = placeData.name.value
        binding.notMainName.text = placeData.name.value

        binding.mainText.text = placeData.shortDesc.value

        setImages(placeData.images)

        if (isCompleted) {
            binding.mainButton.isEnabled = false
            binding.mainButton.text = resources.getString(R.string.place_already_complete_btn)

            binding.mainText.maxLines = Int.MAX_VALUE
            binding.mainText.foreground = null
        } else {
            binding.mainButton.apply {
                text = resources.getString(R.string.place_btn)
                setOnClickListener {
                    userInPlace()
                }
            }
            binding.mainText.maxLines = 5
            binding.mainText.foreground = ContextCompat.getDrawable(requireContext(), R.drawable.foreground_gradient)
        }
    }

    private fun setImages(imageArray: ImageArray?) {
        if (imageArray == null || imageArray.size == 0) {
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

    fun userInPlace() {
        val curPosition = MapManager.instance.locationManager.curPosition
        if (curPosition == null) {
            GlobalTools.instance.let {
                it.toast(it.getString(R.string.location_not_available))
            }
            return
        }

        val coordinate = placeData.coordinates!![0]
        if (!isMinDistance(curPosition, coordinate)) {
            val builder = AlertDialog.Builder(requireContext(), R.style.TransparentDialog)

            val view = LayoutInflater.from(requireContext()).inflate(R.layout.alert_one_button, null)
            builder.setView(view)
            view.findViewById<Button>(R.id.alert_btn).apply {
                setOnClickListener {
                    val uri = Uri.parse("geo:${coordinate.latitude},${coordinate.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(mapIntent)
                }
                text = resources.getString(R.string.open_map_btn)
            }
            view.findViewById<TextView>(R.id.alert_title).text = requireContext()
                .getString(R.string.suggest_to_come_place_title)

            view.findViewById<TextView>(R.id.alert_body).text = resources
                .getString(R.string.suggest_to_come_place_body)
            builder.create().show()

            return
        }

        MapManager.instance.userManager.repository.saveCompletePlace(placeData)
        build()
    }

    private fun isMinDistance(coordinate1: Coordinate, coordinate2: Coordinate): Boolean {
        return Coordinate.getDistance(coordinate1, coordinate2) <= 20
//        return true  // DEBUG
    }
}