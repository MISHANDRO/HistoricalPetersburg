package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.views.bottomsheet.RouteInfoContentBottomSheet
import com.example.historicalpetersburg.tools.value.Value
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RouteData(
    override val id: Int,
    override val name: Value<String>,
    override val shortDesc: Value<String>,
) : IHistoricalObjectData {

    var line: ILine? = null
        set(value) {
            field = value
            field?.setAction {
                select()
                true
            }
        }

    override var visible: Boolean = true
        set(value) {
            field = value
            line?.visibility = field
        }

    override fun select() { // TODO отбить гвозди
//        MapManager.instance.locationManager.follow = false
//
//        MapManager.instance.map.zoom(coordinates)
//        MapManager.instance.objectManager.hideAll()
//        show()

        MapManager.instance.mapFragment.bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED

        RouteInfoContentBottomSheet(this).show()
    }
}
