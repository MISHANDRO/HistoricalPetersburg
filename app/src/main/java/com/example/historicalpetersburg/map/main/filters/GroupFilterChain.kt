package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData

class GroupFilterChain : HistoricalObjectFilterChainBase() {
    var group: Group? = null

    override fun handle(historicalObject: IHistoricalObjectData): Boolean {
        if (group == null) {
            return true
        }

        return MapManager.instance.objectManager.groupRepository.objectInGroup(historicalObject, group!!)
    }
}