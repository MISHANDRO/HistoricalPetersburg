package com.example.historicalpetersburg.map.main

import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.shape.IPlacemark

interface IMapService {
    var zoomPadding: Padding
    var zoomDuration: Float

    val camera: Camera

    fun zoom(step: Float, duration: Float = zoomDuration)
    fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float = zoomDuration)
    fun zoom(coordinates: List<Coordinate>, duration: Float = zoomDuration)

    fun addLine(coordinates: List<Coordinate>): ILine
    fun addCircle(coordinate: Coordinate, radius: Float = 2.5f): Any
    fun addPlacemark(coordinate: Coordinate): IPlacemark

    fun deleteObject(mapObject: Any)

    fun addCameraPositionChangedListener(action: () -> Unit)
}