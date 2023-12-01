package com.example.historicalpetersburg.map.services.routeManagers

import com.example.historicalpetersburg.map.entities.Group
import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate

interface IRouteManager {
    val selectedRoutes: MutableList<Route>

    val groups: MutableList<Group>
    val routes: MutableList<Route>

    fun addGroup(): Group
    fun addRoute(coordinates: List<Coordinate>, groupIds: List<Int> = listOf()): Route

    fun getAllGroups(): List<Group>
    fun getAllRoutes(): List<Route>

    fun getGroupById(id: Int): Group?
    fun getRouteById(id: Int): Route?

    fun selectAll()
    fun selectIntersectionOfGroupsById(ids: List<Int>)
    fun selectGroupsById(ids: List<Int>)
    fun selectRoutesById(ids: List<Int>)

    fun selectRoutes(routes: List<Route>)
    fun selectGroups(groups: List<Group>)

    fun hideAll()
    fun hideGroupsById(ids: List<Int>)
    fun hideRoutesById(ids: List<Int>)

    fun showAll()
    fun showGroupsById(ids: List<Int>)
    fun showRoutesById(ids: List<Int>)

    fun returnSelectedRoutes()
}
