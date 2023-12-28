package com.example.historicalpetersburg.map.yandex

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.main.models.Camera
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.IMapService
import com.example.historicalpetersburg.map.main.models.AnimationZoomType
import com.example.historicalpetersburg.map.main.models.Padding
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.shape.IMapObject
import com.example.historicalpetersburg.map.main.shape.IPlacemark
import com.yandex.mapkit.Animation
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.PolylineBuilder
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider

import kotlin.math.*

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

    override fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float, type: AnimationZoomType) {

        val position = CameraPosition(
            coordinate.toYandexPoint(),
            zoomValue,
            camera.azimuth,
            camera.tilt
        )

        mapView.map.move(position, Animation(
            if (type == AnimationZoomType.SMOOTH) Animation.Type.SMOOTH else Animation.Type.LINEAR,
            duration), null)
    }

    override fun zoom(coordinates: List<Coordinate>, duration: Float, type: AnimationZoomType) {
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

        mapView.map.move(position, Animation(
            if (type == AnimationZoomType.SMOOTH) Animation.Type.SMOOTH else Animation.Type.LINEAR,
            duration), null)
    }

    override fun addLine(coordinates: List<Coordinate>, action: (() -> Unit)?): ILine {
        val points = mutableListOf<RequestPoint>()
        for (coordinate in coordinates) {
            points += RequestPoint(coordinate.toYandexPoint(), RequestPointType.WAYPOINT, null, null)
        }
        val res = YandexLine(coordinates)

        val routeListener = object : Session.RouteListener {

            override fun onMasstransitRoutes(p0: MutableList<Route>) {
                res.polylineObject = mapView.map.mapObjects.addPolyline(p0[0].geometry)
                action?.invoke()
            }

            override fun onMasstransitRoutesError(p0: Error) { }
        }
        val pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter()
        pedestrianRouter.requestRoutes(points, TimeOptions(), routeListener)

//        val builder = PolylineBuilder()
//        for (coordinate in coordinates) {
//            builder.append(coordinate.toYandexPoint())
//        }
//
//        val polyline = builder.build()
//        val polylineObject = mapView.map.mapObjects.addPolyline(polyline)

        return res
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

    override fun deletePlaceMark(placemark: IPlacemark) {
        mapView.map.mapObjects.remove((placemark as YandexPlacemark).placemarkObject)
    }

    override fun addCameraPositionChangedListener(action: () -> Unit) {
        actionsCameraListener.add(CameraListener { _, _, reason, _ ->
            if (reason == CameraUpdateReason.GESTURES)
                action.invoke()
        })
        mapView.map.addCameraListener(actionsCameraListener.last())
    }
}