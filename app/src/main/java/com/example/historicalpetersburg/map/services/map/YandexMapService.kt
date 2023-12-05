package com.example.historicalpetersburg.map.services.map

import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.ILine
import com.example.historicalpetersburg.map.models.mapobject.YandexLine
import com.example.historicalpetersburg.map.models.mapobject.IPlacemark
import com.example.historicalpetersburg.map.models.mapobject.YandexPlacemark
import com.yandex.mapkit.Animation
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.PolylineBuilder
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.mapview.MapView

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
        println("olm $placemarkObject")
        return YandexPlacemark(placemarkObject, coordinate)
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