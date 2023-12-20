package com.example.historicalpetersburg.map.main

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.IHistoricalObjectFilterChain
import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData
import com.example.historicalpetersburg.map.main.objects.Place
import com.example.historicalpetersburg.map.main.objects.RouteData
import com.example.historicalpetersburg.map.main.repositories.IGroupRepository

class HistoricalObjectManager {

    lateinit var groupRepository : IGroupRepository

    val list = mutableListOf<IHistoricalObjectData>()
    val groups = mutableListOf<Group>()

    var filterChain: IHistoricalObjectFilterChain? = null

    val listOfAll: List<IHistoricalObjectData>
        get() = list.toList()

    val listOfShown = mutableListOf<IHistoricalObjectData>()

    val routes: List<RouteData>
        get() = list.filterIsInstance<RouteData>()

    val places: List<Place>
        get() = list.filterIsInstance<Place>()

    fun addRoute(coordinates: List<Coordinate>): RouteData {
        val newRoute = RouteData(coordinates)
        list.add(newRoute)

        return newRoute
    }

    fun addPlace(coordinate: Coordinate, groups: List<Group> = emptyList()): Place {
        val newPlace = Place(coordinate)
        list.add(newPlace)

        return newPlace
    }

    fun addObjectsToGroupsById(historicalObject: IHistoricalObjectData, groupIds: List<Int>) {
        for (id in groupIds) {
            historicalObject.groups += groups[id]
            groups[id].historicalObjects += historicalObject
        }
    }

    fun addObjectsToGroups(historicalObject: IHistoricalObjectData, groups: List<Group>) {
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
        MapManager.instance.locationManager.follow = false
        MapManager.instance.map.zoom(coordinates)
    }
}