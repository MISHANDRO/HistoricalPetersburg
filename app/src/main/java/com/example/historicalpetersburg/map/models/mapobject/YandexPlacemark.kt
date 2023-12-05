package com.example.historicalpetersburg.map.models.mapobject

import com.example.historicalpetersburg.GlobalTools
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.objectstyles.PlacemarkStyle
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider

class YandexPlacemark(
    private val placemarkObject: PlacemarkMapObject,
    override val coordinate: Coordinate,
    style: PlacemarkStyle = PlacemarkStyle.Default
) : IPlacemark {

    private val listener = YandexObjectTapListener()

    override var style: PlacemarkStyle = style
        set(value) {
            field = value
            placemarkObject.setIcon(
                GlobalTools.instance.getImage(value.imageId),
                IconStyle().apply {
                    scale = value.scale
                    flat = true // TODO как лучше?
                     // rotationType = RotationType.ROTATE TODO хрень или как?
                }
            )
    }

    init {
        placemarkObject.addTapListener(listener)
        this.style = style
    }

    override fun setAction(action: (Coordinate) -> Boolean) {
        listener.action = action
    }

    override fun show() {
        placemarkObject.isVisible = true
    }

    override fun hide() {
        placemarkObject.isVisible = false
    }
}