package com.example.historicalpetersburg.map.yandex

import android.graphics.PointF
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.example.historicalpetersburg.tools.GlobalTools
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.shape.style.PlacemarkStyle
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.RotationType

class YandexPlacemark(
    val placemarkObject: PlacemarkMapObject,
    override val coordinate: Coordinate,
    style: PlacemarkStyle = PlacemarkStyle.Default
) : IPlacemark {

    override var visibility: Boolean = true
        set(value) {
            field = value
            placemarkObject.isVisible = field
        }

    override var direction: Float
        get() = placemarkObject.direction
        set(value) { placemarkObject.direction = value }

    private val listener = YandexObjectTapListener()

    override var style: PlacemarkStyle = style
        set(value) {
            field = value
            for (icon in style.icons) {
                placemarkObject.useCompositeIcon().setIcon(
                    icon.toString(),
                    GlobalTools.instance.getImage(icon.imageId),
                    IconStyle().apply {
                        scale = icon.scale
                        flat = icon.flat
                        anchor = PointF(icon.anchorX, icon.anchorY)
                        rotationType = RotationType.ROTATE
                    }
                )
            }
        }

    init {
        placemarkObject.addTapListener(listener)
    }

    override fun setAction(action: (Coordinate) -> Boolean) {
        listener.action = action
    }
}