package com.example.historicalpetersburg.map.main

import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.main.filters.IHistoricalObjectFilterChain
import com.example.historicalpetersburg.map.main.models.Coordinate
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData
import com.example.historicalpetersburg.map.main.repositories.IGroupRepository
import com.example.historicalpetersburg.map.main.repositories.IPlaceRepository
import com.example.historicalpetersburg.map.main.repositories.IRouteRepository

class HistoricalObjectManager {

    lateinit var groupRepository : IGroupRepository
    lateinit var routeRepository : IRouteRepository
    lateinit var placeRepository : IPlaceRepository

    var filterChain: IHistoricalObjectFilterChain? = null

    lateinit var listOfAll: List<IHistoricalObjectData>
        private set
    val listOfShown = mutableListOf<IHistoricalObjectData>()

    fun createAll() {
//        for (route in routeRepository.getAllData()) {
//            if (route.coordinates != null) {
//                route.line = MapManager.instance.map.addLine(route.coordinates!!)
//                route.startPlacemark = MapManager.instance.map.addPlacemark(route.coordinates!![0])
//            }
//
//            listOfShown += route
//        }
//        for (place in placeRepository.getAllData()) {
//            if (place.coordinates != null) {
//                place.placemark = MapManager.instance.map.addPlacemark(place.coordinates!![0])
//            }
//
//            listOfShown += place
//        }

        listOfShown += routeRepository.getAllData()
        listOfShown += placeRepository.getAllData()

        listOfShown.sortBy { it.id }
        listOfAll = listOfShown.toList()
        zoomShown()
    }

    fun showAll() {
        listOfAll.forEach { it.visible = true }
    }

    fun hideAll() {
        listOfAll.forEach { it.visible = false }
    }

    fun updateShown() {
        listOfShown.clear() // TODO
        listOfAll.forEach {
            if (filterChain?.isNormal(it) == true) {
                listOfShown += it
            }
        }
    }

    fun zoomShown() {
        val coordinates = mutableListOf<Coordinate>()
        listOfShown.forEach { if (it.coordinates != null) coordinates += it.coordinates!! }

        hideAll()
        listOfShown.forEach { it.visible = true }

        if (coordinates.isEmpty()) return
        MapManager.instance.locationManager.follow = false

        MapManager.instance.map.zoom(coordinates)
    }
}