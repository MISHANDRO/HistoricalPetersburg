package com.example.historicalpetersburg.map.main.shape.style

import com.example.historicalpetersburg.R

enum class PlacemarkStyle(
    val icons: Array<PlacemarkIcon>
) {
    Default(arrayOf(
        PlacemarkIcon(R.drawable.ic_stale_circle, true,0.09f, 0.5f, 0.5f),
        PlacemarkIcon(R.drawable.ic_arrow, false,0.2f, 0.5f, 0.9f)
    )),
    Start1(arrayOf(
        PlacemarkIcon(R.drawable.ic_arrow, true,0.5f, 0.5f, 0.5f)
    ))
}