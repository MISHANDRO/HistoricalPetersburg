package com.example.historicalpetersburg.map.main.objects

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.Coordinate
import com.example.historicalpetersburg.tools.value.IValue

interface IHistoricalObject {
    var name: IValue<String>
    var shortDesc: IValue<String>
    var longDesc: IValue<String>

    val coordinates: List<Coordinate>
    val groups: MutableList<Group>

    var imagesArrayId: Int

    fun addGroupsById(groupIds: List<Int>) {
        MapManager.instance.objectManager.addObjectsToGroupsById(this, groupIds)
    }

    fun addGroups(group: List<Group>) {
        MapManager.instance.objectManager.addObjectsToGroups(this, group)
    }

    fun select()

    fun hide()
    fun show()
}