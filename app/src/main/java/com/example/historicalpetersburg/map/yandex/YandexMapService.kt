package com.example.historicalpetersburg.map.yandex

import android.graphics.Point
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Camera
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.map.main.IMapService
import com.example.historicalpetersburg.map.main.Padding
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.PolylineBuilder
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.mapview.MapView

class YandexMapService(private val mapView: MapView) : IMapService {

    private val actionsCameraListener = mutableListOf<CameraListener>()

    override var zoomPadding = Padding()
    override var zoomDuration: Float = 0.2f

    override val camera: Camera = Camera()
        get() {
            mapView.map.cameraPosition.let {
                field.target = Coordinate.fromYandexPoint(it.target)
                field.zoom = it.zoom
                field.azimuth = it.azimuth
                field.tilt = it.tilt
            }
            return field
        }

    override fun zoom(step: Float, duration: Float) {

        val position = CameraPosition(
            mapView.map.cameraPosition.target,
            mapView.map.cameraPosition.zoom + step,
            camera.azimuth,
            camera.tilt
        )

        mapView.map.move(position, Animation(Animation.Type.LINEAR, duration), null)
    }

    override fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float) {

        val position = CameraPosition(
            coordinate.toYandexPoint(),
            zoomValue,
            camera.azimuth,
            camera.tilt
        )

        mapView.map.move(position, Animation(Animation.Type.SMOOTH, duration), null)
    }

    override fun zoom(coordinates: List<Coordinate>, duration: Float) {
        if (coordinates.isEmpty()) {
            return
        }

        if (coordinates.size == 1) {
            zoom(coordinates[0], 16f, duration)
            return
        }

        val points = coordinates.map { it.toYandexPoint() }.toMutableList()

        mapView.mapWindow.focusRect = ScreenRect(
            ScreenPoint(zoomPadding.left, zoomPadding.top),
            ScreenPoint(
                mapView.mapWindow.width().toFloat() - zoomPadding.right,
                mapView.mapWindow.height().toFloat() - zoomPadding.bottom,
            )
        )

        val position = mapView.map.cameraPosition(
            Geometry.fromPolyline(Polyline(points)),
        )

        mapView.map.move(position, Animation(Animation.Type.SMOOTH, duration), null)
    }

    override fun addLine(coordinates: List<Coordinate>): ILine {
        val builder = PolylineBuilder()
        for (coordinate in coordinates) {
            builder.append(coordinate.toYandexPoint())
        }

        val polyline = builder.build()
        val polylineObject = mapView.map.mapObjects.addPolyline(polyline)

        return YandexLine(polylineObject, coordinates)
    }

    override fun addCircle(coordinate: Coordinate, radius: Float): Any {
        val circle = Circle(coordinate.toYandexPoint(), radius)
        return mapView.map.mapObjects.addCircle(circle)
    }

    override fun addPlacemark(coordinate: Coordinate): IPlacemark {
        val placemarkObject = mapView.map.mapObjects.addPlacemark().apply {
            geometry = coordinate.toYandexPoint()
        }

        return YandexPlacemark(placemarkObject, coordinate)
    }

    override fun deleteObject(mapObject: Any) {
        mapView.map.mapObjects.remove(mapObject as MapObject)
    }

    override fun addCameraPositionChangedListener(action: () -> Unit) {
        actionsCameraListener.add(CameraListener { _, _, reason, _ ->
            if (reason == CameraUpdateReason.GESTURES)
                action.invoke()
        })
        mapView.map.addCameraListener(actionsCameraListener.last())
    }
}