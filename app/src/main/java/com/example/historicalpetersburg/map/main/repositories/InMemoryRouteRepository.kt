package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.RouteData

class InMemoryRouteRepository : IRouteRepository {
    private val routes = mutableListOf<RouteData>()
    private val groupsUnion: MutableMap<Int, MutableList<Int>> = mutableMapOf()


    override fun drawAll() {

    }
}