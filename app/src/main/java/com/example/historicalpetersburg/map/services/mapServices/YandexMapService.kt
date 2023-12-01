package com.example.historicalpetersburg.map.services.mapServices

import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.models.Coordinate
import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class YandexMapService(private val mapView: MapView) : IMapService {

    private val actionsCameraListener = mutableListOf<() -> Unit>()

    override var zoomPaddingX: Float = 150.0f
    override var zoomPaddingY: Float = 150.0f
    override var zoomDuration: Float = 0.2f

    override fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float) {

        val position = CameraPosition(
            coordinate.toYandexPoint(),
            zoomValue,
            0f,
            0f
        )

        mapView.map.move(position, Animation(Animation.Type.SMOOTH, duration), null)
    }

    override fun zoom(coordinates: List<Coordinate>, duration: Float) {
        if (coordinates.isEmpty()) {
            return
        }

        val points = coordinates.map { it.toYandexPoint() }

        val position = mapView.map.cameraPosition(
            Geometry.fromPolyline(Polyline(points)),
            0.0f,
            0.0f,
            ScreenRect(
                ScreenPoint(zoomPaddingX, zoomPaddingY),
                ScreenPoint(
                    mapView.mapWindow.width().toFloat() - zoomPaddingX,
                    mapView.mapWindow.height().toFloat() - zoomPaddingY,
                )
            )
        )

        mapView.map.move(position, Animation(Animation.Type.SMOOTH, duration), null)
    }

    override fun addLine(coordinates: List<Coordinate>): Any {
        val polyline = Polyline(coordinates.map { it.toYandexPoint() })
        return mapView.map.mapObjects.addPolyline(polyline)
    }

    override fun addCircle(coordinate: Coordinate, radius: Float): Any {
        val circle = Circle(coordinate.toYandexPoint(), radius)
        return mapView.map.mapObjects.addCircle(circle)
    }

    override fun addPlacemark(coordinate: Coordinate): Any {
        val placemark = mapView.map.mapObjects.addPlacemark().apply {
            geometry = coordinate.toYandexPoint() // TODO
            setIcon(ImageProvider.fromResource(MapManager.instance.activity, R.drawable.arrow1),
                IconStyle().apply {
                    rotationType = RotationType.ROTATE
                    scale = 1.5f
                }
            )
        }
        return placemark
    }

    override fun deleteObject(mapObject: Any) {
        mapView.map.mapObjects.remove(mapObject as MapObject)
    }

    override fun addCameraPositionChangedListener(action: () -> Unit) {
//        mapView.map.addCameraListener { map, cameraPosition, cameraUpdateReason, b ->
//            MapManager.instance.locationManager.notFollow()
//        } TODO
    }
}