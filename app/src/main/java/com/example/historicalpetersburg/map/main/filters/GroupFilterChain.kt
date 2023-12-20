package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData

class GroupFilterChain : HistoricalObjectFilterChainBase() {
    var group: Group? = null

    override fun Handle(historicalObject: IHistoricalObjectData): Boolean {
        if (group == null) {
            return true
        }

        return historicalObject.groups.contains(group)
    }
}