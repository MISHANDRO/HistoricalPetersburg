package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.tools.image.ImageArray
import com.example.historicalpetersburg.tools.value.Value

class PartRoute(
    val id: Int,
    val name: Value<String>,
    val text: Value<String>,
    val coordinate: Coordinate
) {
    var nextId: Int? = null
    var preview: Value<String>? = null
    var images: ImageArray? = null
}