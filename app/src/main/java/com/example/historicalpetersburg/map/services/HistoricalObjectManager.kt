package com.example.historicalpetersburg.map.services

import com.example.historicalpetersburg.GlobalTools
import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Group
import com.example.historicalpetersburg.map.entities.IHistoricalObject
import com.example.historicalpetersburg.map.entities.Place
import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate
import java.lang.reflect.Type

class HistoricalObjectManager {
    val list = mutableListOf<IHistoricalObject>()
    val groups = mutableListOf<Group>()

    val shownGroups = mutableListOf<Group>()
    var shownType: Type = IHistoricalObject::class.java

    val listOfAll: List<IHistoricalObject>
        get() = list.toList()

    val listOfShown = mutableListOf<IHistoricalObject>()

    val routes: List<Route>
        get() = list.filterIsInstance<Route>()

    val places: List<Place>
        get() = list.filterIsInstance<Place>()

    init {
        addGroup().apply {
            name = GlobalTools.instance.getString(R.string.all_groups_name)
        }
        shownGroups.add(groups[0])
    }

    fun addGroup(): Group {
        groups.add(Group())
        return groups.last()
    }

    fun addRoute(coordinates: List<Coordinate>, groups: List<Group> = emptyList()): Route {
        val newRoute = Route(coordinates)
        list.add(newRoute)
        groups.forEach {
            it.historicalObjects.add(newRoute)
            newRoute.groups.add(it)
        }

        // TODO
        this.groups[0].historicalObjects.add(newRoute)
        newRoute.groups.add(this.groups[0])

        return newRoute
    }

    fun addPlace(coordinate: Coordinate, groups: List<Group> = emptyList()): Place {
        val newPlace = Place(coordinate)
        list.add(newPlace)
        groups.forEach {
            it.historicalObjects.add(newPlace)
            newPlace.groups.add(it)
        }

        // TODO
        this.groups[0].historicalObjects.add(newPlace)
        newPlace.groups.add(this.groups[0])

        return newPlace
    }

    fun showAll(){
        list.forEach { it.show() }
    }

    fun hideAll(){
        list.forEach { it.hide(); println(it) }
    }

    fun updateShown() {
        listOfShown.clear()
        when (shownType) {
            IHistoricalObject::class.java -> {
                listOfShown += list
            }
            Route::class.java -> {
                listOfShown += routes
            }
            Place::class.java -> {
                listOfShown += places
            }
        }

        listOfShown.removeIf { !it.groups.containsAll(shownGroups) }
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