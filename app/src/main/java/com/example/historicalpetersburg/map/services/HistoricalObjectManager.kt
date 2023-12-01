package com.example.historicalpetersburg.map.services

import com.example.historicalpetersburg.map.entities.IHistoricalObject
import com.example.historicalpetersburg.map.entities.Place
import com.example.historicalpetersburg.map.entities.Route

class HistoricalObjectManager {
    private val list = mutableListOf<IHistoricalObject>()

    val listOfAll: List<IHistoricalObject>
        get() = list.toList()

    val listOfShow = mutableListOf<>()

    val routes: List<Route>
        get() = list.filterIsInstance<Route>()

    val places: List<Place>
        get() = list.filterIsInstance<Place>()
}