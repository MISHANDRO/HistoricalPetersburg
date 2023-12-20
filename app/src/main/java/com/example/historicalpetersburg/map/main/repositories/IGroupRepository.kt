package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.UnionGroup

interface IGroupRepository {
    fun getUnions(count: Int = -1): Array<UnionGroup>
    fun findHistoricalObjects(specification: Specification): List<Product>
}