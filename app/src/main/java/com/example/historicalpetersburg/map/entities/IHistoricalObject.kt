package com.example.historicalpetersburg.map.entities

interface IHistoricalObject {
    var name: String
    var shortDesc: String
    var longDesc: String

    fun select(zoomPaddingX: Float = 150f,
               zoomPaddingY: Float = 150f,
               action: (() -> Unit)? = null)

    fun hide()
    fun show()
}