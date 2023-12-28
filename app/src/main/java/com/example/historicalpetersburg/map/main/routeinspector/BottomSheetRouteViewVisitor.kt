package com.example.historicalpetersburg.map.main.routeinspector

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.activities.FullscreenImageViewerActivity
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.location.LocationUpdateListener
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.objects.PartRoute
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.example.historicalpetersburg.map.main.views.bottomsheet.RouteBottomSheet
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetRouteViewVisitor(private var bottomSheet: RouteBottomSheet) : IRouteInspectorVisitor {
    private val fadeOut = ValueAnimator.ofFloat(1f, 0f)
    private val fadeIn = ValueAnimator.ofFloat(0f, 1f)
    private val duration = 200L

    private var curPlacemark: IPlacemark? = null
    private var nextPartRoute: Int? = null

    private val minDistanceMetres = 20

    private val binding = bottomSheet.binding
    var listener: LocationUpdateListener
    var alertDialog: Dialog? = null

    var followDistanceTextRender: ((Coordinate) -> Unit)? = null

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
        listener = object : LocationUpdateListener() {
            override fun onLocationUpdated(coordinate: Coordinate) {
                followDistanceTextRender?.invoke(coordinate)
            }

            override fun onLocationStatusUpdated(isAvailable: Boolean) {
                println(isAvailable)
                if (!isAvailable) {
                    // TODO
                    val builder = AlertDialog.Builder(bottomSheet.requireContext())
                    builder.setTitle("Заголовок Alert Dialog")
                    builder.setMessage("Нельзя без локации!")
                    builder.setCancelable(false)

                    alertDialog = builder.create()
                    alertDialog?.show()
                } else {
                    alertDialog?.dismiss()
                }
            }
        }
    }

    override fun visit(state: InactiveState) {
        curPlacemark?.let { MapManager.instance.map.deletePlaceMark(it) }
        MapManager.instance.locationManager.locationListeners.remove(listener)
        followDistanceTextRender = null
        bottomSheet.visitor = null

        if (nextPartRoute == null) {
            bottomSheet.behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            val builder = AlertDialog.Builder(bottomSheet.requireContext(), R.style.TransparentDialog)

            val view = LayoutInflater.from(bottomSheet.requireContext()).inflate(R.layout.alert_one_button, null)
            builder.setView(view)
            builder.setOnCancelListener {
                bottomSheet.close()
            }
            val alert = builder.create()
            view.findViewById<Button>(R.id.alert_btn).apply {
                setOnClickListener {
                    alert.cancel()
                }
                text = bottomSheet.resources.getString(R.string.continue_text)
            }
            view.findViewById<TextView>(R.id.alert_title).text = bottomSheet.requireContext().getString(
                R.string.thanks_for_complete_route_title, bottomSheet.routeData.name.value
            )
            view.findViewById<TextView>(R.id.alert_body).text = bottomSheet.resources.getString(R.string.thanks_for_complete_route_body)
            alert.show()
        } else {
            bottomSheet.close()
        }
    }

    override fun visit(state: PathState) {
        markPart(state.partRoute)
        startAnimation {
            binding.name.text = state.partRoute.name.value
            binding.notMainName.text = state.partRoute.name.value

            binding.mainText.text = bottomSheet.resources.getString(R.string.to_next_part, state.partRoute.preview?.value)

            binding.imageLayer.visibility = View.GONE
        }

        var distance = Coordinate.getDistance(MapManager.instance.locationManager.curPosition!!, state.partRoute.coordinate)
        binding.mainButton.apply {
            text = bottomSheet.requireContext().getString(R.string.go_to_cur_point, distance)
            setBackgroundColor(Color.BLACK)
        }

        followDistanceTextRender = { coordinate ->
            distance = Coordinate.getDistance(coordinate, state.partRoute.coordinate)
            binding.mainButton.text = bottomSheet.requireContext().getString(R.string.go_to_cur_point, distance)

            binding.mainButton.apply {
                if (distance <= minDistanceMetres) {
                    setBackgroundColor(Color.BLUE)
                    isEnabled = true
                } else {
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                    isEnabled = false
                }
//                isEnabled = true // DEBUG
            }
        }
    }

    override fun visit(state: PartState) {
        markPart(state.partRoute)

        followDistanceTextRender = null
        startAnimation {
            binding.name.text = state.partRoute.name.value
            binding.notMainName.text = state.partRoute.name.value

            binding.mainText.text = state.partRoute.text.value

            binding.imageLayer.visibility = if (state.partRoute.images != null) View.VISIBLE else View.GONE

            state.partRoute.images?.let {
                binding.bottomSheetRouteImage.adapter = ImageAdapter(it).apply {
                    onItemClick = { position ->

                        val intent = Intent(GlobalTools.instance.activity, FullscreenImageViewerActivity::class.java)
                        intent.putExtra("current_position", position)
                        intent.putExtra("current_array", it.imageVals)
                        GlobalTools.instance.activity.startActivity(intent)
                    }
                    isZoomableOnItem = false

                    scaleTypeOnItem = ImageView.ScaleType.CENTER_CROP
                }
            }
            binding.numberOfPhoto.text = bottomSheet.requireContext().getString(R.string.image_viewer_of, 1, state.partRoute.images?.size ?: 0)
        }

        binding.mainButton.text = GlobalTools.instance.getString(
            (if (state.partRoute.nextId == null) R.string.finish_route_btn else R.string.continue_route_btn)
        )
    }

    fun start() {
        val curPosition = MapManager.instance.locationManager.curPosition
        if (curPosition == null) {
            GlobalTools.instance.let {
                it.toast(it.getString(R.string.location_not_available))
            }
            return
        }

        val startCoordinate = bottomSheet.routeData.startPlacemark!!.coordinate
        if (!isMinDistance(curPosition, startCoordinate)) {
            val builder = AlertDialog.Builder(bottomSheet.requireContext(), R.style.TransparentDialog)

            val view = LayoutInflater.from(bottomSheet.requireContext()).inflate(R.layout.alert_one_button, null)
            builder.setView(view)
            view.findViewById<Button>(R.id.alert_btn).apply {
                setOnClickListener {
                    val uri = Uri.parse("geo:${startCoordinate.latitude},${startCoordinate.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                    bottomSheet.startActivity(mapIntent)
                }
                text = bottomSheet.resources.getString(R.string.open_map_btn)
            }
            view.findViewById<TextView>(R.id.alert_title).text = bottomSheet.requireContext()
                .getString(R.string.suggest_to_come_start_title)

            view.findViewById<TextView>(R.id.alert_body).text = bottomSheet.resources
                .getString(R.string.suggest_to_come_start_body)
            builder.create().show()

            return
        }

        MapManager.instance.routeInspector.apply {
            visitor = this@BottomSheetRouteViewVisitor
            start(bottomSheet.routeData)
        }

        binding.mainButton.setOnClickListener {
            MapManager.instance.routeInspector.toNextPart()
        }

        binding.closeBtn.setOnClickListener {
            val builder = AlertDialog.Builder(bottomSheet.requireContext(), R.style.TransparentDialog)

            val view = LayoutInflater.from(bottomSheet.requireContext()).inflate(R.layout.alert_one_button, null)
            builder.setView(view)
            val alert = builder.create()
            view.findViewById<Button>(R.id.alert_btn).apply {
                setOnClickListener {
                    MapManager.instance.routeInspector.stop()
                    bottomSheet.close()
                    alert.cancel()
                }
                text = bottomSheet.resources.getString(R.string.stop_route_btn)
                setBackgroundColor(Color.RED)
                setTextColor(Color.WHITE)
            }
            view.findViewById<TextView>(R.id.alert_title).text = bottomSheet.requireContext().getString(R.string.stop_route_title)
            view.findViewById<TextView>(R.id.alert_body).text = bottomSheet.resources.getString(R.string.stop_route_body)
            alert.show()
        }

        bottomSheet.behavior.apply {
            isHideable = false

        }

        bottomSheet.routeData.startPlacemark?.visibility = false

        MapManager.instance.locationManager.locationListeners.add(listener)
    }

    private fun isMinDistance(coordinate1: Coordinate, coordinate2: Coordinate): Boolean {
        return Coordinate.getDistance(coordinate1, coordinate2) <= minDistanceMetres // DEBUG
//        return true
    }

    private fun markPart(partRoute: PartRoute) {
        nextPartRoute = partRoute.nextId

        curPlacemark?.let { MapManager.instance.map.deletePlaceMark(it) }
        curPlacemark = MapManager.instance.map.addPlacemark(partRoute.coordinate).apply {
            style = bottomSheet.routeData.partRouteStyle!!
        }

        MapManager.instance.locationManager.curPosition?.let { curPosition ->
            MapManager.instance.map.zoom(listOf(curPosition, partRoute.coordinate))
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