package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.tools.value.Value

interface IHistoricalObjectData {
    val id: Int
    val name: Value<String>
    val shortDesc: Value<String>

    val coordinates: List<Coordinate>?
    val icon: Int

    var visible: Boolean
    fun select()
}