package com.example.historicalpetersburg.map.entities

import com.example.historicalpetersburg.map.models.Coordinate

interface IHistoricalObject {
    var name: String
    var shortDesc: String
    var longDesc: String

    val coordinates: List<Coordinate>
    val groups: MutableList<Group>

    var completed: Boolean

    var imagesArrayId: Int

    fun select()

    fun hide()
    fun show()
}