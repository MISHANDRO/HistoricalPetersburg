package com.example.historicalpetersburg.map.main

import com.example.historicalpetersburg.map.main.models.AnimationZoomType
import com.example.historicalpetersburg.map.main.models.Camera
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.models.Padding
import com.example.historicalpetersburg.map.main.shape.ILine
import com.example.historicalpetersburg.map.main.shape.IMapObject
import com.example.historicalpetersburg.map.main.shape.IPlacemark

interface IMapService {
    var zoomPadding: Padding
    var zoomDuration: Float

    val camera: Camera

    fun zoom(step: Float, duration: Float = zoomDuration)
    fun zoom(coordinate: Coordinate, zoomValue: Float, duration: Float = zoomDuration, type: AnimationZoomType = AnimationZoomType.SMOOTH)
    fun zoom(coordinates: List<Coordinate>, duration: Float = zoomDuration, type: AnimationZoomType = AnimationZoomType.SMOOTH)

    fun addLine(coordinates: List<Coordinate>, action: (() -> Unit)? = null): ILine
    fun addCircle(coordinate: Coordinate, radius: Float = 2.5f): Any
    fun addPlacemark(coordinate: Coordinate): IPlacemark

    fun deletePlaceMark(placemark: IPlacemark)

    fun addCameraPositionChangedListener(action: () -> Unit)
}