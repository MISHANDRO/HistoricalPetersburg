package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.example.historicalpetersburg.map.main.views.bottomsheet.PlaceBottomSheet
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.image.ImageArray
import com.example.historicalpetersburg.tools.value.Value
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PlaceData(
    override val id: Int,
    val placeId: Int,
    override val name: Value<String>,
    override val shortDesc: Value<String>
) : IHistoricalObjectData {

    var placemark: IPlacemark? = null
        set(value) {
            field = value
            field?.setAction {
                select()
                true
            }
            if (field != null) {
                coordinates = null
            }
        }

    override var visible: Boolean = true
        set(value) {
            field = value
            placemark?.visibility = field
        }

    override var coordinates: List<Coordinate>? = null
        get() {
            if (placemark == null) return field
            return listOf(placemark!!.coordinate)
        }

    override var icon: Int = -1

    var images: ImageArray? = null

    override fun select() { // TODO Visitor
        if (MapManager.instance.objectManager.selectedHistoricalObject?.id == id) {
            return
        }
        MapManager.instance.objectManager.selectedHistoricalObject = this

        coordinates?.let {
            MapManager.instance.map.zoom(it)
        }
        MapManager.instance.objectManager.hideAll()
        visible = true

        MapManager.instance.mapFragment.bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED

        MapManager.instance.locationManager.follow = false

        PlaceBottomSheet(this).show(GlobalTools.instance.fragmentManager)
    }
}