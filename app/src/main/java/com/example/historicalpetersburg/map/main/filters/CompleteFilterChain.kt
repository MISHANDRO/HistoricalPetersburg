package com.example.historicalpetersburg.map.main.filters

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData
import com.example.historicalpetersburg.map.main.objects.PlaceData
import com.example.historicalpetersburg.map.main.objects.RouteData

class CompleteFilterChain(val completed: Boolean) : HistoricalObjectFilterChainBase() {

    var active = false
    override fun handle(historicalObject: IHistoricalObjectData): Boolean {
        if (!active) return true

        // TODO
        if (historicalObject is RouteData) {
            val allParts = MapManager.instance.objectManager.routeRepository.getAllPartIdsByRoute(historicalObject)
            val allCompletedParts = MapManager.instance.userManager.repository.getCompletePartsByRoute(historicalObject)
            return (allParts.size == allCompletedParts.size) == completed
        }

        if (historicalObject is PlaceData) {
            return MapManager.instance.userManager.repository.isPlaceCompleted(historicalObject) == completed
        }

        return false
    }
}