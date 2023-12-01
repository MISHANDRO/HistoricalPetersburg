package com.example.historicalpetersburg.map.services.mapServices

import com.example.historicalpetersburg.map.models.Coordinate

interface IMapService {
    var zoomPaddingX: Float
    var zoomPaddingY: Float
    var zoomDuration: Float

    fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float = zoomDuration)
    fun zoom(coordinates: List<Coordinate>, duration: Float = zoomDuration)

    fun addLine(coordinates: List<Coordinate>): Any
    fun addCircle(coordinate: Coordinate, radius: Float = 2.5f): Any
    fun addPlacemark(coordinate: Coordinate): Any

    fun deleteObject(mapObject: Any)

    fun addCameraPositionChangedListener(action: () -> Unit)
}