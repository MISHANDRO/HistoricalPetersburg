package com.example.historicalpetersburg.map.main.repositories

import com.example.historicalpetersburg.map.main.objects.Group
import com.example.historicalpetersburg.map.main.objects.IHistoricalObjectData
import com.example.historicalpetersburg.map.main.objects.UnionGroup

interface IGroupRepository {
    fun getUnions(count: Int = -1): Array<UnionGroup>
    fun objectInGroup(historicalObjectData: IHistoricalObjectData, group: Group): Boolean
//    fun findHistoricalObjects(specification: Specification): List<Product>
}