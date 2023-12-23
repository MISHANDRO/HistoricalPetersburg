package com.example.historicalpetersburg.map.main.shape.style

import android.graphics.Color

enum class RouteStyle(
    val strokeWidth: Float,
    val strokeColor: Int,
    val outlineWidth: Float,
    val outlineColor: Int,
) {
    Default(3f, Color.BLUE, 1f, Color.BLACK),
    RED(5f, Color.RED, 1f, Color.BLACK)
}