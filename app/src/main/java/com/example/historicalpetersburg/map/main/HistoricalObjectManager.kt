package com.example.historicalpetersburg.map.main

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.IHistoricalObjectFilterChain
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObject
import com.example.historicalpetersburg.map.main.objects.Place
import com.example.historicalpetersburg.map.main.objects.Route

class HistoricalObjectManager {
    val list = mutableListOf<IHistoricalObject>()
    val groups = mutableListOf<Group>()

    var filterChain: IHistoricalObjectFilterChain? = null

    val listOfAll: List<IHistoricalObject>
        get() = list.toList()

    val listOfShown = mutableListOf<IHistoricalObject>()

    val routes: List<Route>
        get() = list.filterIsInstance<Route>()

    val places: List<Place>
        get() = list.filterIsInstance<Place>()

    fun addGroup(): Group {
        val newGroup = Group().apply {
            id = groups.size
        }
        groups.add(newGroup)
        return newGroup
    }

    fun addRoute(coordinates: List<Coordinate>): Route {
        val newRoute = Route(coordinates)
        list.add(newRoute)

        return newRoute
    }

    fun addPlace(coordinate: Coordinate, groups: List<Group> = emptyList()): Place {
        val newPlace = Place(coordinate)
        list.add(newPlace)

        return newPlace
    }

    fun addObjectsToGroupsById(historicalObject: IHistoricalObject, groupIds: List<Int>) {
        for (id in groupIds) {
            historicalObject.groups += groups[id]
            groups[id].historicalObjects += historicalObject
        }
    }

    fun addObjectsToGroups(historicalObject: IHistoricalObject,  groups: List<Group>) {
        for (group in groups) {
            historicalObject.groups += group
            group.historicalObjects += historicalObject
        }
    }

    fun showAll(){
        list.forEach { it.show() }
    }

    fun hideAll(){
        list.forEach { it.hide(); println(it) }
    }

    fun updateShown() {
        listOfShown.clear()
        list.forEach {
            if (filterChain?.isNormal(it) == true) {
                listOfShown += it
            }
        }
    }

    fun zoomShown() {
        val coordinates = mutableListOf<Coordinate>()
        listOfShown.forEach { coordinates += it.coordinates }

        hideAll()
        listOfShown.forEach { it.show() }

        if (coordinates.isEmpty()) return
        MapManager.instance.map.zoom(coordinates)
    }
}