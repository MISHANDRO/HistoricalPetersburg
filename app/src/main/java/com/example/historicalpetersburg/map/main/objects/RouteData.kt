package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.example.historicalpetersburg.map.main.views.bottomsheet.RouteBottomSheet
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.tools.value.Value
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RouteData(
    override val id: Int,
    val routeId: Int,
    override val name: Value<String>,
    override val shortDesc: Value<String>
) : IHistoricalObjectData {

    var line: ILine? = null
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

    var startPlacemark: IPlacemark? = null
        set(value) {
            field = value
            field?.setAction {
                select()
                true
            }
        }

    override var coordinates: List<Coordinate>? = null
        get() {
            if (line == null) return field
            return line!!.coordinates
        }

    override var visible: Boolean = true
        set(value) {
            field = value
            line?.visibility = field
            startPlacemark?.visibility = field
        }

    override var icon: Int = -1

    override fun select() {
        MapManager.instance.locationManager.follow = false

        coordinates?.let {
            MapManager.instance.map.zoom(it)
        }
        MapManager.instance.objectManager.hideAll()
        visible = true

        MapManager.instance.mapFragment.bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED

        RouteBottomSheet(this)
            .show(GlobalTools.instance.fragmentManager)
    }
}
