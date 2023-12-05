package com.example.historicalpetersburg.map.models.mapobject.objectstyles

import android.graphics.Color

enum class RouteStyle(
    val strokeWidth: Float,
    val strokeColor: Int,

    val outlineWidth: Float,
    val outlineColor: Int,
) {
    Default(5f, Color.BLUE, 1f, Color.BLACK)
}