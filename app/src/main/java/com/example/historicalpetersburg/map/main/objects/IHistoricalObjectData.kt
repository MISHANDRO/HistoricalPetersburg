package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.tools.value.Value

interface IHistoricalObjectData {
    val id: Int
    val name: Value<String>
    val shortDesc: Value<String>

//    var visible: Boolean
    fun select()
}