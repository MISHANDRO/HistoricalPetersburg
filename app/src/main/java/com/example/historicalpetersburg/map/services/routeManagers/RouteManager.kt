package com.example.historicalpetersburg.map.services.routeManagers

import com.example.historicalpetersburg.R
import com.example.historicalpetersburg.map.MapManager
import com.example.historicalpetersburg.map.entities.Group
import com.example.historicalpetersburg.map.entities.Route
import com.example.historicalpetersburg.map.models.Coordinate
import com.example.historicalpetersburg.map.services.factories.IRouteFactory

class RouteManager : IRouteManager {

    override val selectedRoutes = mutableListOf<Route>()

    override val groups = mutableListOf<Group>()
    override val routes = mutableListOf<Route>()

    lateinit var routeFactory: IRouteFactory
        private set

    init {
        addGroup().apply {
            name = MapManager.instance.activity.getString(R.string.all_groups_name)
        }
    }

    fun initialize(routeFactory: IRouteFactory) {
        this.routeFactory = routeFactory // TODO
    }

    override fun addGroup(): Group {
        val id: Int = groups.size
        val group = Group(id)

        groups.add(group)
        return group
    }

    override fun addRoute(coordinates: List<Coordinate>, groupIds: List<Int>): Route
    {
        val id: Int = routes.size
        val route = routeFactory.create(id, coordinates, groupIds)
        route.groupIds.add(0)

        routes.add(route)
        getGroupById(0)?.routes?.add(route)

        for (groupId in groupIds) {
            getGroupById(groupId)?.routes?.add(route)
        }

        return route
    }

    override fun getAllGroups(): List<Group> {
        return groups
    }

    override fun getAllRoutes(): List<Route> {
        return routes
    }

    override fun getGroupById(id: Int): Group? {
        return groups.find { it.id == id }
    }

    override fun getRouteById(id: Int): Route? {
        return routes.find { it.id == id } // TODO а мож не надо хранить?
    }

    override fun selectAll() {
        selectRoutes(routes)
    }

    override fun selectIntersectionOfGroupsById(ids: List<Int>) {
        val cur = routes.filter {
            it.groupIds.containsAll(ids)
        }

        selectRoutes(cur)
    }

    override fun selectGroupsById(ids: List<Int>) {
        val cur = routes.filter { route ->
            route.groupIds.any {
                ids.contains(it)
            }
        }

        selectRoutes(cur)
    }

    override fun selectRoutesById(ids: List<Int>) {
        val cur = routes.filter {
            ids.contains(it.id)
        }

        selectRoutes(cur)
    }

    override fun selectRoutes(routes: List<Route>) {
        hideAll()
        selectedRoutes.clear()

        val cur = mutableListOf<Coordinate>()
        routes.forEach {
            it.show()
            cur += it.coordinates
            selectedRoutes += it
        }

        MapManager.instance.map.zoom(cur)
    }

    override fun selectGroups(groups: List<Group>) {
        val cur = mutableListOf<Route>()
        groups.forEach {
            cur += it.routes
        }

        selectRoutes(cur)
    }

    override fun hideAll() {
        for (route in routes) {
            route.hide()
        }
    }

    override fun hideGroupsById(ids: List<Int>) {
        groups.forEach {
            if (ids.contains(it.id)) {
                it.hide()
            }
        }
    }

    override fun hideRoutesById(ids: List<Int>) {
        routes.forEach {
            if (ids.contains(it.id)) {
                it.line.hide()
            }
        }
    }

    override fun showAll() {
        for (route in routes) {
            route.line.show()
        }
    }

    override fun showGroupsById(ids: List<Int>) {
        groups.forEach {
            if (ids.contains(it.id)) {
                it.show()
            }
        }
    }

    override fun showRoutesById(ids: List<Int>) {
        routes.forEach {
            if (ids.contains(it.id)) {
                it.line.show()
            }
        }
    }

    override fun returnSelectedRoutes() {
        if (selectedRoutes.isEmpty()) {
            selectAll()
            return
        }

        val cur = mutableListOf<Coordinate>()
        selectedRoutes.forEach {
            it.show()
            cur += it.coordinates
        }

        MapManager.instance.map.zoom(cur)
    }
}