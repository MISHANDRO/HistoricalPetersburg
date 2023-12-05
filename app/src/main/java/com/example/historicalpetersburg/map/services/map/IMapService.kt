package com.example.historicalpetersburg.map.services.map

import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.models.mapobject.ILine
import com.example.historicalpetersburg.map.models.mapobject.IPlacemark

interface IMapService {
    var zoomPaddingX: Float
    var zoomPaddingY: Float
    var zoomDuration: Float

    fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float = zoomDuration)
    fun zoom(coordinates: List<Coordinate>, duration: Float = zoomDuration)

    fun addLine(coordinates: List<Coordinate>): ILine
    fun addCircle(coordinate: Coordinate, radius: Float = 2.5f): Any
    fun addPlacemark(coordinate: Coordinate): IPlacemark

    fun deleteObject(mapObject: Any)

    fun addCameraPositionChangedListener(action: () -> Unit)
}