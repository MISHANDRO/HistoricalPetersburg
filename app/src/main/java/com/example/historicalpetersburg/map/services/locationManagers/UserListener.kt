package com.example.historicalpetersburg.map.services.locationManagers

import android.graphics.Color
import android.graphics.PointF
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider


class UserListener() : UserLocationObjectListener {
    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationView.arrow.setIcon(ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow1))
        userLocationView.arrow.setIconStyle(IconStyle().setRotationType(RotationType.ROTATE))

//        val picIcon = userLocationView.pin.useCompositeIcon()
//
//        picIcon.setIcon("icon", ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow1),
//            IconStyle().setAnchor(PointF(0f, 0f))
//                .setRotationType(RotationType.ROTATE).setZIndex(0f).setScale(1f)
//        )

//        picIcon.setIcon("pin", ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow1),
//            IconStyle().setAnchor(PointF(0.5f, 05f)).setRotationType(RotationType.ROTATE).setZIndex(1f).setScale(0.5f)
//        )
        userLocationView.accuracyCircle.fillColor = Color.GRAY




//        val pinIcon = userLocationView.pin.useCompositeIcon()

        // Задание основного изображения для метки местоположения
//        pinIcon.setIcon(
//            "pin",
//            ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow),
//            IconStyle()
//        )

        // Настройка изображения направления (стрелки)
//        userLocationView.arrow.setIcon(ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow1))
//        userLocationView.accuracyCircle.fillColor = Color.GRAY
    }

    override fun onObjectRemoved(p0: UserLocationView) {
//        TODO("Not yet implemented")
        MapManager.instance.toast("Удалились")
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
//        TODO("Not yet implemented")
        MapManager.instance.toast("Переместились")

    }
}